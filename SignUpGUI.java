package projet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class SignUpGUI extends JFrame {
    private JTextField usernameField;
    private JTextField nameField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton backButton;
    private JLabel errorLabel;
    private JLabel successLabel;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SignUpGUI() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel genderLabel = new JLabel("Gender:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        errorLabel = new JLabel("");
        successLabel = new JLabel("");

        usernameField = new JTextField();
        nameField = new JTextField();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        signUpButton = new JButton("Next");
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGUI();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String name = nameField.getText();
                String gender = "";
                if (maleRadioButton.isSelected()) {
                    gender = "Male";
                } else if (femaleRadioButton.isSelected()) {
                    gender = "Female";
                }
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if (username.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || gender.isEmpty()) {
                    errorLabel.setText("Please fill in all fields.");
                } else if (!username.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
                    errorLabel.setText("Invalid UserName Format!");
                } else if (!name.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
                    errorLabel.setText("Invalid Name Format!");
                } else if (name.length() < 7) {
                    errorLabel.setText("Name So Short!");
                } else if (username.length() < 7) {
                    errorLabel.setText("Username So Short!");
                } else if (password.length() < 8) {
                    errorLabel.setText("Password must be at least 8 characters long.");
                } else if (!password.equals(confirmPassword)) {
                    errorLabel.setText("Passwords do not match.");
                } else {
                    connectToServer(username, name, password, gender);
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(nameLabel);
        add(nameField);
        add(genderLabel);
        add(maleRadioButton);
        add(new JLabel());
        add(femaleRadioButton);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(backButton);
        add(signUpButton);
        add(errorLabel);
        add(successLabel);
        errorLabel.setForeground(Color.RED);
        successLabel.setForeground(Color.GREEN);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        successLabel.setHorizontalAlignment(JLabel.CENTER);

        setVisible(true);
    }

    private void connectToServer(String username, String name, String password, String gender) {
        try {
            socket = new Socket("192.168.43.76", 12345);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject("signup");
            objectOutputStream.flush();

            boolean signupSuccess = sendSignupRequest(username, name, password, gender);
            if (signupSuccess) {
                successLabel.setText("Signup successful!");
                dispose();
                User user = (User) objectInputStream.readObject();
                new Main2(user); 
            } else {
                errorLabel.setText("Username already exists.");
            }

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (ConnectException e) {
            // Handle connection refused error
            errorLabel.setText("Connection refused: Unable to connect to the server.");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            errorLabel.setText("Error: " + ex.getMessage());
        }
    }

    private boolean sendSignupRequest(String username, String name, String password, String gender) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(new String[]{username, name, password, gender});
        objectOutputStream.flush();
        return objectInputStream.readBoolean();
    }
}
