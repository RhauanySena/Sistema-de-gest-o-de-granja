package br.ufpb.dcx.agroplus.repository;

import br.ufpb.dcx.agroplus.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DadosRepository {
    private static DadosRepository instance;

    private List<Funcionario> funcionarios;
    private List<Animal> animais;
    private List<RegistroPeso> pesos;
    private List<VacinaMedicamento> vacinasMedicamentos;
    private List<AplicacaoProduto> aplicacoes;
    private List<VisitaVeterinaria> visitas;
    private List<ProducaoDiaria> producoes;
    private List<Estoque> estoques;
    private List<Alimento> alimentos;
    private List<Relatorio> relatorios;

    private int nextIdFuncionario = 1;
    private int nextIdAnimal = 1;
    private int nextIdPeso = 1;
    private int nextIdProduto = 1;
    private int nextIdAplicacao = 1;
    private int nextIdVisita = 1;
    private int nextIdProducao = 1;
    private int nextIdEstoque = 1;
    private int nextIdAlimento = 1;
    private int nextIdRelatorio = 1;

    private DadosRepository() {
        funcionarios = new ArrayList<>();
        animais = new ArrayList<>();
        pesos = new ArrayList<>();
        vacinasMedicamentos = new ArrayList<>();
        aplicacoes = new ArrayList<>();
        visitas = new ArrayList<>();
        producoes = new ArrayList<>();
        estoques = new ArrayList<>();
        alimentos = new ArrayList<>();
        relatorios = new ArrayList<>();

        inicializarDadosMock();
    }

    public static synchronized DadosRepository getInstance() {
        if (instance == null) {
            instance = new DadosRepository();
        }
        return instance;
    }

    private void inicializarDadosMock() {
        // 1. Funcionários padrões
        addFuncionario(new Funcionario(nextIdFuncionario++, "Maria Rafaela (Patrão)", "111.222.333-44", "Patrão", "(83) 99999-1111", "admin@agroplus.com", "admin123", LocalDate.of(2026, 1, 10), "administrador"));
        addFuncionario(new Funcionario(nextIdFuncionario++, "Mariano Santos (Funcionário)", "555.666.777-88", "Auxiliar", "(83) 99999-2222", "func@agroplus.com", "func123", LocalDate.of(2026, 3, 15), "comum"));
        addFuncionario(new Funcionario(nextIdFuncionario++, "Rhauany Aragão (Veterinária)", "999.888.777-66", "Veterinária", "(83) 99999-3333", "vet@agroplus.com", "vet123", LocalDate.of(2026, 2, 20), "comum"));

        // 2. Animais
        addAnimal(new Animal(nextIdAnimal++, "GRN-001", "Frango", 3, 1.8f, "Saudavel", LocalDate.now().minusMonths(3), "Lote A"));
        addAnimal(new Animal(nextIdAnimal++, "GRN-002", "Galinha", 6, 2.5f, "Doente", LocalDate.now().minusMonths(6), "Lote A"));
        addAnimal(new Animal(nextIdAnimal++, "GRN-003", "Frango", 2, 1.2f, "Saudavel", LocalDate.now().minusMonths(2), "Lote B"));

        // 3. Registro de pesos
        addRegistroPeso(new RegistroPeso(nextIdPeso++, 1, 1.5f, LocalDate.now().minusWeeks(2)));
        addRegistroPeso(new RegistroPeso(nextIdPeso++, 1, 1.8f, LocalDate.now()));
        addRegistroPeso(new RegistroPeso(nextIdPeso++, 2, 2.2f, LocalDate.now().minusWeeks(1)));
        addRegistroPeso(new RegistroPeso(nextIdPeso++, 2, 2.5f, LocalDate.now()));

        // 4. Vacinas e Medicamentos
        addVacinaMedicamento(new VacinaMedicamento(nextIdProduto++, "Vacina Gumboro", "Vacina", "L-9081", LocalDate.now().plusYears(1), 100));
        addVacinaMedicamento(new VacinaMedicamento(nextIdProduto++, "Antibiótico Terramicina", "Medicamento", "L-4432", LocalDate.now().plusMonths(6), 50));

        // 5. Estoque Geral
        addEstoque(new Estoque(nextIdEstoque++, 1, "Ração Crescimento", 500f, "kg", 100f, 1000f, "Galpão Insumos A"));
        addEstoque(new Estoque(nextIdEstoque++, 2, "Desinfetante Instalações", 20f, "L", 5f, 50f, "Galpão Insumos B"));

        // 6. Alimentos
        addAlimento(new Alimento(nextIdAlimento++, "Ração Inicial", "Ração", "AgroRações LTDA", LocalDate.now().plusMonths(4), 4.5f));
        addAlimento(new Alimento(nextIdAlimento++, "Suplemento Vitamínico", "Suplemento", "BioNutri", LocalDate.now().plusMonths(8), 12.0f));

        // 7. Aplicação de Produto
        addAplicacaoProduto(new AplicacaoProduto(nextIdAplicacao++, 2, 2, "2ml", LocalDate.now().minusDays(1), "Rhauany Aragão", "Tratamento de infecção respiratória."));

        // 8. Produção Diária
        addProducaoDiaria(new ProducaoDiaria(nextIdProducao++, LocalDate.now().minusDays(1), 150, "Lote A", "Produção estável"));
        addProducaoDiaria(new ProducaoDiaria(nextIdProducao++, LocalDate.now(), 162, "Lote A", "Clima ameno, boa produção"));
    }

    // Gerenciadores de ID Incremental
    public int getNextIdFuncionario() { return nextIdFuncionario++; }
    public int getNextIdAnimal() { return nextIdAnimal++; }
    public int getNextIdPeso() { return nextIdPeso++; }
    public int getNextIdProduto() { return nextIdProduto++; }
    public int getNextIdAplicacao() { return nextIdAplicacao++; }
    public int getNextIdVisita() { return nextIdVisita++; }
    public int getNextIdProducao() { return nextIdProducao++; }
    public int getNextIdEstoque() { return nextIdEstoque++; }
    public int getNextIdAlimento() { return nextIdAlimento++; }
    public int getNextIdRelatorio() { return nextIdRelatorio++; }

    // Métodos CRUD básicos em memória
    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public void addFuncionario(Funcionario f) { funcionarios.add(f); }

    public List<Animal> getAnimais() { return animais; }
    public void addAnimal(Animal a) { animais.add(a); }

    public List<RegistroPeso> getPesos() { return pesos; }
    public void addRegistroPeso(RegistroPeso rp) { pesos.add(rp); }

    public List<VacinaMedicamento> getVacinasMedicamentos() { return vacinasMedicamentos; }
    public void addVacinaMedicamento(VacinaMedicamento vm) { vacinasMedicamentos.add(vm); }

    public List<AplicacaoProduto> getAplicacoes() { return aplicacoes; }
    public void addAplicacaoProduto(AplicacaoProduto ap) { aplicacoes.add(ap); }

    public List<VisitaVeterinaria> getVisitas() { return visitas; }
    public void addVisita(VisitaVeterinaria vv) { visitas.add(vv); }

    public List<ProducaoDiaria> getProducoes() { return producoes; }
    public void addProducaoDiaria(ProducaoDiaria pd) { producoes.add(pd); }

    public List<Estoque> getEstoques() { return estoques; }
    public void addEstoque(Estoque e) { estoques.add(e); }

    public List<Alimento> getAlimentos() { return alimentos; }
    public void addAlimento(Alimento a) { alimentos.add(a); }

    public List<Relatorio> getRelatorios() { return relatorios; }
    public void addRelatorio(Relatorio r) { relatorios.add(r); }
}
