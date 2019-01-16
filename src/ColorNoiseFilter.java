import processing.core.PApplet;

public class ColorNoiseFilter implements PixelFilter {

    private static final double PROB_TO_ADD = .25;

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        for(int r = 0; r < red.length; r++) {
            for(int c = 0; c < red[r].length; c++) {
                if(Math.random() < PROB_TO_ADD) {
                    red[r][c] = getRandom(256);
                    green[r][c] = getRandom(256);
                    blue[r][c] = getRandom(256);
                }
            }
        }

        img.setRedChannel(red);
        img.setGreenChannel(green);
        img.setBlueChannel(blue);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }

    private short getRandom(int i) {
        return (short) (Math.random() * i);
    }
}
