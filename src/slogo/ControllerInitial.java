package slogo;

import java.util.ArrayList;
import javafx.stage.Stage;

public class ControllerInitial implements ControllerInterface{
  private ViewInterface view;
  private ArrayList<ViewInterface> listeners;

  public ControllerInitial(Stage primaryStage){
    listeners = new ArrayList<>();
    view = generateView(this, primaryStage);
  }

  private ViewInterface generateView(ControllerInterface cont, Stage primaryStage){
    ViewInterface view = new View(this, primaryStage);
    addViewListener(view);
    return view;
  }

  private void addViewListener(ViewInterface vi){
    listeners.add(vi);
  }
  
  private void alertView(){
    for(ViewInterface vi : listeners){
      vi.updateView();
    }
  }

  @Override
  public void updateModel() {

  }
}
