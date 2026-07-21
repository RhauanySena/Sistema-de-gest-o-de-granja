package br.ufpb.dcx.agroplus.controller;

import br.ufpb.dcx.agroplus.model.Funcionario;
import br.ufpb.dcx.agroplus.repository.DadosRepository;

import java.time.LocalDate;
import java.util.List;

public class FuncionarioController {
    private DadosRepository repository;

    public FuncionarioController() {
        this.repository = DadosRepository.getInstance();
    }

    public List<Funcionario> listarFuncionarios() {
        return repository.getFuncionarios();
    }

    public void cadastrarFuncionario(String nome, String cpf, String cargo, String telefone, String email, String senha, String tipoAcesso) {
        if (!AuthController.hasPermission("GERENCIAR_FUNCIONARIOS")) {
            throw new SecurityException("Acesso negado: Apenas administradores (patrão) podem cadastrar funcionários.");
        }

        // Validação de e-mail único
        for (Funcionario f : repository.getFuncionarios()) {
            if (f.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
            }
        }

        Funcionario novo = new Funcionario(
            repository.getNextIdFuncionario(),
            nome,
            cpf,
            cargo,
            telefone,
            email,
            senha,
            LocalDate.now(),
            tipoAcesso
        );
        repository.addFuncionario(novo);
        System.out.println("[SUCESSO] Funcionário cadastrado com êxito!");
    }

    public void alterarCargo(int funcionarioId, String novoCargo) {
        if (!AuthController.hasPermission("GERENCIAR_FUNCIONARIOS")) {
            throw new SecurityException("Acesso negado: Apenas administradores (patrão) podem alterar cargos.");
        }

        Funcionario func = encontrarPorId(funcionarioId);
        if (func == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }

        func.alterarCargo(novoCargo);
        System.out.println("[SUCESSO] Cargo alterado para: " + novoCargo);
    }

    public void demitirFuncionario(int funcionarioId) {
        if (!AuthController.hasPermission("GERENCIAR_FUNCIONARIOS")) {
            throw new SecurityException("Acesso negado: Apenas administradores (patrão) podem demitir funcionários.");
        }

        Funcionario func = encontrarPorId(funcionarioId);
        if (func == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }

        if (func.getId() == AuthController.getUsuarioLogado().getId()) {
            throw new IllegalArgumentException("Erro: Você não pode se demitir.");
        }

        func.demitir();
        System.out.println("[SUCESSO] Funcionário " + func.getNomeCompleto() + " demitido.");
    }

    private Funcionario encontrarPorId(int id) {
        for (Funcionario f : repository.getFuncionarios()) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }
}
