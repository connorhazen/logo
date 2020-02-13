# slogo team meeting 2/13

## Discussion on Simulation team 5 

###method should not be part of the API (i.e., it should not be public)

###method should be part of the external API because it helps people on the team working in other modules write their code

* getRows and getColumns should be external 

* All the methods in the SquareModel

* filereader constructor

* checkExists

* Cell constructor

* getX and getY methods for cell

* increment State

* Model constructor is external

* main should be external

* UserInterface constructor

* SimSpecificUI constructor and getValues()

* ControlPanel constructor and setPause from the ControlPanel class

* update and clear methods from PredPreyGraph class

* Simulator class

* View class constructor and updateAllCells

* controllerPackage constructor, updateSim, resetSim

###method should be part of the internal API because it helps future coders within the module extend it

* setElement

* getIntValue

* getDoubleValue

* All types of exceptions (XMLexception, parameter exception)\

* all other cell methods should be internal

* addIfExists (Model abstract class) 

* getNeighborhood methods, getCell methods 

* get simulation, setupUI, addPredChart(), removeGraph (from UserInterface class)

* the rest of the ControlPanel methods should be internal

* all of the UserSelectDisplay is internal

* reinit from PredPreyGraph

* all of CellVisual class

* clear and replace colors from View method

* the rest of the controllerPackage is internal

## Designing a more optimal API

All the update methods should be grouped together so the entire application can be updated in one step

All clear methods and reset methods should be external and grouped together

All other methods should be internal to the model.

There needs to be a connection from the frontend to the backend which informs the backend on what to run
(picking and setting simulations)

The backend needs to communicate to the frontend all the nodes that need to be displayed (these can be
grouped in some javafx object)

### external: as a client of the backend, the frontend selects an existing simulation to display and then starts running that kind of simulation, updating its own grid visualization
  
  English: how do you want programmers to think of the process of using the API to accomplish this task using metaphors or analogies (e.g., folder paradigm), or standard programming ideas (e.g., step by step or substitution via polymorphism) beyond simply restating the procedural steps
  
  The frontend is responsible for picking the simulation, which is passed to the backend, the backend is responsible for computing the new cell states. The backend then passes the current cell states (updated) to the view for display
  
  Java: list the actual Java methods you expect to use from the API to accomplish this task with a comment about how they would be used (e.g., call, implement, or change)
  
### internal: a new developer joins the the backend team and adds a new possible kind of simulation that can be available for the frontend to choose

English: how do you want programmers to think of the process of using the API to accomplish this task using metaphors or analogies (e.g., folder paradigm), or standard programming ideas (e.g., step by step or substitution via polymorphism) beyond simply restating the procedural steps

The backend is updated in order to code the update logic (Adding a Cell subclass), afterwards everything else is setup to know to direct to it given that the xml file is correctly setup

Java: list the actual Java methods you expect to use from the API to accomplish this task with a comment about how they would be used (e.g., call, implement, or change)