package handler;

import model.Video;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileHandle {
    private final File file;
    public FileHandle(String filePath) {
        this.file = new File(filePath);
    }
    public void save(Video video) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(video.toString());
            bw.newLine();
        } catch (IOException e) {
            // Ignorar erros por enquanto
        }
    }
    public List<Video> findAll() {
        List<Video> videos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Video video = Video.fromString(line);
                if (video != null) {
                    videos.add(video);
                }
            }
        } catch (IOException e) {
            // Ignorar erros por enquanto
        }
        return videos;
    }
    public void editVideo(String videoTitulo, Video video) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String novaDataStr = sdf.format(video.getDataPublicacao());

        List<String> linhasAtualizadas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados[0].equalsIgnoreCase(videoTitulo)) {
                    String novaLinha = String.join(";",
                            video.getTitulo(),
                            video.getDescricao(),
                            String.valueOf(video.getDuracao()),
                            video.getCategoria(),
                            novaDataStr
                    );
                    linhasAtualizadas.add(novaLinha);
                } else {
                    linhasAtualizadas.add(linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String linhaAtualizada : linhasAtualizadas) {
                writer.write(linhaAtualizada);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void deleteVideo(String titulo) {
        List<String> linhasAtualizadas = new ArrayList<>();
        boolean videoEncontrado = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados[0].equalsIgnoreCase(titulo)) {
                    videoEncontrado = true;
                } else {
                    linhasAtualizadas.add(linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        if (!videoEncontrado) {
            System.out.println("Vídeo com o título '" + titulo + "' não encontrado.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String linhaAtualizada : linhasAtualizadas) {
                writer.write(linhaAtualizada);
                writer.newLine();
            }
            System.out.println("Vídeo excluído com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}