package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("null model");
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (model.getActivePuzzleIndex() + 1 < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
    // else {throw new IndexOutOfBoundsException("no more next puzzle");}
  }

  @Override
  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() - 1 >= 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    }
    // else {throw new IndexOutOfBoundsException("no prev puzzle");}
  }

  @Override
  public void clickRandPuzzle() {
    int current = model.getActivePuzzleIndex();
    model.setActivePuzzleIndex(new Random().nextInt(model.getPuzzleLibrarySize()));
    while (model.getActivePuzzleIndex() == current) {
      model.setActivePuzzleIndex(new Random().nextInt(model.getPuzzleLibrarySize()));
    }
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
