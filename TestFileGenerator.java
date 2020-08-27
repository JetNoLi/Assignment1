import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * CSC2002S Parallel Programming A1
 * Test File Generator.
 * @author Mulaudzi Talifhani (MLDTAL001)
 *
 */

public class TestFileGenerator {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int cols = sc.nextInt();

		FileWriter fileWriter = null;
		try {
			String fileName = rows + "by" + cols+"_in.txt";
			fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.printf(String.format("%d %d%n", rows, cols));

			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y++) {
					if(rows <= 50)
						printWriter.printf(String.format("%.2f ", (1 + Math.random() * 99))); 					
					printWriter.printf(String.format("%.2f ", (1350 + Math.random() * 99))); 
				}
				//printWriter.print("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
	}
}