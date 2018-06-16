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
		userAns: 1,
		// Game status
		rows: 3,
		cols: 3,
		gridData: [],
		questions: [],
		inGame: false,
	},
	methods: {
		checkAns: function(event) {
			console.log("Selected answer: " + this.userAns);
			this.trials++;
			if(this.userAns === this.correctAns) {
				this.success = true;
				console.log("correct ans");
				if(this.trials > 1)
					this.score += 5;
				else this.score += 10;
			} else {
				console.log("wrong ans - expected " + this.correctAns + " found " + this.userAns);
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
		startGame: function(event) {
			this.inGame = true;
			this.questions = this.questions.split('\n');
			this.qInd = 0;
			this.score = 0;
			console.log("rows: " + this.rows + ", cols: " + this.cols);
			console.log(this.questions);
			this.nextQuestion();
		},

		// TODO: validate format
		// So far assuming correct file format
		nextQuestion: function() {
			if(this.questions[this.qInd].trim()==='end'){
				// End game
				return;
			}
			// var width = document.getElementById("game-grid").offsetWidth;
			// document.getElementById("question-place").setAttribute("style","width:"+width+"px");
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
					newRow.push(parts[k]);
					this.choices.push(parts[k++]);
				}
				this.gridData.push(newRow);
			}
			this.qInd++;
		},
	},
});
