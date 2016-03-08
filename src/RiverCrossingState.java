
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * weirdest implementation ever. All if else
 * will do better oop looking code next time.
 * I hereby solemnly swear that I did code this by myself.
 */
/**
 *
 * @author 2013-50109 Esteban, Arnold Joseph Caesar P.
 * @version 1.0
 * @since 2016-3-8
 */
public class RiverCrossingState {

    private ArrayList<Character> west;
    private ArrayList<Character> east;
    private boolean isBbCSWest; //checks whether bbComsci is in the west or east

    public RiverCrossingState(ArrayList<Character> west, ArrayList<Character> east) {
        this.west = west;
        this.east = east;
        isBbCSWest = true;
    }
  
    /**
     * reset sets the state back to the initial state
    */
    public void reset(){
        isBbCSWest = true;
        this.west.clear();
        this.east.clear();
        west.add('L'); //Lion
        west.add('R'); //Rabbit
        west.add('C'); //Carrot
        isBbCSWest = true;
    }
    
    /**
     * isIllegalState checks whether one of Bb Comsci's pets will eat one of
     * his possessions. This happens when he leaves them alone. Certain 
     * conditions are followed (based on the problem)
     * @return true if illegal; false otherwise
     */
    public boolean isIllegalState() {
        if (west.contains('L') && west.contains('R') && west.contains('C') && isBbCSWest == false) {
            return true;
        } else if (west.contains('L') && west.contains('R') && east.contains('C') && isBbCSWest == false) {
            return true;
        } else if (west.contains('R') && west.contains('C') && east.contains('L') && isBbCSWest == false) {
            return true;
        } else if (west.contains('C') && east.contains('L') && east.contains('R') && isBbCSWest == true) {
            return true;
        } else if (west.contains('L') && east.contains('R') && east.contains('C') && isBbCSWest == true) {
            return true;
        } else if (east.contains('L') && east.contains('R') && east.contains('C') && isBbCSWest == true) {
            return true;
        }
        return false;
    }

    /**
     * isEndingState checks whether Bb Comsci was able to cross all of his
     * possessions to the east bank
     * @return true if he was able to; false otherwise
     */
    public boolean isEndingState() {
        return (east.contains('L') && east.contains('R') && east.contains('C') 
                && isBbCSWest == false);
    }
    
    
    /**
     * toString returns the current state. "_" separates the characters
     * that are in the west bank and east bank.
     * @return state, the current state of Bb Comsci's river cross challenge
     */
    @Override
    public String toString(){
        String state = "";
        for (char c: west)
            state += c;
        if (isBbCSWest == true)
            state += 'N';
        state += "_";
        for (char c: east)
            state += c;
        if (isBbCSWest == false)
            state += 'N';
        
        return state;
    }
    
    /**
     * MOVE: when bbCS travels to the other bank, with or without his possession
     * @param c movement based on NLRC
     * @return true if movement is allowed; false otherwise
     */
    public boolean move(char c) {
        if (c == 'N') {
            isBbCSWest = !isBbCSWest;
            return true;
        } else if (c == 'L') {
            if (west.contains('L') && isBbCSWest == true) {
                isBbCSWest = !isBbCSWest;
                east.add(west.remove(west.indexOf('L')));
                return true;
        
            } else if (east.contains('L') && isBbCSWest == false) {
                isBbCSWest = !isBbCSWest;
                west.add(east.remove(east.indexOf('L')));
                return true;
            
            }
            
            
        } else if (c == 'C') {
            if (west.contains('C') && isBbCSWest == true) {
                isBbCSWest = !isBbCSWest;
                east.add(west.remove(west.indexOf('C')));
                return true;
            } else if (east.contains('C') && isBbCSWest == false) {
                isBbCSWest = !isBbCSWest;
                west.add(east.remove(east.indexOf('C')));
                return true;
            }
            
            
        } else if (c == 'R') {
            if (west.contains('R') && isBbCSWest == true) {
                isBbCSWest = !isBbCSWest;
                east.add(west.remove(west.indexOf('R')));
                return true;
            } else if (east.contains('R') && isBbCSWest == false) {
                isBbCSWest = !isBbCSWest;
                west.add(east.remove(east.indexOf('R')));
                return true;
            }
        }
        
        return false;
    }

    /**
     * solve: check the movements (NLRC) whether it passes the "dfa" or not
     * @param fileName the inputs, delimited by line
     * @throws FileNotFoundException Scanner parameter
     * @throws IOException FileWriter.
     */    
    public void solve(String fileName) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new File(fileName));
        FileWriter f = new FileWriter(fileName.split("[.]")[0]+".out");
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            for (int i = 0; i < s.length(); i++) {
                char move = s.charAt(i);
                if (!this.move(move)) {
                    f.append("NG\n");
                    break;
                }
                if (this.isIllegalState()) {
                    f.append("NG\n");
                    break;
                }
            }
            if (this.isEndingState())
                f.append("OK\n");
            reset();
        }
        f.close();
    }

    /**
     * solve: check the movements (NLRC) whether it passes the "dfa" or not
     * @param args args[0] filename for input
     * @throws FileNotFoundException Scanner parameter (solve)
     * @throws IOException FileWriter (solve)
     */    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<Character> west, east;
        west = new ArrayList<Character>();
        east = new ArrayList<Character>();

        west.add('L');
        west.add('R');
        west.add('C');

        RiverCrossingState rc = new RiverCrossingState(west, east);
        rc.solve(args[0]);
    }
}
