package slogo.commands;

import slogo.structs.CommandStruct;
import slogo.view.Turtle;
import slogo.structs.VariableStruct;

import java.util.List;

public class Variable extends BasicSyntax {
    public Variable(CommandStruct commandStruct, String text, List<String> args, Turtle toldTurtle) {
        super(commandStruct, text, args, toldTurtle);
        setMyNumArgs(0);
    }

    @Override
    public double execute(Turtle toldTurtle) {
        VariableStruct name = getCommandStruct().getVariable(getMyText());
        if(name!=null){
            return name.getValue();
        }
        else{
            return 0;
        }
    }
}
