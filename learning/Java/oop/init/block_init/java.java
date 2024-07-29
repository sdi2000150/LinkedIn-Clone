
class App {
    public static void main(String[] args) {
        Dish d = new Dish(100,"giaourti");
        d.eat();
        System.out.println("Actulally you got: " + d.getCalories() + " calories!");
    }
}


class Dish{

    int calories = 2000 ;
    String name = "What you have to eat";

// Block initialization
        { System.out.println("The new dish named: " + name + " contains " + calories + " calories!"); } // NOT WHAT WE WANTED - it is copied in the beginning

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
