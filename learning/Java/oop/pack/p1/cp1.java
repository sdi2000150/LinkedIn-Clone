//class cp1 is public, should be declared in a file named cp1.java
package p1;

public class cp1{
    public static void main(String[] args){
        System.out.println("These tricky packages!!!!!!"); 
        p1.inner.cp2.fun(); // ERROR:if fun() is not public in cp2; cannot be accessed from outside package
        cp2.foo();
        p2.cp2.blee();
    }

    public static void bla() {
        System.out.println("This is p1.cp1.bla() executing ");
    }

}