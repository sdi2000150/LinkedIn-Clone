// Class Intialization

class App {
    public static void main(String[] args) {
/*
        Date dtdef = new Date();
        Date dt = new Date(10,12,1915);
        Person p1 = new Person(50);
*/

//    C1 rc1 = new C1();  // static initialization of superclasses occurs

//    System.out.println("The A var is " + C1.impd.get_year()); 


//    System.out.println("The A var is " + rc1.impd.get_year()); // static variable method call
//    System.out.println("The B var is " + rc1.impp.age);  // static variable filed access


/*
// Replace the above with the following:

    C1.bla();
*/

///*
// Replace the above with the following to see the difference in initialization:order
    System.out.println("The A var is " + C1.impd.get_year()); 
    System.out.println("The B var is " + C1.impp.age); 
//*/
    }
}

class Date{
   int day;
   int month;
   int year;
       {System.out.println("The initial Date data is " + day + "  " 
                           + month + "   " + year);}
   Date() {}
   Date(int d, int m, int y)
     { day = d; month = m; year = y;
       System.out.println("The Date data is " + day + "  " 
                           + month + "   " + year);}
   int get_year() {return year;}
}

class Person{
   int age;
   Person(int a) { age = a;
         System.out.println("The Person's age is: " + age);}
}




class  A{
//    static {System.out.println(" A block init executing! ");}
    static Date impd = new Date(5,10,1915);
    static {System.out.println(" A block init executing! ");}
}
 

class B extends A{
   static Person impp = new Person(100);   
}




class C1 extends B{
   static void bla() { System.out.println("C1.bla called"); }
}


