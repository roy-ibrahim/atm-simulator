//importing necessary libraries for the program's design and implementation
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

//defining the Owner class characterized by a name (String) and a pin (String)
class Owner{
    private String name;
    private String pin;
    
    //default constructor
    public Owner(String name, String pin) {
        this.name = name;
        this.pin = pin;
    }

    //getter in order to get the value of the name
    public String getName() {
        return name;
    }
    
    //getter to get the value of the pin
    public String getPin() {
        return pin;
    }
      
}

/*Account class to represent the bank account, it includes the Account Number (String)
accountOwner (Owner) and the balance (int)
*/

class Account{
    //defining the needed variables
    private String AccountNumber;
    private Owner AccountOwner;
    private int Balance;

    //default constructor to create a variable of type Account
    public Account(String accountNumber, Owner accountOwner, int balance) {
        AccountNumber = accountNumber;
        AccountOwner = accountOwner;
        Balance = balance;
    }

    //getter to return the account number
    public String getAccountNumber() {
        return AccountNumber;
    }

    //getter to get the Account owner
    public Owner getAccountOwner() {
        return AccountOwner;
    }

    //getter to get the balance
    public int getBalance() {
        return Balance;
    }

    //setter to change the value of the balance
    public void setBalance(int Balance){
        this.Balance = Balance;
    }
}

//class to Handle accounts of type Account
class HandleAccounts{
    //ArrayList to store the accounts 
    private ArrayList<Account> accounts = new ArrayList<Account>();

    //method to add an Account
    public void addAccount(Account a){
        accounts.add(a);
    }

    //getter to retrn the ArrayList containing the accounts
    public ArrayList<Account> getAccounts(){
        return accounts;
    }
}

//login popup class that inhertits the Jframe methods
class LoginScreen extends JFrame{
    //initializing the needed component to form the login frame
    private JButton login; //button for login
    private JTextField user; //TextField so the use can input the account number
    private JPasswordField pass;//password field so the user enters the password
    private JLabel username;//Label to indicate the username section
    private JLabel error;//error message to pop in case of invalid accout or incorrect password
    private JLabel password;//Label to indicate the password section
    private JCheckBox Checkbox;//check box to show or hide password
    private HandleAccounts acc;
    private ATMScreen atm;

