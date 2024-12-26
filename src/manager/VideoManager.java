package manager;

import model.Video;
import service.VideoService;
import strategy.SearchStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VideoManager {
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
        for (Video video : resultados) {
            System.out.println(video);
        }
    }
}
