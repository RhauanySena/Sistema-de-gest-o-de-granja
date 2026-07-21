package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;

public class AplicacaoProduto {
    private int id;
    private int animalId;
    private int produtoId;
    private String dose;
    private LocalDate dataAplicacao;
    private String funcionarioResponsavel;
    private String observacoes;

    public AplicacaoProduto(int id, int animalId, int produtoId, String dose, LocalDate dataAplicacao, String funcionarioResponsavel, String observacoes) {
        this.id = id;
        this.animalId = animalId;
        this.produtoId = produtoId;
        this.dose = dose;
        this.dataAplicacao = dataAplicacao;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAnimalId() { return animalId; }
    public void setAnimalId(int animalId) { this.animalId = animalId; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = dose; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public String getFuncionarioResponsavel() { return funcionarioResponsavel; }
    public void setFuncionarioResponsavel(String funcionarioResponsavel) { this.funcionarioResponsavel = funcionarioResponsavel; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
