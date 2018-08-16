package bankgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;

public class PRJ2SmithX extends JFrame {

    String[][] accounts = readFile("PRJ2Accounts.txt");
    JLabel enterAccount = new JLabel("Enter Account # (four digit)");
    JTextField numberAccount = new JTextField();
    JLabel label1 = new JLabel("");
    JButton rBalance = new JButton("Request Balance");
    JLabel currentBalance = new JLabel("Current Balance");
    JLabel balance = new JLabel("");
    JLabel transaction = new JLabel("Transaction Amount");
    JTextField transactionAmount = new JTextField();
    JButton increse = new JButton("Increase");
    JButton decrese = new JButton("Decrease");
    JLabel informationMessage = new JLabel(" ");
    JPanel panel = new JPanel();

    public PRJ2SmithX() {
        initWindow();
    }

    public void initWindow() {
        setTitle("ATM Machine");
        setPreferredSize(new Dimension(450, 350));
        setLayout(new BorderLayout());
        createPanel();
        actions();
        add(panel, BorderLayout.CENTER);
        add(informationMessage, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);

    }

    private void createPanel() {
        panel.setLayout(new GridLayout(6, 2, 15, 15));
        panel.add(enterAccount);
        panel.add(numberAccount);
        panel.add(rBalance);
        panel.add(label1);
        panel.add(currentBalance);
        panel.add(balance);
        panel.add(transaction);
        panel.add(transactionAmount);
        panel.add(increse);
        panel.add(decrese);
    }

    private void actions() {
        rBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                if (numberAccount.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Invalid number of account");
                } else {
                    for (int i = 0; i < accounts.length; i++) {
                        if (numberAccount.getText().equals(accounts[i][0]) && accounts[i][1].equals("C")) {
                            balance.setText(accounts[i][4]);
                            found = true;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Account not found");
                    }
                }
            }
        });

        increse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numberAccount.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter account");
                } else if (transactionAmount.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter transaction amount");
                } else {
                    for (int i = 0; i < accounts.length; i++) {
                        if (numberAccount.getText().equals(accounts[i][0]) && accounts[i][1].equals("C")) {
                            double amount = Double.parseDouble(accounts[i][4]);
                            amount = amount + Double.parseDouble(transactionAmount.getText());
                            accounts[i][4] = String.valueOf(amount);
                            informationMessage.setText("         The New Balance for Account " + accounts[i][0] + " is " + accounts[i][4]);
                        }
                    }
                }
            }
        });
        decrese.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numberAccount.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter account");
                } else if (transactionAmount.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter transaction amount");
                } else {
                    for (int i = 0; i < accounts.length; i++) {
                        if (numberAccount.getText().equals(accounts[i][0]) && accounts[i][1].equals("C")) {
                            double amount = Double.parseDouble(accounts[i][4]);
                            if (amount >= Double.parseDouble(transactionAmount.getText())) {
                                amount = amount - Double.parseDouble(transactionAmount.getText());                             
                                if (Integer.parseInt(accounts[i][5]) % 4 == 0) {
                                    amount -= 1.5;
                                }
                                accounts[i][4] = String.valueOf(amount);
                                informationMessage.setText("         The New Balance for Account " + accounts[i][0] + " is " + accounts[i][4]);
                                int amountWindraw = Integer.parseInt(accounts[i][5]);
                                amountWindraw++;
                                accounts[i][5] = String.valueOf(amountWindraw);
                            } else {
                                informationMessage.setText("         Not enought money for decrease");
                            }

                        }
                    }
                }
            }
        });
    }

    public static String[][] readFile(String filename) {
        String[][] accounts = new String[13][6];
        BufferedReader reader = null;
        String line;

        int i = 0;
        int j = 6;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(",");
                for (int p = 0; p < j; p++) {
                    accounts[i][p] = array[p];
                }
                i++;

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
        }
        return accounts;
    }

    public static void main(String[] args) {
        PRJ2SmithX gui = new PRJ2SmithX();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }
}
