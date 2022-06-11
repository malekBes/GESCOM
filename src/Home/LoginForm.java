/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import Config.Commen_Proc;
import Config.ConfigDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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

    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;

    LoginForm() {
        JFrame frame = new JFrame("Authetification");
        // l1 = new JLabel("Login Form");
        //l1.setForeground(Color.blue);
        // l1.setFont(new Font("Serif", Font.BOLD, 20));
        JRadioButton r1 = new JRadioButton("A Distance");
        JRadioButton r2 = new JRadioButton("Sur Site");
        JRadioButton r3 = new JRadioButton("Personalisé");

        l2 = new JLabel("Utilisateur ");
        l3 = new JLabel("Mot de Passe");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("S'authentifier");
        JLabel label_ip = new JLabel("Hote (IP) ");
        JTextField tf_ip = new JTextField();

        //l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 100, 30);
        p1.setBounds(300, 110, 100, 30);
        btn1.setBounds(150, 160, 130, 30);
        r1.setBounds(75, 350, 100, 30);
        r2.setBounds(75, 400, 100, 30);
        r3.setBounds(75, 450, 100, 30);
        label_ip.setBounds(75, 500, 100, 30);
        tf_ip.setBounds(75, 530, 150, 30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);
        bg.add(r3);
        r1.setSelected(true);

        //frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(btn1);
        frame.add(r1);
        frame.add(r2);
        frame.add(r3);
        frame.add(label_ip);
        frame.add(tf_ip);

        frame.setSize(500, 700);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btn1.addActionListener((ae) -> {
            String uname = tf1.getText();
            String pass = p1.getText();
            ConfigDao c = new ConfigDao();
            if (r1.isSelected()) {
                Commen_Proc.Ip_base = "41.228.22.2";
            } else if (r2.isSelected()) {
                Commen_Proc.Ip_base = "192.168.1.121";
            } else {
                if (!tf_ip.getText().isEmpty()) {
                    Commen_Proc.Ip_base = tf_ip.getText();
                } else {
                    JOptionPane.showMessageDialog(this, "Champ Hote est obligatoire",
                            "Message", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            String s = c.login(uname, pass);
            if (!(s.equals("0"))) {
                if (s.equals("error_cnx")) {
                    return;
                }

                frame.dispose();

                App CAL = new App();
                CAL.setVisible(true);
                frame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect login or password",
                        "Message", JOptionPane.INFORMATION_MESSAGE);

            }

        });
    }

    public static void main(String[] args) {
        new LoginForm();
    }

}
