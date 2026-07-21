package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;

public class VacinaMedicamento {
    private int id;
    private String nome;
    private String tipo; // "Vacina" ou "Medicamento"
    private String lote;
    private LocalDate dataValidade;
    private int quantidadeEstoque;

    public VacinaMedicamento(int id, String nome, String tipo, String lote, LocalDate dataValidade, int quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.lote = lote;
        this.dataValidade = dataValidade;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void atualizarEstoque(int quantidade) {
        this.quantidadeEstoque += quantidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
}
