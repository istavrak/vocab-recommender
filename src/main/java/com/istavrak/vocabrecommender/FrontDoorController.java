package com.istavrak.vocabrecommender;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FrontDoorController {
    private final static String VERSION_PROP = "version";
    private final PropertiesLoader propertiesLoader = PropertiesLoader.INSTANCE;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getFrontDoor(ModelMap model) {
        model.addAttribute("version", propertiesLoader.getProperty(VERSION_PROP));
        return "frontdoor";
    }
}
