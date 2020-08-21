# Build

*Modify this file to include instructions on how to build and run your software. Specify which platform you are running on. Running your software involves launching a server and connecting at least 2 clients to the server.*

**1. Building the software**
- Platform: Mac or Linux/Ubuntu 
- Create a new directory using *mkdir [directory name]* using command line
- In side the new directory, run *cmake ..*
- Then run *make*

**2. Running the app**

*a. Server side:*
- Double click on the App file that has just been built or run *./App* on the command line
- Enter *s* when prompted for the role 
- Enter port number (anything greater than or equal to 2000)
- Use the website *https://www.whatismyip.com* to figure out the server's IP
- Enter server's IP when prompted

*b. Client side:*
- Double click on the App file that has just been built or run *./App* on the command line
- Enter *s* when prompted for the role 
- For the server's IP address: enter *0* if both client(s) and server are local; enter the public IP address of the server if client(s) and server are not local 
- Enter the server's port
- Pick a port for the client (anything greater than 2000 and different from other clients)

**3. Using the app**
- Clients and use keyboard or GUI to specify the different kinds and colors of brush they want to and draw on their canvas
- All clients' drawing will appear on all canvas 



