package com.istavrak.vocabrecommender.handler;

import com.istavrak.vocabrecommender.core.extractor.StaticPartsExtractor;
import com.istavrak.vocabrecommender.core.recommender.StaticRecommender;
import com.istavrak.vocabrecommender.model.RecommendationFailure;
import com.istavrak.vocabrecommender.model.RecommendationResponse;
import com.istavrak.vocabrecommender.model.RecommendationResultVocabulary;
import com.istavrak.vocabrecommender.model.TargetPage;
import com.istavrak.vocabrecommender.model.vsearch.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationHandler {

    public RecommendationResponse generateRecommendationsFor(String url) {
        // Extract the content of the URL for further analysis.
        TargetPage page;
        try {
            page = generateTargetPage(url);
        } catch (IOException e) {
            return new RecommendationFailure("Failed to parse the URL.");
        }

        if (page == null) {
            return new RecommendationFailure("Failed to extract the content of the URL.");
        }

        // Extract static parts (images)
        StaticPartsExtractor staticExtractor = new StaticPartsExtractor();
        List<String> keywords = staticExtractor.getTokens(page);

        // Generate the recommendation results
        List<Query> results = resultsForKeywords(keywords);

        // Return the results.
        RecommendationResultVocabulary vocab = new RecommendationResultVocabulary(results);
        return vocab;
    }

    public RecommendationResponse generateRecommendationsFor(List<String> keywords) {
        // Generate the recommendation results
        List<Query> results = resultsForKeywords(keywords);

        // Return the results.
        RecommendationResultVocabulary vocab = new RecommendationResultVocabulary(results);
        return vocab;
    }

    private List<Query> resultsForKeywords(List<String> keywords) {
        List<Query> finalResults = new ArrayList<Query>();

        // Generate the static recommendations
        StaticRecommender staticRecommender = new StaticRecommender();
        List<Query> staticResults = staticRecommender.recommendVocabularyFor(keywords);

        finalResults.addAll(staticResults);
        return finalResults;
    }

    public TargetPage generateTargetPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        TargetPage page = new TargetPage();
        page.setPageURL(url);
        page.setPageDocument(doc);

        return page;
    }
}