    //initializing the screen framce
    public LoginScreen(HandleAccounts acc){
        
        login = new JButton("Login");
        user = new JTextField();
        pass = new JPasswordField();
        username = new JLabel("Account Number");
        password = new JLabel("PIN");
        Checkbox = new JCheckBox("Show Password");
        error = new JLabel("");

        //setting the checkbox design to show or hide password
        Checkbox.setBounds(187, 130, 120, 20);
        Checkbox.setForeground(Color.orange);
        Checkbox.setBackground(Color.black);
        Checkbox.setFont(new Font("Calabri", Font.PLAIN, 11));
        //action listener to show password when selected and hide it when it is not
        Checkbox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(Checkbox.isSelected())
                    pass.setEchoChar((char)0);
                else
                    pass.setEchoChar('*');
            }
        });

        //setting the error box location and design
        error.setBounds(190, 150, 200, 20);
        error.setForeground(Color.orange);
        error.setFont(new Font("Calabri", Font.BOLD, 12));
        setTitle("ATM Login");
        username.setBounds(30, 40, 150, 15);
        user.setBounds(190, 35, 200, 30);
        user.setBorder(new LineBorder(Color.orange, 2, true));
        user.setBackground(Color.DARK_GRAY);
        user.setForeground(Color.white);
        username.setFont(new Font("Calabri", Font.BOLD, 16));
        username.setForeground(Color.orange);

        //password label to place it before the password field
        password.setBounds(30, 100, 150, 15);
        password.setFont(new Font("Calabri", Font.BOLD, 16));
        password.setForeground(Color.orange);

        //password field so the user enters the PIN
        pass.setBounds(190, 95, 200, 30);
        pass.setBorder(new LineBorder(Color.orange, 2, true));
        pass.setBackground(Color.DARK_GRAY);
        pass.setForeground(Color.white);

        //setting the login button location and design
        login.setBounds(150, 190, 80, 30);
        login.setBorder(new LineBorder(Color.orange, 2, true));
        login.setBackground(Color.black);
        login.setForeground(Color.orange);
        //action listener for login validation
        login.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int size = (acc.getAccounts()).size();
                if(size == 0){
                    error.setText("No Accounts registered. ");
                }
                else{
                    String User = user.getText();
                    String Pass = new String(pass.getPassword());

                    for(int i = 0 ; i < size ; i++){
                        if((User == ((acc.getAccounts()).get(i)).getAccountNumber()) && ((Pass == ((acc.getAccounts()).get(i)).getAccountOwner().getPin()))){
                                atm = new ATMScreen(acc.getAccounts().get(i));
                                atm.setVisible(true);
                                dispose();
                        }
                            else{
                                error.setText("Invalid account");
                            }
                        }
                    }
                }
                
            }
        );
        //mouse listener for hover effect
        login.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                login.setBackground(Color.orange);
                login.setForeground(Color.black);
            }
        public void mouseExited(MouseEvent e) {
            login.setBackground(Color.black);
            login.setForeground(Color.orange);
            }
        });

        //adding the components to the frame
        add(username);
        add(password);
        add(user);
        add(pass);
        add(login);
        add(Checkbox);
        add(error);
        
        //setting the design, layout, visiblity and resizability of the frame
        getContentPane().setBackground(Color.black);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}

//Class that inherits JFrame methods to represent the ATM interface
class ATMScreen extends JFrame implements ActionListener{ 
    private JButton exit; //exit button to quit the ATM after all trascations needed are done
    private JLabel background; //label to add an image as a background
    private JButton deposit; //Button to access the deposit frame
    private JButton withdraw; //Button to access the withdraw frame
    private JButton checkBalance; //Button so the account owner checks the account balance
    private JLabel Balance; //Label to show the balance
    private JLabel welcomemsg; //label to display a message to greet the account owner
    private Deposit dep; 
    private Withdraw wd;
    private LoginScreen login;

