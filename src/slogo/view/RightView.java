package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class RightView<T> implements BorderPaneElement {
  private TextArea myErrorBox;
  private TextArea myHistoryBox;

  public RightView(T errorBox, T historyBox){
    myErrorBox = (TextArea)errorBox;
    myHistoryBox = (TextArea)historyBox;
  }
  @Override
  public Node getElement() {
    String colon = ":";
    List<String> labels = new ArrayList<>(ResourceBundle.getBundle("slogo.view.labels").keySet());
    VBox right = new VBox();
    right.getStyleClass().add("vbox");
    Label error = new Label(labels.get(0) + colon);
    myErrorBox = new TextArea();
    myErrorBox.setWrapText(true);

    Label his = new Label(labels.get(1) + colon);
    myHistoryBox = new TextArea();


    right.getChildren().addAll(his, myHistoryBox, error, myErrorBox);

    return right;
  }
}
