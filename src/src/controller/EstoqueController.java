package br.ufpb.dcx.agroplus.controller;

import br.ufpb.dcx.agroplus.model.Estoque;
import br.ufpb.dcx.agroplus.model.VacinaMedicamento;
import br.ufpb.dcx.agroplus.model.AplicacaoProduto;
import br.ufpb.dcx.agroplus.model.Alimento;
import br.ufpb.dcx.agroplus.repository.DadosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstoqueController {
    private DadosRepository repository;

    public EstoqueController() {
        this.repository = DadosRepository.getInstance();
    }

    public List<Estoque> listarEstoqueGeral() {
        return repository.getEstoques();
    }

    public List<VacinaMedicamento> listarMedicamentos() {
        return repository.getVacinasMedicamentos();
    }

    public List<Alimento> listarAlimentos() {
        return repository.getAlimentos();
    }

    public void registrarEntradaInsumo(int estoqueId, float quantidade, String notaFiscal) {
        Estoque e = encontrarEstoquePorId(estoqueId);
        if (e == null) {
            throw new IllegalArgumentException("Insumo de estoque não encontrado.");
        }
        e.registrarEntrada(quantidade, notaFiscal);
        System.out.println("[SUCESSO] Entrada de " + quantidade + " " + e.getUnidadeMedida() + " registrada.");
    }

    public void registrarSaidaInsumo(int estoqueId, float quantidade, String motivo) {
        Estoque e = encontrarEstoquePorId(estoqueId);
        if (e == null) {
            throw new IllegalArgumentException("Insumo de estoque não encontrado.");
        }
        e.registrarSaida(quantidade, motivo);
        System.out.println("[SUCESSO] Saída de " + quantidade + " " + e.getUnidadeMedida() + " registrada.");

        String alerta = e.gerarAlerta();
        if (alerta != null) {
            System.out.println(alerta);
        }
    }

    public void aplicarVacinaMedicamento(int vacinaId, int animalId, String dose, String observacoes) {
        VacinaMedicamento vm = encontrarMedicamentoPorId(vacinaId);
        if (vm == null) {
            throw new IllegalArgumentException("Vacina/Medicamento não encontrado.");
        }

        if (vm.getQuantidadeEstoque() <= 0) {
            throw new IllegalStateException("Estoque zerado para esta vacina/medicamento.");
        }

        vm.atualizarEstoque(-1);

        String nomeUsuario = AuthController.getUsuarioLogado() != null ? 
            AuthController.getUsuarioLogado().getNomeCompleto() : "Sistema";

        AplicacaoProduto aplicacao = new AplicacaoProduto(
            repository.getNextIdAplicacao(),
            animalId,
            vm.getId(),
            dose,
            LocalDate.now(),
            nomeUsuario,
            observacoes
        );
        repository.addAplicacaoProduto(aplicacao);
        System.out.println("[SUCESSO] Aplicação registrada e estoque de " + vm.getNome() + " atualizado.");
    }

    public void registrarConsumoAlimento(int alimentoId, int animalId, float quantidadeKg) {
        Alimento al = encontrarAlimentoPorId(alimentoId);
        if (al == null) {
            throw new IllegalArgumentException("Alimento não encontrado.");
        }

        // Tenta também deduzir do estoque geral de ração se houver correspondência pelo nome
        for (Estoque est : repository.getEstoques()) {
            if (est.getNomeProduto().toLowerCase().contains(al.getNome().toLowerCase()) || 
                al.getNome().toLowerCase().contains(est.getNomeProduto().toLowerCase())) {
                try {
                    est.registrarSaida(quantidadeKg, "Consumo do Animal ID: " + animalId);
                } catch (Exception ex) {
                    System.out.println("[AVISO] Falha ao deduzir do estoque principal: " + ex.getMessage());
                }
            }
        }

        al.consumir(animalId, quantidadeKg);
    }

    public List<String> verificarAlertasEstoque() {
        List<String> alertas = new ArrayList<>();
        for (Estoque e : repository.getEstoques()) {
            String alerta = e.gerarAlerta();
            if (alerta != null) {
                alertas.add(alerta);
            }
        }
        for (VacinaMedicamento vm : repository.getVacinasMedicamentos()) {
            if (vm.getQuantidadeEstoque() <= 5) {
                alertas.add(String.format("[ALERTA] Vacina/Medicamento %s está com estoque baixo! Apenas %d unidades.", 
                    vm.getNome(), vm.getQuantidadeEstoque()));
            }
        }
        return alertas;
    }

    private Estoque encontrarEstoquePorId(int id) {
        for (Estoque e : repository.getEstoques()) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    private VacinaMedicamento encontrarMedicamentoPorId(int id) {
        for (VacinaMedicamento vm : repository.getVacinasMedicamentos()) {
            if (vm.getId() == id) {
                return vm;
            }
        }
        return null;
    }

    private Alimento encontrarAlimentoPorId(int id) {
        for (Alimento al : repository.getAlimentos()) {
            if (al.getId() == id) {
                return al;
            }
        }
        return null;
    }
}
