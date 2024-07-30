// Interface initialization. Experiment by taking comments out
// No initialization sequence is triggered

class App {
    public static void main(String[] args) {
/*
        Date dtdef = new Date();
        Date dt = new Date(10,12,1915);
        Person p1 = new Person(50);
*/
    C1 rc1 = new C1();  // if just instance creation no interface initalization occurs
   System.out.println("The Int2 var is " + rc1.IMPD2.get_year());
   System.out.println("The CInt1 var is " + rc1.IMPP.age); // just CInt1 gets initallized
//    System.out.println("The Int1 var is " + rc1.IMPD.get_year()); // Error: ambiguity
   System.out.println("The Ambig var is " + Int1.IMPD.get_year());
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




interface Int1{
    Date IMPD = new Date(5,10,1915);
}
 

interface CInt1 extends Int1{
   Person IMPP = new Person(100);   // public, static, final
}


interface Int2{
    Date IMPD = new Date();
    Date IMPD2 = new Date(1,1,1111);
}


class C1 implements Int2, CInt1{
}

