Sebastian Williams (svw9)
Avivah Wang (ajw70)
Olga Suchkova (os33)

###API Changes

#Controller Interface
updateModel --> updateModel(String commandName)

Added executeCommand(String commandText)
Added setLanguage(String title)

Removed updateModel

#Model Interface
-boolean runCommand(String input) throws UnkownCommandException;
+Turtle runCommand(String input, Turtle turtle) throws UnkownCommandException;

-Turtle runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException;
+List<String> runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException;

#View Interface
-updateView
+setImage
+printHistory
+printErrorFromException
+updateView