    public ATMScreen(Account acc){
        //setting the background image
        background = new JLabel(new ImageIcon("C:\\Users\\Antonio\\Desktop\\University\\Semester 2\\CSC 314\\Triangle\\ATM-Project\\src\\bg.png"));
        background.setBounds(0, 0, 400, 600);

        //design and functionality of the Exit button
        exit = new JButton("Exit");
        exit.setBounds(5, 5, 100, 40);
        exit.setFont(new Font("Calabri", Font.BOLD, 20));
        exit.setBackground(Color.black);
        exit.setForeground(Color.orange);
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        exit.setBorder(new LineBorder(Color.black, 2, true));
        //hover effect
        exit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exit.setBackground(Color.orange);
                exit.setForeground(Color.black);
            }public void mouseExited(MouseEvent e){
                exit.setBackground(Color.black);
                exit.setForeground(Color.orange);
            }
        });

        //setting the location and desgin of the balance indication
        Balance = new JLabel("You currently have: ");
        Balance.setBounds(70, 20, 300, 50);
        Balance.setFont(new Font("Calabri", Font.BOLD, 17));
        Balance.setForeground(Color.black);
        Balance.setText("You currently have: " + acc.getBalance() + " $");
        Balance.setVisible(false);

        //deposit button designing and implementation
        deposit  = new JButton("Deposit");
        deposit.setBounds(40, 200, 200, 50);
        deposit.setBorder(new LineBorder(Color.orange, 2, true));
        deposit.setForeground(Color.orange);
        deposit.setBackground(Color.black);
        deposit.setFont(new Font("Calabri", Font.BOLD, 16));
        deposit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                deposit.setBackground(Color.orange);
                deposit.setForeground(Color.black);
            }public void mouseExited(MouseEvent e){
                deposit.setBackground(Color.black);
                deposit.setForeground(Color.orange);
            }
        });
        deposit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dep = new Deposit(acc);
                dep.setVisible(true);
            }
        });

        //withdraw button designing and implementation
        withdraw  = new JButton("Withdraw");
        withdraw.setBounds(160, 325, 200, 50);
        withdraw.setBorder(new LineBorder(Color.orange, 2, true));
        withdraw.setForeground(Color.orange);
        withdraw.setBackground(Color.black);
        withdraw.setFont(new Font("Calabri", Font.BOLD, 16));
        withdraw.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                withdraw.setBackground(Color.orange);
                withdraw.setForeground(Color.black);
            }public void mouseExited(MouseEvent e){
                withdraw.setBackground(Color.black);
                withdraw.setForeground(Color.orange);
            }
        });
        withdraw.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                wd = new Withdraw(acc);
                wd.setVisible(true);
            }
        });

        //Check balance button designing and implementation
        checkBalance  = new JButton("Check Balance");
        checkBalance.setBounds(40, 450, 200, 50);
        checkBalance.setBorder(new LineBorder(Color.orange, 2, true));
        checkBalance.setForeground(Color.black);
        checkBalance.setBackground(Color.orange);
        checkBalance.setFont(new Font("Calabri", Font.BOLD, 16));
        checkBalance.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                checkBalance.setBackground(Color.black);
                checkBalance.setForeground(Color.orange);
            }public void mouseExited(MouseEvent e){
                checkBalance.setBackground(Color.orange);
                checkBalance.setForeground(Color.black);
            }
        });

        checkBalance.addActionListener(this);

        //welcome message design 
        welcomemsg = new JLabel();
        welcomemsg.setForeground(Color.black);
        welcomemsg.setBounds(100, 70, 200, 50);
        welcomemsg.setFont(new Font("Calabri", Font.BOLD, 25));
        welcomemsg.setText("Welcome, " + acc.getAccountOwner().getName());

        //adding all the components to the framce
        add(background);
        background.add(welcomemsg);
        background.add(deposit);
        background.add(exit);
        background.add(withdraw);
        background.add(Balance);
        background.add(checkBalance);
        
        //layout of the frame
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setSize(400,600);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //actionPerform for the checkBalance button
    public void actionPerformed(ActionEvent e){
        Balance.setVisible(true);
    }
}

