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
        if (args.length > 1){
            System.out.println("For Sequential cutoff: " + args[1]);
        }

        String gridOutput = "";
        double[] times = new double[5];
        String dummy = "";

        try {
            final float[] grid = paraFunctions.getLinearGrid(new Scanner(new File(args[0])));
            
            for (int i = 0 ; i < 10; i++){
                dummy = runParallel(grid, (int)grid[grid.length - 2], (int) grid[grid.length - 1]);
            }

            for (int i = 0; i<10; i++){
                test.tick();
                gridOutput = runParallel(grid, (int)grid[grid.length - 2], (int) grid[grid.length - 1]);
                System.out.println("Time taken in ms: " + test.tock());
            }
        }

        catch (final FileNotFoundException e) {
            System.out.println("Error File not Found");
        }  

        gridOutput = paraFunctions.getOutputFormat(gridOutput);

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
    
