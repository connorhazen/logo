package slogo;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ComboBox;

public class CommandHistoryView {
  private int currentIndex;
  private ArrayList<String> commands;

  public CommandHistoryView(){
    currentIndex =0;
    commands = new ArrayList<>();
  }
  public String getNext() {
    if (currentIndex + 1 < commands.size()) {
      currentIndex++;
      return commands.get(currentIndex);
    } else {
      currentIndex = commands.size();
      return "";
    }
  }

  public String getPast(){
    if(currentIndex-1>=0){
      currentIndex--;
      return commands.get(currentIndex);
    }
    else {
      currentIndex = -1;
      return "";
    }
  }

  public void add(String com){
    String check = com.strip();
    if(check.length()>0){
      commands.add(com);
      currentIndex = commands.size();
    }
  }
  
  public void clear(){
    commands.clear();
    currentIndex = 0;
  }


}
