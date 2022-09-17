package com.example.mazegame.internal;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Player {
  private List<PlayerListener> listeners;
  private int x;
  private int y;
  private boolean isColliding;
  private int radius;

  public boolean isColliding() {
    return isColliding;
  }

  public Player(int x, int y, int radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    listeners = new ArrayList<>();
    this.isColliding = false;
  }

  public int getX() {
    return x;
  }

  public void setX(int x, Vector<Vector<Cell>> cells) {
    int oldX = this.x;
    this.x = x;
    this.isColliding = Collision.check(this, cells);
    notifyPositionChange(oldX, y, x, y);
  }

  public int getY() {
    return y;
  }

  public void setY(int y, Vector<Vector<Cell>> cells) {
    int oldY = this.y;
    this.y = y;
    this.isColliding = Collision.check(this, cells);
    notifyPositionChange(x, oldY, x, y);
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public void setPosition(int x, int y, Vector<Vector<Cell>> cells) {
    int oldX = this.x;
    int oldY = this.y;
    this.y = y;
    this.x = x;
    this.isColliding = Collision.check(this, cells);
    notifyPositionChange(oldX, oldY, x, y);
  }

  public void addListener(PlayerListener toAdd) {
    listeners.add(toAdd);
  }

  public void removeListener(PlayerListener toRemove) {
    listeners.remove(toRemove);
  }

  private void notifyPositionChange(int oldX, int oldY, int x, int y) {
    for (PlayerListener listener : listeners) {
      listener.playerPositionChanged(oldX, oldY, x, y);
    }
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
