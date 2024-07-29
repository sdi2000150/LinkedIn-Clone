
class App {
    public static void main(String[] args) {
//        Dish d;
       Dish d = new Dish();
       MainDish md = new MainDish(600,"mbrizola", 8);
       LightMainDish lmd = new LightMainDish(300,"Rizogalo", 2);

        System.out.println("////////////////////////////");

        md.eat();
        lmd.eat();
        d = lmd;
        d.eat();
    }
}


// abstract class Dish{
  class Dish{
           int calories = 1000;
           String name = "Junk food";

    Dish() {
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

class LightMainDish extends MainDish{
      LightMainDish(int cal, String nam, int cst) {
         super(cal, nam, cst);
         System.out.println("A LightMainDish has been created");
      }
    void eat() {
              super.eat();
           System.out.println("Eating a Light Main Dish");
           }
           }
