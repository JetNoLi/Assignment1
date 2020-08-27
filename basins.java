import java.util.*;
import java.io.*;


public class basins{
    /** Class to find basins in an N x M grid for Terrain Classification */

    //instance variables
    private float[] linearGrid;
    private int rowLength;
    private int colLength;
    //static double startTime;

    //constructor
    public basins(){
        /**method to read in gridFile data to instance varaibles */
        //this.basinCount = 0;
    }

    public void readInGrid(Scanner gridFile){
        String size[] = gridFile.nextLine().split(" "); //first line = [rows] [col]

        this.rowLength = Integer.parseInt(size[0]);
        this.colLength = Integer.parseInt(size[1]);

        this.linearGrid = new float[rowLength*colLength];
        
        //read in to grid and linearGrid
        for (int i = 0; i< rowLength; i++){
            for (int j = 0; j < colLength; j++){     
                this.linearGrid[this.getLinearizedIndex(i,j)] = Float.parseFloat(gridFile.next());
            }
        }
    }

    public boolean basinCheck(int i){
        //beginning or end of a row( i.e col 0 and last col) 
        if (i%rowLength == 0 || i%(rowLength-1 + i/rowLength * colLength) == 0){
            return false;
        }

        //beginning or end of a column (i.e. row 0 and last row)
        if (i < rowLength || i >= (rowLength-1)*(colLength)){
            return false;
        }

        //check for basins left and right
        if (linearGrid[i+1] - linearGrid[i] >= 0.01 && linearGrid[i-1] - linearGrid[i] >= 0.01){
            //check below

            if (linearGrid[i + rowLength] - linearGrid[i] >= 0.01 && linearGrid[i + rowLength + 1] - linearGrid[i] >= 0.01 && linearGrid[i + rowLength - 1] - linearGrid[i] >= 0.01){
                //check above
               
                if (linearGrid[i - rowLength] - linearGrid[i] >= 0.01 && linearGrid[i - rowLength + 1] - linearGrid[i] >= 0.01 && linearGrid[i - rowLength - 1] - linearGrid[i] >= 0.01){
                    //is a basin
                    return true;           
                } 
            }
        }
        return false;
    }

    public String getBasins(){
        String basinGrid = "";
        int basinCount = 0;

        for (int i = 0 ; i < linearGrid.length; i++){
           if (this.basinCheck(i)){
            int[] ind = getBasinIndices(i);
            basinGrid += ind[0] + " " + ind[1] + "\n";
            basinCount++;
              
           }
        }

        return basinCount + "\n" + basinGrid;
    }

    public int getLinearizedIndex(int row, int col){
        return (row*rowLength + col); 
    }

    public int[] getBasinIndices(int index){
        float r = ((float)index)/((float)rowLength);
        float c = r%1;

        float row = r - c;
        float col = c * rowLength;

        int[] indices = {(int) row ,(int) col };
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
        
        String gridOutput = "";
        String dummy = "";

        for (int i = 0; i < 10; i++){
            dummy = basin.getBasins();
        }

        for (int i = 0; i< 10; i++){

            test.tick();
            gridOutput = basin.getBasins();
            System.out.println("Time taken in ms: " + test.tock());
        }

        try{
            FileWriter wFile = new FileWriter(args[0].replace("in","testOut"));
            wFile.write(gridOutput);
            wFile.close();
        }

        catch(IOException e){
            System.out.println("Could not write to file");
        }
        
    }
}