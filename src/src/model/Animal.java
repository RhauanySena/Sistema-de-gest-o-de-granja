package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;

public class Animal {
    private int id;
    private String identificacaoUnica; // ex: GRN-001
    private String especie; // ex: Frango, Galinha
    private int idade; // em meses
    private float pesoAtual;
    private String situacaoSaude; // "Saudavel", "Doente", "EmTratamento", "Morto"
    private LocalDate dataNascimento;
    private String lote;
    private boolean ativo; // Para soft delete

    public Animal(int id, String identificacaoUnica, String especie, int idade, float pesoAtual, String situacaoSaude, LocalDate dataNascimento, String lote) {
        this.id = id;
        this.identificacaoUnica = identificacaoUnica;
        this.especie = especie;
        this.idade = idade;
        this.pesoAtual = pesoAtual;
        this.situacaoSaude = situacaoSaude;
        this.dataNascimento = dataNascimento;
        this.lote = lote;
        this.ativo = true;
    }

    public void cadastrar() {
        this.ativo = true;
    }

    public void atualizarPeso(float novoPeso) {
        this.pesoAtual = novoPeso;
    }

    public void atualizarSaude(String novaSituacao) {
        this.situacaoSaude = novaSituacao;
    }

    public void excluir() {
        this.ativo = false; // Soft delete, mantendo os registros para histórico
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIdentificacaoUnica() { return identificacaoUnica; }
    public void setIdentificacaoUnica(String identificacaoUnica) { this.identificacaoUnica = identificacaoUnica; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public float getPesoAtual() { return pesoAtual; }
    public void setPesoAtual(float pesoAtual) { this.pesoAtual = pesoAtual; }

    public String getSituacaoSaude() { return situacaoSaude; }
    public void setSituacaoSaude(String situacaoSaude) { this.situacaoSaude = situacaoSaude; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
