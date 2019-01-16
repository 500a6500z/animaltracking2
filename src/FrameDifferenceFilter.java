import processing.core.PApplet;

import javax.swing.*;

public class FrameDifferenceFilter implements PixelFilter {
    private DImage previousImg = null;
    private static final int THRESHOLD = 30;

    @Override
    public DImage processImage(DImage img) {
        if(previousImg == null) {
            previousImg = new DImage(img);
            return img;
        }

        short[][] current = img.getBWPixelGrid();
        short[][] prev = previousImg.getBWPixelGrid();

        for(int r = 0; r < img.getHeight(); r++) {
            for(int c = 0; c < img.getWidth(); c++) {
                if(Math.abs(current[r][c] - prev[r][c]) >= THRESHOLD) {
                    current[r][c] = 255;
                }
                else {
                    current[r][c] = 0;
                }
            }
        }

        previousImg = new DImage(img);

        img.setPixels(current);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}
