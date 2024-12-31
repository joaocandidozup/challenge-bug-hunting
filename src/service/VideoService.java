package service;

import model.Video;

import java.util.Date;
import java.util.List;

public interface VideoService {
    void addVideo(Video video);
    List<Video> listVideos();
    void editVideo(String videoTitulo, Video video);
    void deleteVideo(String titulo);
}