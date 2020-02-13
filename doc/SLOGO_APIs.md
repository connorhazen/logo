#API's 


## Controller

It will create the view and and model classes. These two classes will communicate by talking 
strictly with the controller. This means the controller is responsible for fetching the imputed
command from the UI, passing it to the parser, and calling the model for each line in the command. 


Public Methods 

Constructor - create both a view, model and parser. 


## Parser


## View
The view is responsible for creating the UI and displaying the current turtle location and 
any pen down drawings. 

Public Methods : 

UpdateTurtleLocation(x,y, angle, size)

UpdateStatus(ErrorToDisplay)



## Model
Move the turtle in the method described by given command. It will operate on strictly single 
line commands. 

Public Methods : 

Constructor: make a turtle

doCommand(): create command class associated with passed string. Pass turtle to command

getTurtleInfo(): returns current turtle info.  