var vm = new Vue({
	el: "#app",
	data: {
		// Header 
		playerName: 'Player1',
		score: 0,
		// Question details
		curQuestion: '',
		qInd: 0,
		correctAns: 0,
		choices: [],
		trials: 0,
		success: false,
		// Player's answer
		userAns: 'Answer',
		// Game status
		rows: 'Rows',
		cols: 'Columns',
		gridData: [],
		questions: null,
		inGame: false,
	},
	methods: {
		checkAns: function(event) {
			console.log("Selected answer: " + this.userAns);
			this.trials++;
			var selection = parseInt(this.userAns)-1;
			var selectedRow = Math.floor(selection/this.cols);
			var selectedCol = selection%this.cols;
			var selectedVal = this.gridData[selectedRow][selectedCol]['val'];
			if(selectedVal === this.correctAns) {
				this.success = true;
				console.log("correct ans");
				(this.gridData)[selectedRow][selectedCol]['color'] = '#00FF00';
				if(this.trials > 1)
					this.score += 5;
				else this.score += 10;
			} else {
				(this.gridData)[selectedRow][selectedCol]['color'] = 'red';
				(this.gridData)[selectedRow][selectedCol]['decor'] = 'line-through';
				console.log("wrong ans - expected " + this.correctAns + " found " + selectedVal);
			}
		},
		loadFile: function(event) {
			var files = event.target.files || event.dataTransfer.files;
			if (!files.length)
				return;
			this.loadText(files[0]);
		},
		loadText: function(file) {
			var reader = new FileReader();
			var vm = this;
			reader.onload = (e) => {
				this.questions = e.target.result;
			};
			reader.readAsText(file);
		},
		validateInput: function() {
			if(this.rows==='Rows' || this.cols==='Columns' || this.questions==null) {
				return false;
			}
			return true;
		},
		startGame: function(event) {
			if(!this.validateInput())
				return;
			this.inGame = true;
			this.questions = this.questions.split('\n');
			this.qInd = 0;
			this.score = 0;
			// console.log("rows: " + this.rows + ", cols: " + this.cols);
			// console.log(this.questions);
			this.nextQuestion();
		},

		// TODO: validate format
		// So far assuming correct file format
		nextQuestion: function() {
			if(this.questions[this.qInd].trim()==='end'){
				// End game
				return;
			}
			var parts = this.questions[this.qInd].split(',');
			this.curQuestion = parts[0];
			this.correctAns = parts[1];
			this.trials = 0;
			this.success = false;
			
			// load choices into the grid
			var k = 2;
			this.gridData = [];
			this.choices = [];
			for(var i = 0; i < this.rows; i++) {
				var newRow = [];
				for(var j = 0; j < this.cols; j++) {
					this.choices.push(k-1);
					newRow.push({ val: parts[k++],
						color: '#30505C',
						decor: 'none',
					});
				}
				this.gridData.push(newRow);
			}
			this.qInd++;
		},
	},
});

function extractWebSocketAdressFromHttp(){
	var webSocketUrl = window.location.origin.replace("http","ws");
	webSocketUrl+= "/game"
	return webSocketUrl
}

// TODO: Parse the url in android and change the file to aviod config
// This is very brittle since it depends on the hard coded value in the app code
var wsServer = extractWebSocketAdressFromHttp();
var exampleSocket = new WebSocket(wsServer)

exampleSocket.onopen = function (event) {
	exampleSocket.send("Here's some text that the server is urgently awaiting!"); 
};
exampleSocket.onmessage = function (event) {
	console.log(event.data);

	if(isNaN(parseInt(event.data))){
		console.log("Not INT")	
	}
	else{
		vm.userAns = parseInt(event.data)
		vm.checkAns()
	}
};
