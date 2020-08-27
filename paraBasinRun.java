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

        try {
            final float[] grid = paraFunctions.getLinearGrid(new Scanner(new File(args[0])));
            gridOutput = runParallel(grid, (int)grid[grid.length - 2], (int) grid[grid.length - 1]);

        }

        catch (final FileNotFoundException e) {
            System.out.println("Error File not Found");
        }  

        //System.out.println(paraBasinsFunctions.init(grid,rowLength,colLength));

        System.out.println(gridOutput.lines().count());
        System.out.println(gridOutput);
    }
}
    
