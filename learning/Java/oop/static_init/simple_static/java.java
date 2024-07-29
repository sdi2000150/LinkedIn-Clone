// Static method scope

class Bla{
  public static void main(String[] args){
//    Tt t = new Tt();
    Tt t = new Ttm();
//    Ttm t = new Ttm();
    Tt.bla();
    Ttm.bla();
    t.bla();
   }
}

class Tt { static void bla()
                  { System.out.println("Tt.bla()");}
}
class Ttm extends Tt { 
                 static void bla() 
//                  void bla()   // compilation error
                  { System.out.println("Ttm.bla()");}
}
