package com.istavrak.vocabrecommender.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.istavrak.vocabrecommender.model.vsearch.Query;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecommendationResultVocabulary implements RecommendationResponse, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RecommendationResultVocabulary.class.getName());

    public List<Query> doQuery;

    public RecommendationResultVocabulary() {
    }

    public RecommendationResultVocabulary(List<Query> doQuery) {
        this.doQuery = doQuery;
    }

    @Override
    public boolean getSuccess() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Query query : doQuery) {
            sb.append(query.toString());
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Used by the JSP of the assistant.
     * @return Serialised result to JSON.
     */
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonOutput = null;
        try {
            jsonOutput = mapper.writeValueAsString(doQuery);
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Failed to serialise to JSON.");
        }

        if (jsonOutput != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonOutput);
            jsonOutput = gson.toJson(je);
        }
        return jsonOutput;
    }
}
