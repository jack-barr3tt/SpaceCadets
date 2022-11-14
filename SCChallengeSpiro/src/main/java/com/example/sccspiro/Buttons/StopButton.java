package com.example.sccspiro.Buttons;

import javafx.animation.*;
import javafx.event.*;
import javafx.scene.control.*;

public class StopButton extends Button {
  public StopButton(AnimationTimer timer) {
    super("Stop");

    EventHandler<ActionEvent> stopClick = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        timer.stop();
      }
    };
    this.setOnAction(stopClick);
  }
}
