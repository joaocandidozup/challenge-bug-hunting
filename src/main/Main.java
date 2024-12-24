package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();
        int opcao = 0;
        System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
        while (opcao != 9) {
            exibeMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Digite apenas numeros inteiros no campo opção");
                scanner.nextLine();
            }

            if (opcao == 1) {
                System.out.print("Digite o título do vídeo: ");
                String titulo = scanner.nextLine();
                System.out.print("Digite a descrição do vídeo: ");
                String descricao = scanner.nextLine();
                try {

                    System.out.print("Digite a duração do vídeo (em minutos): ");
                    int duracao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    System.out.print("Digite a categoria do vídeo: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                    String dataStr = scanner.nextLine();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataPublicacao = sdf.parse(dataStr);
                    Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
                    videoService.addVideo(video);
                    System.out.println("Vídeo adicionado com sucesso!");
                } catch (ParseException e) {
                    System.out.println("Video nao cadastrado! erro no formato da data ,use esse padrao (dd/MM/yyyy) ");
                } catch (Exception e) {
                    System.out.println("Digite apenas numeros inteiros no campo duração");
                    scanner.nextLine();
                }

            } else if (opcao == 2) {
                List<Video> videos = videoService.listVideos();
                for (Video video : videos) {
                    System.out.println(video);
                }
            } else if (opcao == 3) {
                System.out.print("Digite o título para busca: ");
                String query = scanner.nextLine();
                List<Video> resultados = searchStrategy.search(videoService.listVideos(), query);
                for (Video video : resultados) {
                    System.out.println(video);
                }
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
        System.out.println(menu);
    }

}