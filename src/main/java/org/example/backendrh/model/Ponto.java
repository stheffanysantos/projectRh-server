package org.example.backendrh.model;

import java.util.Date;

public class Ponto {
    private String id;
    private String funcionarioId;
    private Date dataHora; // Registra data e hora do ponto

    public Ponto() {}

    public Ponto(String funcionarioId, Date dataHora) {
        this.funcionarioId = funcionarioId;
        this.dataHora = dataHora;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(String funcionarioId) { this.funcionarioId = funcionarioId; }

    public Date getDataHora() { return dataHora; }
    public void setDataHora(Date dataHora) { this.dataHora = dataHora; }
}
