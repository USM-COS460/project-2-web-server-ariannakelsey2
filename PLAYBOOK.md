## COS 460/540 - Computer Networks
# Project 2: HTTP Server

# <<Arianna Kelsey>>

This project is written in Java on macOS.

## How to compile

To compile, it is done directly in the command line terminal. 

## How to run

To execute the project, I have a folder on my desktop that is organized such as the www folder in the 
original commit of the github repository. It has another folder named images that has all the images named 
appropriately in it. The other files in www are the index.html and site.css. The Server.java file is also placed
on my desktop. Naviagating to the command line, the first thing inputed is to ensure the correct directory, which
is cd ~/Desktop, and then I compile the code in the terminal with the command line javac Server.java. At this point,
the code is being executed by the java compiler, the next line inputed is java Server 800 www, or whatever portNumber
you want to use, and the directory name. From that point, open a browser and input into the search engine the server
http://localhost:800/. I tried it also simultaneously on Firefox, and in the terminal will show up the HTTP Response. 
If you input a file that is not in the directory, the server will display 404 Not Found. 


## My experience with this project

I learned that browsers are very complicated. Having never written a web server before, 
it took a massive amount of research to understand what libraries, classes, and methods are
used to make a functional web server that is stable. I spent particular time on the Java 
documentation reading about methods and their functionality, and definitely watched some 
instructional youtube videos on how others implemented a simple web server through Java. 
I definitely appreciate that Java is an object oriented language, I think that the paradigm 
of being object-oriented helps in the design of a web server, especially one that has multiple 
functionalities and things to click on. It helped in the process of making sure that the that 
multiple clients could be handled at the same time creating new objects every single time a new
client connects to the server. I learned so much more about the internal workings of 
methods designed for networks such as OutputStream, BufferedReader, InputStream and how they work 
in processing data bytes being sent over to a browser to create the server. There were definitely 
parts of this project where I got stuck, and had spent some time researching to figure out the problem 
or how to fix, but overall my experience of the project was a positive one. I enjoyed the exposure
to designing a web server. 

