package com.dotbots.model;

import com.badlogic.gdx.graphics.Color;

public class Piece {

  private float x;
  private float y;
  private Color color;

  public Piece(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public Piece(Piece piece) {
    x = piece.getX();
    y = piece.getY();
    color = piece.getColor();
  }

  public void move(int move) {
    switch (move) {
      case 0:
        y++;
        break;
      case 1:
        x++;
        break;
      case 2:
        y--;
        break;
      case 3:
        x--;
        break;
    }
  }

  public float getX() { return x; }
  public float getY() { return y; }
  public Color getColor() { return color; }

  public void setX(float x) { this.x = x; }
  public void setY(float y) { this.y = y; }
}
