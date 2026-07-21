package br.ufpb.dcx.agroplus.controller;

import br.ufpb.dcx.agroplus.model.Funcionario;
import br.ufpb.dcx.agroplus.repository.DadosRepository;

public class AuthController {
    private static Funcionario usuarioLogado;
    private DadosRepository repository;

    public AuthController() {
        this.repository = DadosRepository.getInstance();
    }

    public boolean login(String email, String senha) {
        for (Funcionario f : repository.getFuncionarios()) {
            if (f.autenticar(email, senha)) {
                if (f.getCargo().equalsIgnoreCase("Demitido")) {
                    System.out.println("[ERRO] Funcionário demitido não pode acessar o sistema.");
                    return false;
                }
                usuarioLogado = f;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        usuarioLogado = null;
    }

    public static Funcionario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static boolean hasPermission(String permissao) {
        if (usuarioLogado == null) return false;
        return usuarioLogado.getPermissoes().contains(permissao);
    }

    public static boolean isAdmin() {
        if (usuarioLogado == null) return false;
        return "administrador".equalsIgnoreCase(usuarioLogado.getTipoAcesso());
    }
}
