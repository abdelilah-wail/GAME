package projet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main3 extends JFrame {
    private JButton twoPlayersButton;
    private JButton fourPlayersButton;
    private JButton backButton;

    public Main3(User user) {
        setTitle("Player Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));
        setResizable(false);
        twoPlayersButton = new JButton("2 Players");
        fourPlayersButton = new JButton("4 Players");
        backButton = new JButton("Back");

        twoPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            new GameTable(user); 
           
            }
        });

        fourPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Work On It");   
                
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new Main2(user);             }
        });

        add(new JLabel());
        add(twoPlayersButton);
        add(fourPlayersButton);
        add(backButton);

        setVisible(true);
    }
}
