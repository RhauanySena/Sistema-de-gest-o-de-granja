package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;

public class Alimento {
    private int id;
    private String nome;
    private String tipo; // "Ração", "Suplemento" ou "Mineral"
    private String fornecedor;
    private LocalDate validade;
    private float precoUnitario;

    public Alimento(int id, String nome, String tipo, String fornecedor, LocalDate validade, float precoUnitario) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.fornecedor = fornecedor;
        this.validade = validade;
        this.precoUnitario = precoUnitario;
    }

    public void consumir(int animalId, float quantidade) {
        // Registra o consumo
        System.out.println("[INFO] Animal ID " + animalId + " consumiu " + quantidade + " kg do alimento " + this.nome);
    }

    public void atualizarPreco(float novoPreco) {
        if (novoPreco < 0) {
            throw new IllegalArgumentException("O preço unitário não pode ser negativo.");
        }
        this.precoUnitario = novoPreco;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public float getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(float precoUnitario) { this.precoUnitario = precoUnitario; }
}
