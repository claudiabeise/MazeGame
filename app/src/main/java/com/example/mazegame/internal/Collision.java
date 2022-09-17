package com.example.mazegame.internal;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Collision {
  public static boolean check(Player player, Vector<Vector<Cell>> cells) {
    int edgeLength = cells.get(0).get(0).getEdges().stream().findFirst().get().getLength();
    int i = player.getX() / edgeLength;
    int j = player.getY() / edgeLength;
    Set<Edge> cellEdges = cells.get(i).get(j).getEdges();
    List<Point> playerPoints = new ArrayList<>();
    playerPoints.add(new Point(player.getX() + player.getRadius(), player.getY()));
    playerPoints.add(new Point(player.getX(), player.getY() + player.getRadius()));
    playerPoints.add(new Point(player.getX() - player.getRadius(), player.getY()));
    playerPoints.add(new Point(player.getX(), player.getY() - player.getRadius()));

    for (Point p : playerPoints) {
      for (Edge e : cellEdges) {
        if (e.isVisible() && intersect(p, e, player.getRadius())) {
          return true;
        }
      }
    }
    return false;
  }

  static boolean intersect(Point point, Edge edge, int radius) {
    Edge.Line line = edge.getLine();
    return intersect(line.a, line.b, line.c, point.x, point.y, radius);
  }

  static boolean intersect(double a, double b, double c, double x, double y, double radius) {
    double dist = (Math.abs((a * x) + (b * y) + c)) / Math.sqrt((a * a) + (b * b));
    return dist <= radius;
  }

  static class Point {
    int x;
    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    @NonNull
    public String toString() {
      return "Point{" +
        "x=" + x +
        ", y=" + y +
        '}';
    }
  }
}
