package exit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class exit2 extends exit1 {

    JLabel amount;
    JButton pay;
    JLabel money, bye;
    Random ran = new Random();
    int paid = ran.nextInt(1200);
    //container
    public exit2() {

        setDefaultCloseOperation(exit1.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLayout(null);
        setTitle("PULL_UP EXIT");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        Font fo = new Font("Serif", Font.BOLD, 30);

        amount = new JLabel("Total amount is:");
        amount.setBounds(645, 70, 250, 80);
        amount.setFont(fo);
        add(amount);

        money = new JLabel("KSh " + paid);
        money.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 50));
        money.setBounds(625, 150, 300, 60);
        money.setForeground(Color.RED);
        add(money);

        pay = new JButton("PAY");
        pay.setBounds(680, 280, 160, 35);
        pay.addActionListener(this);
        add(pay);

        ConnectVisits();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //pay button
        if (e.getSource() == pay) {
            JOptionPane.showMessageDialog(exit2.this, "PAYBILL: 456897\nACCOUNT: your name");
            try {

                stmt.executeUpdate("UPDATE `visits` SET `paid` = ('" + paid + "') WHERE `visits`.`no.plate` = '"
                        + number_plate + "' AND `visits`.`paid` IS NULL;");
            } catch (SQLException err) {
                JOptionPane.showMessageDialog(exit2.this, err.getMessage());
            }
            JOptionPane.showMessageDialog(exit2.this, "\t\tTHANK YOU\n\tSAFE JOURNEY\n\t\tCOME AGAIN ");
            exit1 exit1 = new exit1();
            plateText.setEditable(true);
            exit1.setVisible(true);
            this.setVisible(false);

        }

    }
}