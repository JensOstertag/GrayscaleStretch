package de.jensostertag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageUtils {
    public BufferedImage load(String path) {
        File file = new File(path);
        if(!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }

        try {
            return ImageIO.read(file);
        } catch(IOException e) {
            throw new IllegalArgumentException("Could not read file: " + path);
        }
    }

    public int getGrayValue(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb & 0xFF);
        return (r + g + b) / 3;
    }

    public Map<Integer, Integer> grayscaleHistogram(BufferedImage image) {
        Map<Integer, Integer> histogram = new HashMap<>();
        for(int y = 0; y < image.getHeight(); y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                int gray = this.getGrayValue(image, x, y);
                if(!histogram.containsKey(gray)) {
                    histogram.put(gray, 1);
                } else {
                    histogram.put(gray, histogram.get(gray) + 1);
                }
            }
        }

        return histogram;
    }

    private int contrastStretch(int gray, double min, double max) {
        return Math.min(255, Math.max(0, (int) ((gray - min) * 255 / (max - min))));
    }

    public BufferedImage contrastStretch(BufferedImage image, double min, double max) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y < image.getHeight(); y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                int gray = this.getGrayValue(image, x, y);
                int newGray = this.contrastStretch(gray, min, max);
                int rgb = (newGray << 16) | (newGray << 8) | newGray;
                result.setRGB(x, y, rgb);
            }
        }

        return result;
    }

    public void save(BufferedImage image, String path) {
        File file = new File(path);
        try {
            ImageIO.write(image, "png", file);
        } catch(IOException e) {
            throw new IllegalArgumentException("Could not write file: " + path);
        }
    }
}
