package com.example.mazegame.internal;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Player {
  private int x;
  private int y;
  public int radius;

  public Player(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
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

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return x == player.x && y == player.y && radius == player.radius;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, radius);
  }

  @NonNull
  @Override
  public String toString() {
    return "Player{" +
      "x=" + x +
      ", y=" + y +
      ", radius=" + radius +
      '}';
  }
}
