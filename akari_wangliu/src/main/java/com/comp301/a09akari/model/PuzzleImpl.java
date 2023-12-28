package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException("bad board dimensions");
    }
    if (board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException("bad puzzle dimensions");
    }
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    // maybe check if its possible to send a coordinate outside of the actual grid
    if (r >= getHeight() || r < 0 || c >= getWidth() || c < 0) {
      throw new IndexOutOfBoundsException("bad getcelltype index");
    }
    int type = board[r][c];
    if (type <= 4) {
      return CellType.CLUE;
    } else if (type == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r >= getHeight() || r < 0 || c >= getWidth() || c < 0) {
      throw new IndexOutOfBoundsException("bad getcelltype index");
    }
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("getClue not a Clue");
    }
    return board[r][c];
  }
}
