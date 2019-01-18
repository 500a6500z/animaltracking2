import javax.swing.*;
import java.util.ArrayList;

public class DataSet {

    private ArrayList<aFrame> data;

    private final int fps = 25;//frames per second

    private double distanceTraveled;
    private double timeSpentInMiddle;
    private double timeSpentInOuterRing;
    private Point center;//uses JOptionPan
    private double radiusFromCenter;//uses JOptionPan
    private double distanceFromWall;//uses JOptionPane
    private ArrayList<Interval> intervalsInOuterRing;
    private ArrayList<Interval> TimesSpentMovingInSpeedIntervals;
    private ArrayList<Interval> intervalsInMiddle;

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

    public DataSet() {
        radiusFromCenter = Double.parseDouble(JOptionPane.showInputDialog("radius from center? "));
        distanceFromWall = Double.parseDouble(JOptionPane.showInputDialog("distance from center? "));
        this.data = new ArrayList<>();
        this.distanceTraveled = 0;
    }

    public void add(aFrame f){
        Point p = data.get(data.size()).getCenter();
        distanceTraveled += Math.sqrt((p.getX() - f.getCenter().getX())*(p.getX() - f.getCenter().getX()) +
                (p.getY() - f.getCenter().getY())*(p.getY() - f.getCenter().getY()));
        data.add(f);
        if(center.getDistance(f.getCenter()) < radiusFromCenter){
            timeSpentInMiddle += 1.0/fps;
        }

        //assign time in ring here
        // assign time codes into arraylist here
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public ArrayList<aFrame> getData() {
        return data;
    }

    public aFrame getFrame(double time){
        return data.get((int) (time*fps));
    }

    public Point getMouseLocation(double time){
        return data.get((int)time*fps).getCenter();
    }

    public double getMouseSpeed(double time){
        return data.get((int) time*fps).getSpeed();
    }

    public double getAverageSpeed(){
        double tot = 0;
        for (int i = 0; i < data.size(); i++) {
            tot+=data.get(i).getSpeed();
        }
        return tot/data.size();
    }

}
