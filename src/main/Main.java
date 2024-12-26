package main;

import manager.VideoManager;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoManager videoManager = new VideoManager();
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();
        int opcao = 0;
        while (opcao != 9) {
            exibeMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();// Consumir a quebra de linha
            } catch (Exception e) {
                System.err.println("Digite apenas numeros inteiros no campo opção");
                scanner.nextLine();
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
                    String categoria = validaString(scanner, "Campo categoria não pode ser vazio ou conter só numeros");
                    System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                    String dataStr = scanner.nextLine();
                    videoManager.adicionaVideo(titulo, descricao, duracao, categoria, dataStr, videoService);
                } catch (Exception e) {
                    System.err.println("Digite apenas numeros inteiros no campo duração");
                    scanner.next();
                }

            } else if (opcao == 2) {
                videoManager.listaVideos(videoService);
            } else if (opcao == 3) {
                System.out.print("Digite o título para busca: ");
                String query = scanner.nextLine();
                videoManager.pesquisaVideoPorTitulo(query, searchStrategy, videoService);
            } else if (opcao == 4) {
                System.out.println("Saindo do sistema...");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    private static void exibeMenu() {
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
        while (string.isEmpty()) {
            System.err.println(mensagemErro);
            string = scanner.nextLine();
        }
        while (string.matches("\\d+")) {
            System.err.println(mensagemErro);
            string = scanner.nextLine();

        }
        return string;
    }

    private static int validaInteiroPositivo(Scanner scanner, String mensagemErro) {
        int numero = scanner.nextInt();
        if (numero <= 0) {
            System.err.println(mensagemErro);
            scanner.next();
        }
        scanner.nextLine();
        return numero;
    }

}