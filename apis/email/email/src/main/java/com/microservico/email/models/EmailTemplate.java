package com.microservico.email.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("templates")
public class EmailTemplate {
    
    @Id
    private Long id;

    private String chave;

    private String assunto;

    private String texto;

    private String remetente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public void construirEmail(String chave, String valor) {
        this.assunto = this.assunto.replace(chave, valor);
        this.texto = this.texto.replace("<"+chave+">", valor);
    }

}
