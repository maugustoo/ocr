
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.sourceforge.tess4j.Tesseract;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class ApplyOCR {

    public static void main(String[] args) {
        String dataDir = "E:\\Frames\\";
        String tesseractDir = "E:\\Frames\\";

        try {
            applyOCR(dataDir, tesseractDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyOCR(String dataDir, String tesseractDir) throws Exception {
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

            String texto = tesseract.doOCR(new File(imagePath));

            if (StringUtils.isNotBlank(texto) && StringUtils.isNumeric(texto.substring(0, 1))) {
                String[] tokens = formatOCRData(texto);

                String time = getNameOfFileWithoutExtension(fileName);

                if (tokens.length == 3 && tokens[0].length() >= 5) {
                    String outData = formatOutputData(tokens, time, dataDir);

                    try {
                        writeOutput(dataDir, outData);

                        System.out.println(outData);
                        System.out.println();
                    } catch (Exception e) {
                        continue;
                    }

                } else {
                    continue;
                }
            } else {
                System.out.println("LIXO!!!!!");
            }

        }
    }

    public static void writeOutput(String dataDir, String outData) throws Exception {
        FileWriter arq = new FileWriter(dataDir + "resultado.txt", true);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf(outData);
        arq.close();
    }

    public static String formatOutputData(String[] tokens, String time, String dataDir) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        stringBuilder.append("\"tempo\": \"" + time + "\",");

        String[] spellCheckerResult = spellChecker(tokens[0]);

        stringBuilder.append("\"distance\": \"" + spellCheckerResult[0] + "\",");

        stringBuilder.append("\"processo\": \"" + spellCheckerResult[1] + "\",");

        tokens[1] = tokens[1].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ0-9\n ]", "");
        stringBuilder.append("\"nome\": \"" + tokens[1].trim() + "\",");

        tokens[2] = tokens[2].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ\n ]", "");
        stringBuilder.append("\"relator\": \"" + tokens[2].trim() + "\"");
        stringBuilder.append("}" + "%n");

        return stringBuilder.toString();
    }

    public static String getNameOfFileWithoutExtension(String nameWithExtension) {
        String[] time = nameWithExtension.split("[.]");

        return time[0];
    }

    public static String[] formatOCRData(String ocrData) {
        ocrData = ocrData.replaceAll("[^a-zA-Z0-9áàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ -/\n:]", "");
        ocrData = ocrData.trim();
        String[] tokens = ocrData.split("\n");
        tokens[0] = tokens[0].replaceAll("[^0-9/.\n-]", "");

        return tokens;
    }

    public static String[] spellChecker(String processo) {
        String[] processos = {"000.621/2014-5", "002.964/2012-0", "005.867/2004-9", "008.213/2016-0", "010.450/1997-1",
                "014.252/2015-5", "014.499/2017-7", "001.225/2014-6", "005.972/2015-9", "012.697/2017-6", "016.254/2015-5",
                "020.690/2014-2", "001.208/2015-2", "018.590/2014-4", "019.501/2017-0", "022.094/2008-9", "025.968/2012-2",
                "038.217/2012-0"};

        int indiceMelhorDistancia = 0;
        Integer melhordistancia = 1000000;

        for (int i = 0; i < processos.length; i++) {
            int dist = EditDistance.distance(processos[i], processo);

            if (dist < melhordistancia) {
                melhordistancia = dist;
                indiceMelhorDistancia = i;
            }
        }

        String[] result = {melhordistancia.toString(), processos[indiceMelhorDistancia]};

        return result;
    }
}

