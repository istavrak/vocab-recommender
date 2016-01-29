package com.istavrak.vocabrecommender;

import com.istavrak.vocabrecommender.model.RecommendationFailure;
import com.istavrak.vocabrecommender.model.RecommendationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    private static final String URL_DEFAULT = "http://www.innsbruck.info/en/innsbruck-city/accommodation.html";
    @RequestMapping("/recommendation")
    public RecommendationResponse handleRecommendationRequest(@RequestParam(value="url", defaultValue=URL_DEFAULT) String name) {
        return new RecommendationFailure("Under construction.");
    }
}
