import processing.core.PApplet;

public class DoNothingFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        // we don't change the input image at all!
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.line(10, 10, 20, 20);
    }

}