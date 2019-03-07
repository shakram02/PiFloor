# PI-Floor Front-end

### Navigate to front-end directory
From the root of the project:
``` cd app/src/main/web ```

### Install dependencies
``` npm install ```

> Make sure you have **Node.js v8.0+** and **Npm v3.5.0+** installed before installing the dependencies.

### Run in debugging mode
``` npm run serve ```

### Build for production
``` npm run build ```

*NOTE* Running this command will clear the content of the assets folder

Resulted files are typically built to **dist** folder.

Copy [the Audio and Image files](https://github.com/shakram02/PiFloor/tree/web-development/app/src/main/assets/webpackProject/pifloor/src/assets) that will be requested by the web app to the **assets** folder.

Note that all the HTTP requests sent by the web app assume the Audio and Image files to be found directly inside the **assets** folder, not nested inside any sub-folders. The `assets` folder is considered the website root.

For example, requesting an audio file may be done like this: ```http://localhost:5444/correct.mp3```
