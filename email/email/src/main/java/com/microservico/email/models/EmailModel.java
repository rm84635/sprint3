package com.microservico.email.models;

import java.util.HashMap;
import java.util.List;

public class EmailModel {

    protected String chave;

    protected List<String> destinatario;
    protected List<String> comCopia;
    protected HashMap<String, String> dados;
    
    public void setDestinatario(List<String> destinatario) {
        this.destinatario = destinatario;
    }
    public void setComCopia(List<String> comCopia) {
        this.comCopia = comCopia;
    }
    public void setDados(HashMap<String, String> dados) {
        this.dados = dados;
    }
    public List<String> getDestinatario() {
        return destinatario;
    }
    public List<String> getComCopia() {
        return comCopia;
    }
    public HashMap<String, String> getDados() {
        return dados;
    }
    public String getChave() {
        return chave;
    }
    public void setChave(String chave) {
        this.chave = chave;
    }
    
}
