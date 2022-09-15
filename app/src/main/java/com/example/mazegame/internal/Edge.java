package com.example.mazegame.internal;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge {
  private boolean visible;
  private List<Cell> cells;
  private int x0;
  private int y0;
  private int x;
  private int y;
  private int length;

  public Edge(int x0, int y0, int x, int y, int length) {
    this.cells = new ArrayList<>();
    this.x0 = x0;
    this.y0 = y0;
    this.x = x;
    this.y = y;
    this.length = length;
    this.visible = true;
  }

  @NonNull
  @Override
  public String toString() {
    return "Edge{" +
      "x0=" + x0 +
      ", y0=" + y0 +
      ", x=" + x +
      ", y=" + y +
      ", cells=" + cells +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge edge = (Edge) o;
    return (x0 == edge.x0 && y0 == edge.y0 && x == edge.x && y == edge.y) || (x == edge.x0 && y == edge.y0 && x0 == edge.x && y0 == edge.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(visible, cells, x0, y0, x, y, length);
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public List<Cell> getCells() {
    return cells;
  }

  public void setCells(List<Cell> cells) {
    this.cells = cells;
  }

  public int getX0() {
    return x0;
  }

  public void setX0(int x0) {
    this.x0 = x0;
  }

  public int getY0() {
    return y0;
  }

  public void setY0(int y0) {
    this.y0 = y0;
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

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }
}
