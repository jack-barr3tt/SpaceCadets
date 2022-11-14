package com.example.sccspiro;

import com.example.sccspiro.Buttons.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Math.*;

public class SpiroApplication extends Application {

  private GraphicsContext gc;

  // Canvas size
  private final int height = 800;
  private final int width = 1000;

  // Parametric term of curve
  private double t = 0;

  // Used to store previous point
  private double prevX = width / 2;
  private double prevY = height / 2;

  // Animation properties
  private double step = 100;

  // Spirograph properties
  private double R = 150;
  private double r = 0;
  private double O = 0;

  private Parent createContent() {
    Pane root = new Pane();

    // Set up the view
    root.setPrefSize(width, height);
    Canvas canvas = new Canvas(width, height);
    gc = canvas.getGraphicsContext2D();

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        drawStep();
      }
    };

    Slider sliderR = new Slider(10, 200, 10);
    Slider sliderr = new Slider(0, 200, 0);
    Slider sliderO = new Slider(0, 200, 0);

    sliderR.setBlockIncrement(1);
    sliderr.setBlockIncrement(1);
    sliderO.setBlockIncrement(1);

    Label labelR = new Label(sliderR.getValue() + "");
    Label labelr = new Label(sliderr.getValue() + "");
    Label labelO = new Label(sliderO.getValue() + "");

    ChangeListener<Number> sliderRChange = (observableValue, number, t1) -> {
      R = (double) t1;
      labelR.setText("" + R);
    };
    sliderR.valueProperty().addListener(sliderRChange);

    ChangeListener<Number> sliderrChange = (observableValue, number, t1) -> {
      r = (double) t1;
      labelr.setText("" + r);
    };
    sliderr.valueProperty().addListener(sliderrChange);

    ChangeListener<Number> sliderOChange = (observableValue, number, t1) -> {
      O = (double) t1;
      labelO.setText("" + O);
    };
    sliderO.valueProperty().addListener(sliderOChange);

    Button startButton = new StartButton(timer);
    Button stopButton = new StopButton(timer);

    Button resetButton = new Button("Reset");

    EventHandler<ActionEvent> resetClick = e -> clearCanvas();
    resetButton.setOnAction(resetClick);

    Button drawAllButton = new Button("Draw All");

    EventHandler<ActionEvent> drawAllClick = e -> {
      timer.stop();
      drawAll();
    };
    drawAllButton.setOnAction(drawAllClick);

    HBox boxR = new HBox(10, new Label("R:"), sliderR, labelR);
    HBox boxr = new HBox(10, new Label("r:"), sliderr, labelr);
    HBox boxO = new HBox(10, new Label("O:"), sliderO, labelO);

    HBox controlBox = new HBox(10, startButton, stopButton, resetButton, drawAllButton);

    VBox box = new VBox(10, boxR, boxr, boxO, controlBox);

    root.getChildren().add(box);

    root.getChildren().add(canvas);

    return root;
  }

  private void clearCanvas() {
    t = 0;
    prevX = width / 2;
    prevY = height / 2;
    gc.clearRect(0, 0, width, height);
  }

  private void draw() {
    Point2D point = getPoint();

    gc.setStroke(Color.PURPLE);
    gc.setLineWidth(2);

    double x = width / 2 + point.getX();
    double y = height / 2 + point.getY();

    if (prevX != width / 2 && prevY != height / 2) gc.strokeLine(prevX, prevY, x, y);

    prevX = x;
    prevY = y;
  }

  private void drawStep() {
    for(int i = 0; i < step; i++) {
      draw();
      t += 0.02;
    }
  }

  private void drawAll() {
    while (t < 360) {
      draw();
      t += 0.02;
    }
  }

  private Point2D getPoint() {
    double x = (R - r) * cos(t) + O * cos(((R - r) / r) * t);
    double y = (R - r) * sin(t) + O * sin(((R - r) / r) * t);

    return new Point2D(x, y);
  }

  @Override
  public void start(Stage stage) {
    stage.setScene(new Scene(createContent()));
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}