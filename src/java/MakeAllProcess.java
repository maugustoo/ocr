




import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class MakeAllProcess {

    public static void main(String[] args) throws Exception {
        String dataDir = "E:\\ApplyOCR\\";

        createFolders(dataDir);

    }

    // Cria uma pasta onde será feito o processo de OCR para cada vídeo no formato .mp4 que estiver na pasta designada
    public static void createFolders(String dataDir) throws Exception {
        File folder = new File(dataDir);

        for (File fileEntry : folder.listFiles()) {
            String fileName = fileEntry.getName();
            File dir = null;
            // Cria a pasta onde será salvo as imagens recortadas e os arquivos de saída da leitura OCR
            // Sendo uma pasta para cada arquivo .mp4
            if (FilenameUtils.getExtension(fileName).equals("mp4")) {
                String fileNameAux = fileName.replace(".mp4", "");
                dir = new File(dataDir + fileNameAux);
                dir.mkdir();
            }

            System.out.println(dataDir + fileName + " " + dir.getPath());

            apply(dataDir + fileName, dir.getPath() + "\\");
        }
    }

    public static void apply(String dataDir, String dirImages) throws Exception {
        ApplySplitInVideo.splitVideo(dataDir, dirImages);
        ApplyFilter.takeImagesAndApplyFilter(dirImages);

        // Pasta onde está localizado a pasta tessdata
        String tesseractDir = dataDir;

        ApplyOCR.applyOCR(dirImages, tesseractDir);
        FormatOutput.applyFormatOutput(dirImages);
    }

}



