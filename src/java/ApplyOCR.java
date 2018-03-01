import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.sourceforge.tess4j.Tesseract;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class ApplyOCR {

    public static void main(String[] args) throws Exception {

        String dataDir = "E:\\Frames2\\";
        // String dataDir = "E:\\23-05-2017_Segunda_Camara_Ordinaria.mp4.frame\\";

        String tesseractDir = "E:\\Frames2\\";

        String fileName = null;
        File folder = new File(dataDir);
        String imagePath = null;

        for (File fileEntry : folder.listFiles()) {
            fileName = fileEntry.getName();

            if (!FilenameUtils.getExtension(fileName).equals("jpg")) {
                continue;
            }

            imagePath = dataDir + fileName;

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tesseractDir);
            tesseract.setLanguage("por");

            // tesseract.setTessVariable("load_system_dawg", "F");
            // tesseract.setTessVariable("load_freq_dawg", "F");
            // tesseract.setTessVariable("user_words_suffix", "user-words");
            // tesseract.setConfigs(Arrays.asList("TestConfig"));

            String texto = tesseract.doOCR(new File(imagePath));

            System.out.println(fileName);
            StringBuilder stringBuilder = new StringBuilder();
            if (StringUtils.isNotBlank(texto) && StringUtils.isNumeric(texto.substring(0, 1))) {
                texto = texto.replaceAll("[^a-zA-Z0-9áàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ -/\n:]", "");
                texto = texto.trim();
                String[] tokens = texto.split("\n");
                String[] time = fileName.split("[.]");
                if (tokens.length == 3) {
                    stringBuilder.append("{");
                    stringBuilder.append("\"tempo\": \"" + time[0] + "\",");

                    tokens[0] = tokens[0].replaceAll("[^0-9/.\n-]", "");
                    stringBuilder.append("\"processo\": \"" + tokens[0].trim() + "\",");

                    tokens[1] = tokens[1].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ0-9\n ]", "");
                    stringBuilder.append("\"nome\": \"" + tokens[1].trim() + "\",");

                    tokens[2] = tokens[2].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ\n ]", "");
                    stringBuilder.append("\"relator\": \"" + tokens[2].trim() + "\"");
                    stringBuilder.append("}");
                }
                try {
                    FileWriter arq = new FileWriter("E:\\Frames\\resultado.txt", true);
                    PrintWriter gravarArq = new PrintWriter(arq);
                    gravarArq.printf(stringBuilder.toString() + "%n");
                    arq.close();

                    System.out.println(stringBuilder.toString());
                    System.out.println();
                } catch (Exception e) {
                    continue;
                }

            } else {
                System.out.println("LIXO!!!!!");
            }

        }
    }
}




