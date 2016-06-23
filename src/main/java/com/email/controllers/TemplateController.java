package com.email.controllers;

import com.email.template.TemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to show e-mail templates
 */
@Controller
@RequestMapping("/emails")
public class TemplateController {

    private TemplateRenderer templateRenderer;

    @Autowired
    public TemplateController(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
    public @ResponseBody String renderTemplate(HttpServletRequest request) {

        Map<String, String[]> urlParams = request.getParameterMap();

        //get templateName
        String templateName = extractValFromMap("templateName", urlParams);

        //get other template variables as Map<String, String>
        Map<String, String> urlVarMap = extractVariables(urlParams);

        //create HTML string from templateName and template variables
        String html = templateRenderer.createHTML(templateName, urlVarMap);

        return html;
    }

    private String extractValFromMap(String key, Map<String, String[]> urlParams) {
        return urlParams.get(key)[0];
    }

    private Map<String, String> extractVariables(Map<String, String[]> urlParams) {
        Map<String, String> urlParamMap = new HashMap<>();

        for (String key : urlParams.keySet()) {
            urlParamMap.put(key, urlParams.get(key)[0]);
        }

        return urlParamMap;
    }
}
