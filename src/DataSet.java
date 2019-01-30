/*
David Zhang, Assaf Vayner
 */
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet implements DS{

    private ArrayList<Point> data;

    private double fps, cmToPixel;

    private Point center;
    private int fieldRadius;
    private int innerRadius;


    /**
     * Constructs a new DataSet
     * @param fps the frames per second that the video is recorded in
     * @param cmToPixels the conversion rate for how many pixels represent a centimeter
     * @param center the Point that represents the center of the field
     * @param fieldRadius the radius of the field in pixels
     * @param innerRadius the radius of the inner "ring" region of the field in pixels
     */
    public DataSet(double fps, double cmToPixels, Point center, int fieldRadius, int innerRadius){
        this.data = new ArrayList<>();
        this.fps = fps;
        this.cmToPixel = cmToPixels;
        this.center = center;
        this.fieldRadius = fieldRadius;
        this.innerRadius = innerRadius;
    }

    /**
     * Returns the intervals in which the mouse is in the inner region of the field
     * @return an ArrayList containing the various intervals in which the mouse is in the inner region of the field
     */
    public ArrayList<Interval> getIntervalsInInnerRegion() {
        ArrayList<Interval> intervals = new ArrayList<>();
        boolean inInterval = false;
        for(int i = 0; i < data.size(); i++) {
            if(inInnerRegion(data.get(i))) {
                if(!inInterval) {
                    intervals.add(new Interval(i / fps));
                }
                else {
                    intervals.get(intervals.size() - 1).setTf(i / fps);
                }
            }
            else {
                inInterval = false;
            }
        }
        return intervals;
    }

    /**
     * Returns the intervals in which the mouse is in the outer region of the field
     * @return an ArrayList containing the various intervals in which the mouse is in the outer region of the field
     */
    public ArrayList<Interval> getIntervalsInOuterRegion() {
        ArrayList<Interval> intervals = new ArrayList<>();
        boolean inInterval = false;
        for(int i = 0; i < data.size(); i++) {
            if(!inInnerRegion(data.get(i))) {
                if(!inInterval) {
                    intervals.add(new Interval(i / fps));
                }
                else {
                    intervals.get(intervals.size() - 1).setTf(i / fps);
                }
            }
            else {
                inInterval = false;
            }
        }
        return intervals;
    }

    /**
     * Returns the intervals in which the mouse is moving at a speed within a specified range
     * @param speed the specified speed in centimeters per second
     * @param threshold the tolerance for what speed constitutes as valid
     * @return an ArrayList containing the various intervals in which the mouse is moving at a certain speed
     */
    public ArrayList<Interval> getIntervalsAtSpeed(double speed, double threshold) {
        ArrayList<Interval> intervals = new ArrayList<>();
        boolean inInterval = false;
        for(int i = 0; i < data.size(); i++) {
            if(Math.abs(getMouseSpeed(i / fps) - speed) <= threshold) {
                if(!inInterval) {
                    intervals.add(new Interval(i / fps));
                }
                else {
                    intervals.get(intervals.size() - 1).setTf(i / fps);
                }
            }
            else {
                inInterval = false;
            }
        }
        return intervals;
    }

    /**
     * Returns the total amount of time spent by the mouse in the inner region of the field
     * @return the amount of time in seconds
     */
    public double getTimeSpentInInnerRegion() {
        ArrayList<Interval> intervals = getIntervalsInInnerRegion();
        double time = 0;
        for(int i = 0; i < intervals.size(); i++) {
            time += intervals.get(i).getDuration();
        }
        return time;
    }

    /**
     * Returns the total amount of time spent by the mouse in the outer region of the field
     * @return the amount of time in seconds
     */
    public double getTimeSpentInOuterRegion() {
        ArrayList<Interval> intervals = getIntervalsInOuterRegion();
        double time = 0;
        for(int i = 0; i < intervals.size(); i++) {
            time += intervals.get(i).getDuration();
        }
        return time;
    }

    /**
     * Returns the total amount of time spent by the mouse at a specified speed
     * @param speed the specified speed in centimeters per second
     * @param threshold the tolerance for what speed constiutes as valid
     * @return the amount of time in seconds
     */
    public double getTimeSpentAtSpeed(double speed, double threshold) {
        ArrayList<Interval> intervals = getIntervalsAtSpeed(speed, threshold);
        double time = 0;
        for(int i = 0; i < intervals.size(); i++) {
            time += intervals.get(i).getDuration();
        }
        return time;
    }

    /**
     * Adds a Point to the DataSet
     * @param pt the Point to be added
     */
    public void add(Point pt){
        data.add(pt);
    }

    /**
     * Returns the total distance traveled by the mouse in the specifed interval
     * @param start the starting time of interest in seconds
     * @param end the ending time of interest in seconds
     * @return the total distance traveled in centimeters
     */
    public double getTotalDistanceTraveled(double start, double end) {
        int startFrames = (int) (start / fps);
        int endFrames = (int) (end / fps);

        int pixelsTraveled = 0;

        for(int i = startFrames + 1; i < endFrames; i++) {
            pixelsTraveled += data.get(i).getDistance(data.get(i-1));
        }
        return getCmToPixel(pixelsTraveled);
    }

    /**
     * Returns all the previous locations of the mouse
     * @return an ArrayList containing all the previous locations of the mouse, in Point format
     */
    public ArrayList<Point> getData() {
        return data;
    }

    /**
     * Returns the mouse's location at the specified time
     * @param time the time of interest, in seconds
     * @return the Point containing the location of the mouse
     */
    public Point getMouseLocation(double time){
        int frame = (int) (time * fps);
        return data.get(frame);
    }

    /**
     * Returns the mouse's speed at the specified time
     * @param time the time of interest, in seconds
     * @return the speed of the mouse, in centimeters per second
     */
    public double getMouseSpeed(double time){
        if(time == 0) {
            return 0;
        }
        int frame = (int) (time * fps);
        double distance = data.get(frame).getDistance(data.get(frame - 1));
        return distance / (1/fps);
    }

    /**
     * Returns the mouse's average speed over a specified interval
     * @param start the starting time of interest, in seconds
     * @param end the ending time of interest, in seconds
     * @return the average speed of the mouse, in centimeters per second
     */
    public double getAverageSpeed(double start, double end){
        int startFrames = (int) (start / fps);
        int endFrames = (int) (end / fps);

        if(endFrames == startFrames) {
            return 0;
        }

        double totalSpeed = 0;

        for(int i = startFrames + 1; i < endFrames; i++) {
            totalSpeed += getMouseSpeed(i);
        }

        return totalSpeed / (endFrames - startFrames);
    }

    /**
     * Returns the mouse's closest distance to the wall at the specified time
     * @param time the time of interest, in seconds
     * @return the mouse's distance from the wall in centimeters
     */
    public double getDistanceFromWall(double time) {
        int frame = (int) (time * fps);
        double distanceFromCenter = data.get(frame).getDistance(center);
        return getCmToPixel((int) (fieldRadius - distanceFromCenter));

    }

    private double getCmToPixel(int pixels) {
        return (double) pixels / cmToPixel;
    }

    private boolean inInnerRegion(Point pt) {
        return center.getDistance(pt) <= innerRadius;
    }


    private void writeDataToFile(String filePath, String dataStr){
        File outFile = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))){
            writer.write(dataStr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private String readFileAsString(String filePath){
        StringBuilder output = new StringBuilder();

        try(Scanner scanner = new Scanner(new File(filePath))){
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                output.append(line + System.getProperty("Line.separator"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return output.toString();
    }

    public void saveDataToFile(String file) {
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < data.size(); i++) {
            output.append(data.get(i).toString() + "\n");
        }

        writeDataToFile(file, output.toString());
    }

}
