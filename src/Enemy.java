import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Enemy {
    String name;
    int maxHP;
    int HP;
    int AC;
    int ATKmod;
    int DMGmod;
    int initiative;
    ArrayList<String> backpack;

    public Enemy(int enemyID) {
        Model m = new Model();
        try {
            ResultSet result = m.getData("enemy", String.valueOf(enemyID));
            result.next();
            int hpDie = result.getInt("hp_die");
            int hpNumOfDice = result.getInt("hp_numOfDice");
            int hpMod = result.getInt("hp_modifier");
            name = result.getString("name");
            maxHP = Model.roll(hpDie, hpNumOfDice) + hpMod;
            HP = maxHP;
            AC = result.getInt("ac");
            ATKmod = result.getInt("atk_mod");
            DMGmod = result.getInt("dmg_mod");
            backpack = m.getBackpack("enemy", enemyID);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void rollInitiative() {
        System.out.println("Enemy rolls...");
        this.initiative = Model.roll(20, 1);
    }

    public int attack(Player player) {
        int atk = Model.roll(20, 1) + this.ATKmod;
        String weapon = backpack.get((int)Math.round(Math.random()));
        if (atk - this.ATKmod == 20) {
            Weapon w = new Weapon(weapon);
            int dmg = (Model.roll(w.die, w.numOfDice) + w.DMGmod + this.DMGmod) * 2;
            JOptionPane.showMessageDialog(null, this.name + " attacks " + player.name + " with " + weapon + ".\n" + this.name + " rolls a NAT20! It's a crit!!\n" + player.name + " takes " + dmg + " damage!");
            return dmg;
        }
        if (atk >= player.AC) {
            Weapon w = new Weapon(weapon);
            int dmg = Model.roll(w.die, w.numOfDice) + w.DMGmod + this.DMGmod;
            JOptionPane.showMessageDialog(null, this.name + " attacks " + player.name + " with " + weapon + ".\n" + this.name + " rolls " + atk + "!\n" + player.name + " takes " + dmg + " damage.");
            return dmg;
        } else {
            JOptionPane.showMessageDialog(null, this.name + " attacks " + player.name + " with " + weapon + ".\n" + this.name + " rolls " + atk + "!\nAttack missed!");
            return 0;
        }
    }
}