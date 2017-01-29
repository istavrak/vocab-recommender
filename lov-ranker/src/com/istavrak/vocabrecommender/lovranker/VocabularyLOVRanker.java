package com.istavrak.vocabrecommender.lovranker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.istavrak.vocabrecommender.PropertiesLoader;
import com.istavrak.vocabrecommender.lovranker.model.Vocab;
import com.istavrak.vocabrecommender.lovranker.model.VocabAggregation;
import com.istavrak.vocabrecommender.lovranker.model.VocabDetails;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabularyLOVRanker {
    private static final Logger logger = Logger.getLogger(VocabularyLOVRanker.class.getName());
    private static final String VOCAB_DETAILS_URL = "http://lov.okfn.org/dataset/lov/api/v2/vocabulary/info?vocab=";
    private static final String DATA_FOLDER = "data/vocabs/";
    private static final PropertiesLoader propertiesLoader = PropertiesLoader.INSTANCE;
    private static List<Vocab> lovVocabs;
    private static HashMap<String, List<String>> vocabAuthorMap = new HashMap<>();
    private static HashMap<String, List<String>> authorVocabMap;
    private static HashMap<String, BigDecimal> authorVocabScoreMap = new HashMap<>();
    private static HashMap<String, BigDecimal> authorScoreMap = new HashMap<>();
    private static HashMap<String, BigDecimal> vocabBacklinkScore = new HashMap<>();
    private static HashMap<String, BigDecimal> vocabFinalScore = new HashMap<>();

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
    private static int aggregateVocabularies() {
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

    private static void rankVocabularies() {
        // prefix;incoming;outgoing;[author ids]
        // acco;2;11;54b2be038433ca9ccf1c0f21

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data/lovVocabs_aggregated_20170122.txt"))));
            String line = null;

            while((line = in.readLine()) != null) {
                String[] vocabDetails = line.split(";");
                Integer backlinks = Integer.parseInt(vocabDetails[1]);
                Integer vocabularies = Integer.parseInt(propertiesLoader.getProperty("vocabularies"));
                Double backlinkScore = backlinks.doubleValue() / vocabularies;
                BigDecimal backclinkScorePrecision = new BigDecimal(backlinkScore);
                backclinkScorePrecision = backclinkScorePrecision.setScale(2, BigDecimal.ROUND_UP);
                vocabBacklinkScore.put(vocabDetails[0], backclinkScorePrecision);
                //System.out.println(vocabDetails[0] + "," + backclinkScorePrecision);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to read the aggregated vocabulary data, exception: " + e.getMessage());
        }
    }

    private static void aggregateAuthors() {
        // prefix;incoming;outgoing;[author ids]
        // acco;2;11;54b2be038433ca9ccf1c0f21
        authorVocabMap = new HashMap<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data/lovVocabs_aggregated_20170122.txt"))));
            String line;
            while((line = in.readLine()) != null) {
                String[] vocabDetails = line.split(";");
                if (vocabDetails.length == 4) {
                    List<String> authors = Arrays.asList(vocabDetails[3].split(","));
                    String vocab = vocabDetails[0];
                    if (!authors.isEmpty()) {
                        vocabAuthorMap.put(vocab, authors);
                    }
                    for (String authorId : authors) {
                        if (authorVocabMap.containsKey(authorId)) {
                            List<String> vocabs = authorVocabMap.get(authorId);
                            vocabs.add(vocab);
                        } else {
                            List<String> vocabs = new ArrayList<>();
                            vocabs.add(vocab);
                            authorVocabMap.put(authorId, vocabs);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to read the aggregated vocabulary data, exception: " + e.getMessage());
        }

        for (Map.Entry entry : authorVocabMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (String vocab : (List<String>) entry.getValue()) {
                sb.append(vocab);
                sb.append(",");
            }
            //System.out.println(entry.getKey() + ";" + sb.toString().substring(0, sb.length() - 1));
        }
    }

    public static void main(String[] args) {
        downloadVocabs();
        aggregateVocabularies();
        rankVocabularies();
        aggregateAuthors();
        rankVocabs();
    }

    private static void rankVocabs() {
        for (Map.Entry entry : authorVocabMap.entrySet()) {
            authorScoreMap.put((String)entry.getKey(), rankAuthor((String) entry.getKey()));
        }

        for (Vocab vocab : lovVocabs) {
            authorVocabScoreMap.put(vocab.getPrefix(), rankVocabByAuthor(vocab));
            vocabFinalScore.put(vocab.getPrefix(), rankVocab(vocab));
        }
    }

    /**
     * Calculates the author's score by taking in consideration
     * the VR scores for the vocabularies that the author has contributed.
     * Uses the formula: S_A = (1 / |V_A|) * SUM (VR(Vi), i=1; i<=m; i++
     * @param author The id of the author under question.
     * @return The score for the given author.
     */
    private static BigDecimal rankAuthor(String author) {
        // There is no author without at least one vocab.
        List<String> vocabsAuthor = authorVocabMap.get(author);
        BigDecimal authorScore = BigDecimal.ONE.divide(new BigDecimal(Math.abs(vocabsAuthor.size())), 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal sumScoreVocabsAuthor = BigDecimal.ZERO;

        // Adding all the corresponding scores of the author's vocabularies.
        for (String vocabPrefix : vocabsAuthor) {
            sumScoreVocabsAuthor = sumScoreVocabsAuthor.add(vocabBacklinkScore.get(vocabPrefix));
        }
        authorScore = authorScore.multiply(sumScoreVocabsAuthor);
        return authorScore;
    }

    /**
     * Calculates the vocab's score by taking in consideration
     * the author scores for the various authors that contribute to the given vocabulary.
     * Uses the formula: S_V = (1 / |A_V|) * SUM (S_A(Ai), i=1; i<=n; i++
     * @param vocab The vocabulary under question.
     * @return The score of the vocabulary given the authors of it.
     */
    private static BigDecimal rankVocabByAuthor(Vocab vocab) {
        List<String> authors = new ArrayList<>();
        if (vocabAuthorMap.containsKey(vocab.getPrefix())) {
            authors = vocabAuthorMap.get(vocab.getPrefix());
        }

        BigDecimal scoreSv = BigDecimal.ZERO;
        if (authors.size() > 0) {
            scoreSv = BigDecimal.ONE.divide(new BigDecimal(Math.abs(authors.size())), 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumAuthorScores = BigDecimal.ZERO;

            // Adding all the author scores.
            for (String author : authors) {
                // There is no author without at least one vocab.
                BigDecimal authorScore = authorScoreMap.get(author);
                sumAuthorScores = sumAuthorScores.add(authorScore);
            }
            scoreSv = scoreSv.multiply(sumAuthorScores);
        }
        return scoreSv;
    }

    /**
     * Simply adds the backlink score with the author based one:
     * VSR = VR + SV
     * @param vocab The vocab under question
     * @return the final ranking score of the vocab
     */
    private static BigDecimal rankVocab(Vocab vocab) {
        BigDecimal sV = authorVocabScoreMap.get(vocab.getPrefix());
        BigDecimal vR = vocabBacklinkScore.get(vocab.getPrefix());
        return vR.add(sV);
    }
}
