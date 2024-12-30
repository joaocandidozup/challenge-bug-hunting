package service;

import model.Video;
import repository.VideoRepository;

import java.util.Date;
import java.util.List;

public class VideoServiceImpl implements VideoService {
    private final VideoRepository repository;

    public VideoServiceImpl(VideoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addVideo(Video video) {
        repository.save(video);
    }

    @Override
    public List<Video> listVideos() {
        return repository.findAll();
    }

    @Override
    public void editVideo(String videoTitulo, String titulo, String descricao, int duracao, String categoria, Date dataPublicacao) {
        repository.editVideo(videoTitulo,titulo,descricao,duracao,categoria,dataPublicacao);
    }

    @Override
    public void deleteVideo(String titulo) {
        repository.deleteVideo(titulo);
    }


}

