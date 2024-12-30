package repository;

import model.Video;

import java.util.Date;
import java.util.List;

public interface VideoRepository {
    void save(Video video);
    List<Video> findAll();
    void editVideo(String videoTitulo, String titulo, String descricao, int duracao, String categoria, Date dataPublicacao);
    void deleteVideo(String titulo);
}