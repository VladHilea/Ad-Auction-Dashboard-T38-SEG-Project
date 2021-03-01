package Models;

import java.util.ArrayList;

public class Chart {
    private ArrayList<ChartPoint> points;

    public Chart(ArrayList<ChartPoint> points) {
        this.points = points;
    }

    public ArrayList<ChartPoint> getPoints() {
        return points;
    }

    public ArrayList<Integer> getXPoints() {
        ArrayList<Integer> xPoints = new ArrayList<>();

        for (ChartPoint point : points) {
            xPoints.add(point.getX());
        }
        return xPoints;
    }

    public ArrayList<Integer> getYPoints() {
        ArrayList<Integer> yPoints = new ArrayList<>();

        for (ChartPoint point : points) {
            yPoints.add(point.getY());
        }
        return yPoints;
    }
}