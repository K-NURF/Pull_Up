package entry;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class entry2 extends entry1 {
    java.sql.Connection conn;
    java.sql.Statement stmt;
    ResultSet rs;
    JButton enter, roof, ground, basement, pay, location;
    JTextField no_plate, roofRem, groundRem, basementRem;
    JLabel company, welcome, plate, rate, weight_height, roof_rem, ground_rem, basement_rem, mula, bye;

    String roofRemid, groundRemi, basementRemi;
//connection for parking_slots table
    public void ConnectSlots() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String host = "jdbc:mysql://localhost/park_app";
            String uName = "root";
            String uPass = "";

            conn = DriverManager.getConnection(host, uName, uPass);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String SQL = "Select * from parking_slots";

            rs = stmt.executeQuery(SQL);

            rs.next();
            int basementRemn = (rs.getInt("no_of_slots"));
            basementRemi = String.valueOf(basementRemn);
            // basementRem.setText(basementRemi);

            rs.next();
            int groundRemn = (rs.getInt("no_of_slots"));
            groundRemi = String.valueOf(groundRemn);
            // ground.setText(groundRemi);

            rs.next();
            int roofRemn = (rs.getInt("no_of_slots"));
            roofRemid = String.valueOf(roofRemn);
            // StringroofRem.setText(roofRemi);

        } catch (SQLException | ClassNotFoundException err) {
            JOptionPane.showMessageDialog(entry2.this, err.getMessage());
        }

    }
    //container
    public entry2() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLayout(null);
        setTitle("PULL_UP ENTRY");
        setBackground(Color.GRAY);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        Font fo = new Font("Serif", Font.PLAIN, 20);
        weight_height = new JLabel("Now select your preferred parking location");
        weight_height.setBounds(570, 20, 500, 50);
        weight_height.setFont(fo);
        weight_height.setForeground(Color.blue);
        add(weight_height);
        roof = new JButton("Roof-top");
        roof.setBounds(690, 150, 100, 25);
        roof.addActionListener(this);
        add(roof);
        ground = new JButton("Ground");
        ground.setBounds(690, 200, 100, 25);
        ground.addActionListener(this);
        add(ground);
        basement = new JButton("Basement");
        basement.setBounds(690, 250, 100, 25);
        basement.addActionListener(this);
        add(basement);
        rate = new JLabel("*Parking is free for the first 30min,\nafter this the rate is Ksh100/hr");
        rate.setBounds(510, 380, 500, 100);
        add(rate);
        rate.setFont(new Font("Harrington", Font.PLAIN, 16));
        ground_rem = new JLabel();
        ground_rem.setBounds(800, 200, 150, 25);
        add(ground_rem);
        roof_rem = new JLabel();
        roof_rem.setBounds(800, 150, 150, 25);
        add(roof_rem);
        basement_rem = new JLabel();
        basement_rem.setBounds(800, 250, 150, 25);
        add(basement_rem);
        location = new JButton("Click to show your slots remaining");
        location.setBounds(620, 100, 250, 25);
        add(location);
        location.addActionListener(this);

        ConnectVisits();
        ConnectCars();
        ConnectSlots();

    }

    @Override
    public void actionPerformed(ActionEvent event) {
    //remaining slots button
        if (event.getSource() == location) {
            ground_rem.setText(groundRemi + " slots remaining");
            roof_rem.setText(roofRemid + " slots remaining");
            basement_rem.setText(basementRemi + " slots remaining");
        }
        //roof-top button
        if (event.getSource() == roof) {
            try {
                String roof = "roof";
                stmt.executeUpdate(
                        "UPDATE `parking_slots` SET `no_of_slots` = (`no_of_slots` - '1') WHERE `parking_slots`.`place` = 'roof';");
                stmt.executeUpdate("UPDATE `visits` SET `place` = ('" + roof + "') WHERE `visits`.`no.plate` = ('"
                        + number_plate + "') AND `visits`.`place` IS NULL;");
                JOptionPane.showMessageDialog(entry2.this,
                        "Take the first right to go to the roof-top parking. WELCOME!");
                entry1 entry1 = new entry1();
                entry1.setVisible(true);
                entry2.setVisible(false);

            } catch (SQLException err) {
                JOptionPane.showMessageDialog(entry2.this, err.getMessage());
            }

        }
        //ground button
        if (event.getSource() == ground) {
            try {
                String ground = "ground";
                stmt.executeUpdate("UPDATE `visits` SET `place` = ('" + ground + "') WHERE `visits`.`no.plate` = ('"
                        + number_plate + "') AND `visits`.`place` IS NULL;");

                stmt.executeUpdate(
                        "UPDATE `parking_slots` SET `no_of_slots` = (`no_of_slots` - '1') WHERE `parking_slots`.`place` = 'ground';");
                JOptionPane.showMessageDialog(entry2.this, "Go straight on to go the ground parking. WELCOME!");
                entry1 entry1 = new entry1();
                entry1.setVisible(true);
                this.setVisible(false);

            } catch (SQLException err) {
                JOptionPane.showMessageDialog(entry2.this, err.getMessage());
            }

        }
        //basement button
        if (event.getSource() == basement) {
            try {
                String basement = "basement";
                stmt.executeUpdate("UPDATE `visits` SET `place` = ('" + basement + "') WHERE `visits`.`no.plate` = ('"
                        + number_plate + "') AND `visits`.`place` IS NULL;");

                stmt.executeUpdate(
                        "UPDATE `parking_slots` SET `no_of_slots` = (`no_of_slots` - '1') WHERE `parking_slots`.`place` = 'basement';");
                JOptionPane.showMessageDialog(entry2.this,
                        "Take the second right to go to the basement parking. WELCOME!");

                entry1 entry1 = new entry1();
                entry1.setVisible(true);
                this.setVisible(false);

            } catch (SQLException err) {
                JOptionPane.showMessageDialog(entry2.this, err.getMessage());
            }
        }

    }

}
