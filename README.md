# SBoards
The Minestom Scoreboard api is not very good if you want to make a scoreboard that is different for each player.
<br> So i made this easy to use api
<br>
<br><br>
This api is inspired by [JScoreboards](https://github.com/JordanOsterberg/JScoreboards)
<br><br>
To use this api, shade it into your project<br>
By [downloading the jar](https://github.com/sqcred/SBoards/releases/latest)  or using maven
<br>
````
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	<dependency>
	    <groupId>com.github.sqcred</groupId>
	    <artifactId>SBoards</artifactId>
	    <version>VERSION</version>
	</dependency>
````
## Usage
````
// First we need to construct an SBoard
SBoard board = new SBoard(
    // Each supplier has a Player object (This is the player the scoreboard is beign showed to)
    (player) -> {
        // This is a Title supplier. 
        // You have to return an component
        return Component.text("Hello " + player.getUsername());
    },
    (player) -> {
        // This is the lines supplier
        // You have to return a List<Component>
        // Note that 15 lines is the max
        return Arrays.asList(
            Component.text("Line1"),
            Component.text("Line2"),
            Component.text("Line3")
        );
    }
);

// Now that we have a scoreboard we need to add a player to it
board.addPlayer(player);


// Now we have made a scoreboard and added a player
// Now everytime you need to update the scoreboard you call

// To update for 1 player
board.update(player);

// Or to update it for every player
board.updateAll();



// You can also remove a player by using
board.removePlayer(player);

// Or to remove all
board.removeAll();

````

There are 2 exceptions:
<br>SBoardNotFoundException -  Gets called when you are trying to update or remove a player from a scoreboard that doesnt exist
<br>SBoardMaxLinesException - Gets called when you have more then 15 lines specified
<br><br>

### More Comming soon...

## Support
If you find any bugs: Create an Issue <br>
or if want to contact me <br>
Discord: sqcred#1143
<br><br>
## Contribute
You can always make a pull request if you want to add and/or improve some things
