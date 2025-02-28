# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
classDiagram
    class BGArenaPlanner {
        +main(String[] args) : void
    }

    class Planner {
        +filter(String filter, GameData sortOn, boolean ascending)
        +reset()
    }

    class IPlanner {
        <<interface>>
        +filter(String filter, GameData sortOn, boolean ascending)
        +reset()
    }
    
    class BoardGame {
        -String name
        -int minPlayers
        -int maxPlayers
        -int minTime
        -int maxTime
        -double difficulty
        -int rank
        -double rating
        -int year
        +toStringWithInfo(GameData col) : String
    }
    
    class GameData {
        <<enumeration>>
        +NAME
        +ID
        +MIN_PLAYERS
        +MAX_PLAYERS
        +MIN_TIME
        +MAX_TIME
        +DIFFICULTY
        +RANK
        +RATING
        +YEAR
        +fromString(String columnName) : GameData
    }

    class IGameList {
        <<interface>>
        +List<String> getGameNames()
        +void clear()
        +int count()
        +void saveGame(String filename)
        +void addToList(String str, Stream<BoardGame> filtered)
        +void removeFromList(String str)
    }

    class GameList {
        +GameList()
        +getGameNames()
        +clear()
        +count()
        +saveGame(String filename)
        +addToList(String str, Stream<BoardGame> filtered)
        +removeFromList(String str)
    }
    
    class GamesLoader {
        +loadGamesFile(String filename) : Set<BoardGame>
    }
    
    class Operations {
        <<enumeration>>
        +GREATER_EQUAL
        +LESS_EQUAL
        +EQUAL
        +NOT_EQUAL
        +APPROX_EQUAL
        +getOperatorFromStr(String op) : Operations
    }
    
    class ConsoleApp {
        +processFilter()
        +processListCommands()
        +processHelp()
    }
    
    BGArenaPlanner --> Planner
    Planner --> BoardGame
    BoardGame --> GameData
    Planner --> IGameList
    IGameList <|-- GameList
    Planner --> GamesLoader
    GamesLoader --> BoardGame
    Planner --> IPlanner
    Planner --> Operations
    ConsoleApp --> IGameList
    ConsoleApp --> IPlanner
```


### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 

I plan to use the existing interfaces and classes without adding new ones because they already cover all needed functions. `Planner` handles filtering, `GameList` manages the game list, and `GamesLoader` loads data. `ConsoleApp` manages user input. Since these classes already do their jobs well, no extra classes are needed. If the project gets more complex later, I can adjust the design.



## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Test filtering by minimum players (minPlayers>2).
2. Test filtering by maximum players (maxPlayers<6).
3. Test filtering by the year published (year>2000).
4. Test filtering by difficulty (difficulty>=3.0).
5. Test filtering by name contains (name~=catan).
6. Test multiple conditions (minPlayers>2,maxPlayers<6).
7. Test resetting the planner (reset())
8. Test Sorting by Year Published (Ascending)
9. Test Sorting by Year Published (Descending)
10. Test Sorting by Difficulty (Ascending)
11. Test Sorting by Difficulty (Descending)



## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 
