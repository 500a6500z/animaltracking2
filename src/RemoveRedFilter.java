import processing.core.PApplet;

        import java.util.Arrays;

public class RemoveRedFilter implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] blackPixels = new short[img.getHeight()][img.getWidth()];

        for(int i = 0; i < blackPixels.length; i++) {
            Arrays.fill(blackPixels[i], 100);
        }

        img.setRedChannel(blackPixels);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}
