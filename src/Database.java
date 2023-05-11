import javax.swing.*;
import java.sql.*;

public class Database {
    public static void main(String[] args) {
        Connection conn = null;
        String user = "te20";
        JPasswordField pf = new JPasswordField();
        JOptionPane.showConfirmDialog(null, pf, "password?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String password = new String(pf.getPassword());

        // Skapa connection till databasen
        try {
            conn = DriverManager.getConnection("jdbc:mysql://db.umea-ntig.se:3306/te20? "+
                    "allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // MySQL-kommandot för att hämta data från tabellerna
        try {
            Statement stmt = conn.createStatement();
            String SQLQuery =
                    "SELECT lgl23players.*, lgl23inventory.item_name\n" +
                    "FROM lgl23players\n" +
                    "JOIN lgl23inventory\n" +
                    "ON lgl23players.id=lgl23inventory.player_id;";
            ResultSet result = stmt.executeQuery(SQLQuery);

            /* Printar ut metadata, behövs ej just nu
            ResultSetMetaData metadata = result.getMetaData();
            int numCols = metadata.getColumnCount();
            for (int i = 1 ; i <= numCols ; i++) {
                System.out.println(metadata.getColumnClassName(i));
            }
            */

            // Printar ut datan från de angivna kolumnerna för varje rad
            while (result.next()) {
                String output = "";
                output += result.getInt("id") + ", " +
                        result.getString("name") + ", " +
                        result.getString("item_name");
                System.out.println(output);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
