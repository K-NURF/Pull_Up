package entry;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class entry1 extends JFrame implements ActionListener {
    java.sql.Connection conn;
    java.sql.Statement stmt;
    ResultSet rs;
    JButton enter, roof, ground, basement, pay;
    JTextField no_plate;
    JLabel company, welcome, plate, rate;

    //connection for cars table
    public void ConnectCars() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String host = "jdbc:mysql://localhost/park_app";
            String uName = "root";
            String uPass = "";

            conn = DriverManager.getConnection(host, uName, uPass);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String SQL = "Select * from cars";

            rs = stmt.executeQuery(SQL);

        } catch (SQLException | ClassNotFoundException err) {
            JOptionPane.showMessageDialog(entry1.this, err.getMessage());
        }

    }
//connection for the visits table
    public void ConnectVisits() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String host = "jdbc:mysql://localhost/park_app";
            String uName = "root";
            String uPass = "";

            conn = DriverManager.getConnection(host, uName, uPass);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String SQL = "Select * from visits";

            rs = stmt.executeQuery(SQL);

        } catch (SQLException | ClassNotFoundException err) {
            JOptionPane.showMessageDialog(entry1.this, err.getMessage());
        }

    }
//used in the next class
    public static String number_plate;
//container
    public entry1() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLayout(null);
        setTitle("PULL_UP ENTRY");
        setBackground(Color.GREEN);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        Font fo = new Font("Serif", Font.BOLD, 20);
        company = new JLabel("THE VIEW!");
        company.setBounds(95, 10, 320, 100);
        company.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 50));
        add(company);
        company.setForeground(Color.BLUE);
        welcome = new JLabel("WELCOME!!!");
        welcome.setBounds(193, 100, 125, 25);
        welcome.setFont(new Font("Copperplate Gothic Bold Light", Font.PLAIN, 20));
        add(welcome);
        plate = new JLabel("Enter your Vehicle's number plate to continue(do not space)");
        plate.setBounds(70, 145, 350, 25);
        add(plate);
        no_plate = new JTextField();
        no_plate.setBounds(170, 200, 130, 30);
        add(no_plate);
        no_plate.setFont(fo);
        enter = new JButton("ENTER");
        enter.setBounds(155, 280, 160, 35);
        add(enter);
        enter.addActionListener(this);
        rate = new JLabel("*Parking is free for the first 30min,\nafter this the rate is Ksh100/hr");
        rate.setBounds(10, 380, 500, 100);
        add(rate);
        rate.setFont(new Font("Harrington", Font.PLAIN, 16));

        ConnectCars();
        ConnectVisits();
    }

    public static entry1 entry1 = new entry1();
    public static entry2 entry2 = new entry2();

    public static void main(String[] args) {
        
        entry1.setVisible(true);
    }

    static String height, weight;

    @Override
    public void actionPerformed(ActionEvent event) {
        //enter button
        if (event.getSource() == enter) {

            try {
                Random ran = new Random();
                double limit = 500;
                Double hght = ran.nextDouble(limit);
                double lmt = 10000;
                Double wght = ran.nextDouble(lmt);
                height = String.valueOf(hght);
                weight = String.valueOf(wght);

                stmt.executeUpdate("INSERT IGNORE cars ( `no.plate`, `height`, `weight`) VALUES ('" + no_plate.getText()
                        + "', '" + height + "', '" + weight + "')");
                //gets the current date and time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                final String entry_time = String.valueOf(dtf.format(now));

                stmt.execute("INSERT INTO visits ( `entry`, `no.plate`) VALUES ('" + entry_time + "', '"
                        + no_plate.getText() + "')");

                number_plate = no_plate.getText();
                //displays your vehicle's height and weight values  
                JOptionPane.showMessageDialog(entry1.this,
                        "Your Vehicle's height is:\n\n " + hght + " cm \n\nand weight is:\n\n " + wght + " kg");


                entry2.setVisible(true);

            } catch (SQLException err) {
                JOptionPane.showMessageDialog(entry1.this, err.getMessage());
            }
        }

    }

}