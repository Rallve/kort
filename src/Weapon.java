import java.sql.ResultSet;

public class Weapon {
    int die;
    int numOfDice;
    int DMGmod;

    public Weapon(String weapon) {
        Model m = new Model();
        try {
            ResultSet result = m.getData("weapon", weapon);
            result.next();
            die = result.getInt("die");
            numOfDice = result.getInt("numOfDice");
            DMGmod = result.getInt("dmg_mod");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
