package com.istavrak.vocabrecommender;

import com.istavrak.vocabrecommender.handler.RecommendationHandler;
import com.istavrak.vocabrecommender.model.RecommendationFailure;
import com.istavrak.vocabrecommender.model.RecommendationResponse;
import com.istavrak.vocabrecommender.model.RecommendationResultVocabulary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RecommendationController {

    private static final Logger logger = Logger.getLogger(RecommendationController.class.getName());

    @RequestMapping(value = "/recommendation", method = RequestMethod.GET, produces = "application/json")
    public RecommendationResponse handleRecommendationRequest(
            @RequestParam(value = "url", required = false) String targetUrl,
            @RequestParam(value = "query", required = false) String queryKeywords,
            @RequestParam(value = "static", required = false) Boolean includeStatic) {

        // TODO validate the url

        RecommendationResponse response;
        RecommendationHandler handler = new RecommendationHandler();
        if (targetUrl != null && queryKeywords == null) {
            response = handler.generateRecommendationsFor(targetUrl, includeStatic);
            logger.log(Level.INFO, "Starting recommendation for url: " + targetUrl);
            if (response instanceof RecommendationResultVocabulary) {
                RecommendationResultVocabulary vocabResult = (RecommendationResultVocabulary) response;
                logger.log(Level.INFO, "Result recommendation for " + targetUrl + ": " + vocabResult);
            }
        } else if (targetUrl == null && queryKeywords != null) {
            List<String> keywordsSplitted = Arrays.asList(queryKeywords.split(","));
            // Arrays.asList returns a fixed-size list. Cannot remove elements later from it.
            // For this reason we add the keywords to a new ArrayList.
            List<String> keywords = new ArrayList<>();
            keywords.addAll(keywordsSplitted);
            if (keywords.size() == 0 || keywords.size() > 10) {
                response = new RecommendationFailure("The number of keywords at the ?query= parameter is not valid." +
                        "The correct number falls in the range [1,10].");
                return response;
            }
            logger.log(Level.INFO, "Starting recommendation for keywords: " + keywords.toString());
            response = handler.generateRecommendationsFor(keywords);
            if (response instanceof RecommendationResultVocabulary) {
                RecommendationResultVocabulary vocabResult = (RecommendationResultVocabulary) response;
                logger.log(Level.INFO, "Result recommendation for " + keywords.toString() + ": " + vocabResult);
            }
        } else {
            response = new RecommendationFailure("Please specify either a URL with the " +
                    "?url= parameter or a comma separated list of keywords with the ?query= parameter.");
        }
        return response;
    }

    @RequestMapping(value = "/recommendation/static", method = RequestMethod.GET, produces = "application/json")
    public RecommendationResponse handleStaticRequest() {
        RecommendationResponse response;

        response = new RecommendationFailure("Under construction");
        return response;
    }
}
