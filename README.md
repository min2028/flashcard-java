# FlashCard Generator

### Questions and brief description of the application

#### What will the application do?
The application will be used to reinforce the memorization of things that will be important in the study or work life
by helping the user create flashcards. The flashcards will be stored in the subjects under the dividers which will help 
the user compart relevant cards together. For instance, different terms (using divider) and within a term, 
the different courses (using subject). The application will also show the user the date the flashcard was added, 
allowing the user to see which flashcard they have **most likely** forgot by then. 

#### Who will use it?
Mainly students who have to *memorize* and *learn* the materials from their studies (for eg, medical students)
and even workers whose jobs require them to *memorize* could benefit from this app.
For instance, waiters who would like to memorize the restaurant menus. 
**Anyone** who has a lot of items to *memorize* will find the app useful. 

#### Why is this project of interest to you?
This project interests me because as a student I have to *memorize* a lot of materials especially when I am taking 
biology and organic chemistry classes. This app could help me with my study and since I created this app,
I could also add some features or functions of personal needs. So, I could use it more effectively and 
other users could also benefit from it.

## User Stories

Functions the application will have:
- As a user, I want to be able to create a divider
- As a user, I want to be able to create a subject
- As a user, I want to be able to create a flashcard
- As a user, I want to be able to add flashcards to a subject
- As a user, I want to be able to add subjects to a divider
- As a user, I want to be able to see the date the flashcard was added
- As a user, I want to be able to save my flashcards, subjects, and dividers to file
- As a user, I want to be able to load my flashcards, subjects, and dividers from file

- As a user, I want to be able to add a flashcard to a subject, and a divider when I click the "set" button (For GUI)
- As a user, I want to be able to add more flashcards to new or existing subject and divider
  whenever I click the "set" button (For GUI)
- As a user, I want to be able to review the information of the flashcard I set when I click review (For GUI)
- As a user, I want to be able to load and save the state of the application (For GUI)
- As a user, I want to be able to see the panel that displays the flashcards, and name of the divider and
  subject it has been added to when I load the generator. (For GUI)
  
#### Phase 4: Task 2
 Added the Compartable interface and Writable abstract class. DividerList, divider, subject classes
 implements the Compartable interface and all four classes in the model package extend the Writable 
 abstract class. So, all four classes in the model package plays a role.
 
#### Phase 4: Task 3
Since DividerList is the list of divider and divider is the list of subject and subject is the list of flashcards, they 
have association downwards. DividerList, divider and subject are all compartments so they implement the compartable 
interface. All the classes in the model package are being translated into json file in to save, so they all implement 
the abstract class, Writable. FlashcardGenerator and NewWindowToCreateFlashcard generates the CUI and GUI respectively, 
so it uses all the four classes in the model package as well as the classes in the persistence package. 
FlashCardGeneratorApp only creates the welcome window so it has no associations with the classes created other than the 
classes from the java library.

If I have time, I would use the observer pattern to notify the user the flashcard or subject or divider they are 
creating. I would also create more classes with a specific behaviour to each class to increase cohesion for the 
FlashCardGenerator and NewWindowToCreateFlashcard classes.