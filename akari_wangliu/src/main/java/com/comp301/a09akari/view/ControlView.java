package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import java.awt.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ControlView implements FXComponent {
  private ClassicMvcController controller;
  private Model model;

  public ControlView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    HBox hBox = new HBox();
    Button random = new Button("Random");
    Button previous = new Button("Previous");
    Button reset = new Button("Reset");
    Button next = new Button("Next");
    random.setOnAction((ActionEvent e) -> controller.clickRandPuzzle());
    reset.setOnAction((ActionEvent e) -> controller.clickResetPuzzle());
    next.setOnAction((ActionEvent e) -> controller.clickNextPuzzle());
    previous.setOnAction((ActionEvent e) -> controller.clickPrevPuzzle());
    Button done = new Button("Done");
    done.setOnAction(
        (ActionEvent e) -> {
          if (model.isSolved()) {
            done.setStyle("-fx-background-color: green");
            done.setTextFill(Color.BLACK);
            done.setText("Correct!");
          } else {
            done.setStyle("-fx-background-color: red");
            done.setTextFill(Color.BLACK);
            done.setText("Something isn't right...try again");
          }
        });
    VBox vBox = new VBox();
    hBox.getChildren().addAll(previous, next, reset, random);
    vBox.getChildren().addAll(hBox, done);
    vBox.setAlignment(Pos.CENTER);
    hBox.setAlignment(Pos.CENTER);
    return vBox;
  }
}
