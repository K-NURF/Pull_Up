package exit;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class exit1 extends JFrame implements ActionListener {

    java.sql.Connection conn;
    java.sql.Statement stmt;
    ResultSet rs;
    JLabel companyLabel;
    JButton button;
    JLabel label2;
    JTextField plateText;
    JLabel success;

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
            JOptionPane.showMessageDialog(exit1.this, err.getMessage());
        }

    }

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
        }catch (SQLException | ClassNotFoundException err) {
            JOptionPane.showMessageDialog(exit1.this, err.getMessage());}
    
    }

    public exit1() {
        setDefaultCloseOperation(exit1.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLayout(null);
        setTitle("PULL_UP EXIT");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        Font fo = new Font("Serif", Font.BOLD, 20);

        companyLabel = new JLabel("THE VIEW!");
        companyLabel.setBounds(90, 10, 320, 100);
        companyLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 50));
        companyLabel.setForeground(Color.BLUE);
        add(companyLabel);

        label2 = new JLabel("Enter your Vehicle's number plate to continue (do Not Space)");
        label2.setBounds(55, 145, 350, 20);
        add(label2);

        plateText = new JTextField();
        plateText.setBounds(145, 190, 160, 30);
        plateText.setFont(fo);
        add(plateText);

        button = new JButton("Enter");
        button.setBounds(145, 280, 160, 35);
        button.addActionListener(this);
        add(button);

        success = new JLabel("");
        success.setBounds(225, 390, 160, 25);
        add(success);

        ConnectVisits();
        ConnectSlots();

    }

    public static void main(String[] args) {
        exit1 sts = new exit1();
        sts.setVisible(true);

    }

    public static String number_plate;

    @Override
    public void actionPerformed(ActionEvent event) {
        //enter button
        if (event.getSource() == button) {
            try {
                //gets the current date and time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                final String exit_time = String.valueOf(dtf.format(now));

                stmt.executeUpdate("UPDATE `visits` SET `exit_time` = ('" + exit_time + "') WHERE `visits`.`no.plate` = '"+ plateText.getText() + "' AND `visits`.`exit_time` IS NULL;");
                stmt.executeUpdate("UPDATE `parking_slots` SET `no_of_slots` = (`no_of_slots`+ '1') WHERE `parking_slots`.`place` = (SELECT `place` FROM `visits` WHERE `no.plate` = '" + plateText.getText() + "');");


                number_plate = plateText.getText();
                exit2 exit2 = new exit2();
                exit2.setVisible(true);
                this.setVisible(false);

            } catch (SQLException err) {
                JOptionPane.showMessageDialog(exit1.this, err.getMessage());
            }
            success.setText("Success!");
        }
    }
}
