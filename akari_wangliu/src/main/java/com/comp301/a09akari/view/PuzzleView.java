package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PuzzleView implements FXComponent {
  private ClassicMvcController controller;
  private Model model;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane gridPane = new GridPane();
    for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {
        if (model.getActivePuzzle().getCellType(r, c) == CellType.WALL) {
          Button button = new Button("  ");
          button.setPrefSize(40, 40);
          button.setMaxSize(40, 40);

          button.setStyle(
              "-fx-background-color: GRAY; -fx-border-width: 1px; -fx-border-color: BLACK");
          gridPane.add(button, r, c);
        } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
          Button button = new Button("  ");
          button.setPrefSize(40, 40);
          button.setMaxSize(40, 40);
          button.setMinSize(40, 40);
          final int tempr = r;
          final int tempc = c;
          button.setOnAction((ActionEvent e) -> controller.clickCell(tempr, tempc));
          gridPane.add(button, r, c);
          if (model.isLit(r, c)) {
            //                        if(model.isLampIllegal(r,c)){
            //                            button.setStyle("-fx-background-color:
            // RED;-fx-border-color: BLACK; -fx-border-width: 1px");
            //                        }
            if (!model.isLamp(r, c)) {
              button.setStyle(
                  "-fx-background-color: GOLD; -fx-border-color: BLACK; -fx-border-width: 1px");
            }
          } else {
            button.setStyle(
                "-fx-background-color: WHITE; -fx-border-color: BLACK; -fx-border-width: 1px");
          }
          if (model.isLamp(r, c)) {
            if (model.isLampIllegal(r, c)) {
              button.setStyle(
                  "-fx-border-width: 1px;-fx-border-color: BLACK; -fx-background-color: RED");
              Image lightBulb = new Image("light-bulb.png");
              ImageView imageView = new ImageView(lightBulb);
              ColorAdjust colorAdjust = new ColorAdjust();
              button.setMinSize(40, 40);
              button.setMaxSize(40, 40);
              colorAdjust.setHue(.2); // Set the hue value to tint the image (0.0 to 1.0)
              colorAdjust.setBrightness(0.2); // Set the brightness value (-1.0 to 1.0)
              colorAdjust.setContrast(1.2); // Set the contrast value (> 0.0)
              colorAdjust.setSaturation(1.5); // Set the saturation value (> 0.0)
              imageView.setEffect(colorAdjust);
              imageView.setFitHeight(20);
              imageView.setFitWidth(20);
              imageView.setStyle("-fx-border-color: black; fx-border-width: 1px");
              button.setGraphic(imageView);
            } else {
              button.setStyle(
                  "-fx-border-color: BLACK; -fx-border-width: 1px; -fx-background-color: GOLD");
              Image lightBulb = new Image("light-bulb.png");
              ImageView imageView = new ImageView(lightBulb);
              ColorAdjust colorAdjust = new ColorAdjust();
              button.setMinSize(40, 40);
              button.setMaxSize(40, 40);
              colorAdjust.setHue(.2); // Set the hue value to tint the image (0.0 to 1.0)
              colorAdjust.setBrightness(0.2); // Set the brightness value (-1.0 to 1.0)
              colorAdjust.setContrast(1.2); // Set the contrast value (> 0.0)
              colorAdjust.setSaturation(1.5); // Set the saturation value (> 0.0)
              imageView.setEffect(colorAdjust);
              imageView.setFitHeight(20);
              imageView.setFitWidth(20);
              imageView.setStyle("-fx-border-color: BLACK; fx-border-width: 2px");
              button.setGraphic(imageView);
            }
          }
        } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          Button button = new Button(String.valueOf(model.getActivePuzzle().getClue(r, c)));
          button.setPrefSize(40, 40);
          button.setMaxSize(40, 40);
          button.setTextFill(Color.BLACK);
          button.setStyle(
              "-fx-font-size:15px;-fx-font-weight: bold; -fx-border-color: BLACK; -fx-background-color: DEEPSKYBLUE");
          if (model.isClueSatisfied(r, c)) {
            button.setStyle(
                "-fx-font-weight: bold;-fx-font-size:15px; -fx-background-color: GREEN");
          }

          gridPane.add(button, r, c);
        }
      }
    }
    gridPane.setAlignment(Pos.CENTER);
    return gridPane;
  }
}
