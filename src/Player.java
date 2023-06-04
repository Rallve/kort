import java.sql.ResultSet;
import java.util.ArrayList;

public class Player {
    int maxHP;
    int HP;
    int AC;
    int ATKmod;
    int DMGmod;
    ArrayList<String> backpack;

    public Player(int playerID) {
        Model m = new Model();
        try {
            ResultSet result = m.getData("player", playerID);
            result.next();
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

    public static void main(String[] args) {

    }
}