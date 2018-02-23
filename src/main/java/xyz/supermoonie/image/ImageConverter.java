package xyz.supermoonie.image;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author moonie
 * @date 2017/10/11
 */
public class ImageConverter {


    private ImageConverter() {
    }

    public static String imageToBase64(BufferedImage image, String formatName) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(imageToBytes(image, formatName));
    }

    public static byte[] imageToBytes(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, formatName, outputStream);
            return outputStream.toByteArray();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static BufferedImage base64ToImage(String base64Image) throws IOException {
        ByteArrayInputStream inputStream = null;
        try {
            byte[] bytes = base64ToBytes(base64Image);
            inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] base64ToBytes(String base64Image) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Image);
    }

}