# PiFloor
[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.1476834.svg)](https://doi.org/10.5281/zenodo.1476834)

An Android Portable interactive floor with minimal setup, oriented towards child education. Detailed description about setup and usage is [here](Pi_Floor_Poster.pdf)

Pi Floor has three main modules
- Optical Character Recognition (OCR) provided by Google's vision API (soon MLKit)
- User Interface a website that's hosted on the phone used to display questions on a projection screen for example (or other device)
- Http and Web Socket server, for communication between user interface and application logic
    <br> 
    <img src="img/AppDesign.png" alt="AppDesign"/>

## How it works

- Put the grid tiles on the floor, where each tiles contains an English word
    <br> 
    <img src="img/tiles.jpg" alt="tiles" width="400" height="239"/>

- Enter calibration mode and select correct text on the tiles that appear on the screen
    <br> 
    <img src="img/grid_calibration.png" alt="Calibration" width="360" height="390"/>
- Enter game mode, start the web server
    <br> 
    <img src="img/game_mode_reduced.jpg" alt="GameMode" width="320" height="325"/>
- Access the given URL in game mode
    <br> 
    <img src="img/home_page.jpg" alt="HomePage" width="240" height="215"/>
- Upload a file containing questions and answers on the following format
    ![sample](sample_question.txt)
    
    ```
    Question?answer,choice0,choice1,choice2,choice3,...etc
    ```
- Students stand on the tile to mark the answer they want
    <br> 
    <img src="img/questions_page.jpg" alt="GameRunning" width="260" height="320"/>

# Thank You

- [AndroidAsync](https://github.com/koush/AndroidAsync)
- [Google's Android Vision Samples](https://github.com/googlesamples/android-vision) OCR Reader
- [Dagger](https://github.com/google/dagger)
- [RxKotlin](https://github.com/ReactiveX/RxKotlin)
