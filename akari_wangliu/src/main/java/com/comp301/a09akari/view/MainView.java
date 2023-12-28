package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public MainView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {

    FXComponent controlView = new ControlView(controller, model);
    FXComponent puzzleView = new PuzzleView(controller, model);
    FXComponent messageView = new MessageView(controller, model);
    BorderPane view = new BorderPane();
    view.setTop(controlView.render());
    view.setCenter(puzzleView.render());
    view.setBottom(messageView.render());
    view.setBorder(
        new javafx.scene.layout.Border(
            new javafx.scene.layout.BorderStroke(
                Color.BLACK, // Border color
                javafx.scene.layout.BorderStrokeStyle.SOLID, // Border style
                null, // Corner radii
                new javafx.scene.layout.BorderWidths(3) // Border widths (5 pixels in this example)
                )));
    return view;
  }
}
