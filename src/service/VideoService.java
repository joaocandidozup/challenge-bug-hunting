package service;

import model.Video;

import java.util.Date;
import java.util.List;

public interface VideoService {
    void addVideo(Video video);
    List<Video> listVideos();
    void editVideo(String videoTitulo, String titulo, String descricao, int duracao, String categoria, Date dataPublicacao);
    void deleteVideo(String titulo);
}