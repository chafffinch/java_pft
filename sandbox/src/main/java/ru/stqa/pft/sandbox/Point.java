//формула нахождения расстояния точек вычисляется как sqrt((x2-x1)^2-(y2-y1)^2))
//sqrt((18-10)^2+(5-3)^2)=8,24621

package ru.stqa.pft.sandbox;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p2) {
        return Math.sqrt((this.x - p2.x) * (this.x - p2.x) + (this.y - p2.y) * (this.y - p2.y));
    }
}