// package LinkedInApp.Learning.Java.crashcourse;

public class Cat {
    String name;
    int age;

    //non-static methods can be called only through created onjects of the class
    public void meow() {
        System.out.println("Meow");
    }

    //static methods can be called as they are without having an object class created
    public static void dingDong() {
        System.out.println("Ding Dong");
    }
}
