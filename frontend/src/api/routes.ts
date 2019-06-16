export const ROUTES = {
  output: {
    broadcastPositions: '/positions/all',
    broadcastDeaths: '/died',
  },
  input: {
    sendPosition: '/position/post',
    enterGame: '/join',
    leaveGame: '/leave',
  },
  websockets: {
    game: '/websocket/game',
  },
};
