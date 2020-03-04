package slogo.view.ViewFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import slogo.ExceptionHelper;
import slogo.view.Turtle;
import slogo.view.View;
import slogo.view.ViewFactory.BorderPaneElement;


public class RightView<T> implements BorderPaneElement {
  private TextArea myErrorBox;
  private TextArea myHistoryBox;
  private Turtle myTurtle;

  public RightView(Turtle turtle, T errorBox, T historyBox){
    myErrorBox = (TextArea)errorBox;
    myHistoryBox = (TextArea)historyBox;
    myTurtle = turtle;
  }
  @Override
  public Node getElement() {
    String colon = ":";
    List<String> labels = new ArrayList<>(ResourceBundle.getBundle("slogo.view.labels").keySet());
    VBox right = new VBox();
    right.getStyleClass().add("vbox");
    Label error = new Label(labels.get(0) + colon);
    myErrorBox.setWrapText(true);

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
}
