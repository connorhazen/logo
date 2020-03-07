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
import slogo.view.MovableElements.BorderPaneLocation;
import slogo.view.Turtle;
import slogo.view.View;


public class RightView<T> implements BorderPaneElement {
  private TextArea myErrorBox;
  private TextArea myHistoryBox;
  private ArrayList<String> myClickedCommands;
  private BorderPaneLocation loc;

  public RightView(ArrayList<String> clickedCommands, BorderPaneLocation loc, T errorBox, T historyBox){
    myErrorBox = (TextArea)errorBox;
    myHistoryBox = (TextArea)historyBox;
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
