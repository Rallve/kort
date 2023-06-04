import java.sql.ResultSet;
import java.util.ArrayList;

public class Enemy {
    int maxHP;
    int HP;
    int AC;
    int ATKmod;
    int DMGmod;
    ArrayList<String> backpack;

    public Enemy(int enemyID) {
        Model m = new Model();
        try {
            ResultSet result = m.getData("enemy", enemyID);
            result.next();
            int hpDie = result.getInt("hp_die");
            int hpNumOfDice = result.getInt("hp_numOfDice");
            int hpMod = result.getInt("hp_modifier");
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

    public static void main(String[] args) {

    }
}