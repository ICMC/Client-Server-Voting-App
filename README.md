# Client-Server-Voting-App

The client application will be done in Android, while the server application will be implemented in `.java` and `.c` and it will be running at the lab's cluster. 

### Steps to Run the Server

1. ssh to the cluster: `ssh username@cosmos.lasdpc.icmc.usp.br –p 2220`
2. ssh to the node10: `ssh node10`
3. connect the application on the designated port to your group

### Material Design 

The colors for the app were based on [Google's Material Design](https://material.io/guidelines/style/color.html#color-color-palette)

### Usefull link for Project Reference 

1. [Server Side Exemple Code](http://androidsrc.net/android-client-server-using-sockets-server-implementation/)
2. [How to Write Json Files](https://crunchify.com/how-to-write-json-object-to-file-in-java/)
3. [How to Read Json Files](https://crunchify.com/how-to-read-json-object-from-file-in-java/)
4. [Transfer File Through Sockets](https://stackoverflow.com/questions/6099636/sending-files-through-sockets)

# what the app does 
1. open conection (send opcode 888)
2. receive candidates (close conection)
3. colect votes
4. send votes (opcode 9999)

