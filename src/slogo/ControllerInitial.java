package slogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.stage.Stage;
import slogo.structs.CommandStruct;
import slogo.view.Turtle;
import slogo.view.View;
import slogo.view.ViewInterface;
import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;

public class ControllerInitial implements ControllerInterface {

  private ViewInterface view;
  private ArrayList<ViewInterface> listeners;
  private Turtle myTurtle;
  private Model model;

  public ControllerInitial(Stage primaryStage) {

    model = new Model();
    myTurtle = new Turtle(250,0,0,-90);
    listeners = new ArrayList<>();
    view = generateView(this, primaryStage, myTurtle);

  }

  private ViewInterface generateView(ControllerInterface cont, Stage primaryStage,
      Turtle turtle) {
    CommandStruct workSpaceInfo = model.getCommandStruct();
    ViewInterface view = new View(this, primaryStage, workSpaceInfo);
    addViewListener(view);
    return view;
  }

  private void addViewListener(ViewInterface vi) {
    listeners.add(vi);
  }
  

  @Override
  public void executeCommand(String commandText) {
    try{
      System.out.println("here");
      List<String> history = model.runCommand(commandText, myTurtle);
      //view.printHistory(history);
      view.updateView(history);
    }

    catch (UnknownCommandException | InvalidParameterException e){
      view.printErrorFromException(e);
    }
  }

  @Override
  public void setLanguage(String title) {
    model.changeLanguage(title);
    System.out.println("Language: " + title);
  }
}


