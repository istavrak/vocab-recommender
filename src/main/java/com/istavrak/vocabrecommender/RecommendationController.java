package com.istavrak.vocabrecommender;

import com.istavrak.vocabrecommender.model.RecommendationFailure;
import com.istavrak.vocabrecommender.model.RecommendationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    @RequestMapping(value="/recommendation", method= RequestMethod.GET, produces = "application/json")
    public RecommendationResponse handleRecommendationRequest(
            @RequestParam(value="url") String targetUrl) {
        return new RecommendationFailure("Under construction.");
    }
}
