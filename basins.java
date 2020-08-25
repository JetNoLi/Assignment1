import java.util.*;
import java.io.*;


public class basins{
    /** Class to find basins in an N x M grid for Terrain Classification */

    //instance variables
    private float[][] grid; //stores grid
    private boolean[][] booleanGrid; 
    private float[] linearGrid;
    private int basinCount;
    private int rowLength;
    private int colLength;


    //constructor
    public basins(){
        /**method to read in gridFile data to instance varaibles */
        this.basinCount = 0;
    }

    public void readInGrid(Scanner gridFile){
        String size[] = gridFile.nextLine().split(" "); //first line = [rows] [col]

        this.rowLength = Integer.parseInt(size[0]);
        this.colLength = Integer.parseInt(size[1]);

        this.grid = new float[rowLength][colLength];
        this.linearGrid = new float[rowLength*colLength];
        this.booleanGrid = new boolean[rowLength-2][colLength-2];
        
        //read in to grid and linearGrid
        for (int i = 0; i< rowLength; i++){
            
            String[] line = gridFile.nextLine().split(" ");

            for (int j = 0; j < colLength; j++){
                //this.grid[i][j] = Float.parseFloat(line[j]); 
                this.linearGrid[this.getLinearizedIndex(i,j)] = Float.parseFloat(line[j]);
            }
        }

    }

    public void getBasins(){

        for (int i = 0 ; i < linearGrid.length; i++){

            //beginning or end of a row( i.e col 0 and last col) 
            if (i%rowLength == 0 || i%(rowLength-1) == 0){
                continue;
            }

            //beginning or end of a column (i.e. row 0 and last row)
            
            if (i < rowLength || i > (rowLength-1)*(colLength)){
                continue;
            }

            //check for basins left and right
            if (linearGrid[i+1] - linearGrid[i] >= 0.01 && linearGrid[i-1] - linearGrid[i] >= 0.01){
                //check above

                if (linearGrid[i + rowLength] - linearGrid[i] >= 0.01 && linearGrid[i + rowLength + 1] - linearGrid[i] >= 0.01 && linearGrid[i + rowLength - 1] - linearGrid[i] >= 0.01){
                    //check below

                    if (linearGrid[i - rowLength] - linearGrid[i] >= 0.01 && linearGrid[i - rowLength + 1] - linearGrid[i] >= 0.01 && linearGrid[i - rowLength - 1] - linearGrid[i] >= 0.01){
                        //is a basin
                        int[] ind = getBooleanIndices(i);
                        booleanGrid[ind[0]][ind[1]] = true;
                        basinCount++;
                          
                    } 
                }
            }
        }
    }

    public void printBasins(){
        
        System.out.println(basinCount);

        for (int i = 0; i < booleanGrid.length; i++){
            for (int j = 0; j < booleanGrid[0].length; j++){
                
                if (booleanGrid[i][j] == true){
                    System.out.println((i-1) + " " + (j-1));
                }

            }   
        }
    }

    public int getLinearizedIndex(int row, int col){
        return (row*rowLength + col); 
    }

    public int[] getBooleanIndices(int index){
        float r = index/grid.length;
        float c = r%1;

        float row = r - c;
        float col = c * row;

        int[] indices = {(int) row + 1,(int) col + 1};
        return indices;
    }


    public static void main(String[] args){
        basins basin = new basins();
        
        
        try{
            basin.readInGrid(new Scanner(new File(args[0])));
        }

        catch(FileNotFoundException e){
            System.out.println("Error File not Found");
        }

        basin.getBasins();
        basin.printBasins();
    }


}