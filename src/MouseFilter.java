import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashSet;

public class MouseFilter implements PixelFilter {

    private static final int TARGET_RADIUS = 200;
    private static final int CENTER_COLUMN = 308;
    private static final int CENTER_ROW = 232;
    private Point center;
    private int rAvg;
    private int cAvg;
    private ArrayList<Point> path;
    private DataSet ds;

    public MouseFilter(){
        path = new ArrayList<>();
        center = new Point(CENTER_ROW, CENTER_COLUMN);
        ds = new DataSet(25, 0.1875);
    }

    @Override
    public DImage processImage(DImage img) {
        ArrayList<Point> list = new ArrayList<>();
        short[][] pixels = img.getBWPixelGrid();

        for(int r = 0; r < img.getHeight(); r++) {
            for(int c = 0; c < img.getWidth(); c++) {
                double ptRadius = center.getDistance(new Point(r, c));

                if(ptRadius < TARGET_RADIUS && pixels[r][c] < 100) {
                    pixels[r][c] = 255;
                    list.add(new Point(r, c));
                }
                else {
                    pixels[r][c] = 0;
                }
            }
        }

        rAvg = 0;
        cAvg = 0;

        for(int i = 0; i < list.size(); i++) {
            rAvg += list.get(i).getR();
            cAvg += list.get(i).getC();
        }

        rAvg /= list.size();
        cAvg /= list.size();

        ds.add(new Point(rAvg, cAvg));

        img.setPixels(pixels);

        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.stroke(0, 255, 255);

        //DOESNT WORK
//        for(int i = 1; i < path.size(); i++) {
//            int pathRow1 = path.get(i).r;
//            int pathCol1 = path.get(i).c;
//            int pathRow2 = path.get(i - 1).r;
//            int pathCol2 = path.get(i - 1).c;
//            window.line(pathCol1, pathRow1, pathCol2, pathRow2);
//        }

        window.fill(255, 0, 0);
        window.ellipse(cAvg, rAvg, 5, 5);

    }

}