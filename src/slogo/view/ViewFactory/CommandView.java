package slogo.view.ViewFactory;

import java.io.IOException;
import java.util.Properties;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import slogo.ExceptionHelper;
import slogo.view.BorderPaneLocation;
import slogo.view.View;

public class CommandView implements BorderPaneElement {
  private BorderPaneLocation loc;
  private static final String PROPERTIES = "commandview.properties";
  public CommandView(BorderPaneLocation loc){
    this.loc = loc;
  }
  public BorderPaneLocation getLoc(){
    return loc;
  }
  @Override
  public Node getElement() {
    Properties props = new Properties();
    try{
      props.load(View.class.getResourceAsStream(PROPERTIES));
    } catch(IOException e){
      new ExceptionHelper().fileNotFound(e);
    }
    VBox left = new VBox();
    left.getStyleClass().add("vbox");
    for(String key : props.keySet().toArray(new String[0])){
      left.getChildren().add(new Label(props.getProperty(key)));
      left.getChildren().add(new TextArea());
    }
    return left;
  }
}
