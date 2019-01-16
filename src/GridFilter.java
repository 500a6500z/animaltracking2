import processing.core.PApplet;

import javax.swing.*;

public class GridFilter implements PixelFilter {
    private int N;

    public GridFilter() {
        N = Integer.parseInt(JOptionPane.showInputDialog("input N"));
    }
    @Override
    public DImage processImage(DImage img) {
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        int rSpace = original.getHeight() / N;
        int cSpace = original.getWidth() / N;
        for(int i = 0; i < original.getHeight(); i += rSpace) {
            window.line(0, i, original.getWidth(), i);
        }
        for(int i = 0; i < original.getWidth(); i += cSpace) {
            window.line(i, 0, i, original.getHeight());
        }
    }
}
