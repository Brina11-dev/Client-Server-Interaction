# Java Client-Server ChatApp
A simple client server application built in java using TCP sockets
## Features
- Real socket connection between client and server
- Supports multiple commands
- Runs in a single terminal
## How to run
** Compile**
javac ChatApp.java
** Run **
java -cp . ChatApp
## Commands
- `TIME` - returns the current time
- `ECHO <msg>` -echoes your message back
- `UPPER <msg>` -converts message to uppercase
- `REVERSE <msg>` - Reverses your message
- `QUIT` - exists the program
## Example
- You >> ECHO hello
- Server >> Hello
- You >> Time
- Server >> Current time : Wed Apr 01 15:42:53 EAT 2026
## Concepts
- Java TCP sockets
- Multithreading
- Java I/O streams

