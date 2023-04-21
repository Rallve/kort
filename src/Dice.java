import java.lang.Math;
import java.util.ArrayList;

public class Dice {
    public static ArrayList<Integer> roll(int d, int n) {
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rolls.add((int)Math.ceil(Math.random() * d));
        }
        return rolls;
    }
}
