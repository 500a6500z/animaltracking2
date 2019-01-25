import java.util.ArrayList;

public interface DS {


    public ArrayList<Interval> getIntervalsInMiddle();

    public ArrayList<Interval> getIntervalsInOuterRing();

    public double getTimeSpentInMiddle();

    public double getTimeSpentInOuterRing();

    public void add(Point f);

    public double getTotalDistanceTraveled();

    public ArrayList<Point> getData();

    public Point getMouseLocation(double time);

    public double getMouseSpeed(double time);

    public double getAverageSpeed();

    public double getDistanceFromWall();

    public void setDistanceFromWall(double distanceFromWall);

}
