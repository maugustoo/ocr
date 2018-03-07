import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class ApplyParallelism {

    public static void main(String[] args) {
        String foldersDir = "E:\\ApplyOCR2\\";

        File folder = new File(foldersDir);

        for (File fileEntry : folder.listFiles()) {
            final String fileName = fileEntry.getName();

            if (FilenameUtils.getExtension(fileName).equals("")) {
                new Thread() {

                    @Override
                    public void run() {
                        String dataDir = "E:\\ApplyOCR2\\" + fileName + "\\";
                        try {
                            MakeAllProcess.createFolders(dataDir);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }
}


