package com.email.controllers;

import com.email.models.EmailModel;
import com.email.template.TemplateRenderer;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * REST controller to send e-mails via sendgrid
 */

@RestController
public class SendEmailController {

    @Value("${sendgrid.API_KEY}")
    private String API_KEY;

    private EmailModel emailModel;
    private TemplateRenderer templateRenderer;

    @Autowired
    public SendEmailController(EmailModel emailModel, TemplateRenderer templateRenderer) {
        this.emailModel = emailModel;
        this.templateRenderer = templateRenderer;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity sendEmail(@RequestBody @Valid EmailModel emailModel) {

        // read request body
        String subject = emailModel.getSubject();
        String toEmail = emailModel.getToEmail();
        String fromEmail = emailModel.getFromEmail();
        String templateName = emailModel.getTemplateName();
        Map<String, String> variables = emailModel.getVariables();

        // create HTML using request body
        String html = templateRenderer.createHTML(templateName, variables);

        // create email
        SendGrid sendGrid = new SendGrid(API_KEY);
        SendGrid.Email email = new SendGrid.Email();
        email.addTo(toEmail);
        email.setFrom(fromEmail);
        email.setSubject(subject);
        email.setHtml(html);

        // send e-mail
        try {
            SendGrid.Response response = sendGrid.send(email);

            // success
            if (response.getStatus()) {
                System.out.println("SUCCESS");
                return new ResponseEntity(HttpStatus.OK);
            }
            // server is fine but there's some issues
            else {
                System.out.println(response.getMessage());
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        // server issues
        catch (SendGridException e) {
            System.out.println(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}