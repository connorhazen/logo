package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;

import java.util.List;

public class SetPosition extends TurtleCommand {
    public SetPosition(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(2);
    }

    @Override
    protected double execute(Turtle executeOnTurtle) {
        double xCur = executeOnTurtle.getX();
        double yCur = executeOnTurtle.getY();

        double x = getArgsDouble().get(0);
        double y = getArgsDouble().get(1);
        moveToLoc(x, y, executeOnTurtle);

        double distance = Math.sqrt(Math.pow(xCur - x, 2) + Math.pow(yCur - y, 2));

        return distance;
    }
}
