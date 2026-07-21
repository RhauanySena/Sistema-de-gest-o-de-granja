package br.ufpb.dcx.agroplus.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Relatorio {
    private int id;
    private String tipo; // "Producao", "Estoque", "Saude", "Desempenho"
    private LocalDate dataGeracao;
    private Map<String, Object> parametros;
    private byte[] conteudo;

    public Relatorio(int id, String tipo, LocalDate dataGeracao, Map<String, Object> parametros, byte[] conteudo) {
        this.id = id;
        this.tipo = tipo;
        this.dataGeracao = dataGeracao;
        this.parametros = parametros;
        this.conteudo = conteudo;
    }

    public static File gerar(String tipo, Map<String, Object> parametros, byte[] conteudo) {
        File temp = new File("relatorio_" + tipo + "_" + LocalDate.now() + ".txt");
        try (FileWriter fw = new FileWriter(temp)) {
            fw.write("=== RELATÓRIO AGROPLUS: " + tipo.toUpperCase() + " ===\n");
            fw.write("Data de Geração: " + LocalDate.now() + "\n");
            fw.write("Parâmetros: " + parametros.toString() + "\n\n");
            fw.write(new String(conteudo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void exportarPDF() {
        System.out.println("[INFO] Relatório exportado com sucesso para PDF (Simulado: relatorio_" + this.tipo + "_" + this.id + ".pdf)");
    }

    public void exportarExcel() {
        System.out.println("[INFO] Relatório exportado com sucesso para Excel (Simulado: relatorio_" + this.tipo + "_" + this.id + ".xlsx)");
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDate dataGeracao) { this.dataGeracao = dataGeracao; }

    public Map<String, Object> getParametros() { return parametros; }
    public void setParametros(Map<String, Object> parametros) { this.parametros = parametros; }

    public byte[] getConteudo() { return conteudo; }
    public void setConteudo(byte[] conteudo) { this.conteudo = conteudo; }
}
