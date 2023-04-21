import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> AttackDmg = Dice.roll(4, 10);
        for (int i = 0; i < AttackDmg.size(); i++) {
            System.out.println(AttackDmg.get(i));
        }

    }
}
