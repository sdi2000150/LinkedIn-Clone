package p1;

public class cp2{

     public static void bloo() {
          System.out.println("This is p1.cp2.bloo() executing ");
     }

     static void foo(){
          System.out.println("Inside p1.cp2.foo()"); 
          p1.inner.cp2.fun(); // ERROR:if fun() is not public in cp2; cannot be accessed from outside package
          bla();     
     }

     private static void bla() {
          System.out.println("This is p1.cp2.bla() executing ");
     }
     
}

// package p2; // Not allowed

