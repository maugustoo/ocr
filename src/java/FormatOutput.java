
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

    public static void main(String[] args) throws Exception {
        List<String> lines = readLinesOfFile();

        String processBase = null;

        for (int i = 0; i < lines.size() - 1; i++) {
            JSONObject jsonObj = new JSONObject(lines.get(i));

            Integer time = Integer.parseInt(jsonObj.getString("tempo"));
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
                    StringBuilder stringBuilder = new StringBuilder("Tempo: " + time + ", Processo: " + process);
                    printInFile(stringBuilder);
                }
            } else {
                if (process.equals(processBase)) {
                    continue;
                } else if (process.equals(nextProcess)) {
                    processBase = process;
                    StringBuilder stringBuilder = new StringBuilder("Tempo: " + time + ", Processo: " + process);
                    printInFile(stringBuilder);
                }
            }

        }
    }

    public static List<String> readLinesOfFile() {
        Path path = Paths.get("E:\\Frames2\\", "resultado.txt");

        Charset charset = Charset.forName("ISO-8859-1");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path, charset);
        } catch (IOException e) {
            System.out.println(e);
        }

        return lines;
    }

    public static void printInFile(StringBuilder stringBuilder) {
        String dataDir = "E:\\Frames2\\";

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


