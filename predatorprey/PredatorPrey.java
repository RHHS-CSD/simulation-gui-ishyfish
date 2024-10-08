/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package predatorprey;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.lang.Exception;

/**
 *
 * @author ishy1
 */
public class PredatorPrey {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //intialize starting grid
        int[][]grid = initializeGrid(8,20);
        
        //create 2d array of same dimensions of grid to store steps predators have gone without eating prey
        int[][]preylessSteps = new int[grid.length][grid[0].length];
        
        //create 2d array of same dimensions of grid to store steps prey have gone without eating food
        int[][]foodlessSteps = new int[grid.length][grid[0].length];
        
        //create another 2d array of same dimensions to keep track of where bushes are located
        int[][]bushes = initializeBushes(grid.length, grid[0].length);

        //create scanner
        Scanner s = new Scanner(System.in);
        
        boolean playSimulation = true;
        while (playSimulation){
            //continue to next frame once input entered
            System.out.println("Enter any button to continue, Q to exit");
            String input = s.nextLine();
            
            //quit if "Q" entered 
            if (input.equals("Q") == true){
                playSimulation = false;
            }
            else {
                System.out.println("Prey population: " + getPopulation(grid, 1));
                System.out.println("Predator population: " + getPopulation(grid, 2));

                
                printGrid(grid, bushes);
                
                oneTurn(grid, preylessSteps, foodlessSteps, bushes); 
            }    
            
        }
        
        
    }
    
    //prints out the 2d grid to the console
    private static void printGrid(int[][]grid, int[][]bushes){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                //if cell contains predator print O
                if (grid[i][j] == 2){
                    //if cell has a bush
                    if (bushes[i][j] == 1){
                        System.out.print("o ");
                    }
                    else{
                        System.out.print("O ");
                    }
                }
                //if cell contains prey print P
                else if (grid[i][j] == 1){
                    //if cell has a bush
                    if (bushes[i][j] == 1){
                        System.out.print("p ");
                    }
                    else{
                        System.out.print("P ");
                    }
                }
                //if cell has no prey or predator 
                else{
                    //if cell has a bush
                    if (bushes[i][j] == 1){
                        System.out.print("x ");
                    }
                    else{
                        System.out.print(". ");
                    }
                }
                
            }
            System.out.println("\n");
        }
    }
    
    //randomly generates a starting frame for a new grid based on given rows/cols
    public static int[][] initializeGrid(int row, int col){    
        /** Method randomly generates a starting simulation grid filled with prey and predators
         * @param row - inputted number of rows for grid
         * @param col - inputted number of columns for grid
         * @returns returns the generated grid
         */

        //create new Random
        Random r = new Random();
        
        //create blank grid
        int[][]grid = new int[row][col];
        
        //calculate total elements
        int area = row*col;
        
        //set prey population (30% of total grid area rounded up)
        int preyPopulation = (int)Math.ceil(area*3.0/10.0);
        
        //loop for each prey to add a new prey to the grid
        for (int i=0; i<preyPopulation; i++){
            boolean preyNotPlaced = true;
            
            //while the prey hasn't been placed, generate a new random position
            while(preyNotPlaced){
                int preyRow = r.nextInt(row);
                int preyCol = r.nextInt(col);
                //if generated grid position is empty fill with the prey representing it by 1, else continue generating
                if (grid[preyRow][preyCol] == 0){
                    grid[preyRow][preyCol] = 1;
                    preyNotPlaced = false;
                }
            }
        }
        
        //set predator population (5% of total grid area rounded up)
        int predatorPopulation = (int)(Math.ceil(area/20.0));
        
        //loop for each predator
        for (int i=0; i<predatorPopulation; i++){
            boolean predNotPlaced = true;
                    
            //while the predator hasn't been placed, generate a new random position
            while(predNotPlaced){
                int predRow = r.nextInt(row);
                int predCol = r.nextInt(col);
                //if grid generated grid position is empty fill with the prey represented by 2, else continue generating
                if (grid[predRow][predCol] == 0){
                    grid[predRow][predCol] = 2;
                    predNotPlaced = false;
                }
            }
        }
        
        return grid;
    }
    
    //randomly generates starting bushes when simulation starts
    public static int[][] initializeBushes(int row, int col){
        /** Method randomly generates bushes in a 2D array 
         * @param row - inputted number of rows for array
         * @param col - inputted number of columns for array
         * @returns returns the generated array with bushes
         */

        //create new Random
        Random r = new Random();
        
        //create blank grid
        int[][]bushes = new int[row][col];
        
        //calculate total elements
        int area = row*col;
        
        //set number of bushes to be added (25% of total grid area rounded up)
        int bushNumber = (int)Math.ceil(area/4.0);

        //loop for each bush, add a bush to the grid
        for (int i=0; i<bushNumber; i++){
            boolean bushNotPlaced = true;
            
            //while the prey hasn't been placed, generate a new random position
            while(bushNotPlaced){
                int bushRow = r.nextInt(row);
                int bushCol = r.nextInt(col);
                //if generated grid position is empty fill with the prey representing it by 1, else continue generating
                if (bushes[bushRow][bushCol] == 0){
                    bushes[bushRow][bushCol] = 1;
                    bushNotPlaced = false;
                }
            }
        }
        return bushes;
    }
    
    //returns the population of the type of population inputted in a grid
    public static int getPopulation(int[][]grid, int populationType){
        /** Method determines the number of cells in a 2D array that contains a certain element
         * @param grid - inputted 2D array
         * @param populationType - inputted element to be counted
         * @return returns the population of the inputted element in the array
         */
        
        int population = 0;
        
        //loop through the entire grid and add one to the counter when a cell contains the population type
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j] == populationType){
                    population ++;
                }
            }
        }
        return population;
    }
    
    //checks the surroundings of a given cell (row/col) in the inputted grid
    private static int[] checkSurroundings(int[][]grid, int row, int col){
        //cell values: 0-surrounding cell is empty, 1 - cell is prey, 2 - cell is predator, -1 - cell doesnt exist
        //cell indexes: 0 - cell above, 1 - cell to the right, 2 - cell below, 3- cell to the left
        int[]surroundings = new int[4];
        
        //determine cell above
        try{
            surroundings[0] = grid[row-1][col];
            
        }
        catch(Exception ArrayIndexOutOfBoundsException){
            surroundings[0] = -1;
        }
        //determine cell to the right
        try{
            surroundings[1] = grid[row][col+1];

        }
        catch(Exception ArrayIndexOutOfBoundsException){
            surroundings[1] = -1;
        }
        //determine cell below
        try{
            surroundings[2] = grid[row+1][col];
            
        }
        catch(Exception ArrayIndexOutOfBoundsException){
            surroundings[2] = -1;
        }
        //determine cell to the left
        try{
            surroundings[3] = grid[row][col-1];
        }
        catch(Exception ArrayIndexOutOfBoundsException){
            surroundings[3] = -1;
        }
        
        return surroundings;
    }
    
    //randomly chooses one element in an array
    public static int chooseRandomArrayElement(int[]array){
        /** Method takes in an array and chooses a random element to return
         * @param array - the inputted array
         * @return - 0 if array is empty; else the randomly chosen element
         */
        //create new random
        Random r = new Random();
        if (array.length > 0){
            //choose random index
            int randomIndex = r.nextInt(array.length);
            //return value of random index
            return array[randomIndex];

        }
        else {
            return 0;
        }
        
    }
    
    //adds an extra int to an array
    public static int[] addToArray(int[]array, int x){
        /** Method adds an additional value to the end of an inputted
         * @param array - inputted array to be extended
         * @param x - value to be added to the array
         * @return returns the new extended array
         */
        
        //create a copy of the array with one additoinal cell
        int[]copy = Arrays.copyOf(array,array.length+1);
        
        //add inputed value to last cell
        copy[copy.length-1] = x;
        
        return copy;
    }
    
    //moves the predators and then prey one turn; small chance for predators/preys to reproduce before they move under certain conditions
    public static void oneTurn(int[][]grid, int[][]preylessSteps, int[][]foodlessSteps, int[][]bushes){
        /** Method simulates one entire turn of the simulation including movement, reproduction, and death 
         * @param grid - 2D array that represents the simulation grid
         * @param preylessSteps - 2D array that stores the number of steps predators have gone without eating
         * @param foodlessSteps - 2D array that stores the number of steps prey have gone without eating
         * @param bushes - 2D array that stores which cells contain bushes in the simulation grid
         * @return - n/a
         */

        //create a new 2d grid with same dimensions to store where predators move
        int[][]newGrid = new int[grid.length][grid[0].length];
        
        //copy all of current grid to new grid
         for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                newGrid[i][j] = grid[i][j];
            }
        }
         
        //move all predators
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
              
                //if cell cotnains predator that has starved
                if (grid[i][j] == 2 && preylessSteps[i][j] >= 10){
                    //remove preadtor from arrays  
                    newGrid[i][j] = 0;
                    preylessSteps[i][j] = 0;

                }
                //if cell contains predator that hasn't starved
                else if (grid[i][j] == 2 && preylessSteps[i][j] < 10){
                    //get its surroundings                   
                    int[]predSurroundings = checkSurroundings(newGrid, i, j);
                    int[]bushSurroundings = checkSurroundings(bushes, i, j);
                    int[]predSurroundingsCopy = Arrays.copyOf(predSurroundings, predSurroundings.length);
                    Arrays.sort(predSurroundingsCopy);
                          
                    //create variable for direction predator moves to
                    int direction = 0;
                    int[]possibleDirections = new int[0];
                    int offspringDirection = 0;

                    
                    //if predator surroundings contain prey that is not hidden in a bush, predators eats prey and replaces it in new grid
                    if (Arrays.binarySearch(predSurroundingsCopy,1) >= 0){
                        //create an array that stores which directions have prey; values: 1 - above, 2 - right, 3 - down, 4 - left 
                        
                        //if cell above contains prey
                        if (predSurroundings[0] == 1 && bushSurroundings[0]!= 1){
                            possibleDirections = addToArray(possibleDirections, 1);
                        }
                        //if cell to the right contains prey
                        if (predSurroundings[1] == 1 && bushSurroundings[1]!= 1) {
                            possibleDirections = addToArray(possibleDirections, 2);
                        }
                        //if cell below contains prey
                        if (predSurroundings[2] == 1 && bushSurroundings[2]!= 1){
                            possibleDirections = addToArray(possibleDirections, 3);
                        }
                        //if cell to the left contains prey
                        if (predSurroundings[3] == 1 && bushSurroundings[3]!= 1){
                            possibleDirections = addToArray(possibleDirections, 4);
                        }
                        
                        
                        //if there are no prey surrounding that aren't in bushes, choose random existing direction without another predator to move to
                        if (possibleDirections.length<=0){
                            if (predSurroundings[0] != -1 && predSurroundings[0] != 2){
                                possibleDirections = addToArray(possibleDirections, 1);
                            }
                            if (predSurroundings[1] != -1 && predSurroundings[0] != 2){
                                possibleDirections = addToArray(possibleDirections, 2);
                            }
                            if (predSurroundings[2] != -1 && predSurroundings[0] != 2){
                                possibleDirections = addToArray(possibleDirections, 3);
                            }
                            if (predSurroundings[3] != -1 && predSurroundings[0] != 2){
                                possibleDirections = addToArray(possibleDirections, 4);
                            }
                            
                        }
                        
                        //choose a random direction with a prey
                        direction = chooseRandomArrayElement(possibleDirections);
                        
                        //reset the preadtors steps without eating prey value to 0
                        preylessSteps[i][j] = 0;
                    }
                    //if predator surroundings dont contain prey but contain empty spaces
                    else if (Arrays.binarySearch(predSurroundingsCopy,0) >= 0){

                        //if cell above contains empty space
                        if (predSurroundings[0] == 0){
                            possibleDirections = addToArray(possibleDirections, 1);
                        }
                        //if cell to the right contains empty space
                        if (predSurroundings[1] == 0){
                            possibleDirections = addToArray(possibleDirections, 2);
                        }
                        //if cell below contains empty space
                        if (predSurroundings[2] == 0){
                            possibleDirections = addToArray(possibleDirections, 3);
                        }
                        //if cell to the left contains empty space
                        if (predSurroundings[3] == 0){
                            possibleDirections = addToArray(possibleDirections, 4);
                        }
                        
                        //choose a random direction with an empty space
                        direction = chooseRandomArrayElement(possibleDirections);
                        
                       
                        
                        //add 1 to predator's steps without eating prey
                        preylessSteps[i][j] += 1;
                    }
                    //no avaliblabe movements
                    else {
                        //add 1 to predator's steps without eating prey
                        preylessSteps[i][j] += 1;
                    }
                    
                    //predator reproduce
                       Random r = new Random();
                       int chance = r.nextInt(10);
                       //10% chance to reproduce if there are at least 2 cells around with empty space and predator has eaten in the past 5 steps
                       if (chance == 0 && possibleDirections.length > 1 && preylessSteps[i][j] <= 5){
                           //choose random direction to spawn offspring that is different from the direction the predator will move to
                           boolean chooseOffspringDirection = true;
                           while(chooseOffspringDirection){
                               offspringDirection = chooseRandomArrayElement(possibleDirections);
                               if (offspringDirection != direction){
                                   chooseOffspringDirection = false;
                               }
                           }
                       }
                    
                    //Change predator location in new grid based on direction and adjust the preylessSteps array to match 
                    //no movement
                    if (direction == 0){
                        newGrid[i][j] = grid[i][j];
                    }
                    //move up
                    else if (direction == 1){
                        newGrid[i-1][j] = grid[i][j];
                        newGrid[i][j] = 0;
                        
                        preylessSteps[i-1][j] = preylessSteps[i][j];
                        preylessSteps[i][j] = 0;
                    }
                    //move right
                    else if (direction == 2){
                        newGrid[i][j+1] = grid[i][j];
                        newGrid[i][j] = 0;
                        
                        preylessSteps[i][j+1] = preylessSteps[i][j];
                        preylessSteps[i][j] = 0;
                    }
                    //move down
                    else if (direction == 3){
                        newGrid[i+1][j] = grid[i][j];
                        newGrid[i][j] = 0;
                        
                        preylessSteps[i+1][j] = preylessSteps[i][j];
                        preylessSteps[i][j] = 0;
                    }
                    //move left
                    else if (direction == 4){
                        newGrid[i][j-1] = grid[i][j];
                        newGrid[i][j] = 0;
                        
                        preylessSteps[i][j-1] = preylessSteps[i][j];
                        preylessSteps[i][j] = 0;
                    }
                    
                    //add offspring if any genereated to corresponding cell based on direction
                    if (offspringDirection == 1){
                        newGrid[i-1][j] = grid[i][j];
                    }
                    else if (offspringDirection == 2){
                        newGrid[i][j+1] = grid[i][j];
                    }
                    else if (offspringDirection == 3){
                        newGrid[i+1][j] = grid[i][j];
                    }
                    else if (offspringDirection == 4){
                        newGrid[i][j-1] = grid[i][j];
                    }
                    
                }
            }
        }
        
        //move prey
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j] == 1 && foodlessSteps[i][j] > 2){
                    //remove preadtor from arrays  
                    newGrid[i][j] = 0;
                    foodlessSteps[i][j] = 0;
                }
                //if cell contains prey that HASNT just been eaten and HASNT starved 
                else if (grid[i][j] == 1 && newGrid[i][j]!= 2 && foodlessSteps[i][j] <=2){
                   //get its surroundings in updated grid                 
                   int[]preySurroundings = checkSurroundings(newGrid, i, j);
                   int[]preySurroundingsCopy = Arrays.copyOf(preySurroundings, preySurroundings.length);
                   Arrays.sort(preySurroundingsCopy);
                    
                   //create variable for direction predator moves to and direction potential offspring will be produced
                   int direction = 0;
                   int offspringDirection = 0;
                   
                   //move only if at least one surrounding cells is unoccupied
                   if (Arrays.binarySearch(preySurroundingsCopy,0) >= 0){
                        int[]directionsWithEmptySpace = new int[0];

                        //if cell above contains empty space
                        if (preySurroundings[0] == 0){
                            directionsWithEmptySpace = addToArray(directionsWithEmptySpace, 1);
                        }
                        //if cell to the right contains empty space
                        if (preySurroundings[1] == 0){
                            directionsWithEmptySpace = addToArray(directionsWithEmptySpace, 2);
                        }
                        //if cell below contains empty space
                        if (preySurroundings[2] == 0){
                            directionsWithEmptySpace = addToArray(directionsWithEmptySpace, 3);
                        }
                        //if cell to the left contains empty space
                        if (preySurroundings[3] == 0){
                            directionsWithEmptySpace = addToArray(directionsWithEmptySpace, 4);
                        }
                        
                        //reset steps without eating
                        foodlessSteps[i][j] = 0;

                        
                        //choose a random direction with a prey
                        direction = chooseRandomArrayElement(directionsWithEmptySpace);
                        
                        //prey reproduction
                        Random r = new Random();
                        int chance = r.nextInt(10);
                        //10% chance to reproduce if there are at least 2 cells aroudn with empty space 
                        if (chance == 0 && directionsWithEmptySpace.length > 1){
                            //choose random direction to spawn offspring that is different from the direction the prey will move to
                            boolean chooseOffspringDirection = true;
                            while(chooseOffspringDirection){
                                offspringDirection = chooseRandomArrayElement(directionsWithEmptySpace);
                                if (offspringDirection != direction){
                                    chooseOffspringDirection = false;
                                }
                            }
                        }
                    }
                   
                   //Change prey location in grids based on direction
                    //no movement
                    if (direction == 0){
                        newGrid[i][j] = grid[i][j];
                        foodlessSteps[i][j] += 1;

                    }
                    //move up
                    else if (direction == 1){
                        newGrid[i-1][j] = grid[i][j];
                        newGrid[i][j] = 0;
                        
                    }
                    //move right
                    else if (direction == 2){
                        newGrid[i][j+1] = grid[i][j];
                        newGrid[i][j] = 0;
                    }
                    //move down
                    else if (direction == 3){
                        newGrid[i+1][j] = grid[i][j];
                        newGrid[i][j] = 0;
                    }
                    //move left
                    else if (direction == 4){
                        newGrid[i][j-1] = grid[i][j];
                        newGrid[i][j] = 0;
                    }
                    
                    //add offspring if any genereated
                    if (offspringDirection == 1){
                        newGrid[i-1][j] = grid[i][j];
                    }
                    else if (offspringDirection == 2){
                        newGrid[i][j+1] = grid[i][j];
                    }
                    else if (offspringDirection == 3){
                        newGrid[i+1][j] = grid[i][j];
                    }
                    else if (offspringDirection == 4){
                        newGrid[i][j-1] = grid[i][j];
                    }
                }
            }
        }
        
        //copy all of new grid to original grid
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                grid[i][j] = newGrid[i][j];
            }
        }

    }
}
