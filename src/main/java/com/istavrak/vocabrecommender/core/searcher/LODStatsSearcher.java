package com.istavrak.vocabrecommender.core.searcher;

import com.google.gson.Gson;
import com.istavrak.vocabrecommender.model.lodstats.Results;
import com.istavrak.vocabrecommender.model.lodstats.Term;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LODStatsSearcher extends Searcher {
    private static final Logger logger = Logger.getLogger(LODStatsSearcher.class.getName());
    private static final String LODSTATS_URL = "http://stats.lod2.eu/rdf_classes?search=";

    Results invokeViaHttp(String url) {
        String jsonResult = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "application/json");
            HttpResponse result = httpClient.execute(request);
            jsonResult = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to communicate with the LODStats search endpoint, url: " + url);
        }

        if (jsonResult != null && !jsonResult.isEmpty()) {
            Gson gson = new Gson();
            Term[] termsArray = gson.fromJson(jsonResult, Term[].class);
            return new Results(Arrays.asList(termsArray));
        }
        return null;
    }

    @Override
    String getApiSearchUrl() {
        return LODSTATS_URL;
    }
}
