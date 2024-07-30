
public class App {

    public static void main(String[] args) {
//        Dish d = new Dish;     // error
//        Dish d = new Dish();   // error, unless:
                                 // - no constructor existed or 
                                 // - default constructor exists as well
        Dish d = new Dish(100,"giaourti");
        System.out.println(d.getCalories());
    }

}


class Dish{

// fields: class visibility 

private    int calories;
private    String name;

// constructor: package visibility

    Dish(int cal, String nam) { // CAREFULL: no use of declaring public
                                // as class Dish has package level visibility!
                calories = cal;
                name = nam;
                System.out.println("The new dish named: " + name + 
                           " contains " + calories + 
                           " calories!"); 
                } 


// methods:
    public void eat() {    // CAREFULL: no use of declaring public 
                           // as class Dish has package level visibility!
           System.out.println("Eating " + name );
           }

    public int getCalories(){  // CAREFULL: no use of declaring public
                                // as class Dish has package level visibility!
               return calories;
            }

}
