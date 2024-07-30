// Multiple constructors
// Visibility restrictions on Members

class App {
    public static void main(String[] args) {
        Dish d = new Dish();
//        System.out.println(d.name);  // if field is "private" this cases error
//        Dish d1 = new Dish("hot dog");
//        Dish d2 = new Dish(100, "giaourti");
    }
}


class Dish{
/*
    int calories;
    String name;
*/
/*
 private    int calories = 1000;
 private    String name = "Junk food";
*/

// Intialization with no default values:
           int calories = 1000;
           String name = "Junk food";


/*
    Dish() {
        System.out.println("The new dish named: " + name + 
                           " contains " + calories + " calories!"); 
                } 

*/
///*
// If default construction with default values needed
   Dish() {
           this(400,"LightFood");
           System.out.println("Again: The new dish named: " + name +
                           " contains " + calories + " calories!");
               }
//*/
    Dish(String nam) {
        name = nam;
        System.out.println("The new dish named: " + name + 
                           " contains " + calories + " calories!"); 
                }
    Dish(int cal, String nam) {
        calories = cal;
        name = nam;
        System.out.println("The new dish named: " + name + 
                           " contains " + calories + " calories!"); 
               }

    void eat() {
           System.out.println("Eating " + name );
           }

    int getCalories(){
               return calories;
            }

}
