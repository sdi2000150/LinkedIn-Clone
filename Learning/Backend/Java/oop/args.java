
class args {
    public static void main(String[] args) {

        // System.out.println(args.length); 

        // Should check for proper number and type of args though!
        // for (String s: args) { // use the enhanced for statement as args
        //                        // is array
        //     System.out.println(s);           
        // }

        // for (int i = 0 ; i < args.length ; i++) {
        //     System.out.println(args[i]); 
        // }

        // Parse string args to int
        // parse<NumType>: convert a String representing a number
        int N = args.length; // length is a public final field of array
        int[] iargs = new int[N];
        for (int i=0; i<N; i++) {
            iargs[i] = Integer.parseInt(args[i]); // Integer.parseInt parses the
                                                  // string argument as a signed
                                                  // decimal integer.
            System.out.println(iargs[i]);
        }

        ///////////////////////////////////////////////////
    }
}