//class to represent the Deposit frame that inherits al JFrame functions
class Deposit extends JFrame{
    private JButton back; //back butto to go back to the ATM screen
    private JLabel amount; //display the amount input by the account owner
    private JButton cashin; //button to automate the transaction
    private JLabel currency; //label to display the currency
    private JButton numbers[]; //array of JButtons for the number pad
    private String Numbers[] = {"1","2","3","4","5","6","7","8","9","0"};
    private Account acc;
    private ATMScreen atm;

    
    public Deposit(Account acc){
        //initializing the JButton array
        numbers = new JButton[10];

        //setting the design and location of the amount label
        amount = new JLabel();
        amount.setBounds(120, 60, 150, 40);
        amount.setBorder(new LineBorder(Color.orange, 2, true));
        amount.setForeground(Color.orange);
        amount.setBackground(Color.black);
        amount.setFont(new Font("Calabri", Font.BOLD, 15));

        ////setting the design and location of the back button
        back = new JButton("←");
        back.setBounds(5, 5, 40, 40);
        back.setFont(new Font("Calabri", Font.BOLD, 25));
        back.setBackground(Color.black);
        back.setForeground(Color.orange);
        back.setBorder(new LineBorder(Color.black, 2, true));
        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setBackground(Color.orange);
                back.setForeground(Color.black);
            }public void mouseExited(MouseEvent e){
                back.setBackground(Color.black);
                back.setForeground(Color.orange);
            }
        });
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                atm = new ATMScreen(acc);
                atm.setVisible(true);
                dispose();
            }
        });

        //setting the design and location of the deposit button
        cashin = new JButton("Deposit");
        cashin.setBounds(100, 480, 200, 50);
        cashin.setBorder(new LineBorder(Color.orange, 2, true));
        cashin.setForeground(Color.black);
        cashin.setBackground(Color.orange);
        cashin.setFont(new Font("Calabri", Font.BOLD, 16));
        cashin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                cashin.setBackground(Color.black);
                cashin.setForeground(Color.orange);
            }public void mouseExited(MouseEvent e){
                cashin.setBackground(Color.orange);
                cashin.setForeground(Color.black);
            }
        });
        cashin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int Amount = Integer.parseInt(amount.getText());
                acc.setBalance(acc.getBalance() + Amount);
                JOptionPane.showMessageDialog(null, "$ " + Amount + " have been added to your account balance. ");
            }
        });

        currency = new JLabel("$");
        currency.setBounds(250,65,30,30);
        currency.setForeground(Color.orange);
        currency.setBackground(Color.black);
        currency.setFont(new Font("Calabri", Font.BOLD, 18));

        //looping through the array of buttons to customize them and give them the hover effect
        for(int i = 0 ; i < 10 ; i++){
            numbers[i] = new JButton("" + i);
            numbers[i].setBackground(Color.black);
            numbers[i].setForeground(Color.orange);
            numbers[i].setBorder(new LineBorder(Color.orange, 2, true));
            numbers[i].setFont(new Font("Calabri", Font.BOLD, 15));
            numbers[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    amount.setText(amount.getText() + e.getActionCommand());
                }
            });
        }

        //setting the bounds of the number pad
        numbers[1].setBounds(80, 150, 60, 60);
        numbers[2].setBounds(160, 150, 60, 60);
        numbers[3].setBounds(240, 150, 60,60);
        numbers[4].setBounds(80, 230, 60,60);
        numbers[5].setBounds(160, 230, 60,60);
        numbers[6].setBounds(240, 230, 60,60);
        numbers[7].setBounds(80, 310, 60,60);
        numbers[8].setBounds(160, 310, 60,60);
        numbers[9].setBounds(240, 310, 60,60);
        numbers[0].setBounds(160, 390, 60, 60);

        //adding the components
        add(cashin);
        add(amount);
        add(currency);
        add(back);
        for(int i = 0 ; i < 10 ; i++)
            add(numbers[i]);
        
        //layout of the frame
        getContentPane().setBackground(Color.black);
        setSize(400,600);
        setLayout(null);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
}

//class that inherits JFrame to represent the withdraw
class Withdraw extends JFrame{
    private JButton back; //back button to return to the ATM screen
    private JLabel title; //title of the frame
    private JButton buttons[]; //array of buttons for different withdrawal options
    private int value[] = {20, 50, 100, 200, 500, 1000}; //array of the withdrawal options values
    private Account acc;
    private ATMScreen atm;

