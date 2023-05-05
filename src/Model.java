import java.util.ArrayList;
import java.lang.Math;

public class Model {
    public static ArrayList<Integer> roll(int d, int n) {
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rolls.add((int)Math.ceil(Math.random() * d));
        }
        return rolls;
    }

    public static Integer Attack() {
        int totalDmg = 0;
        ArrayList<Integer> AttackDmg = Model.roll(10, 4);
        for (int i = 0; i < AttackDmg.size(); i++) {
            System.out.println(AttackDmg.get(i));
            totalDmg += AttackDmg.get(i);
        }
        System.out.println("----------- \nTotal damage: " + totalDmg);
        return totalDmg;
    }
}

/*
class Sword {
    int die = 6;
    int numOfDice = 2;
    public Sword(int die, int numOfDice) {
        this.die = die;
        this.numOfDice = numOfDice;
    }
}
*/

class Inventory extends Item {
    static ArrayList<Object> Backpack = new ArrayList<>();
    public static void generateBackpack() {
        Item Longsword = new Item();
        Longsword.die = 6;
        Longsword.numOfDice = 2;
        Backpack.add(Longsword);

        Item Greataxe = new Item();
        Greataxe.die = 12;
        Greataxe.numOfDice = 1;
        Backpack.add(Greataxe);
        System.out.println(Longsword);
    }
    public static ArrayList<Object> getBackpack() {
        return Backpack;
    }
}

class Item {
    int die;
    int numOfDice;
}