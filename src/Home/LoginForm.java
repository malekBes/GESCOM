/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import javax.swing.JFrame;

/**
 *
 * @author Malek
 */
/*
import Config.ConfigDao;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
*/
public class LoginForm extends JFrame {

    /*JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;

    LoginForm() {
        JFrame frame = new JFrame("Authetification");
        // l1 = new JLabel("Login Form");
        //l1.setForeground(Color.blue);
        // l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Utilisateur ");
        l3 = new JLabel("Mot de Passe");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("S'authentifier");

        //l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 100, 30);
        p1.setBounds(300, 110, 100, 30);
        btn1.setBounds(150, 160, 130, 30);

        //frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(btn1);

        frame.setSize(500, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tf1.setText("1");
        p1.setText("1");
        btn1.addActionListener((ae) -> {
            String uname = tf1.getText();
            String pass = p1.getText();
            ConfigDao c = new ConfigDao();

            if (!c.login(uname, pass).isEmpty()) {
                frame.dispose();
                App CAL = new App();
                CAL.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect login or password",
                        "Error", JOptionPane.ERROR_MESSAGE);

            }

        });
    }

    public static void main(String[] args) {
        new LoginForm();
    }
*/
}
