###Too complicated code
Methods should not be too complex
Longest method - ElementMove
Try should not be nested too deeply
Intra-File duplication SettingsBar

###Bad OOP
Instance methods should not write to static fields
coordinates have public instance variables
Implement as List instead of LinkedList

###Things Duvall will fail you for
Boolean literals
PrintStackTrace

###Things to change:
1. Boolean literals - need to change the logic so it will
return true if it needs to. That's why we had an issue with the literal,
as the logic never led it to return true;

2. Print Stack Trace - need to change every call of print stack trace
to use the exception helper that was made early on to show a popup 
with a description of the error

3. Implementing List as LinkedList - we want to use the interface of List as
opposed to actually implementing the type as it allows for more flexibility 
in our code and keeps it shy.

4. Changing public instance variables - public instance variables violate
encapsulation as they allow other classes to change the state of another class without
the class whose state is changing from knowing so. 

5. Instance methods should not write to static fields - because you can't
guarantee which instances exist, you don't want the ability for an instance method
to be able to change a static variable as you cannot predict what changes will be made.

NOTE: The other design issues on the static design page are a product of a very work
in progress feature on the master branch right now. It will be deleted soon, so that is
why we did not refactor it. Also, the nested try statements, although very deep, are 
required for reflection as invoking a method required exception handling.