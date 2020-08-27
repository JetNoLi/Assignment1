import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.io.*;
import java.util.concurrent.ForkJoinPool;

    public class paraBasins extends RecursiveTask<String>{
    /** Class to find basins in an N x M grid for Terrain Classification */

    //instance variables 
    private float[] linearGrid;
    private int rowLength;
    private int colLength;
    private int lo;
    private int hi;
    static final int Threshold = 66; //replacable


    //constructor
     paraBasins(float[] linearGrid, int rowLength, int colLength, int hi, int lo){
        this.linearGrid = linearGrid;
        this.rowLength = rowLength;
        this.colLength = colLength;
        this.hi = hi;
        this.lo = lo;
        
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

    //@Override
    protected String compute(){
        
        if(hi - lo < Threshold){
            String basinGrid = "";
        
            for (int i = lo ; i < hi; i++){
                if (this.basinCheck(i)){
                    int[] ind = getBasinIndices(i);
                    basinGrid += ind[0] + " " + ind[1] + "\n";
                    
                }
            }
            return basinGrid;
        }
        else{
            paraBasins left = new paraBasins(linearGrid,rowLength,colLength,(hi+lo)/2,lo);
            paraBasins right = new paraBasins(linearGrid,rowLength,colLength, hi, (lo+hi)/2);
            left.fork();
            String rightAns = right.compute();
            String leftAns = left.join();
            //System.out.println(rightAns);
            return leftAns + rightAns;
        }   
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
}

class paraFunctions{

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

    public static String getOutputFormat(String gridOutput){
        return gridOutput.lines().count() + "\n" + gridOutput;
    }
    
}