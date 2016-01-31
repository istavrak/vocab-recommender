package com.istavrak.vocabrecommender;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FrontDoorController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getFrontDoor(ModelMap model) {

        model.addAttribute("version", "v1");
        return "frontdoor";
    }
}
