# SLogo High Level Design 

When does parsing need to take place and what does it need to start properly?

* Parsing needs to take place after the program starts, and the parsing of the command should take place after the command is typed 
by the user. It needs to be passed to the back end to ensure that the command is formatted correctly so that it can be parsed. 
If not, it needs to be able to handle the error if it is formatted in a way that is unable to be parsed.

What is the result of parsing and who receives it?

* The result of parsing is a series of translated method calls to the turtle. If there are separate classes for each of the calls, 
the results may go to those classes which can then act on the turtle. This depends on the implementation.

When are errors detected and how are they reported?

* Errors can be detected when the command is formatted incorrectly for the parser to be able to recognize it, if the turtle goes 
off of the screen, an invalid command is given, etc. Errors can be reported by a display that will tell the user that there 
is an error without crashing the program.

What do commands know, when do they know it, and how do they get it?

* Commands do not really know anything about the status of the program itself, they simply receive a command from the user and 
execute it. They know this after the parser has translated the user's input into the command itself. It gets this from the back 
end command parser which will call the relevant methods in the correct command class.

How is the GUI updated after a command has completed execution?

* The GUI will get the turtle's current location or orientation and update it based on the command that it has been told to 
execute. In order to get the drawing function to work, it would make sense for the front end to receive command-by-command updates 
from the back end so that it can store where the turtle has been, but the display does not actually update until the entire 
command has been run so that it can backtrack to its initial state if it runs into an error.

* We are tentatively thinking that we will have a separate command parser that will be in the runner. So, the front end can 
pass the string to the runner which can parse the command and then send the commands to the back end. This leaves some flexibility 
to change the design later if we feel it makes more sense to put the parser in either the front end or the back end, or we can 
keep it as is.