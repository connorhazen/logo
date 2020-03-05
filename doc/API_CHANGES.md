Sebastian Williams (svw9)
Avivah Wang (ajw70)
Olga Suchkova (os33)

#API Changes

##Controller Interface
###Added executeCommand(String commandText)
This change was generally considered minor as it just added another
method to the controllers that implemented this method. Overall, it didn't change
a whole lot about the design of our project, but it did make it easier to execute
commands from the controller, which makes our design more consistent with the MVC
pattern. We believe this change was for the better as it allowed for more flexibility
and made sense as executing a command is something every controller needs to do.
###Added setLanguage(String title)
Again, this change was minor as it really only allowed for more flexibility. It allowed
for changing the language of the controller, which is something every controller needs to be
able to do, just like executing commands. Clearly, this change was for the better.
###Removed updateModel
This was a minor change. Originally we wanted to have updating the model be a critical part of 
any controller, but as the project went on, we realized it wasn't necessary. Upon further abstraction
later on in the project, I forsee this method potentially coming, but for now it is
not needed. 
##Model Interface
###+List<String> runCommand(String input, Turtle turtle) throws UnknownCommandException, InvalidParameterException;
This method went through several variations of what it returned and what parameters it took. The
final conclusion was to return a List<String> and take the input and turtle object as parameters.
The changes made were minor overall as the core idea of running a command from the model and getting
that information to the controller never really changed. However, upon implementing the history
box, we realized we needed to have a list of executed commands, which is why we finally
decided on returning a List<String>. Overall, this change was for the better
because it allows for accessing the history of executed commands regardless of the type of model.
##View Interface
###+setImage and +printHistory and +printErrorFromException
These three methods have all been added because they allow for Views to communciation
important information easily. Set image was added because the background image
was decided to be a critical state of the View as it needed to be changed and returned by
the commands. Print history was also see as very important as we needed he ability to make sure
the view was able to display the previous list of commands. Finally, printErrorFromException
was originally added to ensure that every view had proper error handing, but because of the
use of the ExceptionHelper method, we project that this method will most likely get removed.

