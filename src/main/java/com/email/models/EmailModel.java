package com.email.models;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Definition of email request (payload for POST request)
 */

@Component
public class EmailModel {

    private String toEmail;
    private String fromEmail;
    private String subject;
    private String templateName;

    private Map<String, String> variables;

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "EmailModel{" +
                "toEmail='" + toEmail + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                ", variables=" + variables +
                '}';
    }
}
