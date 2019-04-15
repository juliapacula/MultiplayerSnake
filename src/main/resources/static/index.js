let stompClient = null;

function connect() {
    var socket = new SockJS(WebSocketEndpoint.game);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe(OutputEndpoint.currentGame, function (message) {
            console.log(JSON.parse(message.body));
        });
    });
}

function disconnect() {
  if (stompClient !== null) {
      stompClient.disconnect();
  }
  console.log("Disconnected");
}

function send() {
  console.log("Clicked");
  stompClient.send(InputEndpoint.postPosition, {}, JSON.stringify({'x': 15, 'y': 20}));
}

function getLast() {
  console.log("Clicked");
  stompClient.send(InputEndpoint.lastPosition, {}, '');
}

(function () {
    connect();
})();