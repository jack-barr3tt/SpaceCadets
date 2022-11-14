package com.example.sccspiro.Buttons;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.control.*;

public class StartButton extends Button {
  public StartButton(AnimationTimer timer) {
    super("Start");

    EventHandler<ActionEvent> startClick = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        timer.start();
      }
    };
    this.setOnAction(startClick);
  }
}
