package com.example.mazegame.internal;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Cell {
  private int x;
  private int y;
  private int row;
  private int col;
  private boolean maze;
  private boolean visited;
  private Set<Edge> edges;

  @Override
  @NonNull
  public String toString() {
    return "Cell{" +
      "x=" + x +
      ", y=" + y +
      ", row=" + row +
      ", col=" + col +
      ", maze=" + maze +
      ", visited=" + visited +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return x == cell.x && y == cell.y && row == cell.row && col == cell.col && maze == cell.maze;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, row, col, maze);
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public Cell(int x, int y, int row, int col, boolean maze) {
    this.x = x;
    this.y = y;
    this.row = row;
    this.col = col;
    this.maze = maze;
    this.visited = false;
    edges = new HashSet<>();
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
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

  public boolean isMaze() {
    return maze;
  }

  public void setMaze(boolean maze) {
    this.maze = maze;
  }

  public Set<Edge> getEdges() {
    return edges;
  }

  public void setEdges(Set<Edge> edges) {
    this.edges = edges;
  }
}
