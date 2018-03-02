import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.aspose.imaging.Image;
import com.aspose.imaging.License;
import com.aspose.imaging.RasterImage;

public class ApplyFilter {
    public static void main(String... args) throws Exception {
        // The path to the documents directory.
        License license = new License();
        license.setLicense(Thread.currentThread().getContextClassLoader().getResourceAsStream("Aspose.Total.Java.lic"));

        if (License.isLicensed()) {
            String dataDir = "E:\\Frames\\";
            String imagePath = null;

            File folder = new File(dataDir);
            String fileName = null;
            String outputPathJpg = null;
            for (File fileEntry : folder.listFiles()) {

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
        } else {
            System.out.println("Aspose n?o licenciado");
        }
    }
}

