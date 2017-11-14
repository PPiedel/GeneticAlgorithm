package main.model;

/**
 * Created by Pawel_Piedel on 09.10.2017.
 */
public class City {
    private double x;
    private double y;

    public City(double x, double y) {
        this.x = x;
        this.y = y;

    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(City city) {
        return Math.sqrt((x - city.getX()) * (x - city.getX()) + (y - city.getY()) * (y - city.getY()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (Double.compare(city.x, x) != 0) return false;
        return Double.compare(city.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
