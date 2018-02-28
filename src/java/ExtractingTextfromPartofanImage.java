import com.aspose.ocr.IRecognizedPartInfo;
import com.aspose.ocr.IRecognizedTextPartInfo;
import com.aspose.ocr.ImageStream;
import com.aspose.ocr.License;
import com.aspose.ocr.OcrEngine;
import com.aspose.ocr.RecognitionBlock;

public class ExtractingTextfromPartofanImage {
    public static void main(String[] args) throws Exception {
        // ExStart:ExtractingTextfromPartofanImage
        // Initialize an instance of OcrEngine
        License license = new License();
        license.setLicense("D:\\Users\\clindembergmp\\Desktop\\Aspose.Total.Java.lic");

        String dataDir = "E:\\28-11-2017_Primeira_Camara_Ordinaria.mp4.frame\\";
        String imagePath = dataDir + "scene03001.png";

        OcrEngine ocrEngine = new OcrEngine();

        // Clear notifier list
        ocrEngine.clearNotifies();

        // Clear recognition blocks
        ocrEngine.getConfig().clearRecognitionBlocks();

        // Add 2 rectangle blocks to user defined recognition blocks
        ocrEngine.getConfig().addRecognitionBlock(RecognitionBlock.createTextBlock(0, 860, 1590, 160));
        // ocrEngine.getConfig().addRecognitionBlock(RecognitionBlock.createTextBlock(990, 46, 38, 46));

        // Ignore everything else on the image other than the user defined
        // recognition blocks
        ocrEngine.getConfig().setDetectTextRegions(false);

        // Set Image property by loading an image from file path
        ocrEngine.setImage(ImageStream.fromFile(imagePath));

        // Run recognition process
        if (ocrEngine.process()) {
            for (IRecognizedPartInfo info : ocrEngine.getText().getPartsInfo()) {
                IRecognizedTextPartInfo textInfo = (IRecognizedTextPartInfo) info;
                System.out.println("Block: " + info.getBox() + " Text: " + textInfo.getText());
            }
        }
        // ExEnd:ExtractingTextfromPartofanImage
    }

}