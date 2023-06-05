import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.util.ArrayList;


public class Main extends JFrame {
    Model model;
    View view;
    public Main(View v, Model m) {
        this.model = m;
        this.view = v;
        //this.setContentPane(view.getPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        View view = new View();
        Model model = new Model();

        int playerID = 0;
        while (playerID == 0) {
            String username = JOptionPane.showInputDialog(null, "Username?\n" +
                    "Type the username of your account to log in.\n" +
                    "Typing a username that doesn't exist will create a new account.\n" +
                    "(case sensitive)");
            if (username.isEmpty()) { // Creates NullPointerException rather than exiting properly. Good enough.
                System.out.println("Exiting program...");
                System.exit(0);
            }
            playerID = model.login(username);
        }

        Main controller = new Main(view, model);
        controller.setVisible(true);

        Player player = new Player(playerID);
        System.out.println("Player HP: " + player.HP);
        ArrayList<Enemy> enemies = model.createEnemyList(3);
        Enemy[] order = model.orderByInitiative(enemies, player);

        while (player.HP > 0 && !enemies.isEmpty()) {
            model.battleEncounter(enemies, order, player);
        }
        if (player.HP <= 0) {
            JOptionPane.showMessageDialog(null, "You lost.");
        } else {
            JOptionPane.showMessageDialog(null, "You won!");
        }
        System.exit(0);
    }
}
