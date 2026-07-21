package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;

public class RegistroPeso {
    private int id;
    private int animalId;
    private float peso;
    private LocalDate dataPesagem;

    public RegistroPeso(int id, int animalId, float peso, LocalDate dataPesagem) {
        this.id = id;
        this.animalId = animalId;
        this.peso = peso;
        this.dataPesagem = dataPesagem;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAnimalId() { return animalId; }
    public void setAnimalId(int animalId) { this.animalId = animalId; }

    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }

    public LocalDate getDataPesagem() { return dataPesagem; }
    public void setDataPesagem(LocalDate dataPesagem) { this.dataPesagem = dataPesagem; }
}
