package strategy;

import model.Video;

import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Video> search(List<Video> videos, String query) {
        return videos.stream()
                .filter(video -> video.getTitulo().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Video> buscaPorCategoria(List<Video> videos, String query) {
        return videos.stream()
                .filter(video -> video.getCategoria().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Video> ordenaPorDataDePublicacao(List<Video> videos) {
        return videos.stream()
                .sorted((v1, v2) -> v1.getDataPublicacao().compareTo(v2.getDataPublicacao()))
                .collect(Collectors.toList());
    }
}