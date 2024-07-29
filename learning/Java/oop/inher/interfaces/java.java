
class App {
    public static void main(String[] args) {
        Meat mt = new Meat(600,"mbrizola", 15);
        mt.eat();
        System.out.println(mt.getCalories());
        System.out.println(mt.isLate());
        System.out.println(mt.givesStrength());
    }
}


// abstract class Dish{
interface Dish{
    void eat();    // public
    int getCalories();   // public
}

interface MainDish extends Dish{
    int MEALTIME = 12;   // public, static, final
    boolean isLate();
}


interface HealthyDish{
    int LOWCALORIES = 300;
    int givesStrength();
}


class Meat implements MainDish, HealthyDish{
      int calories;
      String name;
      int time;
      Meat(int cal, String nam, int t) {
           calories = cal;
           name = nam;
           time = t;
           System.out.println("You are eating :" + name + " at " + time +" having " + calories + " calories");
      }

      public void eat() { System.out.println( "Eating " + name); }    // public
      public int getCalories() { return calories; }   // public
      public boolean isLate() { return ((time > MEALTIME )? true : false); }
      public int givesStrength() {return (calories - LOWCALORIES);}
}

