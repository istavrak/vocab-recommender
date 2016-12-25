package com.istavrak.vocabrecommender;

import com.istavrak.vocabrecommender.model.RecommendationResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/assistant")
public class AssistantController {
    private final static String VERSION_PROP = "version";
    private final PropertiesLoader propertiesLoader = PropertiesLoader.INSTANCE;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAssistant(ModelMap model) {
        model.addAttribute("version", propertiesLoader.getProperty(VERSION_PROP));
        return "assistant";
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    public String getAssistantHandler(@RequestParam(value = "inputField", required = false) String input,
                                      @RequestParam(value = "searchType", required = false) Integer searchType,
                                      ModelMap model) {

        System.out.println(input);
        System.out.println(searchType);
        RecommendationController controller = new RecommendationController();
        RecommendationResponse response = controller.handleRecommendationRequest(input, null, true);
        model.put("response", response);
        model.addAttribute("version", propertiesLoader.getProperty(VERSION_PROP));
        return "assistant";
    }
}
