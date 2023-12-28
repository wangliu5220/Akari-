package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import java.awt.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageView implements FXComponent {
  private ClassicMvcController controller;
  private Model model;

  public MessageView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    HBox pane = new HBox();
    pane.setPrefHeight(100);
    pane.setPrefWidth(500);
    pane.getChildren().clear();
    pane.getStyleClass().add("vbox");
    Label success;
    if (model.isSolved()) {
      success = new Label("Success!  ");
      success.setStyle("-fx-font-size: 25px");
    } else {
      success = new Label("");
    }
    Label number =
        new Label(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + " of " + model.getPuzzleLibrarySize());
    number.setStyle("-fx-font-size: 25px");
    pane.getChildren().addAll(success, number);
    pane.setAlignment(Pos.CENTER);
    return pane;
  }
}
