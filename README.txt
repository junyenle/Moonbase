# moonbase - co-op 2D platformer game written in Java.

# author: Jun Yen Leung
Responsible for all aspects EXCEPT login/registration backend.
	Login/Registration backened written by Uri Rahimi and Jake Leventhal.

Documentation:
	See fulldoc.docx for documentation.
	See moonbase_presentation.ppt for demo powerpoint.
	
# RUN INSTRUCTIONS:
	import moonbase.zip project in eclipse
	
	single player:
		run Driver.GameStarter.java on one client
		continue as guest
		1P game
	multiplayer:
		run setup.sql
		run Server.ServerDriver.java
		run Driver.GameStarter.java on two clients
		register, login, select 2P game
		
	OR, FOR SINGLE PLAYER ONLY (no project import required):
		run moonbase.jar
		continue as guest
		1P game
