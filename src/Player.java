import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Player {
    String name;
    int maxHP;
    int HP;
    int AC;
    int ATKmod;
    int DMGmod;
    ArrayList<String> backpack;

    int initiative;

    public Player(int playerID) {
        Model m = new Model();
        try {
            ResultSet result = m.getData("player", String.valueOf(playerID));
            result.next();
            name = result.getString("name");
            maxHP = result.getInt("hp_max");
            HP = result.getInt("hp");
            AC = result.getInt("ac");
            ATKmod = result.getInt("atk_mod");
            DMGmod = result.getInt("dmg_mod");
            backpack = m.getBackpack("player", playerID);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void rollInitiative() {
        JOptionPane.showMessageDialog(null, "Roll for initiative!", "Battle encounter", 1, null);
        System.out.println("Player rolls...");
        int roll = Model.roll(20, 1);
        JOptionPane.showMessageDialog(null, "You rolled " + roll + "!");
        this.initiative = roll;
    }

    public int attack(Enemy enemy) {
        Object[] options = backpack.toArray();
        String choice = (String) JOptionPane.showInputDialog(null, "What do you want to attack with?",
                "Your turn", 1, null, // Use default icon
                options, // Array of choices
                options[0]); // Initial choice

        int atk = Model.roll(20, 1) + this.ATKmod;
        if (atk - this.ATKmod == 20) {
            Weapon w = new Weapon(choice);
            int dmg = (Model.roll(w.die, w.numOfDice) + w.DMGmod + this.DMGmod) * 2;
            JOptionPane.showMessageDialog(null, this.name + " attacks " + enemy.name + " with " + choice + ".\n" + this.name + " rolls a NAT20! It's a crit!!\n" + enemy.name + " takes " + dmg + " damage!");
            return dmg;
        }
        if (atk >= enemy.AC) {
            Weapon w = new Weapon(choice);
            int dmg = Model.roll(w.die, w.numOfDice) + w.DMGmod + this.DMGmod;
            JOptionPane.showMessageDialog(null, this.name + " attacks " + enemy.name + " with " + choice + ".\n" + this.name + " rolls " + atk + "!\n" + enemy.name + " takes " + dmg + " damage.");
            return dmg;
        } else {
            JOptionPane.showMessageDialog(null, this.name + " attacks " + enemy.name + " with " + choice + ".\n" + this.name + " rolls " + atk + "!\nAttack missed!");
            return 0;
        }
    }
}