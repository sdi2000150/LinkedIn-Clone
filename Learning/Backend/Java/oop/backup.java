// import java.util.ArrayList;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]), reuma = Integer.parseInt(args[1]), katastasi = Integer.parseInt(args[2]);
        movement r = movement.values()[reuma];
        condition k = condition.values()[katastasi];

        artifact[] artifactArray = new artifact[N];
        Random rand = new Random();
        int eidos;
        for (int i = 0 ; i < N ; i++) {
            eidos = rand.nextInt(2);
            if (eidos == 0) {
                artifactArray[i] = new painting(rand.nextInt(90)+10,rand.nextInt(90)+10,technique.values()[rand.nextInt(3)],movement.values()[rand.nextInt(3)],condition.values()[rand.nextInt(3)],i,"Creator"+i,rand.nextInt(800)+1200); //lenght,width are between 10.0 and 100.0 .year is between 1200 and 2000
            } else {
                artifactArray[i] = new sculpture(rand.nextInt(10)+1,material.values()[rand.nextInt(3)],movement.values()[rand.nextInt(3)],condition.values()[rand.nextInt(3)],i,"Creator"+i,rand.nextInt(800)+1200); //volume is between 1.0 and 10.0 .year is between 1200 and 2000
            }
        }
        auction(N,artifactArray,r,k);
    }

    static void auction(int N, artifact[] artifactArray,movement r,condition k) {
        System.out.println("\nArtifacts need to be from "+r+" movement and in "+k+" condition, in order to be accepted");
        for (int i = 0 ; i < N ; i++) {
            System.out.println();
            artifactArray[i].getIndex();
            artifactArray[i].getInfo();
            boolean apodekto = artifactArray[i].evaluate(r,k);
            if (apodekto == true) {
                System.out.println("Artifact is acceptable");
            } else {
                System.out.println("Artifact is not acceptable");
            }
        }
    }
}

//abstract
abstract class artifact {
    private final int index; //private bacause no use in subclasses
    private final String creator; //private bacause no use in subclasses
    private final int year; //private bacause no use in subclasses

    // artifact() {}
    artifact(int i, String c, int y) {
        index = i;
        creator = c;
        year = y;
        System.out.println("Creating an instance of artifact");
    }

    void getInfo() {
        System.out.println("Artifact's creator is: "+creator+"\nArtifact's creation year is: "+year+" AD");
    }
    void getIndex() {
        System.out.println("Artifact's index is: "+index);
    }
    abstract boolean evaluate(movement r,condition k); 
    abstract boolean evaluate(movement r);
}

enum movement {
    impressionism,
    expressionism,
    naturalism
}
enum condition {
    bad,
    good,
    excellent
}
//abstract(?)
abstract class masterpiece extends artifact {
    protected final movement reuma; //final(?) //protected for use in subclasses
    protected condition katastasi; //protected for use in subclasses

    // masterpiece() {}
    masterpiece(movement r, condition k, int i, String c, int y) {
        super(i,c,y);
        reuma = r;
        katastasi = k;
        System.out.println(" Creating an instance of masterpiece");
    }

    void getInfo() {
        super.getInfo();
        System.out.println(" Masterpiece's movement is: "+reuma+"\n Masterpiece's condition is: "+katastasi);
    }
}

enum technique {
    oil,
    aquarelle,
    tempera
}
class painting extends masterpiece {
    private final double lenght; //private bacause no use in subclasses(none other)
    private final double width; //private bacause no use in subclasses(none other)
    private final technique texnikh; //private bacause no use in subclasses(none other)

    // painting() {}
    painting(double l, double w, technique t, movement r, condition k, int i, String c, int y) {
        super(r,k,i,c,y);
        lenght = l;
        width = w;
        texnikh = t;
        System.out.println("  Creating an instance of painting");
    }

    void getInfo() {
        super.getInfo();
        System.out.println("  Painting's length is: "+lenght+" cm\n  Painting's width is: "+width+" cm\n  Painting's technique is: "+texnikh);

        double surface = lenght*width;
        System.out.println("  Painting's surface is: "+surface+" cm^2");
    }

    boolean evaluate(movement r,condition k) {
        if (reuma == r && (katastasi == k || (katastasi == condition.excellent && k == condition.good))) {
            return true;
        } else {
            return false;
        }
    }
    boolean evaluate(movement r) {  //condition = good
        return evaluate(r,condition.good);
        // if (reuma == r && (katastasi == condition.good || katastasi == condition.excellent)) {
        //     return true;
        // } else {
        //     return false;
        // }
    }
}

enum material {
    iron,
    stone,
    wood
}
class sculpture extends masterpiece {
    private final double volume; //final(?) //private bacause no use in subclasses(none other)
    private final material uliko; //final(?) //private bacause no use in subclasses(none other)

    // sculpture() {}
    sculpture(double v, material u, movement r, condition k, int i, String c, int y) {
        super(r,k,i,c,y);
        volume = v;
        uliko = u;
        System.out.println("  Creating an instance of sculpture");
    }

    void getInfo() {
        super.getInfo();
        System.out.println("  Sculpture's volume is: "+volume+" m^3\n  Sculpture's material is: "+uliko);
    }

    boolean evaluate(movement r,condition k) {
        if (reuma == r && katastasi == k && !(katastasi == condition.excellent && k == condition.good)) {
            return true;
        } else {
            return false;
        }
    }
    boolean evaluate(movement r) {  //condition = excellent
        return evaluate(r,condition.excellent);
        // if (reuma == r && katastasi == condition.excellent) {
        //     return true;
        // } else {
        //     return false;
        // }
    }
}