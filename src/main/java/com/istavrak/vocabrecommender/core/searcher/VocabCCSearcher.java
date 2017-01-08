package com.istavrak.vocabrecommender.core.searcher;

import com.istavrak.vocabrecommender.model.vocabcc.Description;
import com.istavrak.vocabrecommender.model.vocabcc.Results;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabCCSearcher extends Searcher {
    private static final Logger logger = Logger.getLogger(VocabCCSearcher.class.getName());
    private static final String VOCAB_URL = "http://vocab.cc/v/search?query=";

    Results invokeViaHttp(String url) {
        String rdfResult = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "application/rdf+xml");
            HttpResponse result = httpClient.execute(request);
            rdfResult = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to communicate with the Vocab.cc search endpoint, url: " + url);
            return null;
        }

        JAXBContext jaxbContext = null;
        StringReader reader = new StringReader(rdfResult);
        try {
            jaxbContext = JAXBContext.newInstance(Results.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Results results =  (Results) jaxbUnmarshaller.unmarshal(reader);
            postProcess(results);
            return results;
        } catch (JAXBException e) {
            logger.log(Level.WARNING, "Failed to unmarshal the result of Vocab.cc, exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Removes the result from the response that corresponds to the summary of the search results.
     * @param results
     */
    private void postProcess(Results results) {
        Iterator itTerms = results.getDescriptions().iterator();
        while (itTerms.hasNext()) {
            Description term = (Description) itTerms.next();
            if (term.usedAsClass == null && term.usedAsProperty == null) {
                itTerms.remove();
            }
        }
    }

    @Override
    String getApiSearchUrl() {
        return VOCAB_URL;
    }
}
