package com.istavrak.vocabrecommender;

import com.istavrak.vocabrecommender.handlers.RecommendationHandler;
import com.istavrak.vocabrecommender.model.RecommendationFailure;
import com.istavrak.vocabrecommender.model.RecommendationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RecommendationController {

    @RequestMapping(value="/recommendation", method= RequestMethod.GET, produces = "application/json")
    public RecommendationResponse handleRecommendationRequest(
            @RequestParam(value="url", required = false) String targetUrl,
            @RequestParam(value="query", required = false) String queryKeywords) {

        // TODO validate the url
        // TODO add logging
        RecommendationResponse response;
        RecommendationHandler handler = new RecommendationHandler();
        if (targetUrl != null && queryKeywords == null) {
            response = handler.generateRecommendationsFor(targetUrl);
        } else if (targetUrl == null && queryKeywords != null) {
            List<String> keywords = Arrays.asList(queryKeywords.split(","));
            if (keywords.size() == 0 || keywords.size() > 10) {
                response = new RecommendationFailure("The number of keywords at the ?query= parameter is not valid." +
                        "The correct number falls in the range [1,10].");
                return response;
            }
            response = handler.generateRecommendationsFor(keywords);
        } else {
            response = new RecommendationFailure("Please specify either a URL with the " +
                    "?url= parameter or a comma separated list of keywords with the ?query= parameter.");
        }

        return response;
    }
}
