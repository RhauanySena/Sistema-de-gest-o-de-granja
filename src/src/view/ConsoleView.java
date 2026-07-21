package br.ufpb.dcx.agroplus.view;

import br.ufpb.dcx.agroplus.controller.*;
import br.ufpb.dcx.agroplus.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Cores ANSI para um design premium no terminal
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private final AuthController authController;
    private final FuncionarioController funcionarioController;
    private final AnimalController animalController;
    private final EstoqueController estoqueController;
    private final ProducaoController producaoController;
    private final RelatorioController relatorioController;

    public ConsoleView() {
        this.authController = new AuthController();
        this.funcionarioController = new FuncionarioController();
        this.animalController = new AnimalController();
        this.estoqueController = new EstoqueController();
        this.producaoController = new ProducaoController();
        this.relatorioController = new RelatorioController();
    }

    public void iniciar() {
        exibirBoasVindas();
        
        while (true) {
            if (AuthController.getUsuarioLogado() == null) {
                new LoginView().exibir();
            } else {
                new DashboardView().exibir();
            }
        }
    }

    private void exibirBoasVindas() {
        System.out.println(GREEN + BOLD + "=============================================" + RESET);
        System.out.println(GREEN + BOLD + "       SISTEMA DE GESTÃO DE GRANJA           " + RESET);
        System.out.println(GREEN + BOLD + "               AGROPLUS                      " + RESET);
        System.out.println(GREEN + BOLD + "=============================================" + RESET);
        System.out.println(CYAN + "Bem-vindo ao AgroPlus! O seu gerenciador avícola." + RESET);
    }

    // --- SUB-CLASSES DE VISÃO (CORRESPONDÊNCIA COM UML) ---

    // 1. LOGIN VIEW
    private class LoginView {
        public void exibir() {
            System.out.println("\n" + BOLD + "--- TELA DE AUTENTICAÇÃO ---" + RESET);
            System.out.print("E-mail: ");
            String email = scanner.nextLine().trim();
            System.out.print("Senha: ");
            String senha = scanner.nextLine().trim();

            if (authController.login(email, senha)) {
                System.out.println(GREEN + BOLD + "\n[SUCESSO] Login realizado com sucesso!" + RESET);
                System.out.println("Bem-vindo(a), " + BOLD + AuthController.getUsuarioLogado().getNomeCompleto() + RESET + " (" + AuthController.getUsuarioLogado().getCargo() + ")");
            } else {
                System.out.println(RED + BOLD + "\n[ERRO] E-mail ou senha incorretos." + RESET);
            }
        }
    }

    // 2. DASHBOARD VIEW
    private class DashboardView {
        public void exibir() {
            System.out.println(GREEN + BOLD + "\n=============================================" + RESET);
            System.out.println(BOLD + "                 PAINEL PRINCIPAL            " + RESET);
            System.out.println(GREEN + BOLD + "=============================================" + RESET);

            // Métricas Rápidas
            int totalAnimais = animalController.listarAnimaisAtivos().size();
            List<String> alertas = estoqueController.verAlertasEstoque();

            System.out.println(CYAN + "Total de Animais Ativos: " + BOLD + totalAnimais + RESET);
            System.out.println(CYAN + "Alertas de Estoque Pendentes: " + BOLD + alertas.size() + RESET);
            for (String alerta : alertas) {
                System.out.println(YELLOW + "  -> " + alerta + RESET);
            }

            System.out.println("\nMenu:");
            System.out.println("1. Módulo de Animais");
            System.out.println("2. Módulo de Estoque e Consumo");
            System.out.println("3. Módulo de Produção");
            System.out.println("4. Geração de Relatórios");
            System.out.println("5. Gerenciamento de Funcionários");
            System.out.println("6. Alterar Minha Senha");
            System.out.println("7. Logout (Sair)");
            System.out.println("8. Fechar Programa");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();
            switch (opcao) {
                case "1":
                    new AnimalView().exibir();
                    break;
                case "2":
                    new EstoqueView().exibir();
                    break;
                case "3":
                    new ProducaoView().exibir();
                    break;
                case "4":
                    new RelatorioView().exibir();
                    break;
                case "5":
                    new FuncionarioView().exibir();
                    break;
                case "6":
                    alterarPropriaSenha();
                    break;
                case "7":
                    authController.logout();
                    System.out.println(GREEN + "\nSessão encerrada com sucesso." + RESET);
                    break;
                case "8":
                    System.out.println(GREEN + "\nObrigado por utilizar o AgroPlus! Finalizando aplicação." + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Opção inválida!" + RESET);
            }
        }

        private void alterarPropriaSenha() {
            System.out.println("\n" + BOLD + "--- ALTERAR MINHA SENHA ---" + RESET);
            System.out.print("Digite a nova senha: ");
            String nova = scanner.nextLine().trim();
            if (nova.isEmpty()) {
                System.out.println(RED + "Senha não pode ser vazia." + RESET);
                return;
            }
            AuthController.getUsuarioLogado().alterarSenha(nova);
            System.out.println(GREEN + "[SUCESSO] Senha alterada com sucesso!" + RESET);
        }
    }

    // 3. ANIMAL VIEW
    private class AnimalView {
        public void exibir() {
            while (true) {
                System.out.println("\n" + BOLD + "--- MÓDULO DE ANIMAIS ---" + RESET);
                System.out.println("1. Listar Animais Ativos");
                System.out.println("2. Cadastrar Novo Animal");
                System.out.println("3. Atualizar Peso de Animal");
                System.out.println("4. Atualizar Situação de Saúde");
                System.out.println("5. Consultar Histórico Completo de Animal");
                System.out.println("6. Excluir Animal");
                System.out.println("7. Voltar ao Painel Principal");
                System.out.print("Escolha uma opção: ");

                String op = scanner.nextLine().trim();
                switch (op) {
                    case "1":
                        listarAnimais();
                        break;
                    case "2":
                        cadastrarAnimal();
                        break;
                    case "3":
                        atualizarPeso();
                        break;
                    case "4":
                        atualizarSaude();
                        break;
                    case "5":
                        consultarHistorico();
                        break;
                    case "6":
                        excluirAnimal();
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println(RED + "Opção inválida!" + RESET);
                }
            }
        }

        private void listarAnimais() {
            System.out.println("\n" + BOLD + "--- ANIMAIS CADASTRADOS ---" + RESET);
            List<Animal> animais = animalController.listarAnimaisAtivos();
            if (animais.isEmpty()) {
                System.out.println("Nenhum animal cadastrado.");
                return;
            }
            System.out.printf("%-4s | %-10s | %-8s | %-6s | %-8s | %-12s | %-8s\n", "ID", "Identif.", "Espécie", "Idade", "Peso(kg)", "Saúde", "Lote");
            System.out.println("----------------------------------------------------------------------");
            for (Animal a : animais) {
                System.out.printf("%-4d | %-10s | %-8s | %-3d mes | %-8.2f | %-12s | %-8s\n", 
                    a.getId(), a.getIdentificacaoUnica(), a.getEspecie(), a.getIdade(), a.getPesoAtual(), a.getSituacaoSaude(), a.getLote());
            }
        }

        private void cadastrarAnimal() {
            System.out.println("\n" + BOLD + "--- CADASTRAR ANIMAL ---" + RESET);
            System.out.print("Identificação única (ex: GRN-005): ");
            String ident = scanner.nextLine().trim();
            System.out.print("Espécie (ex: Frango/Galinha): ");
            String especie = scanner.nextLine().trim();
            
            int idade = lerInteiro("Idade (em meses): ");
            float peso = lerFloat("Peso inicial (kg): ");
            
            System.out.print("Situação de Saúde (Saudavel, Doente, EmTratamento, Morto): ");
            String saude = scanner.nextLine().trim();
            System.out.print("Lote (ex: Lote A): ");
            String lote = scanner.nextLine().trim();

            try {
                animalController.cadastrarAnimal(ident, especie, idade, peso, saude, lote);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] Falha ao cadastrar: " + e.getMessage() + RESET);
            }
        }

        private void atualizarPeso() {
            int id = lerInteiro("Digite o ID do animal: ");
            float peso = lerFloat("Digite o novo peso (kg): ");
            try {
                animalController.atualizarPeso(id, peso);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void atualizarSaude() {
            int id = lerInteiro("Digite o ID do animal: ");
            System.out.print("Nova Situação de Saúde (Saudavel, Doente, EmTratamento, Morto): ");
            String saude = scanner.nextLine().trim();
            try {
                animalController.atualizarSaude(id, saude);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void consultarHistorico() {
            int id = lerInteiro("Digite o ID do animal: ");
            try {
                HistoricoAnimal ha = animalController.obterHistorico(id);
                System.out.println(ha.exibirCompleto());
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void excluirAnimal() {
            int id = lerInteiro("Digite o ID do animal a ser excluído: ");
            try {
                animalController.excluirAnimal(id);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }
    }

    // 4. ESTOQUE VIEW
    private class EstoqueView {
        public void exibir() {
            while (true) {
                System.out.println("\n" + BOLD + "--- MÓDULO DE ESTOQUE ---" + RESET);
                System.out.println("1. Listar Estoque Geral");
                System.out.println("2. Registrar Entrada de Insumo");
                System.out.println("3. Registrar Saída de Insumo");
                System.out.println("4. Listar Medicamentos/Vacinas");
                System.out.println("5. Aplicar Vacina/Medicamento em Animal");
                System.out.println("6. Registrar Consumo de Alimento por Animal");
                System.out.println("7. Voltar ao Painel Principal");
                System.out.print("Escolha uma opção: ");

                String op = scanner.nextLine().trim();
                switch (op) {
                    case "1":
                        listarEstoque();
                        break;
                    case "2":
                        registrarEntrada();
                        break;
                    case "3":
                        registrarSaida();
                        break;
                    case "4":
                        listarMedicamentos();
                        break;
                    case "5":
                        aplicarMedicamento();
                        break;
                    case "6":
                        registrarConsumo();
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println(RED + "Opção inválida!" + RESET);
                }
            }
        }

        private void listarEstoque() {
            System.out.println("\n" + BOLD + "--- ESTOQUE ATUAL ---" + RESET);
            List<Estoque> estoques = estoqueController.listarEstoqueGeral();
            System.out.printf("%-4s | %-20s | %-12s | %-8s | %-8s | %-15s\n", "ID", "Produto", "Qtd Atual", "Mínimo", "Máximo", "Local");
            System.out.println("--------------------------------------------------------------------------------");
            for (Estoque e : estoques) {
                System.out.printf("%-4d | %-20s | %-8.2f %s | %-8.2f | %-8.2f | %-15s\n", 
                    e.getId(), e.getNomeProduto(), e.getQuantidadeAtual(), e.getUnidadeMedida(), e.getEstoqueMinimo(), e.getEstoqueMaximo(), e.getLocalArmazenamento());
            }
        }

        private void registrarEntrada() {
            int id = lerInteiro("ID do insumo no estoque: ");
            float qtd = lerFloat("Quantidade de entrada: ");
            System.out.print("Número da Nota Fiscal: ");
            String nf = scanner.nextLine().trim();
            try {
                estoqueController.registrarEntradaInsumo(id, qtd, nf);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void registrarSaida() {
            int id = lerInteiro("ID do insumo no estoque: ");
            float qtd = lerFloat("Quantidade de saída: ");
            System.out.print("Motivo: ");
            String motivo = scanner.nextLine().trim();
            try {
                estoqueController.registrarSaidaInsumo(id, qtd, motivo);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void listarMedicamentos() {
            System.out.println("\n" + BOLD + "--- VACINAS E MEDICAMENTOS EM ESTOQUE ---" + RESET);
            List<VacinaMedicamento> lista = estoqueController.listarMedicamentos();
            System.out.printf("%-4s | %-25s | %-12s | %-10s | %-12s | %-10s\n", "ID", "Nome", "Tipo", "Lote", "Validade", "Estoque");
            System.out.println("-------------------------------------------------------------------------------------");
            for (VacinaMedicamento vm : lista) {
                System.out.printf("%-4d | %-25s | %-12s | %-10s | %-12s | %-10d\n", 
                    vm.getId(), vm.getNome(), vm.getTipo(), vm.getLote(), vm.getDataValidade(), vm.getQuantidadeEstoque());
            }
        }

        private void aplicarMedicamento() {
            int vacinaId = lerInteiro("ID da Vacina/Medicamento: ");
            int animalId = lerInteiro("ID do Animal: ");
            System.out.print("Dosagem (ex: 2ml): ");
            String dose = scanner.nextLine().trim();
            System.out.print("Observações: ");
            String obs = scanner.nextLine().trim();

            try {
                estoqueController.aplicarVacinaMedicamento(vacinaId, animalId, dose, obs);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void registrarConsumo() {
            System.out.println("\nAlimentos Disponíveis:");
            List<Alimento> alimentos = estoqueController.listarAlimentos();
            for (Alimento al : alimentos) {
                System.out.printf("  ID: %d | Nome: %s | Tipo: %s | Validade: %s\n", 
                    al.getId(), al.getNome(), al.getTipo(), al.getValidade());
            }
            int alimentoId = lerInteiro("ID do Alimento consumido: ");
            int animalId = lerInteiro("ID do Animal: ");
            float qtd = lerFloat("Quantidade (kg): ");

            try {
                estoqueController.registrarConsumoAlimento(alimentoId, animalId, qtd);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }
    }

    // 5. PRODUCAO VIEW
    private class ProducaoView {
        public void exibir() {
            while (true) {
                System.out.println("\n" + BOLD + "--- MÓDULO DE PRODUÇÃO ---" + RESET);
                System.out.println("1. Listar Histórico de Produção de Ovos");
                System.out.println("2. Registrar Produção Diária");
                System.out.println("3. Filtrar Produção por Período");
                System.out.println("4. Voltar ao Painel Principal");
                System.out.print("Escolha uma opção: ");

                String op = scanner.nextLine().trim();
                switch (op) {
                    case "1":
                        listarProducao();
                        break;
                    case "2":
                        registrarProducao();
                        break;
                    case "3":
                        filtrarProducao();
                        break;
                    case "4":
                        return;
                    default:
                        System.out.println(RED + "Opção inválida!" + RESET);
                }
            }
        }

        private void listarProducao() {
            System.out.println("\n" + BOLD + "--- HISTÓRICO DE PRODUÇÃO ---" + RESET);
            List<ProducaoDiaria> producoes = producaoController.listarTodaProducao();
            System.out.printf("%-4s | %-12s | %-12s | %-10s | %-20s\n", "ID", "Data", "Qtd Ovos", "Lote", "Observações");
            System.out.println("-------------------------------------------------------------------------");
            for (ProducaoDiaria pd : producoes) {
                System.out.printf("%-4d | %-12s | %-12d | %-10s | %-20s\n", 
                    pd.getId(), pd.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), pd.getQuantidadeOvos(), pd.getLote(), pd.getObservacoes());
            }
        }

        private void registrarProducao() {
            LocalDate data = lerData("Data da produção (dd/MM/yyyy, ex: 21/07/2026): ");
            int ovos = lerInteiro("Quantidade de Ovos: ");
            System.out.print("Lote Associado (ex: Lote A): ");
            String lote = scanner.nextLine().trim();
            System.out.print("Observações: ");
            String obs = scanner.nextLine().trim();

            try {
                producaoController.registrarProducao(data, ovos, lote, obs);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void filtrarProducao() {
            LocalDate inicio = lerData("Data Inicial (dd/MM/yyyy): ");
            LocalDate fim = lerData("Data Final (dd/MM/yyyy): ");

            try {
                List<ProducaoDiaria> result = producaoController.getProducaoPorPeriodo(inicio, fim);
                System.out.println("\n" + BOLD + "--- PRODUÇÃO FILTRADA ---" + RESET);
                if (result.isEmpty()) {
                    System.out.println("Nenhum registro encontrado no período informado.");
                    return;
                }
                int totalOvos = 0;
                System.out.printf("%-4s | %-12s | %-12s | %-10s | %-20s\n", "ID", "Data", "Qtd Ovos", "Lote", "Observações");
                System.out.println("-------------------------------------------------------------------------");
                for (ProducaoDiaria pd : result) {
                    System.out.printf("%-4d | %-12s | %-12d | %-10s | %-20s\n", 
                        pd.getId(), pd.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), pd.getQuantidadeOvos(), pd.getLote(), pd.getObservacoes());
                    totalOvos += pd.getQuantidadeOvos();
                }
                System.out.println(CYAN + BOLD + "Total de ovos produzidos no período: " + totalOvos + RESET);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }
    }

    // 6. RELATORIO VIEW
    private class RelatorioView {
        public void exibir() {
            while (true) {
                System.out.println("\n" + BOLD + "--- MÓDULO DE RELATÓRIOS ---" + RESET);
                System.out.println("1. Listar Relatórios Gerados");
                System.out.println("2. Gerar Novo Relatório");
                System.out.println("3. Voltar ao Painel Principal");
                System.out.print("Escolha uma opção: ");

                String op = scanner.nextLine().trim();
                switch (op) {
                    case "1":
                        listarRelatorios();
                        break;
                    case "2":
                        gerarRelatorio();
                        break;
                    case "3":
                        return;
                    default:
                        System.out.println(RED + "Opção inválida!" + RESET);
                }
            }
        }

        private void listarRelatorios() {
            System.out.println("\n" + BOLD + "--- RELATÓRIOS NO HISTÓRICO ---" + RESET);
            List<Relatorio> relatorios = relatorioController.listarRelatoriosGerados();
            if (relatorios.isEmpty()) {
                System.out.println("Nenhum relatório gerado no histórico.");
                return;
            }
            System.out.printf("%-4s | %-12s | %-12s | %-25s\n", "ID", "Tipo", "Data", "Gerado por");
            System.out.println("-----------------------------------------------------------");
            for (Relatorio r : relatorios) {
                System.out.printf("%-4d | %-12s | %-12s | %-25s\n", 
                    r.getId(), r.getTipo(), r.getDataGeracao(), r.getParametros().get("geradoPor"));
            }
        }

        private void gerarRelatorio() {
            System.out.println("\nTipos de Relatório Disponíveis:");
            System.out.println(" - Estoque");
            System.out.println(" - Producao");
            System.out.println(" - Saude");
            System.out.println(" - Desempenho");
            System.out.print("Escolha o tipo: ");
            String tipo = scanner.nextLine().trim();
            System.out.print("Descrição / Informações Adicionais para o relatório: ");
            String desc = scanner.nextLine().trim();

            try {
                relatorioController.gerarRelatorio(tipo, desc);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }
    }

    // 7. FUNCIONARIO VIEW
    private class FuncionarioView {
        public void exibir() {
            while (true) {
                System.out.println("\n" + BOLD + "--- MÓDULO DE FUNCIONÁRIOS (Apenas Patrão) ---" + RESET);
                System.out.println("1. Listar Funcionários");
                System.out.println("2. Cadastrar Funcionário");
                System.out.println("3. Alterar Cargo de Funcionário");
                System.out.println("4. Demitir Funcionário");
                System.out.println("5. Voltar ao Painel Principal");
                System.out.print("Escolha uma opção: ");

                String op = scanner.nextLine().trim();
                switch (op) {
                    case "1":
                        listarFuncionarios();
                        break;
                    case "2":
                        cadastrarFuncionario();
                        break;
                    case "3":
                        alterarCargo();
                        break;
                    case "4":
                        demitirFuncionario();
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println(RED + "Opção inválida!" + RESET);
                }
            }
        }

        private void listarFuncionarios() {
            System.out.println("\n" + BOLD + "--- QUADRO DE FUNCIONÁRIOS ---" + RESET);
            List<Funcionario> lista = funcionarioController.listarFuncionarios();
            System.out.printf("%-4s | %-30s | %-12s | %-12s | %-12s\n", "ID", "Nome Completo", "Cargo", "Acesso", "Status");
            System.out.println("---------------------------------------------------------------------------------");
            for (Funcionario f : lista) {
                String status = f.getCargo().equalsIgnoreCase("Demitido") ? RED + "Inativo" + RESET : GREEN + "Ativo" + RESET;
                System.out.printf("%-4d | %-30s | %-12s | %-12s | %s\n", 
                    f.getId(), f.getNomeCompleto(), f.getCargo(), f.getTipoAcesso(), status);
            }
        }

        private void cadastrarFuncionario() {
            System.out.println("\n" + BOLD + "--- NOVO FUNCIONÁRIO ---" + RESET);
            System.out.print("Nome Completo: ");
            String nome = scanner.nextLine().trim();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine().trim();
            System.out.print("Cargo (ex: Gerente, Auxiliar, Veterinário): ");
            String cargo = scanner.nextLine().trim();
            System.out.print("Telefone: ");
            String tel = scanner.nextLine().trim();
            System.out.print("E-mail para login: ");
            String email = scanner.nextLine().trim();
            System.out.print("Senha: ");
            String senha = scanner.nextLine().trim();
            System.out.print("Nível de acesso (administrador / comum): ");
            String acesso = scanner.nextLine().trim().toLowerCase();

            try {
                funcionarioController.cadastrarFuncionario(nome, cpf, cargo, tel, email, senha, acesso);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void alterarCargo() {
            int id = lerInteiro("ID do funcionário: ");
            System.out.print("Novo cargo: ");
            String cargo = scanner.nextLine().trim();
            try {
                funcionarioController.alterarCargo(id, cargo);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }

        private void demitirFuncionario() {
            int id = lerInteiro("ID do funcionário a ser demitido: ");
            try {
                funcionarioController.demitirFuncionario(id);
            } catch (Exception e) {
                System.out.println(RED + "[ERRO] " + e.getMessage() + RESET);
            }
        }
    }

    // --- LEITORES AUXILIARES SEGUROS DE ENTRADA ---

    private int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada inválida. Digite um número inteiro." + RESET);
            }
        }
    }

    private float lerFloat(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Float.parseFloat(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println(RED + "Entrada inválida. Digite um número decimal." + RESET);
            }
        }
    }

    private LocalDate lerData(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println(RED + "Formato de data inválido. Use dd/MM/yyyy." + RESET);
            }
        }
    }
}
