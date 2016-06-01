package com.email.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;
import java.util.Map;

/**
 * Created by jkang on 6/1/16.
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

    public String createHTML(String templateName, Map<String, String> vars) {

        Context ctx = new Context(Locale.ENGLISH, vars);

        return springTemplateEngine.process(getPath(templateName), ctx);
    }

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
