import java.util.concurrent.ForkJoinPool;
import java.util.Scanner;
import java.io.*;

public class paraBasinRun {
    //private float[] grid;
    //private int rowLength;
    //private int colLength;

    static final ForkJoinPool fjPool = new ForkJoinPool();

    static String runParallel(final float[] linearGrid, final int rowLength, final int colLength) {
        return fjPool.invoke(new paraBasins(linearGrid, rowLength, colLength, rowLength * colLength,0));

    }


    public static void main(final String[] args) {
        String gridOutput = "";
        double time = 0;

        try {
            final float[] grid = paraFunctions.getLinearGrid(new Scanner(new File(args[0])));
            test.tick();
            gridOutput = runParallel(grid, (int)grid[grid.length - 2], (int) grid[grid.length - 1]);
            time = test.tock();
        }

        catch (final FileNotFoundException e) {
            System.out.println("Error File not Found");
        }  

        gridOutput = paraFunctions.getOutputFormat(gridOutput);
        //System.out.println(gridOutput);
        System.out.println("Time taken in ms: " + time);

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
    
