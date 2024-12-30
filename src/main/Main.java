package main;

import manager.VideoManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        VideoManager videoManager = new VideoManager();

        int opcao = 0;
        while (opcao != 9) {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();// Consumir a quebra de linha
            } catch (Exception e) {
                System.err.println("Digite apenas numeros inteiros no campo opção");
                return;
            }
            if (opcao == 1) {
                System.out.print("Digite o título do vídeo: ");
                String titulo = validaString(scanner, "Campo titulo não pode ser vazio ou conter so numeros");
                System.out.print("Digite a descrição do vídeo: ");
                String descricao = validaString(scanner, "Campo descrição não pode ser vazio ou conter só numeros");
                System.out.print("Digite a duração do vídeo (em minutos): ");
                try {
                    int duracao = validaInteiroPositivo(scanner, "Duração tem que ser maior que zero");
                    System.out.print("Digite a categoria do vídeo: ");
                    String categoria = validaCategoria(scanner, "Categorias aceitas: Serie, Filme ou Documentario");

                    videoManager.adicionarVideo(titulo, descricao, duracao, categoria);
                } catch (Exception e) {
                    System.err.println("campo duração nao pode ser letra");
                    scanner.nextLine();
                }

            } else if (opcao == 2) {
                videoManager.listarVideos();
            } else if (opcao == 3) {
                System.out.print("Digite o título para busca: ");
                String query = scanner.nextLine();
                videoManager.pesquisarVideoPorTitulo(query);
            } else if (opcao == 4) {
                System.out.print("Digite o título do vídeo para editar: ");
                String videoTitulo = scanner.nextLine();
                System.out.print("Digite o título do vídeo: ");
                String titulo = validaString(scanner, "Campo título não pode ser vazio ou conter só números");
                System.out.print("Digite a descrição do vídeo: ");
                String descricao = validaString(scanner, "Campo descrição não pode ser vazio ou conter só números");
                System.out.print("Digite a duração do vídeo (em minutos): ");
                try {
                    int duracao = validaInteiroPositivo(scanner, "Duração tem que ser maior que zero");
                    System.out.print("Digite a categoria do vídeo: ");
                    String categoria = validaCategoria(scanner, "Categorias aceitas: Serie, Filme ou Documentario");
                    System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                    videoManager.editarVideo(videoTitulo, titulo, descricao, duracao, categoria);
                    System.out.println("Vídeo atualizado com sucesso!");
                } catch (Exception e) {
                    System.err.println("Erro ao editar o vídeo: " + e.getMessage());
                    scanner.next();
                }
            } else if (opcao == 5) {
                System.out.print("Digite o título do vídeo para excluir: ");
                String titulo = validaString(scanner, "Campo título não pode ser vazio ou conter só números");
                videoManager.excluirVideo(titulo);
            } else if (opcao == 6) {
                System.out.println("Digite a Categoria de busca");
                String query = validaCategoria(scanner, "Categorias aceitas: Serie, Filme ou Documentario");
                videoManager.buscarPorCategoria(query);
            } else if (opcao == 7) {
                videoManager.ordenarPorDataDePublicacao();
            } else if (opcao == 8) {
                videoManager.exibirRelatorio();
            } else if (opcao == 9) {
                System.out.println("Saindo do sistema...");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        String menu = """
                
                === Sistema de Gerenciamento de Vídeos ===
                1 - Adicionar vídeo.
                2 - Listar vídeos.
                3 - Pesquisar vídeo por título.
                4 - Editar vídeo.
                5 - Excluir vídeo.
                6 - Filtrar vídeos por categoria.
                7 - Ordenar vídeos por data de publicação.
                8 - Exibir relatório de estatísticas.
                9 - Sair
                Digite uma opção:
                """;
        System.out.print(menu);
    }

    private static String validaString(Scanner scanner, String mensagemErro) {
        String string = scanner.nextLine();
        while (string.isEmpty() || string.matches("\\d+")) {
            System.err.println(mensagemErro);
            string = scanner.nextLine();

        }
        return string;
    }

    private static int validaInteiroPositivo(Scanner scanner, String mensagemErro) {
        int numero = -1;
        while (true) {
            try {
                numero = scanner.nextInt();
                if (numero > 0) {
                    break;
                } else {
                    System.err.println(mensagemErro);
                }
            } catch (Exception e) {
                System.err.println("Digite apenas números inteiros.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return numero;
    }

    private static String validaCategoria(Scanner scanner, String mensagemErro) {
        String categoria = scanner.nextLine();
        while (!categoria.equalsIgnoreCase("Filme") && !categoria.equalsIgnoreCase("Serie") && !categoria.equalsIgnoreCase("Documentario")) {
            System.err.println(mensagemErro);
            categoria = scanner.nextLine();
        }
        return categoria;
    }


}