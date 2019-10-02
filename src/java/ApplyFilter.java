import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.aspose.imaging.Image;
import com.aspose.imaging.License;
import com.aspose.imaging.RasterImage;

public class ApplyFilter {
    public static void main(String... args) {
        String dataDir = "E:\\TesteFinal\\2016\\09-10\\18-10-2016_Segunda_Camara_Ordinaria\\";

        try {
            takeImagesAndApplyFilter(dataDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeImagesAndApplyFilter(String dataDir) throws Exception {
        // The path to the documents directory.
        License license = new License();
        license.setLicense(Thread.currentThread().getContextClassLoader().getResourceAsStream("Aspose.Total.Java.lic"));

        if (License.isLicensed()) {
            String imagePath = null;

            File folder = new File(dataDir);
            String fileName = null;
            String outputPathJpg = null;

            for (File fileEntry : folder.listFiles()) {
                try {
                    fileName = fileEntry.getName();

                    if (!FilenameUtils.getExtension(fileName).equals("png")) {
                        continue;
                    }

                    imagePath = dataDir + fileName;

                    com.aspose.imaging.fileformats.png.PngImage png = (com.aspose.imaging.fileformats.png.PngImage)
                            com.aspose.imaging.Image
                                    .load(imagePath);

                    String[] time = fileName.split("[.]");
                    outputPathJpg = dataDir + String.format("%05d", Integer.parseInt(time[0])) + ".jpg";
                    png.save(outputPathJpg, new com.aspose.imaging.imageoptions.JpegOptions());

                    applyFilter(outputPathJpg);
                } catch (Exception e) {
                    continue;
                }
            }
        } else {
            System.out.println("Aspose n?o licenciado");
        }
    }

    public static void applyFilter(String outputPathJpg) throws Exception {
        RasterImage image = (RasterImage) Image.load(outputPathJpg);

        try {
            image.crop(375, 370, 880, 66);
            image.adjustBrightness(-100);
            image.adjustContrast(100);
            image.grayscale();
            image.binarizeOtsu();
            image.adjustGamma(2.5f, 2f, 2f);
            image.save(outputPathJpg);

        } finally {
            image.dispose();
        }
    }
}




