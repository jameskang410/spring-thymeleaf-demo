package com.email.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;
import java.util.Map;

/**
 * Template -> HTML
 */

@Component
public class TemplateRenderer {

    // path to template store for e-mails
    @Value("${templates.path}")
    private String emailPath;

    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    public TemplateRenderer(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    /**
     * create HTML with variables to input
     * @param templateName
     * @param vars - map of template variables and values to input
     * @return
     */
    public String createHTML(String templateName, Map<String, String> vars) {

        Context ctx = new Context(Locale.ENGLISH, vars);

        return springTemplateEngine.process(getPath(templateName), ctx);
    }

    /**
     * Create path to template HTML file
     * @param templateName
     * @return path (relative to resources/templates)
     */
    private String getPath(String templateName) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(emailPath)
                .append("/")
                .append(templateName)
                .append("/")
                .append(templateName);

        return buffer.toString();
    }
}
