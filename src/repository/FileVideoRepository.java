package repository;

import handler.FileHandle;
import model.Video;

import java.util.Date;
import java.util.List;

public class FileVideoRepository implements VideoRepository {
    private final FileHandle fileHandle;

    public FileVideoRepository(FileHandle fileHandle) {
        this.fileHandle = fileHandle;
    }


    @Override
    public void save(Video video) {
        fileHandle.save(video);
    }

    @Override
    public List<Video> findAll() {
        return fileHandle.findAll();
    }

    @Override
    public void editVideo(String videoTitulo, Video video) {
        fileHandle.editVideo(videoTitulo,video);
    }

    @Override
    public void deleteVideo(String titulo) {
        fileHandle.deleteVideo(titulo);
    }
}