    public Withdraw(Account acc){
        //customizing the title
        title = new JLabel("Withdraw");
        title.setForeground(Color.orange);
        title.setBounds(140, 40, 200, 50);
        title.setFont(new Font("Calabri", Font.BOLD, 25));

        //design and implementation of the back button
        back = new JButton("←");
        back.setBounds(5, 5, 40, 40);
        back.setFont(new Font("Calabri", Font.BOLD, 25));
        back.setBackground(Color.black);
        back.setForeground(Color.orange);
        back.setBorder(new LineBorder(Color.black, 2, true));
        back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back.setBackground(Color.orange);
                back.setForeground(Color.black);
            }public void mouseExited(MouseEvent e){
                back.setBackground(Color.black);
                back.setForeground(Color.orange);
            }
        });
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                atm = new ATMScreen(acc);
                atm.setVisible(true);
                dispose();
            }
        });
        
        //design and implementation of the array of buttons
        buttons = new JButton[6];
        for(int i = 0 ; i < value.length ; i++){
            buttons[i] = new JButton("" + value[i] + " $");
            buttons[i].setBackground(Color.black);
            buttons[i].setForeground(Color.orange);
            buttons[i].setBorder(new LineBorder(Color.orange, 2, true));
            buttons[i].setFont(new Font("Calabri", Font.BOLD, 15));
        }
        //assigning for each button an action listener to display the number
        buttons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int amount = acc.getBalance() - value[0];
                if(amount > 0){
                    acc.setBalance(amount);
                    JOptionPane.showMessageDialog(null, "Transaction Complete. ");
                }
                else
                    JOptionPane.showMessageDialog(null, "The amount you are trying to withdraw is not available. ");
            }
        });
        
        buttons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int amount = acc.getBalance() - value[1];
                if(amount > 0){
                    acc.setBalance(amount);
                    JOptionPane.showMessageDialog(null, "Transaction Complete. ");
                }
                else
                    JOptionPane.showMessageDialog(null, "The amount you are trying to withdraw is not available. ");
            }
        });

        buttons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int amount = acc.getBalance() - value[2];
                if(amount > 0){
                    acc.setBalance(amount);
                    JOptionPane.showMessageDialog(null, "Transaction Complete. ");
                }
                else
                    JOptionPane.showMessageDialog(null, "The amount you are trying to withdraw is not available. ");
            }
        });

        buttons[3].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int amount = acc.getBalance() - value[3];
                if(amount > 0){
                    acc.setBalance(amount);
                    JOptionPane.showMessageDialog(null, "Transaction Complete. ");
                }
                else
                    JOptionPane.showMessageDialog(null, "The amount you are trying to withdraw is not available. ");
            }
        });

        buttons[4].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int amount = acc.getBalance() - value[4];
                if(amount > 0){
                    acc.setBalance(amount);
                    JOptionPane.showMessageDialog(null, "Transaction Complete. ");
                }
                else
                    JOptionPane.showMessageDialog(null, "The amount you are trying to withdraw is not available. ");
            }
        });

        buttons[5].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int amount = acc.getBalance() - value[5];
                if(amount > 0){
                    acc.setBalance(amount);
                    JOptionPane.showMessageDialog(null, "Transaction Complete. ");
                }
                else
                    JOptionPane.showMessageDialog(null, "The amount you are trying to withdraw is not available. ");
            }
        });

        //setting the bounds of the buttons
        buttons[0].setBounds(30, 120, 150, 40);
        buttons[1].setBounds(30, 180, 150, 40);
        buttons[2].setBounds(30, 240, 150, 40);
        buttons[3].setBounds(210, 120, 150, 40);
        buttons[4].setBounds(210, 180, 150, 40);
        buttons[5].setBounds(210, 240, 150, 40);
        
        //adding the components
        add(back);
        add(title);
        for(int i = 0 ; i < buttons.length; i++)
            add(buttons[i]);

        //layout of the frame
        getContentPane().setBackground(Color.black);
        setSize(400,600);
        setLayout(null);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
}

public class Driver {
    public static void main(String[] args) throws Exception {
        //driver program to test the code 
        HandleAccounts acc = new HandleAccounts();
        Owner own1 = new Owner("Roy", "1245");
        Owner own2 = new Owner("Peter", "2574");
        Owner own3 = new Owner("Joe", "5965");
        Account acc1 = new Account("256554668", own1,1200);
        Account acc2 = new Account("4796651651", own2, 500);
        Account acc3 = new Account("951651", own3, 1800);
        acc.addAccount(acc1);
        acc.addAccount(acc2);
        acc.addAccount(acc3);
        new LoginScreen(acc);
        //new ATMSceeen(acc1);
    }
}
