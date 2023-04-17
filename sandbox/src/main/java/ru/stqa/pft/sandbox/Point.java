//формула нахождения расстояния точек вычисляется как sqrt((x2-x1)^2-(y2-y1)^2))
//sqrt((18-10)^2+(5-3)^2)=8,24621


package ru.stqa.pft.sandbox;

public class Point {
  public static double x1;
  public static double y1;
  public static double x2;
  public static double y2;

  public static void main (String[] args) {
    Point p1 = new Point();
    Point p2 = new Point();

    p1.x1 = 10;
    p1.y1 = 3;
    p2.x2 = 18;
    p2.y2 = 5;

    System.out.println("Расстояние между двумя точками: " + p1.toString() + " и " + p2.toString1() + " = " + distance(p1, p2));
  }

  public static double distance(Point p1, Point p2){
    return Math.sqrt((p2.x2 - p1.x1) * (p2.x2 - p1.x1) + (p2.y2 - p1.y1) * (p2.y2 - p1.y1));
  }

  public String toString() {
    return "(" + this.x1 + "; " + this.y1 + ")";
  }


  public String toString1() {
    return "(" + this.x2 + "; " + this.y2 + ")";
  }
}
