package slogo.commands;

import slogo.exceptions.InvalidParameterException;
import slogo.exceptions.UnknownCommandException;

public interface CommandInterface {

    double executeCommand() throws UnknownCommandException, InvalidParameterException;
}