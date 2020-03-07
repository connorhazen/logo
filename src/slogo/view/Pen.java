package slogo.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class Pen implements PenInterface {

    private SimpleIntegerProperty myPenSize = new SimpleIntegerProperty();
    private SimpleIntegerProperty myColorIndex = new SimpleIntegerProperty();
    private SimpleObjectProperty<Color> myPenColor = new SimpleObjectProperty<>();

    public Pen(){
        myPenSize.setValue(3); //TODO: make properties file
        myPenColor.setValue(Color.BLACK);
    }

    @Override
    public boolean setSize(double penSize) {
        myPenSize.setValue(penSize);
        return true;
    }

    @Override
    public double getSize() {
        return myPenSize.getValue();
    }

    public double getPenColorIndex(){
        return myColorIndex.getValue();
    }

    public boolean setPenColorIndex(double index){
        myColorIndex.setValue(Math.round(index)); //TODO: add error out of bounds handeling
        return true;
    }

    public Color getColor(){
        return myPenColor.getValue();
    }

    public boolean setColor(Color setColor){ //TODO: make this a non javafx object representation
        myPenColor.setValue(setColor);
        return true;
    }

}
