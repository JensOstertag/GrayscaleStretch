package de.jensostertag;

import java.awt.image.BufferedImage;

public class GrayscaleStretch {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Missing arguments inputFile and outputFile");
            return;
        }

        System.out.println("Input file: " + args[0]);
        System.out.println("Output file: " + args[1]);

        ImageUtils imageUtils = new ImageUtils();
        BufferedImage original = imageUtils.load(args[0]);

        float minPercentile = 0.25f;
        float maxPercentile = 1.0f;
        if(args.length >= 3) {
            minPercentile = Float.parseFloat(args[2]);
        }
        if(args.length >= 4) {
            maxPercentile = Float.parseFloat(args[3]);
        }

        double min = MathUtils.percentile(imageUtils.grayscaleHistogram(original), minPercentile);
        double max = MathUtils.percentile(imageUtils.grayscaleHistogram(original), maxPercentile);

        BufferedImage contrast = imageUtils.contrastStretch(original, min, max);
        imageUtils.save(contrast, args[1]);
    }
}
