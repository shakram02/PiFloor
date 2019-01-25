# Roadmap
1. Design and implement a user-friendly UI that suits students in a small age (less than 13 years) and teachers (that are able to speak their native language only)
    1. - [ ] The UI (frontend) must be attractive to keep the students attached during game play
    2. - [ ] The application should have badges and accomplishments for the students to achieve, i.e. you solved 5 questions per day, 10 days in a row, or stuff like Khan Academy
    3. - [ ] The UI should be accessible in Arabic (or other languages) (localized strings)
    4. - [ ] Please make sure that the application flow is smooth and doesn't need a tutorial (displaying a tutorial when the app first runs is highly recommended, e.g. [AppIntro](https://github.com/AppIntro/AppIntro))
    5. - [ ] Please make sure the colors match the students' age range (it would be cool to make colors for boys and another set of colors for girls -the teacher chooses-)
    6. - [ ] Please make sure that the website is responsive and works on multiple screen sizes (it can be run on a mobile device, up to a projector screen)
    
2. Restructure the Android application
    1. - [ ] The application needs to be re-written and restructured to a loosely coupled architecture (e.g. MVVM, ...etc) with Dependency Injection ([Dagger2](https://github.com/google/dagger))

3. - [ ] Rewrite the frontend app

3. Implement an Admin page
    1. - [ ] The teacher / parent can add questions through the admin page (web itnerface)
    2. - [ ] The admin page allows the teacher / parent to save the created questions locally on the device

4. Google Drive
    1. - [ ] A teacher should be able to store the question-file on Google Drive
    2. - [ ] The teacher should be able to share their questions with other users. (`extra` Nearby APIs?)
    3. - [ ] `extra` The teacher decides if the shared questions can be used only once or multiple times
    4. - [ ] Other users should be able to download the file (e.g. A Teacher shares questions with parents so students can play at home)
    
5. Databases
    1. - [ ] Allow the teacher to group the questions by year and subject (or just add them in a generic entry)
    2. - [ ] A teacher should be able to add students to their class in case they run the game with students in the same class over the year for a couple of times
    3. - [ ] `extra` Allow the teacher to save their students progress on the cloud
    4. - [ ] The game stores the students who played on the device so they can login and view their scores, ...etc
