# LowestPathCost

This repository was made for Android Application that finds the path of lowest cost when moving across a grid. 
https://github.com/bereket11/LowestPathCost. To use the application you need to installed it into your phone with .apk format or clone the package and run the application using Android Studio. 

The application presents the path of lowest cost with the number of rows that path passes through it using two fragments each for user input and hardcoded data in MVC Architectural pattern. It checks if there is correct input for the grid and it return yes or no depending in the cost of the lowest path. And, I followed TDD to developer the codes of the application. 

The application contains MainActivity in which it launches two fragments using tab option to use hardcode grids of integers or user can enter input integers. In the fragments folder, there a HardCodedDataFragment and UserDataFragment fragments in which both fragments accepts input and display the result in the output. Then TraverseGrid class receives the given grid that will be use getBestPathGrid to traverse through each row by sending the current row, the given grid and state of the path. For each row, the RowTraverseColumns update the status of the grid with the lowest path by comparing the adjust rows and keep marching to the end of columns. After the best path for the row is found with its cost, it will be added to the PathStateCollector to be compared with other rows best paths.

Future Improvemnt
-----------------
If I had more time, I was implement Mockito and robolectric for unit testing and Espresso for UI testing. Also, I will use MVVM Architectural pattern so that it will be east to have unit test. And, I will make the UI more attractive using Material Design. 


