package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private final List<ModelObserver> modelObservers;
  private int currentPuzzleIndex = 0;
  private boolean[][] lamps;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException("bad library");
    }
    this.puzzleLibrary = library;
    modelObservers = new ArrayList<>();
    lamps = new boolean[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      for (int j = 0; j < getActivePuzzle().getWidth(); j++) {
        lamps[r][j] = false;
      }
    }
  }

  @Override
  public void addLamp(int r, int c) {
    validCords(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("bad lamp");
    }
    lamps[r][c] = true;
    notify_observers();
  }

  private void notify_observers() {
    for (ModelObserver modelObserver : modelObservers) {
      modelObserver.update(this);
    }
  }

  private void validCords(int r, int c) {
    Puzzle active_puzzle = getActivePuzzle();
    if (((r >= active_puzzle.getHeight()) || r < 0) || (c >= active_puzzle.getWidth()) || (c < 0)) {
      throw new IndexOutOfBoundsException("out of bounds");
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    validCords(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("bad lamp");
    }
    lamps[r][c] = false;
    notify_observers();
  }

  private boolean isCorridor(int r, int c) {
    return getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR;
  }

  @Override
  public boolean isLit(int r, int c) {
    validCords(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("bad isLit");
    }
    if (lamps[r][c]) {
      return true;
    }
    Puzzle active = getActivePuzzle();
    int horizontal = c;
    while (horizontal >= 0) { // going left
      if (!isCorridor(r, horizontal)) {
        break;
      }
      if (lamps[r][horizontal]) {
        return true;
      }
      horizontal--;
    }
    horizontal = c;
    while (horizontal < active.getWidth()) { // going right
      if (!isCorridor(r, horizontal)) {
        break;
      }
      if (lamps[r][horizontal]) {
        return true;
      }
      horizontal++;
    }
    int vertical = r;
    while (vertical >= 0) { // going up
      if (!isCorridor(vertical, c)) {
        break;
      }
      if (lamps[vertical][c]) {
        return true;
      }
      vertical--;
    }
    vertical = r;
    while (vertical < active.getHeight()) { // going down
      if (!isCorridor(vertical, c)) {
        break;
      }
      if (lamps[vertical][c]) {
        return true;
      }
      vertical++;
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    validCords(r, c);
    if (!isCorridor(r, c)) {
      throw new IllegalArgumentException("not a corridor (isLamp)");
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    validCords(r, c);
    if (!lamps[r][c]) {
      throw new IllegalArgumentException("no lamp here");
    }
    Puzzle active = getActivePuzzle();
    int horizontal = c - 1;
    while (horizontal >= 0) { // going left
      if (!isCorridor(r, horizontal)) {
        break;
      }
      if (lamps[r][horizontal]) {
        return true;
      }
      horizontal--;
    }
    horizontal = c + 1;
    while (horizontal < active.getWidth()) { // going right
      if (!isCorridor(r, horizontal)) {
        break;
      }
      if (lamps[r][horizontal]) {
        return true;
      }
      horizontal++;
    }
    int vertical = r - 1;
    while (vertical >= 0) { // going up
      if (!isCorridor(vertical, c)) {
        break;
      }
      if (lamps[vertical][c]) {
        return true;
      }
      vertical--;
    }
    vertical = r + 1;
    while (vertical < active.getHeight()) { // going down
      if (!isCorridor(vertical, c)) {
        break;
      }
      if (lamps[vertical][c]) {
        return true;
      }
      vertical++;
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(currentPuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return currentPuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= puzzleLibrary.size()) {
      throw new IndexOutOfBoundsException("Set Index error");
    }
    currentPuzzleIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle current = getActivePuzzle();
    lamps = new boolean[current.getHeight()][current.getWidth()];
    notify_observers();
  }

  @Override
  public boolean isSolved() {
    // check for light up
    // check for all clues
    // check for illegal
    Puzzle active = getActivePuzzle();
    for (int r = 0; r < active.getHeight(); r++) {
      for (int c = 0; c < active.getWidth(); c++) {
        if (isCorridor(r, c)) {
          if (lamps[r][c]) { // / might raise an error with lamp??
            if (isLampIllegal(r, c)) {
              return false;
            }
          }
          if (!isLit(r, c)) {
            return false;
          }
        } else if (active.getCellType(r, c) == CellType.CLUE) {
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    validCords(r, c);
    Puzzle active = getActivePuzzle();
    if (active.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("not a clue");
    }
    int clue = active.getClue(r, c);
    if ((c + 1) < active.getWidth() && isCorridor(r, c + 1) && isLamp(r, c + 1)) {
      clue--;
    } // right
    if ((c - 1) >= 0 && isCorridor(r, c - 1) && isLamp(r, c - 1)) {
      clue--;
    } // left
    if ((r + 1) < active.getHeight() && isCorridor(r + 1, c) && isLamp(r + 1, c)) {
      clue--;
    } // down
    if ((r - 1) >= 0 && isCorridor(r - 1, c) && isLamp(r - 1, c)) {
      clue--;
    } // up
    return clue == 0;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("bad observer (add)");
    }
    modelObservers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("bad observer (remove)");
    }
    modelObservers.remove(observer);
  }
}
