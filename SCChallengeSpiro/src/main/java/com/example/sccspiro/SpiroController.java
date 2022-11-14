package com.example.sccspiro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpiroController {
  @FXML
  private Label welcomeText;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");
  }
}