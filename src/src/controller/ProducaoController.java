package br.ufpb.dcx.agroplus.controller;

import br.ufpb.dcx.agroplus.model.ProducaoDiaria;
import br.ufpb.dcx.agroplus.repository.DadosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProducaoController {
    private DadosRepository repository;

    public ProducaoController() {
        this.repository = DadosRepository.getInstance();
    }

    public List<ProducaoDiaria> listarTodaProducao() {
        return repository.getProducoes();
    }

    public void registrarProducao(LocalDate data, int quantidadeOvos, String lote, String observacoes) {
        if (data == null) {
            throw new IllegalArgumentException("A data da produção diária é obrigatória.");
        }
        if (quantidadeOvos < 0) {
            throw new IllegalArgumentException("A quantidade de ovos não pode ser negativa.");
        }

        ProducaoDiaria pd = new ProducaoDiaria(
            repository.getNextIdProducao(),
            data,
            quantidadeOvos,
            lote,
            observacoes
        );
        repository.addProducaoDiaria(pd);
        System.out.println("[SUCESSO] Produção diária de ovos registrada com êxito!");
    }

    public List<ProducaoDiaria> getProducaoPorPeriodo(LocalDate dataIni, LocalDate dataFim) {
        if (dataIni == null || dataFim == null) {
            throw new IllegalArgumentException("As datas inicial e final são obrigatórias.");
        }
        List<ProducaoDiaria> filtrada = new ArrayList<>();
        for (ProducaoDiaria pd : repository.getProducoes()) {
            if (!pd.getData().isBefore(dataIni) && !pd.getData().isAfter(dataFim)) {
                filtrada.add(pd);
            }
        }
        return filtrada;
    }
}
