import javax.swing.*;
import java.util.ArrayList;

public class DataSet implements DS{

    private ArrayList<aFrame> data;

    private double fps, cmToPixel;

    private double distanceTraveled;
    private double timeSpentInMiddle;
    private double timeSpentInOuterRing;
    private Point center;
    private double distanceFromWall;
    private ArrayList<Interval> intervalsInOuterRing;
    private ArrayList<Interval> TimesSpentMovingInSpeedIntervals;
    private ArrayList<Interval> intervalsInMiddle;


    public DataSet(double fps, double cmToPixels){
        this.data = new ArrayList<>();
        this.distanceTraveled = 0;
        this.fps = fps;
        this.cmToPixel = cmToPixels;
    }

    public ArrayList<Interval> getIntervalsInMiddle() {
        return intervalsInMiddle;
    }

    public ArrayList<Interval> getIntervalsInOuterRing() {
        return intervalsInOuterRing;
    }


    public double getTimeSpentInMiddle() {
        return timeSpentInMiddle;
    }

    public double getTimeSpentInOuterRing() {
        return timeSpentInOuterRing;
    }


    public void add(aFrame f){
        Point p = data.get(data.size()).getCenter();
        distanceTraveled += Math.sqrt((p.getX() - f.getCenter().getX())*(p.getX() - f.getCenter().getX()) +
                (p.getY() - f.getCenter().getY())*(p.getY() - f.getCenter().getY()));
        data.add(f);

    }

    public double getTotalDistanceTraveled() {
        return distanceTraveled;
    }

    public ArrayList<aFrame> getData() {
        return data;
    }

    public aFrame getFrame(double time){
        return data.get((int) (time*fps));
    }

    public Point getMouseLocation(double time){
        return data.get((int)(time*fps)).getCenter();
    }

    public double getMouseSpeed(double time){
        return data.get((int)(time*fps)).getSpeed();
    }

    public double getAverageSpeed(){
        double tot = 0;
        for (aFrame frame : data) {
            tot += frame.getSpeed();
        }
        return tot/data.size();
    }

    public double getDistanceFromWall() {
        return distanceFromWall;
    }

    public void setDistanceFromWall(double distanceFromWall) {
        this.distanceFromWall = distanceFromWall;
    }
}
