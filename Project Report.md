# Project Report

### Developer: Toshal Ghimire
#### Project description
 ​​An android app designed to used by fantasy football players in order to
    research team stats, get live depth chart, league news and injuries updates.
    
[Video Demo](https://www.youtube.com/watch?v=J3jaws8nDek&feature=youtu.be)

All features that I originally planned on implementing were implemented. With some additional features as well

#### Implemented Features
| ID | Name | Description |
| ------ | ------ | ------ |
| U-01 | Browse teams| A user can scroll through all 32 NFL teams.|
| U-02 | Search teams| A user can search for a specific NFL team .|
| U-03 | View team |A user can view an NFL teams stats and depth charts.|
| U-04 | View News| A user can view the 10 latest news articles from the nfl|
| U-05 | View Injuries | A user can view the 10 latest injuries of players around the league|

The following is a list of features I would implement if I were to go back and work further in this project.


#### Additional Features
| ID | Name | Description |
| ------ | ------ | ------ |
|U-06 |View Schedule| Users should be able to view to current NFL schedule for the week|
|U-07 |View Standings |Users should be able to view current NFL standings|


### Design pattern implemented
From my Demo video you saw that I chose to implement the strategy design pattern, inside a
singleton object. The image on the left is of my class diagram, and the image on the right is a
diagram of the strategy design pattern. The class on top was my singleton and the two classes in
the middle, getOffense and getDefense, are classes that extends from the built in Android class
AsyncTask which is the parent of these two concrete Strategy classes. The purpose of these
classes was to get stats on NFL teams. I used the strategy pattern because I knew the logic for
getting offensive stats were different from getting the defensive ones. These two child classes
are part of the strategy design pattern and are used inside the singleton.
      .
#### What have I learned about the process of analysis and design through this project? 


To me the most important process I learned through this project is the proper use of UML. I realized its importance after having a lack of it for my project. With proper planning and clear UML diagrams we can help make software development process a lot easier.UML also helps when someone new is working on the code base understand how the code is structured.


### Backend
The stats and depthchart data scraping is done on the app. However, due to the fact that the Offical NFL website loads in data Dynamicaly, I had to write python scripts to parse the data and upload regularly update my own firebase real time database. I use AWS EC2 to preform cronjobs and update my app regularly.  


