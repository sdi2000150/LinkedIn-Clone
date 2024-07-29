
class App {
    public static void main(String[] args) {
//*
        Employee.get_population();
        Person.get_population();
//*/

        Person p1 = new Person(2,3,1972);


        p1.print(); 
        p1.get_population();

        Employee.get_population();
///*
        Employee e1 = new Employee(2,3,1972,1,1,1995);


        e1.print();
        e1.get_population();
        e1.date.print();
        System.out.println(e1.population);

//*/
        System.out.println("/////////////////////////////////");
///*
        p1 = e1;
        p1.print();   // instance method call
        p1.get_population();   // class (static) method call
        p1.date.print();   // field access
        System.out.println(p1.population); // static field access
        System.out.println(Person.population); // static field explicit access
        System.out.println(Employee.population); // static field explicit access

//*/
          }
}


class Date {

      int day;
      int month;
      int year;

    Date(int d, int m, int y)
       { day = d;
         month = m;
         year = y;

         System.out.println("A new Date was created " +  day + "   " +
                            month + "   " + year);
       }
   void print()
       { System.out.println(" " + +  day + "   " +
                            month + "   " + year);
       }
}



class Person{
            static int population;
            Date date; 
// Static block initialization
            static { System.out.println("The initial population is: " + population);}
      Person(int d, int m, int y) {
            date = new Date(d,m,y);
            population++ ;
            System.out.println("A new person was born in ");
           date.print();
             }
     static int get_population() { 
             System.out.println("People population is " + population);
             return population;}
     void print() { 
             System.out.println("Printing birth date ");
             date.print();
             System.out.println("Age should be non zero");
             }
}


class Employee extends Person{

            static int population;
            Date date;

            static { System.out.println("The initial Employee population is: " + population);}
      Employee(int d1, int m1, int y1,
               int d2, int m2, int y2) {

              super(d1,m1,y1);
              date = new Date(d2,m2,y2);
              population++ ;
            System.out.println("A new Employee in ");
            date.print();
             }

     static int get_population() { 
             System.out.println("Employee population is " + population);
             return population;}
     void print() {
             System.out.println("Printing employment date ");
             date.print();
             System.out.println("Age should be over 18");
             }

}
