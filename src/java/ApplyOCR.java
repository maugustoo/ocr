import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import net.sourceforge.tess4j.Tesseract;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class ApplyOCR {

    public static void main(String[] args) throws Exception {

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
                // System.out.println(texto);
                // System.out.println("TEXTO_FORMATADO");
                // texto = texto.replaceAll("[^a-zA-Z0-9 -/\n:]", "");
                // System.out.println(texto);
                String[] tokens = texto.split("\n");
                for (int i = 0; i < tokens.length; i++) {
                    if (i == 0) {
                        stringBuilder.append(tokens[i]);
                    } else {
                        stringBuilder.append(" - ");
                        stringBuilder.append(tokens[i]);
                    }

                }

                FileWriter arq = new FileWriter("E:\\Frames\\resultado.txt", true);
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.printf("Tempo em segundos: " + fileName + ":" + stringBuilder.toString() + "%n");
                arq.close();

                System.out.println(stringBuilder.toString());
                System.out.println();

            } else {
                System.out.println("LIXO!!!!!");
            }

        }
    }
}
