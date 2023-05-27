import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.lang.Math;

public class Model {
    Connection conn = null;

    public Model() {
        String user = "te20";
        JPasswordField pf = new JPasswordField();
        JOptionPane.showConfirmDialog(null, pf, "database password?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String password = new String(pf.getPassword());

        // Skapa connection till databasen
        try {
            conn = DriverManager.getConnection("jdbc:mysql://db.umea-ntig.se:3306/te20? "+
                    "allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getData() {
        String output = "";

        // MySQL-kommandot för att hämta data från tabellerna
        try {
            Statement stmt = conn.createStatement();
            String SQLQuery =
                    "SELECT lgl23players.*, lgl23inventory.item_name\n" +
                            "FROM lgl23players\n" +
                            "JOIN lgl23inventory\n" +
                            "ON lgl23players.id=lgl23inventory.player_id;";
            ResultSet result = stmt.executeQuery(SQLQuery);

            while (result.next()) {
                output += result.getInt("id") + ", " +
                        result.getString("name") + ", " +
                        result.getString("item_name");
                System.out.println(output);
                output = "";
            }

            stmt.close();
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static void main(String[] args) {
        Model m = new Model();
        System.out.println(m.getData());
    }

    public void login(String username) {
        try {
            Statement stmt = conn.createStatement();
            String SQLQuery =
                    "SELECT * FROM lgl23players WHERE name=" + '\u0022' + username + '\u0022';
            ResultSet result = stmt.executeQuery(SQLQuery);

            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            if (!result.isBeforeFirst()) {
                System.out.println("No data.");
                String createAccQuery =
                        "INSERT INTO lgl23players (name) VALUES (" + '\u0022' + username + '\u0022' + ")";
                stmt.executeUpdate(createAccQuery);
            } else {
                while (result.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = result.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }
            }


            stmt.close();
            conn.close(); // Might wanna remove this.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

/*
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
*/