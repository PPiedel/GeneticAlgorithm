package main;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class City {
    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distanceTo(City city) {
        return Math.sqrt((x - city.getX()) * (x - city.getX()) + (y - city.getY()) * (y - city.getY()));
    }

    @Override
    public String toString() {
        return "City{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
