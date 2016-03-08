
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * weirdest implementation ever. will do better oop looking code next time.
 */
/**
 *
 * @author rj
 */
public class RiverCrossingState {

    private ArrayList<Character> west;
    private ArrayList<Character> east;
    private boolean isBbCSWest;

    public RiverCrossingState(ArrayList<Character> west, ArrayList<Character> east) {
        this.west = west;
        this.east = east;
        isBbCSWest = true;
    }

    public static void main(String[] args) throws FileNotFoundException{
        ArrayList<Character> west, east;
        west = new ArrayList<Character>();
        east = new ArrayList<Character>();

        west.add('L');
        west.add('R');
        west.add('C');

        RiverCrossingState rc = new RiverCrossingState(west, east);
        rc.solve("mp2.in");
    }
    
    public void reset(){
        isBbCSWest = true;
        this.west.clear();
        this.east.clear();
        west.add('L');
        west.add('R');
        west.add('C');
        isBbCSWest = true;
    }

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

    public boolean isEndingState() {
        return (east.contains('L') && east.contains('R') && east.contains('C') && isBbCSWest == false);
    }
    
    public String toString(){
        String ret = "";
        for (char c: west)
            ret += c;
        if (isBbCSWest == true)
            ret += 'N';
        ret += "_";
        for (char c: east)
            ret += c;
        if (isBbCSWest == false)
            ret += 'N';
        
        return ret;
    }

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

    public void solve(String fileName) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            for (int i = 0; i < s.length(); i++) {
                char move = s.charAt(i);
                if (!this.move(move)) {
                    System.out.println("NG");
                    break;
                }
                if (this.isIllegalState()) {
                    System.out.println("NG");
                    break;
                }
            }
            if (this.isEndingState())
                System.out.println("OK");
            reset();
        }
    }
}
