//References to class pointing to objects of subclass and vice versa
class App {
    public static void main(String[] args) {
       Dish d = new Dish();
       Dish ff = new MainDish(50,"light",0);
//        MainDish ff = new Dish(50,"light");  //Icompatible types
//        MainDish ff = d;    //Incopatible types
       d.eat();
       Dish md = new MainDish(600,"mbrizola", 15);
//        md.onDiet();  ///// Error: md of type Dish => no onDiet() in scope of Dish
       Bla b = new Bla();
//        md = b;   //Incompatible types
       md.eat();
       md = d;   
//        MainDish md1 = md;   //Incompatible types
//        md1 = d;     //Incompatible types
        md.eat();
        md.eatMore(200);
        d = md;
        d.eat();
///// Refs assignment
        MainDish md1 = new MainDish(700,"patates",3);   
        MainDish md2 = new MainDish(400,"fakes",1);   
        md1 = md2;   // refs to same object
//        md2 = md; // error: incompatible types: Dish cannot be converted to MainDish
        md1.eatMore(3000);
        System.out.println(md1.getCalories());
        System.out.println(md2.getCalories());
    }
}

class Bla {int i;}

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
    void eatMore(int cal) {
           calories += cal;
           System.out.println("Eating more calories " + calories );
           }
    int getCalories(){
               return calories;
            }

}

class MainDish extends Dish{
      int cost = 10;
      MainDish(int cal, String nam, int cst) {
         super(cal, nam);
         cost = cst;
         System.out.println("The cost of the main dish is " + cost);
      }
    void eat() {
           System.out.println("Eating Main Dish " + name );
           }
    void onDiet() { System.out.println( "Sorry, I have to refuse it! ");}
}
