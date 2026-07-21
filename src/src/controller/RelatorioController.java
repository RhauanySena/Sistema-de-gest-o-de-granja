package br.ufpb.dcx.agroplus.controller;

import br.ufpb.dcx.agroplus.model.Relatorio;
import br.ufpb.dcx.agroplus.repository.DadosRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioController {
    private DadosRepository repository;

    public RelatorioController() {
        this.repository = DadosRepository.getInstance();
    }

    public List<Relatorio> listarRelatoriosGerados() {
        return repository.getRelatorios();
    }

    public void gerarRelatorio(String tipo, String descricaoAdicional) {
        if (!AuthController.hasPermission("GERAR_RELATORIOS")) {
            throw new SecurityException("Acesso negado: Apenas administradores (patrão) podem gerar relatórios.");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("geradoPor", AuthController.getUsuarioLogado().getNomeCompleto());
        params.put("cargoUsuario", AuthController.getUsuarioLogado().getCargo());
        params.put("descricao", descricaoAdicional);

        StringBuilder sb = new StringBuilder();
        sb.append("=== DETALHES DO RELATÓRIO ===\n");
        sb.append("Tipo: ").append(tipo).append("\n");
        sb.append("Gerado por: ").append(AuthController.getUsuarioLogado().getNomeCompleto()).append("\n");
        sb.append("Data de Geração: ").append(LocalDate.now()).append("\n");
        sb.append("Descrição: ").append(descricaoAdicional).append("\n");

        if ("Estoque".equalsIgnoreCase(tipo)) {
            sb.append("\nInsumos cadastrados:\n");
            repository.getEstoques().forEach(e -> {
                sb.append(String.format("- %s: %.2f %s (Mínimo: %.2f %s) | Local: %s\n", 
                    e.getNomeProduto(), e.getQuantidadeAtual(), e.getUnidadeMedida(), e.getEstoqueMinimo(), e.getUnidadeMedida(), e.getLocalArmazenamento()));
            });
        } else if ("Producao".equalsIgnoreCase(tipo)) {
            sb.append("\nProduções diárias registradas:\n");
            repository.getProducoes().forEach(p -> {
                sb.append(String.format("- Data: %s | Ovos: %d | Lote: %s | Obs: %s\n", 
                    p.getData(), p.getQuantidadeOvos(), p.getLote(), p.getObservacoes()));
            });
        } else if ("Saude".equalsIgnoreCase(tipo)) {
            sb.append("\nSituação de saúde dos animais:\n");
            repository.getAnimais().forEach(a -> {
                if (a.isAtivo()) {
                    sb.append(String.format("- Animal %s | Espécie: %s | Saúde: %s | Lote: %s\n", 
                        a.getIdentificacaoUnica(), a.getEspecie(), a.getSituacaoSaude(), a.getLote()));
                }
            });
        } else {
            sb.append("\nDados Gerais do Sistema AgroPlus compilados com sucesso.\n");
        }

        byte[] conteudo = sb.toString().getBytes();
        Relatorio rel = new Relatorio(
            repository.getNextIdRelatorio(),
            tipo,
            LocalDate.now(),
            params,
            conteudo
        );

        // Gera também o arquivo de texto no diretório local
        File f = Relatorio.gerar(tipo, params, conteudo);
        repository.addRelatorio(rel);
        System.out.println("[SUCESSO] Relatório gerado em memória e salvo fisicamente em: " + f.getName());
    }
}
