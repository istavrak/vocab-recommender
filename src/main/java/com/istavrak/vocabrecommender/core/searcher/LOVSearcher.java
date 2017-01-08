package com.istavrak.vocabrecommender.core.searcher;


import com.google.gson.Gson;
import com.istavrak.vocabrecommender.model.lov.Results;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LOVSearcher extends Searcher{
    private static final Logger logger = Logger.getLogger(LOVSearcher.class.getName());
    private static final String LOV_URL = "http://lov.okfn.org/dataset/lov/api/v2/term/search?q=";

    Results invokeViaHttp(String url) {
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
            return gson.fromJson(jsonResult, Results.class);
        }
        return null;
    }

    @Override
    String getApiSearchUrl() {
        return LOV_URL;
    }
}
