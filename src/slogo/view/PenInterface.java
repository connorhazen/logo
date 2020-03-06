package slogo.view;

import javafx.scene.paint.Color;

public interface PenInterface {

    boolean setSize(double penSize);

    double getSize();

    double getPenColorIndex();

    boolean setPenColorIndex(double index);

    Color getColor();

}
