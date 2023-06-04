import javax.swing.*;
import java.util.ArrayList;


public class Main extends JFrame {
    Model model;
    View view;
    public Main(View v, Model m) {
        this.model = m;
        this.view = v;
        this.setContentPane(view.getPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        View view = new View();
        Model model = new Model();

        boolean loggedIn = false;
        while (!loggedIn) {
            String username = JOptionPane.showInputDialog(null, "Username?\n" +
                    "Type the username of your account to log in.\n" +
                    "Typing a username that doesn't exist will create a new account.\n" +
                    "(case sensitive)");
            if (username.isEmpty()) { // Creates NullPointerException rather than exiting properly. Good enough.
                System.out.println("Exiting program...");
                System.exit(0);
            }
            loggedIn = model.login(username);
        }

        Main controller = new Main(view, model);
        controller.setVisible(true);

        ArrayList<Enemy> enemies = model.createEnemyList(3);
        model.rollInitiative();
    }
}
