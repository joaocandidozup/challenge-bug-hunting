package handler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandle {
    private static final String FILE_PATH = "videos.txt";
    public void editaVideo(String titulo, String novoTitulo, String novaDescricao, int novaDuracao, String novaCategoria, String novaDataStr) {
        File file = new File(FILE_PATH);
        List<String> linhasAtualizadas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados[0].equalsIgnoreCase(titulo)) {
                    String novaLinha = String.join(";", novoTitulo, novaDescricao, String.valueOf(novaDuracao), novaCategoria, novaDataStr);
                    linhasAtualizadas.add(novaLinha);
                } else {
                    linhasAtualizadas.add(linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
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
}

