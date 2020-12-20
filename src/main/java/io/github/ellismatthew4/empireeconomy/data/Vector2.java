package io.github.ellismatthew4.empireeconomy.data;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double angleTo(Vector2 vec) {
        return Math.atan2(y - vec.y,x - vec.x) * (180/Math.PI);
    }

    public Integer distanceTo(Vector2 vec) {
        return (int) Math.sqrt(((vec.x-x) * (vec.x-x)) + ((vec.y-y) * (vec.y-y)));
    }
}
