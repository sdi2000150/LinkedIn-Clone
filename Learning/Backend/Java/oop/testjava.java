import java.util.Random;

enum testLetter {
    A,
    B,
    C
}
public class testjava {
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        // System.out.println(n); 
        Random rand = new Random();
        for (int i = 0 ; i < 8 ; i ++ ) {
            // double di = rand.nextInt(10) ;//+ rand.nextDouble(); //random double from 0.0 to 10.0
            // System.out.println(di);
            int ni= rand.nextInt(800)+1200;
            System.out.println(ni);
        }

        System.out.println(testLetter.values()[0]);
        System.out.println(testLetter.values().length);
        // int gear = 1;
        // System.out.println(gear);
        // gear = change(gear);
        // System.out.println(gear);
    }
    
    // static int change(int i) { return ++i ; }
}
