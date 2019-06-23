const ws = require('ws');
const readline = require('readline');
const PORT_NUMBER = 5444;
const TOPIC_NAME = 'game';

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
    console.log("Listening...");
});
server.on('connection', (socket, request) => {
    console.log("Yayr!");
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