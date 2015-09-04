package com.dotbots.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.dotbots.model.Board;
import com.dotbots.model.Piece;

public class InputHandler implements InputProcessor {

  private Board board;
  private BoardViewTranslator bvt;
  private int screenHeight;

  private boolean touched;
  private Piece touchedPiece;
  private float touchX;
  private float touchY;

  public InputHandler(Board board, BoardViewTranslator bvt, int screenHeight) {
    this.board = board;
    this.bvt = bvt;
    this.screenHeight = screenHeight;
    touched = false;
  }

  // An important thing to note is the difference in coordinate systems between the drawer and the
  //   input processor. The drawer (and bvt) say y = 0 is the bottom of the screen. The
  //   InputProcessor claims y = 0 is the top
  @Override
  public boolean touchDown(int x, int y, int pointer, int button) {
    y = screenHeight - y;
    for (int i = 0; i < bvt.getPieces().size(); i++) {
      float dx = x - bvt.getPieces().get(i).getX();
      float dy = y - bvt.getPieces().get(i).getY();
      if (Math.sqrt(dx * dx + dy * dy) < bvt.spotWidth / 2) {
        touchX = x;
        touchY = y;
        touched = true;
        touchedPiece = board.getPieces().get(i);
      }
    }
    return true;
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    y = screenHeight - y;
    if (touched) {
      float dx = x - touchX;
      float dy = y - touchY;
      if (Math.sqrt(dx * dx + dy * dy) > bvt.spotWidth * 2) {
        int move;
        if (Math.abs(dy) > Math.abs(dx)) {
          if (dy > 0) {
            move = 0;
          } else {
            move = 2;
          }
        } else {
          if (dx > 0) {
            move = 1;
          } else {
            move = 3;
          }
        }
        if (board.getMoves(touchedPiece).contains(move)) {
          touched = false;
          board.movePiece(touchedPiece, move);
          bvt.translate();
        }
      }
    }
    return true;
  }

  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    touched = false;
    return true;
  }

  // unused inputs
  // ----------------------------------------------------------------------------------------------

  @Override public boolean keyDown(int keycode) { return false; }
  @Override public boolean keyUp(int keycode) { return false; }
  @Override public boolean keyTyped(char character) { return false; }
  @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
  @Override public boolean scrolled(int amount) { return false; }
}
