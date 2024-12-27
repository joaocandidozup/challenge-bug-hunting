package strategy;

import model.Video;

import java.util.List;

public interface SearchStrategy {
    List<Video> search(List<Video> videos, String query);
    List<Video> buscaPorCategoria(List<Video> videos, String query);
    List<Video> ordenaPorDataDePublicacao(List<Video> videos);
}