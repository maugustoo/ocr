import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONObject;

public class FormatOutput {

    public static void main(String[] args) {
        String dataDir = "E:\\TesteFinal\\2016\\10-Outubro\\04-10-2016_Primeira_ Camara_Ordinaria\\";

        applyFormatOutput(dataDir);
    }

    public static void applyFormatOutput(String dataDir) {
        List<String> lines = readLinesOfFile(dataDir);

        try {
            formatOutput(lines, dataDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void formatOutput(List<String> lines, String dataDir) throws Exception {
        String processBase = null;

        for (int i = 0; i < lines.size() - 1; i++) {
            JSONObject jsonObj = new JSONObject(lines.get(i));

            // -1 Para ajustar o tempo para a leitura do processo
            Integer time = Integer.parseInt(jsonObj.getString("tempo")) - 1;
            String process = jsonObj.getString("processo");
            Integer distance = Integer.parseInt(jsonObj.getString("distance"));

            if (distance >= 7) {
                continue;
            }

            JSONObject jsonObjNext = new JSONObject(lines.get(i + 1));
            String nextProcess = jsonObjNext.getString("processo");

            // First line
            if (processBase == null) {
                if (process.equals(nextProcess)) {
                    processBase = process;
                    StringBuilder stringBuilder = new StringBuilder("Tempo: " + removeFrameError(time) + ", Processo: " + process);
                    printInFile(stringBuilder, dataDir);
                }
            } else {
                if (process.equals(processBase)) {
                    continue;
                } else if (process.equals(nextProcess)) {
                    processBase = process;
                    StringBuilder stringBuilder = new StringBuilder("Tempo: " + removeFrameError(time) + ", Processo: " + process);
                    printInFile(stringBuilder, dataDir);
                }
            }
        }
    }

    public static Integer removeFrameError(int time) {
        Integer realTime = (int) ((time * 30) / 29.97);

        return realTime;
    }

    public static List<String> readLinesOfFile(String dataDir) {
        Path path = Paths.get(dataDir, "resultado.txt");

        Charset charset = Charset.forName("ISO-8859-1");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path, charset);
        } catch (IOException e) {
            System.out.println(e);
        }

        return lines;
    }

    public static void printInFile(StringBuilder stringBuilder, String dataDir) {
        FileWriter arq = null;
        try {
            arq = new FileWriter(dataDir + "output.txt", true);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(stringBuilder.toString() + "%n");
            arq.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


