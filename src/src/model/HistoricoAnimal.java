package br.ufpb.dcx.agroplus.model;

import java.util.List;

public class HistoricoAnimal {
    private Animal animal;
    private List<RegistroPeso> listaPesos;
    private List<AplicacaoProduto> listaAplicacoes;
    private List<ProducaoDiaria> listaProducaoAssociada; // Produção associada ao lote do animal

    public HistoricoAnimal(Animal animal, List<RegistroPeso> listaPesos, List<AplicacaoProduto> listaAplicacoes, List<ProducaoDiaria> listaProducaoAssociada) {
        this.animal = animal;
        this.listaPesos = listaPesos;
        this.listaAplicacoes = listaAplicacoes;
        this.listaProducaoAssociada = listaProducaoAssociada;
    }

    public String exibirCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("   HISTÓRICO COMPLETO DO ANIMAL: ").append(animal.getIdentificacaoUnica()).append("\n");
        sb.append("=========================================\n");
        sb.append("Espécie: ").append(animal.getEspecie()).append("\n");
        sb.append("Idade: ").append(animal.getIdade()).append(" meses\n");
        sb.append("Peso Atual: ").append(animal.getPesoAtual()).append(" kg\n");
        sb.append("Situação de Saúde: ").append(animal.getSituacaoSaude()).append("\n");
        sb.append("Lote: ").append(animal.getLote()).append("\n");
        sb.append("Status: ").append(animal.isAtivo() ? "Ativo" : "Inativo (Excluído)").append("\n");
        
        sb.append("\n--- Histórico de Pesagens ---\n");
        if (listaPesos.isEmpty()) {
            sb.append("Nenhum registro de peso encontrado.\n");
        } else {
            for (RegistroPeso rp : listaPesos) {
                sb.append(String.format("- Peso: %.2f kg em %s\n", rp.getPeso(), rp.getDataPesagem()));
            }
        }

        sb.append("\n--- Histórico de Vacinas/Medicamentos Aplicados ---\n");
        if (listaAplicacoes.isEmpty()) {
            sb.append("Nenhuma aplicação registrada.\n");
        } else {
            for (AplicacaoProduto ap : listaAplicacoes) {
                sb.append(String.format("- Produto ID: %d | Dose: %s | Data: %s | Aplicado por: %s | Obs: %s\n", 
                    ap.getProdutoId(), ap.getDose(), ap.getDataAplicacao(), ap.getFuncionarioResponsavel(), ap.getObservacoes()));
            }
        }

        sb.append("\n--- Produção Associada ao Lote (%s) ---\n".formatted(animal.getLote()));
        if (listaProducaoAssociada.isEmpty()) {
            sb.append("Nenhum registro de produção encontrado para este lote.\n");
        } else {
            for (ProducaoDiaria pd : listaProducaoAssociada) {
                sb.append(String.format("- Data: %s | Ovos Produzidos: %d | Obs: %s\n", 
                    pd.getData(), pd.getQuantidadeOvos(), pd.getObservacoes()));
            }
        }
        sb.append("=========================================");
        return sb.toString();
    }

    // Getters e Setters
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public List<RegistroPeso> getListaPesos() { return listaPesos; }
    public void setListaPesos(List<RegistroPeso> listaPesos) { this.listaPesos = listaPesos; }

    public List<AplicacaoProduto> getListaAplicacoes() { return listaAplicacoes; }
    public void setListaAplicacoes(List<AplicacaoProduto> listaAplicacoes) { this.listaAplicacoes = listaAplicacoes; }

    public List<ProducaoDiaria> getListaProducaoAssociada() { return listaProducaoAssociada; }
    public void setListaProducaoAssociada(List<ProducaoDiaria> listaProducaoAssociada) { this.listaProducaoAssociada = listaProducaoAssociada; }
}
