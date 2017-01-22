package com.istavrak.vocabrecommender.lovranker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.istavrak.vocabrecommender.lovranker.model.Vocab;
import com.istavrak.vocabrecommender.lovranker.model.VocabAggregation;
import com.istavrak.vocabrecommender.lovranker.model.VocabDetails;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabularyLOVRanker {
    private static final Logger logger = Logger.getLogger(VocabularyLOVRanker.class.getName());
    private static final String VOCAB_DETAILS_URL = "http://lov.okfn.org/dataset/lov/api/v2/vocabulary/info?vocab=";
    private static final String DATA_FOLDER = "data/vocabs/";
    private static List<Vocab> lovVocabs;

    private static List<Vocab> vocabsLoader() {
        InputStream input = null;
        try {
            input = new FileInputStream(new File("data/lovVocabs_20170122.json"));
            ObjectMapper mapper = new ObjectMapper();
            Vocab[] myObjects = mapper.readValue(input, Vocab[].class);
            lovVocabs = Arrays.asList(myObjects);
            return lovVocabs;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to use the vocabularies JSON file, exception: " + e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Collections.emptyList();
    }

    private static VocabDetails vocabDetailsSearcher(String url) {
        String jsonResult = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            jsonResult = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to communicate with the LOV search endpoint, url: " + url);
        }

        if (jsonResult != null && !jsonResult.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(jsonResult, VocabDetails.class);
        }
        return null;
    }

    private static boolean vocabDetailsDownloader(String vocabPrefix) {
        boolean vocabExists = new File(DATA_FOLDER + vocabPrefix + ".json").exists();

        if (vocabExists) {
            return false;
        }

        String jsonResult = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(VOCAB_DETAILS_URL + vocabPrefix);
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            jsonResult = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to communicate with the LOV search endpoint for " + vocabPrefix);
        }

        if (jsonResult != null && !jsonResult.isEmpty()) {
            OutputStream output = null;
            Writer writer = null;
            try {
                output = new FileOutputStream(new File(DATA_FOLDER + vocabPrefix + ".json"));
                writer = new BufferedWriter(new OutputStreamWriter(output));
                writer.write(jsonResult);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to write the LOV vocabulary " + vocabPrefix);
                return false;
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return true;
    }

    private static VocabDetails vocabDetailsLoader(String vocabPrefix) {
        InputStream input = null;
        try {
            input = new FileInputStream(new File(DATA_FOLDER + vocabPrefix + ".json"));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(input, VocabDetails.class);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to use the vocabulary JSON file, exception: " + e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static void downloadVocabs(){
        vocabsLoader();
        for (Vocab vocab : lovVocabs) {
            if (vocab != null && !vocab.getPrefix().isEmpty()) {
                boolean vocabExists = new File(DATA_FOLDER + vocab.getPrefix() + ".json").exists();
                if (vocabExists) {
                    continue;
                }

                try {
                    Thread.sleep((long)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Received an InterruptedException while waiting for some random time up to 1sec: " + e.getMessage());
                }
                System.out.println(vocab.getPrefix() + ": " + vocabDetailsDownloader(vocab.getPrefix()));
            }
        }
    }

    /**
     * Returns the number of vocabularies that have been aggregated successfully.
     */
    public static int aggregateVocabularies() {
        List<VocabAggregation> aggregations = new ArrayList<>();

        HashMap<String, Integer> incomingLinks = new HashMap<>();

        for (Vocab vocab : lovVocabs) {
            VocabDetails details = vocabDetailsLoader(vocab.getPrefix());
            if (details == null || details.getLatestVersion() == null) {
                continue;
            }
            for (String outgoingUri : details.getLatestVersion().getOutgoingLinks()) {
                if (outgoingUri.charAt(outgoingUri.length() - 1) == '#'
                        || outgoingUri.charAt(outgoingUri.length() - 1) == '/') {
                    outgoingUri = outgoingUri.substring(0, outgoingUri.length() - 1);
                }
                if (incomingLinks.containsKey(outgoingUri)) {
                    incomingLinks.put(outgoingUri, incomingLinks.get(outgoingUri) + 1);
                } else {
                    incomingLinks.put(outgoingUri, 1);
                }
            }
        }

        for (Vocab vocab : lovVocabs) {
            VocabDetails details = vocabDetailsLoader(vocab.getPrefix());
            String namespace = vocab.getNamespace();
            if (namespace.charAt(namespace.length() - 1) == '#'
                    || namespace.charAt(namespace.length() - 1) == '/') {
                namespace = namespace.substring(0, namespace.length() - 1);
            }
            Integer incoming = incomingLinks.get(namespace);
            if (details != null && details.getLatestVersion() != null) {
                VocabAggregation vocabAggregation = new VocabAggregation(
                        vocab.getPrefix(),
                        incoming != null ? incoming : 0,
                        details.getLatestVersion().getOutgoingLinks().size(),
                        details.getContributorIds());
                aggregations.add(vocabAggregation);
                //System.out.println(vocabAggregation.toString());
            }
        }
        return aggregations.size();
    }



    public static void main(String[] args) {
        downloadVocabs();
        aggregateVocabularies();
    }
}
