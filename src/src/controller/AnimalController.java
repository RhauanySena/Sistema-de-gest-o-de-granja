package br.ufpb.dcx.agroplus.controller;

import br.ufpb.dcx.agroplus.model.Animal;
import br.ufpb.dcx.agroplus.model.RegistroPeso;
import br.ufpb.dcx.agroplus.model.AplicacaoProduto;
import br.ufpb.dcx.agroplus.model.ProducaoDiaria;
import br.ufpb.dcx.agroplus.model.HistoricoAnimal;
import br.ufpb.dcx.agroplus.repository.DadosRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalController {
    private DadosRepository repository;

    public AnimalController() {
        this.repository = DadosRepository.getInstance();
    }

    public List<Animal> listarAnimaisAtivos() {
        List<Animal> ativos = new ArrayList<>();
        for (Animal a : repository.getAnimais()) {
            if (a.isAtivo()) {
                ativos.add(a);
            }
        }
        return ativos;
    }

    public void cadastrarAnimal(String identificacaoUnica, String especie, int idadeMeses, float peso, String saude, String lote) {
        // Validação de identificação única
        for (Animal a : repository.getAnimais()) {
            if (a.isAtivo() && a.getIdentificacaoUnica().equalsIgnoreCase(identificacaoUnica)) {
                throw new IllegalArgumentException("Identificação de animal já existente.");
            }
        }

        Animal novo = new Animal(
            repository.getNextIdAnimal(),
            identificacaoUnica,
            especie,
            idadeMeses,
            peso,
            saude,
            LocalDate.now().minusMonths(idadeMeses),
            lote
        );
        repository.addAnimal(novo);

        // Adiciona registro de peso inicial
        repository.addRegistroPeso(new RegistroPeso(
            repository.getNextIdPeso(),
            novo.getId(),
            peso,
            LocalDate.now()
        ));

        System.out.println("[SUCESSO] Animal cadastrado com sucesso!");
    }

    public void atualizarPeso(int animalId, float novoPeso) {
        Animal a = encontrarPorId(animalId);
        if (a == null) {
            throw new IllegalArgumentException("Animal não encontrado.");
        }
        a.atualizarPeso(novoPeso);
        repository.addRegistroPeso(new RegistroPeso(
            repository.getNextIdPeso(),
            a.getId(),
            novoPeso,
            LocalDate.now()
        ));
        System.out.println("[SUCESSO] Peso do animal atualizado e registrado no histórico!");
    }

    public void atualizarSaude(int animalId, String novaSaude) {
        Animal a = encontrarPorId(animalId);
        if (a == null) {
            throw new IllegalArgumentException("Animal não encontrado.");
        }
        a.atualizarSaude(novaSaude);
        System.out.println("[SUCESSO] Situação de saúde do animal atualizada para: " + novaSaude);
    }

    public void excluirAnimal(int animalId) {
        if (!AuthController.hasPermission("EXCLUIR_REGISTROS")) {
            throw new SecurityException("Acesso negado: Apenas administradores (patrão) podem excluir registros.");
        }

        Animal a = encontrarPorId(animalId);
        if (a == null) {
            throw new IllegalArgumentException("Animal não encontrado.");
        }
        a.excluir();
        System.out.println("[SUCESSO] Animal desativado do sistema com sucesso (mantido no histórico).");
    }

    public HistoricoAnimal obterHistorico(int animalId) {
        Animal a = encontrarPorId(animalId);
        if (a == null) {
            // Se não encontrar ativo, procura nos inativos para histórico
            for (Animal animal : repository.getAnimais()) {
                if (animal.getId() == animalId) {
                    a = animal;
                    break;
                }
            }
        }
        if (a == null) {
            throw new IllegalArgumentException("Animal não encontrado no banco de dados.");
        }

        // Filtra pesos
        List<RegistroPeso> listaPesos = new ArrayList<>();
        for (RegistroPeso rp : repository.getPesos()) {
            if (rp.getAnimalId() == a.getId()) {
                listaPesos.add(rp);
            }
        }

        // Filtra aplicações de vacinas/medicamentos
        List<AplicacaoProduto> listaAplicacoes = new ArrayList<>();
        for (AplicacaoProduto ap : repository.getAplicacoes()) {
            if (ap.getAnimalId() == a.getId()) {
                listaAplicacoes.add(ap);
            }
        }

        // Filtra produção diária do lote do animal
        List<ProducaoDiaria> listaProd = new ArrayList<>();
        for (ProducaoDiaria pd : repository.getProducoes()) {
            if (pd.getLote().equalsIgnoreCase(a.getLote())) {
                listaProd.add(pd);
            }
        }

        return new HistoricoAnimal(a, listaPesos, listaAplicacoes, listaProd);
    }

    public Animal encontrarPorId(int id) {
        for (Animal a : repository.getAnimais()) {
            if (a.getId() == id && a.isAtivo()) {
                return a;
            }
        }
        return null;
    }
}
