package com.istavrak.vocabrecommender;

import com.istavrak.vocabrecommender.model.RecommendationFailure;
import com.istavrak.vocabrecommender.model.RecommendationResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AssistantController {
    private final static String VERSION_PROP = "version";
    private final PropertiesLoader propertiesLoader = PropertiesLoader.INSTANCE;

    @RequestMapping(value = "/assistant", method = RequestMethod.GET)
    public String getAssistant(ModelMap model) {
        model.addAttribute("version", propertiesLoader.getProperty(VERSION_PROP));
        return "assistant";
    }

    @RequestMapping(value = "/assistant/recommend", method = RequestMethod.POST)
    public String getAssistantHandler(@RequestParam(value = "inputField", required = false) String input,
                                      @RequestParam(value = "searchType", required = false) Integer searchType,
                                      @RequestParam(value = "static", required = false, defaultValue = "false") Boolean includeStatic,
                                      ModelMap model) {
        RecommendationController controller = new RecommendationController();
        RecommendationResponse response = null;
        if (searchType == 0) {
            response = controller.handleRecommendationRequest(null, input, includeStatic);
        } else if(searchType ==1) {
            response = controller.handleRecommendationRequest(input, null, includeStatic);
        }
        if (input == null || input.isEmpty()) {
            response = new RecommendationFailure("Provide your input via the form.");
        }
        model.put("response", response);
        model.addAttribute("version", propertiesLoader.getProperty(VERSION_PROP));
        return "assistant";
    }
}
