package manager;

import handler.FileHandle;
import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VideoManager {
    SearchStrategy searchStrategy = new TitleSearchStrategy();
    VideoService videoService = new VideoServiceImpl(new FileVideoRepository(new FileHandle("videos.txt")));
    Scanner scanner = new Scanner(System.in);

    public void adicionarVideo(String titulo, String descricao, int duracao, String categoria) {
        System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataPublicacao = sdf.parse(dataStr);
            Video video = new Video(titulo, descricao, duracao, categoria, validaData(dataPublicacao, scanner));
            videoService.addVideo(video);
            video.getCategoria();
            System.out.println("Vídeo adicionado com sucesso!");
        } catch (ParseException e) {
            System.err.println("Data inválida, digite no formato (dd/MM/yyyy)");
        } catch (Exception e) {

        }
    }

    public void editarVideo(String videoTitulo, String titulo, String descricao, int duracao, String categoria) {
        String novaDataStr = scanner.nextLine();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataPublicacao = sdf.parse(novaDataStr);
            Video video = new Video(titulo, descricao, duracao, categoria, validaData(dataPublicacao, scanner));
            videoService.editVideo(videoTitulo, video);
        } catch (ParseException e) {
            System.err.println("Data inválida, digite no formato (dd/MM/yyyy)");
        } catch (Exception e) {

        }
    }

    public void excluirVideo(String titulo) {
        videoService.deleteVideo(titulo);
    }

    public void listarVideos() {
        List<Video> videos = videoService.listVideos();
        for (Video video : videos) {
            System.out.println(video);
        }
    }

    public void pesquisarVideoPorTitulo(String query) {
        List<Video> resultados = searchStrategy.search(videoService.listVideos(), query);
        if (!resultados.isEmpty()) {
            for (Video video : resultados) {
                System.out.println(video);
            }
        } else {
            System.out.println("Vídeo não encontrado");
        }

    }

    public void buscarPorCategoria(String query) {
        List<Video> resultados = searchStrategy.searchByCategory(videoService.listVideos(), query);
        if (!resultados.isEmpty()) {
            for (Video video : resultados) {
                System.out.println(video);
            }
        } else {
            System.out.println("Categoria não contem videos");
        }
    }

    public void ordenarPorDataDePublicacao() {
        List<Video> resultados = searchStrategy.orderByPublicationDate(videoService.listVideos());
        if (!resultados.isEmpty()) {
            for (Video video : resultados) {
                System.out.println(video);
            }
        } else {
            System.out.println("");
        }
    }

    public void exibirRelatorio() {
        List<Video> videos = videoService.listVideos();

        int total = 0;
        for (Video video : videos) {
            total += video.getDuracao();
        }

        Map<String, Integer> quantidadePorCategoria = new HashMap<>();
        for (Video video : videos) {
            String categoria = video.getCategoria();
            quantidadePorCategoria.put(categoria, quantidadePorCategoria.getOrDefault(categoria, 0) + 1);
        }

        System.out.println(
                "Número total de vídeos: " + videos.size() +
                        "\nDuração total de todos os vídeos: " + total +
                        "\nQuantidade de vídeos por categoria"
        );

        for (Map.Entry<String, Integer> entry : quantidadePorCategoria.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static Date validaData(Date dataPublicacao, Scanner scanner) throws ParseException {

        Date dataAtual = new Date();
        if (dataPublicacao.after(dataAtual)) {
            System.err.println("Data inválida, não pode ser uma data futura");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sdf.format(dataPublicacao);
        if (data.charAt(6) == '0') {
            System.err.println("Data inválida, tem que conter 4 digitos no ano");
            return null;
        }
        dataPublicacao = sdf.parse(data);
        return dataPublicacao;
    }


}