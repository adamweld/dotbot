package com.dotbots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.dotbots.model.Board;
import com.dotbots.util.BoardDrawer;
import com.dotbots.util.BoardViewTranslator;
import com.dotbots.util.InputHandler;

public class DotBotsGame extends ApplicationAdapter {

  private BoardDrawer boardDrawer;

  @Override
  public void create () {
    // get screen width and height
    int w = Gdx.graphics.getWidth();
    int h = Gdx.graphics.getHeight();

    // new board and its board drawer
    Board board = new Board(10);
    BoardViewTranslator bvt = new BoardViewTranslator(board, w, h);
    boardDrawer = new BoardDrawer(bvt);

    // handle input
    Gdx.input.setInputProcessor(new InputHandler(board, bvt, h));
  }

  @Override
  public void render () {
    // set up the screen
    Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // draw all the things
    boardDrawer.draw();
  }
}
