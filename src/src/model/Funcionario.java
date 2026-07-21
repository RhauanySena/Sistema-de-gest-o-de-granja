package br.ufpb.dcx.agroplus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private int id;
    private String nomeCompleto;
    private String cpf;
    private String cargo;
    private String telefone;
    private String email;
    private String senha;
    private LocalDate dataAdmissao;
    private String tipoAcesso; // "administrador" (patrão) ou "comum"

    public Funcionario(int id, String nomeCompleto, String cpf, String cargo, String telefone, String email, String senha, LocalDate dataAdmissao, String tipoAcesso) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.cargo = cargo;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.dataAdmissao = dataAdmissao;
        this.tipoAcesso = tipoAcesso;
    }

    public boolean autenticar(String email, String senha) {
        return this.email.equalsIgnoreCase(email) && this.senha.equals(senha);
    }

    public void alterarCargo(String novoCargo) {
        this.cargo = novoCargo;
    }

    public void demitir() {
        this.cargo = "Demitido";
        this.tipoAcesso = "comum"; // Remove acessos admin caso tenha
    }

    public List<String> getPermissoes() {
        List<String> permissoes = new ArrayList<>();
        permissoes.add("VISUALIZAR_DASHBOARD");
        permissoes.add("VISUALIZAR_ANIMAIS");
        permissoes.add("REGISTRAR_PESO");
        permissoes.add("REGISTRAR_PRODUCAO");
        permissoes.add("REGISTRAR_ESTOQUE");

        if ("administrador".equalsIgnoreCase(this.tipoAcesso)) {
            permissoes.add("GERENCIAR_FUNCIONARIOS");
            permissoes.add("EXCLUIR_REGISTROS");
            permissoes.add("GERAR_RELATORIOS");
        }
        return permissoes;
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }

    public String getTipoAcesso() { return tipoAcesso; }
    public void setTipoAcesso(String tipoAcesso) { this.tipoAcesso = tipoAcesso; }
}
