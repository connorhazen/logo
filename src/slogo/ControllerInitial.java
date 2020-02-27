package slogo;

import java.util.ArrayList;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import slogo.ControllerInterface;
import slogo.Turtle;
import slogo.View;
import slogo.ViewInterface;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;

public class ControllerInitial implements ControllerInterface {

  private ViewInterface view;
  private ArrayList<ViewInterface> listeners;
  private Turtle myTurtle;
  private Model model;

  public ControllerInitial(Stage primaryStage) {

    model = new Model();

    myTurtle = new Turtle(250,0,0,-90, 75);
    listeners = new ArrayList<>();
    view = generateView(this, primaryStage, myTurtle);

  }

  private ViewInterface generateView(ControllerInterface cont, Stage primaryStage,
      Turtle turtle) {
    ViewInterface view = new View(this, primaryStage, turtle);
    addViewListener(view);
    return view;
  }

  private void addViewListener(ViewInterface vi) {
    listeners.add(vi);
  }
  

  @Override
  public void executeCommand(String commandText) {
    try{
      model.runCommand(commandText, myTurtle);
      //view.updateView(model.runCommand(commandText, myTurtle));
    }

    catch (UnknownCommandException | InvalidParameterException e){
      view.printErrorFromException(e);
    }
    System.out.println(commandText);
  }

  @Override
  public void setLanguage(String title) {
    System.out.println("Language: " + title);
  }
}


