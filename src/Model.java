import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.lang.Math;

public class Model {
    Connection conn = null;

    public Model() {
        String user = "te20";
        String password = "";
        try {
            BufferedReader brTest = new BufferedReader(new FileReader("src/databaselogin.txt"));
            password = brTest.readLine();
        } catch (Exception e) {
            System.out.println("You need the database password inside a file titled 'databaselogin.txt' within the 'src' folder.");
            e.printStackTrace();
        }

        // Skapa connection till databasen
        try {
            conn = DriverManager.getConnection("jdbc:mysql://db.umea-ntig.se:3306/te20? "+
                    "allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    public ResultSet getData(String type, int ID) {
        try {
            String SQLQuery = "";
            Statement stmt = conn.createStatement();
            if (type.equals("player")) {
                SQLQuery =
                        "SELECT * FROM lgl23players WHERE id='" + ID + "';";
            }
            if (type.equals("enemy")) {
                SQLQuery =
                        "SELECT * FROM lgl23enemies WHERE id='" + ID + "';";
            }
            ResultSet result = stmt.executeQuery(SQLQuery);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    /*
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
     */

    public static void main(String[] args) {
        Model m = new Model();
    }

    public boolean login(String username) {
        try {
            Statement stmt = conn.createStatement();
            String SQLQuery =
                    "SELECT * FROM lgl23players WHERE name=" + '\u0022' + username + '\u0022';
            ResultSet result = stmt.executeQuery(SQLQuery);

            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            if (!result.isBeforeFirst()) {
                System.out.println("No data.");
                int reply = JOptionPane.showConfirmDialog(null, "Username '" + username + "' does not exist.\nDo you want to create a new account?");
                if (reply == 1) {
                    return false;
                }
                if (reply == 2) {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                String createAccQuery =
                        "INSERT INTO lgl23players VALUES (NULL, '" + username + "', 20, 20, 14, 4, 2)";
                stmt.executeUpdate(createAccQuery);
                createAccQuery =
                        "SELECT id FROM lgl23players WHERE name='" + username + "'";
                ResultSet idResult = stmt.executeQuery(createAccQuery);
                idResult.next();
                int playerID = idResult.getInt("id");
                backpackInsert(playerID, "Shortsword");
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
            result.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList rollList(int d, int n) {
        ArrayList<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rolls.add((int)Math.ceil(Math.random() * d));
        }
        return rolls;
    }

    public static Integer roll(int d, int n) {
        int total = 0;
        int roll = 0;
        for (int i = 0; i < n; i++) {
            roll = (int)Math.ceil(Math.random() * d);
            System.out.println(roll);
            total += roll;
        }
        return total;
    }

    public static Integer Attack() {
        int totalDmg = 0;
        ArrayList<Integer> AttackDmg = rollList(10, 4);
        for (int i = 0; i < AttackDmg.size(); i++) {
            System.out.println(AttackDmg.get(i));
            totalDmg += AttackDmg.get(i);
        }
        System.out.println("----------- \nTotal damage: " + totalDmg);
        return totalDmg;
    }

    public void backpackInsert(int playerID, String item) {
        try {
            Statement stmt = conn.createStatement();
            String SQLQuery =
                    "INSERT INTO lgl23inventory VALUES ('" + playerID + "', '" + item + "')";
            stmt.executeUpdate(SQLQuery);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList getBackpack(String inv, int ID) {
        ArrayList<String> backpack = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String SQLQuery = "";
            if (inv.equals("player")) {
                SQLQuery =
                        "SELECT * FROM lgl23inventory WHERE player_id='" + ID + "';";
            }
            if (inv.equals("enemy")) {
                SQLQuery =
                        "SELECT item_name FROM lgl23enemInventory WHERE enemy_id='" + ID + "';";
            }
            ResultSet backpackResult = stmt.executeQuery(SQLQuery);

            while (backpackResult.next()) {
                String item = backpackResult.getString("item_name");
                System.out.println(item);
                backpack.add(item);
            }
            return backpack;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList createEnemyList(int n) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Enemy slime = new Enemy((int)Math.ceil(Math.random() * 2));
            enemies.add(slime);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(enemies.get(i).maxHP);
        }
        return enemies;
    }

    public void rollInitiative() {


    }
}
