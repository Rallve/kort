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

        String username = JOptionPane.showInputDialog("Username?\n" +
                "Type the username of your account to log in.\n" +
                "Typing a username that doesn't exist will create a new account.\n" +
                "(case sensitive)");

        model.login(username);


        Main controller = new Main(view, model);
        controller.setVisible(true);

        //Model.Attack();
        //Inventory.generateBackpack();
        //System.out.println(Inventory.getBackpack().get(0));


    }
}
