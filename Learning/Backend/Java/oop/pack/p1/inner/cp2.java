package p1.inner;

//import p1;  //Cannot be written like that
import p1.*;  // import (public) types from p1 to use without name qualification

public class cp2{
//   static void fun(){
  public static void fun(){
        System.out.println("This is p1.inner.cp2.fun() executing ");
        p1.cp1.bla(); 
        cp1.bla(); // ERROR:if bla() is not public in cp1; cannot be accessed from outside package
  }
}


