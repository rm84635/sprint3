package com.microservico.email.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservico.email.models.EmailEnviadoModel;
import com.microservico.email.models.EmailModel;
import com.microservico.email.models.EmailTemplate;
import com.microservico.email.repositories.EmailEnviadoRepos;
import com.microservico.email.repositories.TemplateEmailRepos;

@RestController
@RequestMapping("/email")
public class EmailController {
    
    @Autowired
    private TemplateEmailRepos templateRepos;

    @Autowired
    private EmailEnviadoRepos emailEnviadoRepos;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<EmailEnviadoModel> enviar(@RequestBody EmailModel model) {

        if (model.getDados() == null) {
            return new ResponseEntity("Sem dados", HttpStatus.BAD_REQUEST);
        }

        Optional<EmailTemplate> template = templateRepos.obterPorChave(model.getChave());

        if (!template.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        for (String chave : model.getDados().keySet()) {
            template.get().construirEmail(chave, model.getDados().get(chave));
        }

        EmailModel em = new EmailEnviadoModel();
        EmailEnviadoModel emailEnviado = (EmailEnviadoModel) em;
        emailEnviado.setAssunto(template.get().getAssunto());
        emailEnviado.setTexto(template.get().getTexto());
        emailEnviado.setDestinatario(model.getDestinatario());
        emailEnviado.setComCopia(model.getComCopia());
        emailEnviado.setData(java.time.LocalDateTime.now().toString());

        SimpleMailMessage emailMessage = new SimpleMailMessage();

        String[] destinatarios = new String[emailEnviado.getDestinatario().size()];
        destinatarios = emailEnviado.getDestinatario().toArray(destinatarios);

        String[] destComCopia = new String[emailEnviado.getComCopia().size()];
        destComCopia = emailEnviado.getComCopia().toArray(destComCopia);

        try {
            
            emailMessage.setFrom(template.get().getRemetente());
            emailMessage.setTo(destinatarios);
            emailMessage.setCc(destComCopia);
            emailMessage.setSubject(emailEnviado.getAssunto());
            emailMessage.setText(emailEnviado.getTexto());

            emailEnviado.setEnviado(true);

        } catch (Exception e) {
            // TODO: handle exception
            emailEnviado.setEnviado(false);
            emailEnviadoRepos.save(emailEnviado);
            return new ResponseEntity<EmailEnviadoModel>(emailEnviado, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        emailEnviadoRepos.save(emailEnviado);
        return new ResponseEntity(HttpStatus.OK);

    }

}
