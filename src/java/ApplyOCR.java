import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.sourceforge.tess4j.Tesseract;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class ApplyOCR {

    public static void main(String[] args) throws Exception {
        String[] processos = {"000.621/2014-5", "002.964/2012-0", "005.867/2004-9", "008.213/2016-0", "010.450/1997-1",
                "014.252/2015-5", "014.499/2017-7", "001.225/2014-6", "005.972/2015-9", "012.697/2017-6", "016.254/2015-5",
                "020.690/2014-2", "001.208/2015-2", "018.590/2014-4", "019.501/2017-0", "022.094/2008-9", "025.968/2012-2",
                "038.217/2012-0"};

        String dataDir = "E:\\Frames\\";
        // String dataDir = "E:\\23-05-2017_Segunda_Camara_Ordinaria.mp4.frame\\";

        String tesseractDir = "E:\\Frames\\";

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
                if (tokens.length == 3 && tokens[0].replaceAll("[^0-9/.\n-]", "").length() >= 5) {
                    stringBuilder.append("{");
                    stringBuilder.append("\"tempo\": \"" + time[0] + "\",");

                    tokens[0] = tokens[0].replaceAll("[^0-9/.\n-]", "");

                    int indiceMelhorDistancia = 0;
                    int melhordistancia = 1000000;

                    for (int i = 0; i < processos.length; i++) {
                        int dist = EditDistance.distance(processos[i], tokens[0]);

                        if (dist < melhordistancia) {
                            melhordistancia = dist;
                            indiceMelhorDistancia = i;
                        }

                    }

                    stringBuilder.append("\"processo\": \"" + tokens[0].trim() + "\",");

                    tokens[1] = tokens[1].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ0-9\n ]", "");
                    stringBuilder.append("\"nome\": \"" + tokens[1].trim() + "\",");

                    tokens[2] = tokens[2].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ\n ]", "");
                    stringBuilder.append("\"relator\": \"" + tokens[2].trim() + "\"");
                    stringBuilder.append("}");

                    stringBuilder.append(" -- dist: " + melhordistancia + " - " + processos[indiceMelhorDistancia]);

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

                }
            } else {
                System.out.println("LIXO!!!!!");
            }

        }
    }
}

