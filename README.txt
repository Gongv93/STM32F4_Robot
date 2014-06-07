Objective:
This is a project to help me learn and expand my knowledge on more of the hardware side of Computer Engineering.

General Overview:
Using STM32F4 discovery and an android phone I will make a simple robot that is controlled wirelessly via android app.

The android app will serve as a server taking in commands from a client which will be on a desktop computer. Once the android app gets the command it will send signals from USB to the STM32F4 discovery board. When the board gets the signals it will do the commands given.

There will be 4 motors to control each wheel. Motor drivers will be also needed to control the DC motors.

Commands:
 cRotate <time>		- Clockwise rotation
 ccRotate <time>	- Counter-clockwise rotation
 foward <time>		- move foward
 back <time>		- move back

Other ideas of commands that I might expand upon
 mode <state>		- switch to different modes where the robot will be in 			  		  autopilot


