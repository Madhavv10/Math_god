# Math_god
I made a basic math game android application in which you are given multiplication based questions for a duration of one minute and you have to score maximum score in that time.
The score of every user is then save on the firebase database and the ranks are provided in the leaderboard that i have included in the app.

The Main activity consists of a kind of simple user login in which a name already used in the game can not be continued, if the username is new to the game the game start on the click of go button.

The play activity is where the game will run with the help of a timer of one minute.

on the finish of game (the finish of timer), a button for the leaderboard will be visible which will show the ranks of all the participants.

user.java is the class made to store the object of every individual participants data, these objects are stored on firebase database.
