package br.ufpb.dcx.agroplus.model;

public class Estoque {
    private int id;
    private int produtoId;
    private String nomeProduto;
    private float quantidadeAtual;
    private String unidadeMedida; // kg, L, unidade, etc.
    private float estoqueMinimo;
    private float estoqueMaximo;
    private String localArmazenamento;

    public Estoque(int id, int produtoId, String nomeProduto, float quantidadeAtual, String unidadeMedida, float estoqueMinimo, float estoqueMaximo, String localArmazenamento) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidadeAtual = quantidadeAtual;
        this.unidadeMedida = unidadeMedida;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueMaximo = estoqueMaximo;
        this.localArmazenamento = localArmazenamento;
    }

    public void registrarEntrada(float quantidade, String notaFiscal) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }
        if (this.quantidadeAtual + quantidade > this.estoqueMaximo) {
            System.out.println("[AVISO] A entrada excede o estoque máximo (" + this.estoqueMaximo + "). Ajustando quantidade.");
        }
        this.quantidadeAtual += quantidade;
    }

    public void registrarSaida(float quantidade, String motivo) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de saída deve ser maior que zero.");
        }
        if (this.quantidadeAtual - quantidade < 0) {
            throw new IllegalStateException("Saldo de estoque insuficiente para realizar esta saída.");
        }
        this.quantidadeAtual -= quantidade;
    }

    public boolean verificarEstoqueBaixo() {
        return this.quantidadeAtual <= this.estoqueMinimo;
    }

    public String gerarAlerta() {
        if (verificarEstoqueBaixo()) {
            return String.format("[ALERTA] Estoque de %s está baixo! Atual: %.2f %s (Mínimo: %.2f %s)", 
                this.nomeProduto, this.quantidadeAtual, this.unidadeMedida, this.estoqueMinimo, this.unidadeMedida);
        }
        return null;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public float getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(float quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public float getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(float estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public float getEstoqueMaximo() { return estoqueMaximo; }
    public void setEstoqueMaximo(float estoqueMaximo) { this.estoqueMaximo = estoqueMaximo; }

    public String getLocalArmazenamento() { return localArmazenamento; }
    public void setLocalArmazenamento(String localArmazenamento) { this.localArmazenamento = localArmazenamento; }
}
