# MyBoxCricket
Develop a simple Android application for box cricket called 'MOTM Calculator.' The app's core purpose is to track player performance in real-time and calculate the 'Man of the Match' based on a point-based scoring system. The user should be able to add players on the fly. The scoring interface must be simple and fast, with dedicated buttons to record runs, wickets, catches, and run outs for each player. The app should not include extras like wides or no-balls. Finally, it should display a leaderboard to show the 'Man of the Match' winner after the data has been entered. The main goal is a streamlined, user-friendly experience for live match scoring.
#Features
App Functionality and User Flow
The app should be designed to track a player's performance in a box cricket match, with a focus on simplicity and on-the-fly data entry.

Start Match/Add Players:

The app should begin with a screen to start a new match.

Users can add player names to the scorecard. This should be a simple text input field for each player.

Live Scoring Interface:

This is the main screen for the match. Each player's name should be listed.

Next to each player's name, there should be dedicated sections or buttons to record their performance in real-time.

Batting Score Entry:

For each player, there should be a quick way to add runs for each ball they face.

The scoring should be granular, tracking runs per ball. For example, buttons for 0, 1, 2, 3, 4, and 6 runs.

A 'Wicket' button to record when a player gets out.

Bowling Score Entry:

For the bowler, there should be buttons to track their performance.

A button to add a wicket (e.g., Wicket).

A way to track runs conceded (e.g., buttons to add 0, 1, 2, 3, 4, 6 runs conceded).

A way to track balls bowled.

Fielding/Other Performance:

Simple buttons to record fielding contributions: Catch and Run Out. These can be assigned to the player who made the play.

Man of the Match Calculation:

The app needs a scoring algorithm to determine the Man of the Match. This can be based on a points system, for example:

Batting: A point for each run.

Bowling: Points for each wicket, and a deduction for runs conceded.

Fielding: Points for each catch and run out.

The app should have a button, like 'Calculate MOTM,' that shows a scoreboard with each player's points and a clear winner.

Data Persistence:

The app should save the current match data so the user can leave and come back to it.

Simplified UI and Technical Considerations
UI/UX: The interface should be minimal and optimized for speed and single-hand use. Large, tappable buttons are crucial.

Data Structure: You'll need a simple data model to store player names and their performance stats (runs, balls faced, wickets, catches, run outs, etc.).

No Extras: As per your request, the app should not include options for 'wide' or 'no ball' extras. This keeps the logic simple.
## Screen 1
Create Match ( Match Title & Select Day)
## Screen 2
Create a Team (Team Name & Team Icon)
## Screen 3
Add Player into Team
## Screen 4:
Select No Of Overs Match:
Over all Team score display Run and Over along with Strike Rate.
On the Top Balls comes.Each Ball user can select the Run (0,1,2,3,4,6,Wicket). Before the Update the Run Can select the Player who is playing the ball.
Underlay App manage the How many Runs player score including information how many 4 & 6. 
Team player have radio button a time only one player can active. before start score active 2 player out of team.
At the bottom End Match Button. Now Redirect to next activity Scoreboard where along with heading like player name, Runs,balls, 4s, & 6s display the runs
