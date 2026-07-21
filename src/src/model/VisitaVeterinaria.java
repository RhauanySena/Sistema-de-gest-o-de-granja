package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;
import java.util.List;

public class VisitaVeterinaria {
    private int id;
    private LocalDate dataVisita;
    private int funcionarioId;
    private String objetivo;
    private String observacoes;
    private List<Integer> animaisAtendidos;

    public VisitaVeterinaria(int id, LocalDate dataVisita, int funcionarioId, String objetivo, String observacoes, List<Integer> animaisAtendidos) {
        this.id = id;
        this.dataVisita = dataVisita;
        this.funcionarioId = funcionarioId;
        this.objetivo = objetivo;
        this.observacoes = observacoes;
        this.animaisAtendidos = animaisAtendidos;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDataVisita() { return dataVisita; }
    public void setDataVisita(LocalDate dataVisita) { this.dataVisita = dataVisita; }

    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public List<Integer> getAnimaisAtendidos() { return animaisAtendidos; }
    public void setAnimaisAtendidos(List<Integer> animaisAtendidos) { this.animaisAtendidos = animaisAtendidos; }
}
