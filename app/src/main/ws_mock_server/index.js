const ws = require('ws');
const readline = require('readline');
const PORT_NUMBER = 5444;
const TOPIC_NAME = 'game';

/**
 * TODO: Don't forget to adjust the server URL in the vue code
 */

 var server = new ws.Server({ port: PORT_NUMBER, path: '/' + TOPIC_NAME });
var clients = [];
function handleMessage(message) {
    console.log(message);
}

function sendWithTimeout(commaSeparatedData) {
    var index = 0;
    var data = commaSeparatedData.split(",");
    var timeoutObject = setInterval(() => {
        clients.forEach(client => {
            console.log("Sending:" + data[index]);
            client.send(data[index++]);
            if (index == data.length) { clearInterval(timeoutObject); } // Stop sending
        });
    }, 1000);
}

server.on('listening', () => {
    var address = server.address();
    var ip = address.address;
    var port = address.port;
    console.log("Listening... [%s:%s]\nTo send a message type something like the following:1,1,1,2,3,1", ip, port);
});

server.on('connection', (socket, request) => {
    console.log("CONNECTION:%s", request.connection.remoteAddress);
    socket.on('message', handleMessage);
    clients.push(socket);
});

var rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
    terminal: false
});

rl.on('line', (line) => {
    sendWithTimeout(line);
});
