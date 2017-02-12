package com.istavrak.vocabrecommender.handler;

import com.istavrak.vocabrecommender.core.extractor.CoreNlpExtractor;
import com.istavrak.vocabrecommender.core.extractor.StaticPartsExtractor;
import com.istavrak.vocabrecommender.core.recommender.LOVRecommender;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecommendationHandler {

    private static final Logger logger = Logger.getLogger(RecommendationHandler.class.getName());

    /**
     * Handles the generation of recommendations for a target URL.
     * @param url The URL of the target webpage.
     * @param includeStatic Flag to disable the inclusion of terms for static elements.
     * @return A successful response.
     */
    public RecommendationResponse generateRecommendationsFor(String url, Boolean includeStatic) {
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
        List<String> keywords = new ArrayList<>();

        // Extract static parts (images)
        if (includeStatic) {
            StaticPartsExtractor staticExtractor = new StaticPartsExtractor();
            keywords.addAll(staticExtractor.getTokens(page));
        }

        CoreNlpExtractor coreNlpExtractor = new CoreNlpExtractor();
        List<String> nlpTokens = coreNlpExtractor.getTokens(page);
        keywords.addAll(nlpTokens);
        logger.log(Level.INFO, "The extracted keywords using NLP: " + nlpTokens);

        // Generate the recommendation results
        List<Query> results = resultsForKeywords(keywords);

        // Return the results.
        RecommendationResultVocabulary vocab = new RecommendationResultVocabulary(results);
        return vocab;
    }

    /**
     * Handles the generation of recommendations for a list of keywords.
     * @param keywords List of keywords.
     * @return A successful response.
     */
    public RecommendationResponse generateRecommendationsFor(List<String> keywords) {
        // Generate the recommendation results
        List<Query> results = resultsForKeywords(keywords);

        // Return the results.
        RecommendationResultVocabulary vocab = new RecommendationResultVocabulary(results);
        return vocab;
    }

    private List<Query> resultsForKeywords(List<String> keywords) {
        List<Query> finalResults = new ArrayList<>();

        // Generate the static recommendations
        StaticRecommender staticRecommender = new StaticRecommender();
        List<Query> staticResults = staticRecommender.recommendVocabularyFor(keywords);
        if (!staticResults.isEmpty()) {
            finalResults.addAll(staticResults);
        }

        // Generate the LOV recommendations
        LOVRecommender lovRecommender = new LOVRecommender();
        List<Query> lovResults = lovRecommender.recommendVocabularyFor(keywords);
        finalResults.addAll(lovResults);
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
