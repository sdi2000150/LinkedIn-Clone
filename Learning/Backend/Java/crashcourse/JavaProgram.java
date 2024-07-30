// package LinkedInApp.Learning.Java.crashcourse;

public class JavaProgram {
    public static void main(String[] args) {

        // Primitive var types (they start with lowercase):
        int myInt = 7;
        double shoeSize = 9.5;
        char myInitial = 'J';

        double result = myInt * shoeSize;

        System.out.println(myInt); 
        System.out.println(myInt * shoeSize); 

        // Non-primitive types (they start with uppercase):
        String myName = "John";
        //and their many available methods with . (for example .length):
        System.out.println(myName.length());

        // Call manual methods:
        // burp("John", 3);   //with parameters
        String name =  burp("John", 3);   //with parameters
        System.out.println(name);

        // Conditionals:
        if (name.equals("John")) {
            System.out.println("This guy is awesome");
        } else if (myInt == 5) {
            System.out.println("Number is 5");
        } else {
            System.out.println("bla");
        }

        // Loops:
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello World - looping");
        }

        // Call other classes (including methods that are static)
        Cat.dingDong();

        // Create new objects from class
        Cat myCat = new Cat();
        myCat.name = "Fred";
        myCat.age = 6;
        System.out.println(myCat.age);

        Cat anotherCat = new Cat();
        anotherCat.name = "Max";
        anotherCat.age = 4;
    }

    // Manual method creation:
    // with return value
    private static String burp(String name, int number) {
        System.out.println("Buurrrp");
        return "My name is " + name; //concatanating strings with +
    }
    // without return value (void)
    // private static void burp(String name, int number) {
    //     System.out.println("Buurrrp");
    //     System.out.println("My name is " + name); //concatanating strings with +
    // }
}
