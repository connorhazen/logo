package slogo;

import java.util.ArrayList;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.Turtle;
import slogo.View;
import slogo.ViewInterface;

public class ControllerInitial implements ControllerInterface {

  private ViewInterface view;
  private ArrayList<ViewInterface> listeners;
  private Turtle turtle;

  public ControllerInitial(Stage primaryStage) {

    listeners = new ArrayList<>();
    view = generateView(this, primaryStage);
    turtle = new Turtle(250,250,10,10);
  }

  private ViewInterface generateView(ControllerInterface cont, Stage primaryStage) {
    ViewInterface view = new View(this, primaryStage);
    addViewListener(view);
    return view;
  }

  private void addViewListener(ViewInterface vi) {
    listeners.add(vi);
  }

  private void alertView() {
    for (ViewInterface vi : listeners) {
      vi.updateView(new Turtle(250,250,0,10));
    }
  }

  @Override
  public void updateModel(String commandString) {
    turtle.setX(turtle.getX()-20);
    turtle.setY(turtle.getY()-20);
    view.updateView(turtle);
  }

  @Override
  public void executeCommand(String commandText) {
    System.out.println(commandText);
  }

  @Override
  public void setLanguage(String title) {
    System.out.println("Language: " + title);
  }
}


