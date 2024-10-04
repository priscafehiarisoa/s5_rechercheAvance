package rechercheAvance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Pair {
    private int intValue;
    private String stringValue;

    public Pair(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
    public List<Pair> sortPair(List<Pair> paire){
        Collections.sort(paire, Comparator.comparingInt(Pair::getIntValue).reversed());
        return paire;
    }

    @Override
    public String toString() {
        return "(" + intValue + ", " + stringValue + ")";
    }

    public Pair() {
    }

    public static void main(String[] args) {
        List<Pair> pairs = new ArrayList<>();

        // Add some sample data
        pairs.add(new Pair(10, "A"));
        pairs.add(new Pair(5, "B"));
        pairs.add(new Pair(15, "C"));

        // Sort the list based on the int values

        // Print the sorted list
        new Pair().sortPair(pairs).forEach(System.out::println);
    }
}
