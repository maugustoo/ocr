import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.sourceforge.tess4j.Tesseract;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class ApplyOCR {

    public static void main(String[] args) throws Exception {
        String dataDir = "E:\\Frames2\\";

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
                    String[] spellCheckerResult = spellChecker(tokens[0]);

                    stringBuilder.append("\"distance\": \"" + spellCheckerResult[0] + "\",");

                    stringBuilder.append("\"processo\": \"" + spellCheckerResult[1] + "\",");

                    tokens[1] = tokens[1].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ0-9\n ]", "");
                    stringBuilder.append("\"nome\": \"" + tokens[1].trim() + "\",");

                    tokens[2] = tokens[2].replaceAll("[^a-zA-ZáàâãéèêíïóôõöúçñÁÀ ÃÉÈÍÏÓÔÕÖÚÇÑ\n ]", "");
                    stringBuilder.append("\"relator\": \"" + tokens[2].trim() + "\"");
                    stringBuilder.append("}");

                    try {
                        FileWriter arq = new FileWriter(dataDir + "resultado.txt", true);
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

    public static String[] spellChecker(String processo) {
        String[] processos = {"006.431/2016-0",
                "020.164/2015-7",
                "031.166/2015-6",
                "000.317/2002-0",
                "001.476/2015-7",
                "014.798/2010-7",
                "025.974/2014-9",
                "029.962/2014-5",
                "000.524/2016-6",
                "000.524/2016-6",
                "000.524/2016-6",
                "001.254/2017-0",
                "007.389/2017-5",
                "008.770/2015-8",
                "009.984/2014-3",
                "010.256/2013-0",
                "013.835/2012-2",
                "016.816/2015-3",
                "025.409/2017-4",
                "035.738/2015-4",
                "006.921/2014-0",
                "006.924/2014-0",
                "009.724/2015-0",
                "012.748/2009-9",
                "017.079/2014-4",
                "021.519/2016-1",
                "033.235/2016-3"};

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


