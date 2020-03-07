package slogo.view.ViewFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import slogo.ExceptionHelper;
import slogo.view.BorderPaneLocation;
import slogo.view.Turtle;
import slogo.view.View;
import slogo.view.ViewFactory.BorderPaneElement;


public class RightView<T> implements BorderPaneElement {
  private TextArea myErrorBox;
  private TextArea myHistoryBox;
  private Turtle myTurtle;
  private ArrayList<String> myClickedCommands;
  private BorderPaneLocation loc;

  public RightView(Turtle turtle, ArrayList<String> clickedCommands, BorderPaneLocation loc, T errorBox, T historyBox){
    myErrorBox = (TextArea)errorBox;
    myHistoryBox = (TextArea)historyBox;
    myTurtle = turtle;
    myClickedCommands = clickedCommands;
    this.loc = loc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    String colon = ":";
    List<String> labels = new ArrayList<>(ResourceBundle.getBundle("slogo.view.labels").keySet());
    VBox right = new VBox();
    right.getStyleClass().add("vbox");
    Label error = new Label(labels.get(0) + colon);
    myErrorBox.setWrapText(true);
    myHistoryBox.setOnMouseClicked(e -> getLineOnClick());
    Label his = new Label(labels.get(1) + colon);

    Properties properties = new Properties();
    try{
      properties.load(View.class.getResourceAsStream("turtle_status_labels.properties"));
    } catch (IOException iox){
      new ExceptionHelper().fileNotFound(iox);
    }
    HashMap<String, String> labelBindingMap = new HashMap<>();
    for(String s : properties.keySet().toArray(new String[0])){
      labelBindingMap.put(s, properties.getProperty(s));
    }
    right.getChildren().addAll(his, myHistoryBox, error, myErrorBox);

    Label turtLabel = new Label(labelBindingMap.keySet().toArray(new String[0])[0]);
    Label turtStatus = new Label();
    turtStatus.textProperty().bind(myTurtle.getCordsProperty().asString());
    right.getChildren().addAll(turtLabel, turtStatus);

    turtLabel = new Label(labelBindingMap.keySet().toArray(new String[0])[1]);
    turtStatus = new Label();
    turtStatus.textProperty().bind(myTurtle.getAngleProperty().asString());
    right.getChildren().addAll(turtLabel, turtStatus);

    turtLabel = new Label(labelBindingMap.keySet().toArray(new String[0])[2]);
    turtStatus = new Label();
    turtStatus.textProperty().bind(myTurtle.getVisibleProperty().asString());
    right.getChildren().addAll(turtLabel, turtStatus);



    return right;
  }

  private void getLineOnClick(){
    //https://stackoverflow.com/questions/51414314/selecting-an-entire-line-from-textarea-on-mouse-click
    String text = myHistoryBox.getText();
    int caretPos = myHistoryBox.getCaretPosition();
    int lineBreak1 = text.lastIndexOf('\n', caretPos - 1);
    int lineBreak2 = text.indexOf('\n', caretPos);
    if(lineBreak1 < 0) lineBreak1 = 0;
    if(lineBreak2 < 0) lineBreak2 = text.length();
    myClickedCommands.add(myHistoryBox.getText(lineBreak1, lineBreak2));
  }
}
