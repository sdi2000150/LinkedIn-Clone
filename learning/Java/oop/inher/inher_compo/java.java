// Inheritance and Composition
class App {
    public static void main(String[] args) {
//        Dish d = new Dish();
//        Meal ml = new Meal();

// /*
        Meal ml = new Meal(10, "salata", 7,
                           600,"mbrizola", 15,
                           200, "crema karamele", 4);

// */
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

class Starter extends Dish{
      int cost = 5;
      Starter(int cal, String nam, int cst) {
         super(cal, nam);
         cost = cst;
         System.out.println("The cost of the starter is " + cost);
      }
     int getCost() {return cost;}
}

class MainDish extends Dish{
      int cost = 10;
      MainDish(int cal, String nam, int cst) {
         super(cal, nam);
         cost = cst;
         System.out.println("The cost of the main dish is " + cost);
      }
     int getCost() {return cost;}
}

class Dessert extends Dish{
      int cost = 3;
      Dessert(int cal, String nam, int cst) {
         super(cal, nam);
         cost = cst;
         System.out.println("The cost of the dessert is " + cost);
      }
     int getCost() {return cost;}
}

class Meal {

     Starter starter;
     MainDish maindish ;
//     MainDish maindish = new MainDish(500, "Omelette", 6); // Not a good idea!
     Dessert dessert;
//     Meal() {}
     Meal() {  maindish = new MainDish(500, "Omelette", 6);} // Better like this if omelette is the default
     Meal(int cal1, String nam1, int cst1,
          int cal2, String nam2, int cst2,
          int cal3, String nam3, int cst3) {

          starter = new Starter(cal1,nam1,cst1);
          maindish = new MainDish(cal2,nam2,cst2);
          dessert = new Dessert(cal3,nam3,cst3);

          int energy = starter.getCalories() +
                       maindish.getCalories() + dessert.getCalories();
          int bill = starter.getCost() + maindish.getCost() + 
                     dessert.getCost();
          System.out.println("You are about to eat a meal of " + energy + " calories and pay : " +
                             bill + " Euros");
      }
    
}
