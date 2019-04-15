# Snake multiplayer game
### About

### Server-side
Server side of this game was created in Java using Spring framework with WebMVC plus WebSockets.
It uses [STOMP](http://jmesnil.net/stomp-websocket/doc/) protocol to communicate with client using
[SocketJS](https://github.com/sockjs/sockjs-client) library. 

### Client-side
On client side we use P5.js framework to render game together with VueJS Framework.


### Building the application
Go to the root directory and run:
```
mvn clean install
```
It will install all required dependencies in both backend and frontend. 

To run server, in root directory run command:
```
mvn --projects backend spring-boot:run
```

After that, go to http://localhost:8088/ and you will see app running.
During the process, there were generated some static files in `/frontend/target/dist`, so the actual app uses those files.
For a convenient development you can run your local Webpack server. While still running backend on port 8088,
go to `/frontend` and run `npm run serve`. Now on http://localhost:8080/ there will be running frontend on dev server.
Don't worry, all the requests will forwarded to the port 8088 (the only thing that is required is that url starts with `/api`. 