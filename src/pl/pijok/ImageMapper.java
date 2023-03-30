package pl.pijok;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageMapper {

    public TrainData mapImageToTrainData(String path, String group) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int width = image.getWidth();
            int height = image.getHeight();

            Double[] values = new Double[width * height];
            int index = 0;
            for(int row = 0; row < height; row++) {
                for(int col = 0; col < width; col++) {
                    values[index] = (double) image.getRGB(col, row);
                    index++;
                }
            }

            return new TrainData(group, values);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
