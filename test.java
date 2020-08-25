public class test {
    
    public static void main(String[] args){
        int index = 9;
        int rowLength = 4;
        float r = index/rowLength;
        float c = r%1;

        float row = r - c;
        float col = c * rowLength;

        

        System.out.println("r = "+ r);
        System.out.println("c = "+ c);
        System.out.println("col " + col);
    }
}