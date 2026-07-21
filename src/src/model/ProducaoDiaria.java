package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ProducaoDiaria {
    private int id;
    private LocalDate data;
    private int quantidadeOvos;
    private String lote;
    private String observacoes;

    public ProducaoDiaria(int id, LocalDate data, int quantidadeOvos, String lote, String observacoes) {
        this.id = id;
        this.data = data;
        this.quantidadeOvos = quantidadeOvos;
        this.lote = lote;
        this.observacoes = observacoes;
    }

    public Relatorio getRelatorioProducao() {
        Map<String, Object> params = new HashMap<>();
        params.put("lote", this.lote);
        params.put("data", this.data);
        params.put("quantidadeOvos", this.quantidadeOvos);
        
        String conteudoTexto = String.format(
            "Relatorio de Producao do Lote %s na data %s:\nQuantidade de ovos produzidos: %d\nObservacoes: %s",
            this.lote, this.data, this.quantidadeOvos, this.observacoes
        );

        return new Relatorio(
            this.id,
            "Producao",
            LocalDate.now(),
            params,
            conteudoTexto.getBytes()
        );
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public int getQuantidadeOvos() { return quantidadeOvos; }
    public void setQuantidadeOvos(int quantidadeOvos) { this.quantidadeOvos = quantidadeOvos; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
