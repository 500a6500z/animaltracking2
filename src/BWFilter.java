import processing.core.PApplet;

public class BWFilter implements PixelFilter {

    private static final int THRESHOLD = 100;

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();

        for(int r = 0; r < pixels.length; r++) {
            for(int c = 0; c < pixels[r].length; c++) {
                if(pixels[r][c] > THRESHOLD) {
                    pixels[r][c] = 255;
                }
                else {
                    pixels[r][c] = 0;
                }
            }
        }

        img.setPixels(pixels);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}
