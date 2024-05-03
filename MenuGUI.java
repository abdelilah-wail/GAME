package projet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends JFrame {
    private JButton loginButton;
    private JButton signUpButton;
    private JButton exitButton;
    public MenuGUI() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(4, 1)); 

        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        exitButton = new JButton("Exit");
        //ImageIcon signUpIcon = new ImageIcon("signup_icon.png"); 
        ImageIcon signUpIcon = new ImageIcon("11.png"); 
        
        //  Image img2 = signUpIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        //signUpIcon = new ImageIcon(img2);
        //loginButton.setIcon(loginIcon);
        //signUpButton.setIcon(signUpIcon);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginGUI(); 
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new SignUpGUI();
            }
        });

       // add(new JLabel());
        add(new JLabel(signUpIcon));
       // add(new JLabel());
       // add(new JLabel());
        add(loginButton);
        //add(new JLabel());
       // add(new JLabel());
        add(signUpButton);
        add(exitButton);
        // add(new JLabel());
        setVisible(true);
    }
}
