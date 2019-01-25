/*
David Zhang, Assaf Vayner
The coding was only done by one partner, but the other partner was the one who created the API
As a result, it's pretty incomplete at the moment but it will be finished
 */
import java.util.ArrayList;

public class DataSet implements DS{

    private ArrayList<Point> data;

    private double fps, cmToPixel;

    private double distanceTraveled;
    private double timeSpentInMiddle;
    private double timeSpentInOuterRing;
    private Point center;
    private double distanceFromWall;
    private ArrayList<Interval> intervalsInOuterRing;
    private ArrayList<Interval> TimesSpentMovingInSpeedIntervals;
    private ArrayList<Interval> intervalsInMiddle;


    /*
    Data takes in Point and Speed
     */
    public DataSet(double fps, double cmToPixels){
        this.data = new ArrayList<>();
        this.distanceTraveled = 0;
        this.fps = fps;
        this.cmToPixel = cmToPixels;
    }

    public ArrayList<Interval> getIntervalsInMiddle() {
    }

    public ArrayList<Interval> getIntervalsInOuterRing() {
    }

    public double getTimeSpentInMiddle() {

    }

    public double getTimeSpentInOuterRing() {
        /*
        This would traverse through each interval in geIntervalsInOuterRing and sum the times and then return it
         */
        return timeSpentInOuterRing;
    }


    public void add(Point pt){
        data.add(pt);
    }

    public double getTotalDistanceTraveled() {
        /*
        this would traverse through all the points, sum the distances, and return it
         */
        return distanceTraveled;
    }

    public ArrayList<Point> getData() {
        return data;
    }

    public Point getMouseLocation(double time){
    }

    public double getMouseSpeed(double time){
    }

    public double getAverageSpeed(){
    }

    public double getDistanceFromWall() {
    }

    private double getCmToPixel(int pixels) {

    }

}
