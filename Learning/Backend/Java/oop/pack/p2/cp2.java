package p2;

import p1.*;

public class cp2{

     public static void blee() {
            System.out.println("This is p2.cp2.blee() executing ");
            p1.cp2.bloo();   // needs full path qualification
            p1.inner.cp2.fun();
      }

}


