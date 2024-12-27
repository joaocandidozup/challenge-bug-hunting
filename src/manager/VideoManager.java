package manager;

import handler.FileHandle;
import model.Video;
import service.VideoService;
import strategy.SearchStrategy;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoManager {

    private List<Video> videos;

    public VideoManager() {
        this.videos = new ArrayList<>();
    }

    public void adicionaVideo(String titulo, String descricao, int duracao, String categoria, String dataStr, VideoService videoService) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataPublicacao = sdf.parse(dataStr);
            Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
            videoService.addVideo(video);
            video.getCategoria();
            System.out.println("Vídeo adicionado com sucesso!");
        } catch (ParseException e) {
            System.err.println("Data inválida, digite no formato (dd/MM/yyyy)");
        }
    }

    public void listaVideos(VideoService videoService) {
        List<Video> videos = videoService.listVideos();
        for (Video video : videos) {
            System.out.println(video);
        }
    }

    public void pesquisaVideoPorTitulo(String query, SearchStrategy searchStrategy, VideoService videoService) {
        List<Video> resultados = searchStrategy.search(videoService.listVideos(), query);
        if (!resultados.isEmpty()) {
            for (Video video : resultados) {
                System.out.println(video);
            }
        } else {
            System.out.println("Vídeo não encontrado");
        }

    }

    public void buscarPorCategoria(String query, SearchStrategy searchStrategy, VideoService videoService) {
        List<Video> resultados = searchStrategy.buscaPorCategoria(videoService.listVideos(), query);
        if (!resultados.isEmpty()) {
            for (Video video : resultados) {
                System.out.println(video);
            }
        } else {
            System.out.println("Categoria não contem videos");
        }
    }

    public void ordenarPorDataDePublicacao( SearchStrategy searchStrategy, VideoService videoService) {
        List<Video> resultados = searchStrategy.ordenaPorDataDePublicacao(videoService.listVideos());
        if (!resultados.isEmpty()) {
            for (Video video : resultados) {
                System.out.println(video);
            }
        } else {
            System.out.println("");
        }
    }
}
