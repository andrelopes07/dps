package pt.eseig.dps.views;

import pt.eseig.dps.controllers.UserController;
import pt.eseig.dps.models.User;

import javax.swing.*;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;

public class LoginView {

	JFrame frmDpsDevice;
	private JTextField txtUserName;
	private JPasswordField txtPassword;

	// LOGGED USER DATA
    private static String name;
    private static String email;
    private static String accessLevel;

    public static String getName() {
        return name;
    }

    public static String getEmail() {
        return email;
    }

    public static String getAccessLevel() {
        return accessLevel;
    }

    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {

        UserController.loadUsersFromFile();

        if (UserController.getUsers().size() == 0) {
            email = JOptionPane.showInputDialog(null, "Primeira vez que inicia DPS! Insira o seu email:");
            name = JOptionPane.showInputDialog(null, "Escolha agora um nome de utilizador:");
            String password = JOptionPane.showInputDialog(null, "E por fim uma password:");
            JOptionPane.showMessageDialog(null, "Registo efetuado! Bem-vindo ao Device Positioning System!");

            User user = new User(name, password, email, "Administrador");
            UserController.getUsers().add(user);
            UserController.saveUsersToFile();

            String userDataString = UserController.authenticate(name, password.toCharArray());
            String campos [] = userDataString.split("#");
            accessLevel = campos[3];
            Home frame = new Home();
            frame.setVisible(true);
        } else {

            EventQueue.invokeLater(() -> {
                try {
                    LoginView window = new LoginView();
                    window.frmDpsDevice.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
	}

	/**
	 * Create the application.
	 */
	LoginView() {

		frmDpsDevice = new JFrame();
		frmDpsDevice.getContentPane().setBackground(Color.WHITE);
		frmDpsDevice.setResizable(false);
		frmDpsDevice.setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		frmDpsDevice.setTitle("Device Positioning System");
		frmDpsDevice.setBounds(100, 100, 620, 415);
		frmDpsDevice.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmDpsDevice.getContentPane().setLayout(null);
		frmDpsDevice.setLocationRelativeTo(null);

		txtUserName = new JTextField();
		txtUserName.setToolTipText("Insira o Nome de Utilizador");
		txtUserName.setBounds(169, 273, 144, 20);
		txtUserName.setColumns(10);
        frmDpsDevice.getContentPane().add(txtUserName);

		JLabel lblInvalidData = new JLabel("Dados Inválidos!");
		lblInvalidData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblInvalidData.setForeground(Color.RED);
		lblInvalidData.setBounds(323, 344, 93, 14);
		lblInvalidData.setVisible(false);
        frmDpsDevice.getContentPane().add(lblInvalidData);

		JButton btnLogin = new JButton("Entrar");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(255, 165, 0));
		btnLogin.addActionListener(arg0 -> {
            String userDataString = UserController.authenticate(txtUserName.getText(), txtPassword.getPassword());
            if(userDataString.equals("")) {
                lblInvalidData.setVisible(true);
                txtUserName.setText("");
                txtPassword.setText("");
            } else {
                String campos [] = userDataString.split("#");
                name = campos[0];
				email = campos[2];
                accessLevel = campos[3];
                frmDpsDevice.setVisible(false);
                Home frame = new Home();
                frame.setVisible(true);
            }
        });
		btnLogin.setFont(new Font("Impact", Font.PLAIN, 14));
		btnLogin.setBounds(213, 340, 100, 23);
		frmDpsDevice.getContentPane().add(btnLogin);

		JButton btnExit = new JButton("Sair");
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(new Color(255, 165, 0));
		btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
		btnExit.addActionListener(e -> System.exit(0));
		btnExit.setBounds(101, 340, 100, 23);
		frmDpsDevice.getContentPane().add(btnExit);

		JLabel lblPassword = new JLabel("Senha:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblPassword.setBounds(121, 305, 38, 14);
		frmDpsDevice.add(lblPassword);

		JLabel lblUser = new JLabel("Utilizador:");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblUser.setBounds(90, 276, 69, 14);
		frmDpsDevice.getContentPane().add(lblUser);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(169, 304, 144, 20);
		frmDpsDevice.getContentPane().add(txtPassword);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("./img/background.png"));
		label.setBounds(44, 0, 614, 363);
		frmDpsDevice.getContentPane().add(label);

	}
}
