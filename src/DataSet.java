import java.util.ArrayList;

public class DataSet {

    private ArrayList<aFrame> data;

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    private double distanceTraveled;


    //total time spent close to the wall
    //time spent close to center
    //Histogram of speeds. (in cm/sec)

    public DataSet() {
        this.data = new ArrayList<>();
        this.distanceTraveled = 0;
    }

    public void add(aFrame f){
        Point p = data.get(data.size()).getCenter();
        distanceTraveled += Math.sqrt((p.getX() - f.getCenter().getX())*(p.getX() - f.getCenter().getX()) +
                (p.getY() - f.getCenter().getY())*(p.getY() - f.getCenter().getY()));
        data.add(f);
    }


    public ArrayList<aFrame> getData() {
        return data;
    }

    public aFrame getFrame(int index){
        return data.get(index);
    }

}
