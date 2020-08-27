
import java.io.*;
import java.util.Scanner;
//import org.apache.commons.io.FileUtils;
public class test{
    static double startTime = 0;

    public static void tick(){
        startTime = System.currentTimeMillis();
    } 

    public static double tock(){
        return (System.currentTimeMillis() - startTime)/1000.0;
    }
    

    public static void main(String[] args){
        Scanner src;
        Scanner test;

        
        try{
            src = new Scanner(new File(args[0]));
            test = new Scanner(new File(args[1]));

            if (contentEquals(src,test)){
                System.out.println("Output is correct");
            }
            else{
                System.out.println("Output is incorrect");
            }
        }

        catch(IOException e){
            System.out.println("Error reading in Files");
        }
    }

    public static boolean contentEquals(Scanner src, Scanner test){
        
        while (src.hasNext()){
            if (src.next().equals(test.next())){
                continue;
            }

            else{
                return false;
            }
        }

        return true;
    }

}