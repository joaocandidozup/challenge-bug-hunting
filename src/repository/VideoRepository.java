package repository;

import model.Video;

import java.util.Date;
import java.util.List;

public interface VideoRepository {
    void save(Video video);
    List<Video> findAll();
    void editVideo(String videoTitulo, Video video);
    void deleteVideo(String titulo);
}