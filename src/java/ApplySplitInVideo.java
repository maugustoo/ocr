import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class ApplySplitInVideo {

    public static void main(String[] args) {
        String dataDir = "E:\\Frames\\28-11-2017_Primeira_Camara_Ordinaria.mp4";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(dataDir));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        byte[] buf = new byte[1024];
        int n;
        try {
            while (-1 != (n = fis.read(buf))) {
                baos.write(buf, 0, n);
            }

            byte[] videoBytes = baos.toByteArray();

            criarThumbnailDoVideo(videoBytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void criarThumbnailDoVideo(byte[] bytes) throws IOException {

        // Instanciando e carregando byte para stream
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // Instanciando conversor
        Java2DFrameConverter converter = new Java2DFrameConverter();

        // Instanciando grabber
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);

        grabber.setImageWidth(1920);
        grabber.setImageHeight(1090);

        System.out.println(grabber.getImageHeight());
        System.out.println(grabber.getImageWidth());

        grabber.start();

        int secondOfVideo = 0;

        // Percorrendo o vídeo, tirando os frames a cada segundo.
        for (long i = 1; i <= grabber.getLengthInTime(); i++) {
            Frame frame = grabber.grabImage();

            BufferedImage grabbedImage = converter.convert(frame);

            if (grabbedImage == null) {
                break;
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(grabbedImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] imageInByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            if (i % 30 == 0) {
                // Pasta na qual será salvo os frames.
                FileOutputStream fos = new FileOutputStream("E:\\Frames\\" + ++secondOfVideo + ".png");
                fos.write(imageInByte);
                FileDescriptor fd = fos.getFD();
                fos.flush();
                fd.sync();
                fos.close();
            }
        }

    }
}