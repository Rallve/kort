import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

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

    public ResultSet getData(String type, String ID) {
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
            if (type.equals("weapon")) {
                SQLQuery =
                        "SELECT * FROM lgl23items WHERE item_name='" + ID + "';";
            }
            ResultSet result = stmt.executeQuery(SQLQuery);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public ResultSet getWeaponData(String weapon) {
        try {
            String SQLQuery = "";
            Statement stmt = conn.createStatement();
            SQLQuery =
                    "SELECT * FROM lgl23items WHERE item_name='" + weapon + "';";
            ResultSet result = stmt.executeQuery(SQLQuery);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public int login(String username) {
        try {
            int playerID = 0;
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
                    return 0;
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
                playerID = idResult.getInt("id");
                backpackInsert(playerID, "Longsword");
                backpackInsert(playerID, "Greataxe");
            } else {
                while (result.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = result.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }
                String createAccQuery =
                        "SELECT id FROM lgl23players WHERE name='" + username + "'";
                ResultSet idResult = stmt.executeQuery(createAccQuery);
                idResult.next();
                playerID = idResult.getInt("id");
            }
            result.close();
            stmt.close();
            return playerID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
            Enemy enemy = new Enemy((int)Math.ceil(Math.random() * 2));
            enemies.add(enemy);
        }
        for (int i = 0; i < n; i++) {
            System.out.println(enemies.get(i).maxHP);
        }
        return enemies;
    }

    public Enemy[] orderByInitiative(ArrayList<Enemy> enemies, Player player) {
        Enemy order[] = new Enemy[enemies.size() + 1];
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).rollInitiative();
        }
        player.rollInitiative();
        int highest = 0;
        int pos = 0;
        boolean playerCheck = false;
        for (int i = 0; i < enemies.size(); i++) { // For every enemy 'i'...
            for (int n = 0; n < enemies.size(); n++) { // ...for every enemy 'n'...
                if (enemies.get(n).initiative >= highest && !Arrays.asList(order).contains(enemies.get(n))) { // ...which position has the highest initiative?
                    highest = enemies.get(n).initiative;
                    pos = n;
                }
                if (player.initiative > highest && !playerCheck) {
                    highest = player.initiative;
                    pos = -1;
                }
            }
            if (pos == -1) {
                playerCheck = true;
                i -= 1;
            } else {
                if (playerCheck) {
                    order[i + 1] = enemies.get(pos);
                } else {
                    order[i] = enemies.get(pos);
                }
            }
            highest = 0;
        }
        for (int i = 0; i < order.length; i++) {
            if (order[i] == null) {
                System.out.println("Player initiative: " + player.initiative);
            } else {
                System.out.println("Initiative: " + order[i].initiative);
            }
        }
        return order;
    }

    public void battleEncounter(ArrayList<Enemy> enemies, Enemy[] order, Player player) {
        System.out.println(player.HP > 0 && enemies != null);
        for (int i = 0; i < order.length; i++) {
            if (order[i] != null && order[i].HP <= 0) {
                continue;
            }
            if (order[i] == null) {
                Object[] options = enemies.toArray();
                Enemy choice = (Enemy) JOptionPane.showInputDialog(null, "Who do you want to attack?\nYour HP: " + player.HP,
                    "Your turn", 1, null, // Use default icon
                    options, // Array of choices
                    options[0]); // Initial choice

                int dmg = player.attack(choice);
                System.out.println("Enemy takes " + dmg + " damage.");
                choice.HP -= dmg;
                if (choice.HP <= 0) {
                    enemies.remove(choice);
                    if (enemies.isEmpty()) {
                        System.out.println();
                        break;
                    }
                }
            } else {
                int dmg = order[i].attack(player);
                System.out.println("Player takes " + dmg + " damage.");
                player.HP -= dmg;
                System.out.println("Player HP: " + player.HP);
            }
        }
    }
}
