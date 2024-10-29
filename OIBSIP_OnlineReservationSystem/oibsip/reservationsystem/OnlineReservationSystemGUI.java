package oibsip.reservationsystem; // Add package declaration

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineReservationSystemGUI extends JFrame {
    private static Map<String, String> users = new HashMap<>(); // Stores user credentials
    private static List<Reservation> reservations = new ArrayList<>(); // Stores reservations
    private static int pkrCounter = 1000; // PKR Counter for unique reservations

    public OnlineReservationSystemGUI() {
        // Add a sample user for testing login
        users.put("user1", "password1");

        // Initialize the Login Screen
        initLoginScreen();
    }

    private void initLoginScreen() {
        setTitle("Online Reservation System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel userLabel = new JLabel("User ID:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(new JLabel()); // Empty cell for layout
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String user = userText.getText();
            String password = new String(passwordText.getPassword());

            if (users.containsKey(user) && users.get(user).equals(password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                initMainScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }
        });

        setVisible(true);
    }

    private void initMainScreen() {
        getContentPane().removeAll();
        setTitle("Online Reservation System - Main Menu");
        setSize(400, 200);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

        JButton reserveButton = new JButton("Make a Reservation");
        JButton cancelButton = new JButton("Cancel Reservation");

        add(reserveButton);
        add(cancelButton);

        reserveButton.addActionListener(e -> initReservationForm());
        cancelButton.addActionListener(e -> initCancellationForm());

        revalidate();
        repaint();
    }

    private void initReservationForm() {
        getContentPane().removeAll();
        setTitle("Reservation Form");
        setSize(500, 400);
        setLayout(new GridLayout(8, 2, 10, 10));
        setLocationRelativeTo(null);

        JTextField nameField = new JTextField();
        JTextField trainNumberField = new JTextField();
        JTextField classTypeField = new JTextField();
        JTextField journeyDateField = new JTextField();
        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();

        JButton reserveButton = new JButton("Reserve");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Train Number:"));
        add(trainNumberField);
        add(new JLabel("Class Type:"));
        add(classTypeField);
        add(new JLabel("Journey Date (DD/MM/YYYY):"));
        add(journeyDateField);
        add(new JLabel("From:"));
        add(fromField);
        add(new JLabel("To:"));
        add(toField);
        add(new JLabel());
        add(reserveButton);

        reserveButton.addActionListener(e -> {
            try {
                int trainNumber = Integer.parseInt(trainNumberField.getText());
                String name = nameField.getText();
                String classType = classTypeField.getText();
                String journeyDate = journeyDateField.getText();
                String from = fromField.getText();
                String to = toField.getText();

                Reservation reservation = new Reservation(pkrCounter++, name, trainNumber, classType, journeyDate, from, to);
                reservations.add(reservation);

                JOptionPane.showMessageDialog(this, "Reservation successful! Your PKR: " + reservation.getPkr());
                initMainScreen();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid train number.");
            }
        });

        revalidate();
        repaint();
    }

    private void initCancellationForm() {
        getContentPane().removeAll();
        setTitle("Cancellation Form");
        setSize(300, 200);
        setLayout(new GridLayout(3, 1, 10, 10));
        setLocationRelativeTo(null);

        JTextField pkrField = new JTextField();
        JButton cancelButton = new JButton("Cancel Reservation");

        add(new JLabel("Enter PKR Number:"));
        add(pkrField);
        add(cancelButton);

        cancelButton.addActionListener(e -> {
            try {
                int pkr = Integer.parseInt(pkrField.getText());
                Reservation reservation = reservations.stream()
                        .filter(r -> r.getPkr() == pkr)
                        .findFirst()
                        .orElse(null);

                if (reservation != null) {
                    reservations.remove(reservation);
                    JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No reservation found with this PKR.");
                }
                initMainScreen();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid PKR number.");
            }
        });

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineReservationSystemGUI::new); // Using method reference
    }
}

class Reservation {
    private int pkr;
    private String name;
    private int trainNumber;
    private String classType;
    private String journeyDate;
    private String from;
    private String to;

    public Reservation(int pkr, String name, int trainNumber, String classType, String journeyDate, String from, String to) {
        this.pkr = pkr;
        this.name = name;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.from = from;
        this.to = to;
    }

    public int getPkr() {
        return pkr;
    }

    @Override
    public String toString() {
        return "PKR: " + pkr + ", Name: " + name + ", Train Number: " + trainNumber + ", Class: " + classType +
                ", Date: " + journeyDate + ", From: " + from + ", To: " + to;
    }
}
