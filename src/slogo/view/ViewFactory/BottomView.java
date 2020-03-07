package slogo.view.ViewFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.function.Consumer;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import slogo.ExceptionHelper;
import slogo.view.MovableElements.BorderPaneLocation;
import slogo.view.Turtle;
import slogo.view.View;
import slogo.view.ViewInterface;

public class BottomView<T> implements BorderPaneElement {
  private TextArea inputText;
  private ViewInterface view;
  private BorderPaneLocation loc;
  private Consumer<Slider> paramFunc;
  public BottomView(TextArea ta, ViewInterface view, BorderPaneLocation loc, Consumer<Slider> paramFunc){
    inputText = ta;
    this.view = view;
    this.loc = loc;
    this.paramFunc = paramFunc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    HBox myBox = new HBox();
    myBox.getStyleClass().add("hbox-bot");
    myBox.getChildren().add(inputText);
    Properties props = new Properties();
    HashMap<String, String> myButtonMap = new HashMap<>();
    try {
      props.load(View.class.getResourceAsStream("bottom_view.properties"));
      for(String key : props.stringPropertyNames()){
        myButtonMap.put(key, props.getProperty(key));
      }
    } catch (IOException e) {
      new ExceptionHelper().fileNotFound(e);
    }
    Class<?> thisView = View.class;
    Object obj = view;
    for(String key : myButtonMap.keySet()){
      for(Method m : thisView.getDeclaredMethods()){
        if(myButtonMap.get(key).equals(m.getName())){
          Button b = new Button(key);
          b.setOnAction(e -> {
            try {
              m.setAccessible(true);
              if(m.getName().equals("reset")) m.invoke(obj);
              else m.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException ex) {
              new ExceptionHelper().reflectionError(ex);

            }
          });
          b.getStyleClass().add("run-bot");
          myBox.getChildren().add(b);
        }
      }
    }

    VBox slid = new VBox();
    slid.getChildren().add(new Label("Animation Speed"));
    Slider a = new Slider(0,5,2);
    slid.getChildren().add(a);
    myBox.getChildren().add(slid);
    paramFunc.accept(a);
    return myBox;
  }
}
