package projet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton; 
    private JLabel errorLabel;
    private JLabel successLabel;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private User user;

    public LoginGUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        errorLabel = new JLabel("");
        successLabel = new JLabel("");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Back"); 
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGUI();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if(username.isEmpty() || password.isEmpty()) {
                    errorLabel.setText("Please fill in all fields.");
                }
                else try {
                    connectToServer(username, password);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                errorLabel.setText("");
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                errorLabel.setText("");
            }
        });
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(new JLabel());
        add(backButton); 
        add(loginButton);
        add(errorLabel);
        add(successLabel);
        errorLabel.setForeground(Color.RED);
        successLabel.setForeground(Color.GREEN);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        successLabel.setHorizontalAlignment(JLabel.CENTER);
        setVisible(true);
    }

    private void connectToServer(String username, String password) throws IOException, ClassNotFoundException {
        socket = new Socket("192.168.43.76", 12345);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream.writeObject("login");
        objectOutputStream.flush();
        sendAuthenticationRequest(username, password);
        user = (User) objectInputStream.readObject();
        if (user != null) {
            if (user.getUsername().equals(username)) {
                errorLabel.setText("");
                successLabel.setText("Login successful!");
                dispose();
                new Main2(user);
            } else {
                errorLabel.setText("Invalid username or password. Please try again.");
            }
        } else {
            errorLabel.setText("Invalid username or password. Please try again.");
        }

        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }

    private void sendAuthenticationRequest(String username, String password) throws IOException {
        objectOutputStream.writeObject(new String[]{username, password});
        objectOutputStream.flush();
    }

}
