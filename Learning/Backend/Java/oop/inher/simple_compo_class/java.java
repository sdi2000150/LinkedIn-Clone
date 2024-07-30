
class App {
    public static void main(String[] args) {
       Dish d = new Dish();   // Dish by default
       MainDish md = new MainDish(600,"mbrizola", 8);
       AirMeal am = new AirMeal();  // AirMeal by default
    }
}


// abstract class Dish{
class Dish{

           int calories = 1000;
           String name = "Junk food";

    Dish() {
        this(2000,"FatAndSpices");
        System.out.println("The new dish named: " + name + " contains " + calories + " calories!"); 
                } 
    Dish(String nam) {
        name = nam;
        System.out.println("The new dish named: " + name + " contains " + calories + " calories!");
                }
    Dish(int cal, String nam) {
        calories = cal;
        name = nam;
        System.out.println("The new dish named: " + name + " contains " + calories + " calories!"); 
               }
    void eat() {
           System.out.println("Eating " + name );
           }

    int getCalories(){
               return calories;
            }

}

class MainDish extends Dish{
      int cost = 6;
      MainDish(int cal, String nam, int cst) {
         super(cal, nam);
         cost = cst;
         System.out.println("The cost of the main dish is " + cost);
      }
}

class AirMeal {
         MainDish gg1 = new MainDish(0,"JustAGlassofWater",0); 
         Dish gg2 = new Dish(0,"JustAnotherAGlassofWater"); 
}
