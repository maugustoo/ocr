import com.aspose.ocr.ImageStream;
import com.aspose.ocr.LanguageFactory;
import com.aspose.ocr.License;
import com.aspose.ocr.OcrEngine;

public class PerformOCROnImage {
    public static void main(String[] args) throws Exception
    {
        License license = new License();
        license.setLicense("D:\\Users\\clindembergmp\\Desktop\\Aspose.Total.Java.lic");

        // The path to the documents directory.
        String dataDir = "E:\\28-11-2017_Primeira_Camara_Ordinaria.mp4.frame\\";

        // / Set the paths
        String imagePath = dataDir + "scene03001_out_end_negative.jpg"; // "scene03001_out.jpg"; // "scene03001.png";

        // Create an instance of OcrEngine
        OcrEngine ocr = new OcrEngine();

        // Set image file
        ocr.setImage(ImageStream.fromFile(imagePath));

        // Clear the default language (English)
        ocr.getLanguageContainer().clear();

        // Load the resources of the language from file path location or an instance of InputStream
        ocr.getLanguageContainer()
                .addLanguage(
                        LanguageFactory
                                .load("D:\\Aspose.Words-for-Java-master\\Examples\\src\\main\\resources\\Portuguese_language_resource_file_for_Aspose.OCR_for_.NET_3.2.0.zip"));

        // Perform OCR and get extracted text
        try {

            // // Set filters
            // Create CorrectionFilters collection
            // CorrectionFilters filters = new CorrectionFilters();
            // Filter filter = null;
            //
            // // Initialize Median filter
            // filter = new MedianFilter(5);
            // filters.add(filter);
            //
            // // Create Gaussian Blur filter
            // filter = new GaussBlurFilter();
            // filters.add(filter);
            //
            // // Assign collection to OcrEngine
            // ocr.getConfig().setCorrectionFilters(filters);

            if (ocr.process()) {
                System.out.println("Resultado: " + ocr.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}