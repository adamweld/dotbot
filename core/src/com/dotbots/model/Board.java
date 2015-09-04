package com.dotbots.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

  private List<Piece> pieces;
  private List<Wall> walls;
  private int size;

  // constructors
  // ----------------------------------------------------------------------------------------------

  public Board(int size) {
    this.size = size;
    initPieces();
    initWalls();
  }

  public Board(Board board) {
    pieces = new ArrayList<Piece>();
    walls = new ArrayList<Wall>();
    size = board.getSize();
    for (Piece piece : board.getPieces()) {
      pieces.add(new Piece(piece));
    }
    for (Wall wall : board.getWalls()) {
      walls.add(new Wall(wall));
    }
  }

  // initialization
  // ----------------------------------------------------------------------------------------------

  private void initPieces() {
    Object[] keys = {
      2, 7, Color.valueOf("F44336"), // x, y, color
      3, 4, Color.valueOf("2196F3"),
      6, 2, Color.valueOf("4CAF50"),
      7, 7, Color.valueOf("9C27B0")};
    pieces = new ArrayList<Piece>();
    for (int i = 0; i < keys.length; i += 3) {
      Piece piece = new Piece((Integer) keys[i], (Integer) keys[i + 1], (Color) keys[i + 2]);
      pieces.add(piece);
    }
  }

  private void initWalls() {
    int[] keys = {
      0, 2, 0, // x, y, dir
      0, 7, 0,
      2, 7, 0,
      3, 4, 0,
      3, 10, 1,
      4, 0, 0,
      6, 6, 2,
      7, 0, 0,
      7, 3, 2,
      7, 10, 1,
      8, 8, 2,
      10, 2, 2,
      10, 6, 2 };
    walls = new ArrayList<Wall>();
    for (int i = 0; i < keys.length; i += 3) {
      Wall wall = new Wall(keys[i], keys[i + 1], keys[i + 2]);
      walls.add(wall);
    }
  }

  // piece moving
  // ---------------------------------------------------------------------------------------------

  // TODO - optimize this
  public List<Integer> getMoves(Piece piece) {
    Integer[] movesArray = { 0, 1, 2, 3 }; // 0 = up, 1 = right, 2 = down, 3 = left
    List<Integer> moves = new ArrayList<Integer>(Arrays.asList(movesArray));

    // remove moves if you are against map borders
    if (piece.getX() == 0) {
      moves.remove(Integer.valueOf(3));
    } else if (piece.getX() == size - 1) {
      moves.remove(Integer.valueOf(1));
    }
    if (piece.getY() == 0) {
      moves.remove(Integer.valueOf(2));
    } else if (piece.getY() == size - 1) {
      moves.remove(Integer.valueOf(0));
    }

    // remove moves if pieces are right beside you
    for (Piece otherPiece : pieces) {
      if (otherPiece.getX() == piece.getX()) {
        if (otherPiece.getY() == piece.getY() + 1) {
          moves.remove(Integer.valueOf(0));
        } else if (otherPiece.getY() == piece.getY() - 1) {
          moves.remove(Integer.valueOf(2));
        }
      } else if (otherPiece.getY() == piece.getY()) {
        if (otherPiece.getX() == piece.getX() + 1) {
          moves.remove(Integer.valueOf(1));
        } else if (otherPiece.getX() == piece.getX() - 1) {
          moves.remove(Integer.valueOf(3));
        }
      }
    }

    // TODO - this can be optimized for sure, I see a pattern
    // remove moves if walls are right beside you
    for (Wall wall : walls) {
      if (wall.getX() == piece.getX()) {
        if (wall.getY() == piece.getY()) {
          switch (wall.getDir()) {
            case 0:
              moves.remove(Integer.valueOf(2));
              moves.remove(Integer.valueOf(3));
              break;
            case 1:
              moves.remove(Integer.valueOf(2));
              break;
            case 2:
              break;
            case 3:
              moves.remove(Integer.valueOf(3));
              break;
          }
        } else if (wall.getY() == piece.getY() + 1) {
          switch (wall.getDir()) {
            case 0:
              moves.remove(Integer.valueOf(0));
              break;
            case 1:
              moves.remove(Integer.valueOf(0));
              moves.remove(Integer.valueOf(3));
              break;
            case 2:
              moves.remove(Integer.valueOf(3));
              break;
            case 3:
              break;
          }
        }
      } else if (wall.getX() == piece.getX() + 1) {
        if (wall.getY() == piece.getY()) {
          switch (wall.getDir()) {
            case 0:
              moves.remove(Integer.valueOf(1));
              break;
            case 1:
              break;
            case 2:
              moves.remove(Integer.valueOf(2));
              break;
            case 3:
              moves.remove(Integer.valueOf(2));
              moves.remove(Integer.valueOf(1));
              break;
          }
        } else if (wall.getY() == piece.getY() + 1) {
          switch (wall.getDir()) {
            case 0:
              break;
            case 1:
              moves.remove(Integer.valueOf(1));
              break;
            case 2:
              moves.remove(Integer.valueOf(0));
              moves.remove(Integer.valueOf(1));
              break;
            case 3:
              moves.remove(Integer.valueOf(0));
              break;
          }
        }
      }
    }
    return moves;
  }

  public void movePiece(Piece piece, int move) {
    piece.move(move);
    if (getMoves(piece).contains(move)) {
      movePiece(piece, move);
    }
  }

  // getters & setters
  // ----------------------------------------------------------------------------------------------

  public List<Piece> getPieces() { return pieces; }
  public List<Wall> getWalls() { return walls; }
  public int getSize() { return size; }
}
