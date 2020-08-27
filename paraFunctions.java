import java.util.Scanner;

public class paraFunctions{

    public static float[] getLinearGrid(Scanner gridFile){
        String size[] = gridFile.nextLine().split(" "); //first line = [rows] [col]
        
        int rowLength = Integer.parseInt(size[0]);
        int colLength = Integer.parseInt(size[1]);

        float[] linearGrid = new float[rowLength*colLength + 2];
        
        //read in to grid and linearGrid
        for (int i = 0; i< rowLength; i++){
            for (int j = 0; j < colLength; j++){     
                linearGrid[i*rowLength + j] = Float.parseFloat(gridFile.next());
            }
        }

        linearGrid[linearGrid.length-2] = rowLength; //algorithm does not check these final indices
        linearGrid[linearGrid.length-1] = colLength;
        return linearGrid;
    }
    
}