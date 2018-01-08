package pt.eseig.dps.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.Arrays;

import pt.eseig.dps.controllers.UserController;
import pt.eseig.dps.models.User;

class CfgUsersView extends JFrame {

    private JTextField txtName;
	private JTextField txtEmail;
	private JPasswordField txtPassword;

	/**
	 * Create the frame.
	 */
    CfgUsersView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Configurar Utilizadores");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 380);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 604, 351);
		contentPane.add(panel);
		
		JLabel lblUser = new JLabel("Utilizadores:");
		lblUser.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblUser.setBounds(34, 36, 95, 16);
		panel.add(lblUser);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(365, 62, 205, 20);
		panel.add(txtName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(365, 93, 205, 20);
		panel.add(txtEmail);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(365, 124, 205, 20);
		panel.add(txtPassword);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblName.setBounds(315, 64, 40, 16);
		panel.add(lblName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblEmail.setBounds(227, 95, 128, 16);
		panel.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Palavra-Passe:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblPassword.setBounds(256, 126, 99, 16);
		panel.add(lblPassword);
		
		JLabel lblAccessLevel = new JLabel("Perfil:");
		lblAccessLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccessLevel.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblAccessLevel.setBounds(296, 153,
                59, 16);
		panel.add(lblAccessLevel);

		JRadioButton rdBtnAdministrator = new JRadioButton("Administrador");
		rdBtnAdministrator.setBackground(new Color(255, 255, 255));
		rdBtnAdministrator.setFont(new Font("Calibri", Font.PLAIN, 14));
		rdBtnAdministrator.setBounds(365, 150, 119, 23);
		panel.add(rdBtnAdministrator);
		
		JRadioButton rdBtnManager = new JRadioButton("Gestor");
		rdBtnManager.setBackground(new Color(255, 255, 255));
		rdBtnManager.setFont(new Font("Calibri", Font.PLAIN, 14));
		rdBtnManager.setBounds(365, 176, 82, 23);
		panel.add(rdBtnManager);

        JRadioButton rdBtnOperator = new JRadioButton("Operador");
        rdBtnOperator.setFont(new Font("Calibri", Font.PLAIN, 14));
        rdBtnOperator.setBackground(new Color(255, 255, 255));
        rdBtnOperator.setBounds(365, 202, 81, 23);
        panel.add(rdBtnOperator);

        ButtonGroup group = new ButtonGroup();
        group.add(rdBtnAdministrator);
        group.add(rdBtnOperator);
        group.add(rdBtnManager);

		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> list = new JList<>(model);
		list.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		list.setFont(new Font("Calibri", Font.PLAIN, 14));
		list.setVisibleRowCount(3);
		//JScrollPane scrollPane = new JScrollPane(list);
		list.addListSelectionListener(arg0 -> {
            if (list.getSelectedValue().equals("Novo Utilizador")) {
                txtName.setText("");
                txtPassword.setText("");
                txtEmail.setText("");
                rdBtnAdministrator.setSelected(true);
            } else {
                for (int i = 0; i < UserController.getUsers().size(); i++) {
                    User user = UserController.getUsers().get(i);
                    if (list.getSelectedValue().equals(user.getName())) {
                        txtName.setText(user.getName());
                        txtPassword.setText(user.getPassword());
                        txtEmail.setText(user.getEmail());
                        if(user.getAccessLevel().equals("Administrador")) {
                            rdBtnAdministrator.setSelected(true);
                        }
                        if(user.getAccessLevel().equals("Gestor")) {
                            rdBtnManager.setSelected(true);
                        }
                        if(user.getAccessLevel().equals("Operador")) {
                            rdBtnOperator.setSelected(true);
                        }
                    }
                }
            }
        });
		
		model.addElement("Novo Utilizador");
		list.setSelectedIndex(0);
		for (int i = 0; i < UserController.getUsers().size(); i++) {
			User u = UserController.getUsers().get(i);
			String nome = u.getName();
			model.addElement(nome);
		}
		list.setBounds(34, 62, 195, 257);
		panel.add(list);
		
		JButton btnRemoveUser = new JButton("Remover Utilizador");
		btnRemoveUser.setBackground(new Color(255, 165, 0));
		btnRemoveUser.setForeground(new Color(255, 255, 255));
		btnRemoveUser.setFont(new Font("Impact", Font.PLAIN, 14));
		btnRemoveUser.addActionListener(arg0 -> {
            String selectedUser = list.getSelectedValue();
            for (int i = 0; i < UserController.getUsers().size(); i++) {
                User user = UserController.getUsers().get(i);
                if (selectedUser.equals(user.getName())) {
                    if (selectedUser.equals(LoginView.getName())) {
                        JOptionPane.showMessageDialog(null, "Não é possivel remover um utilizador com sessão iniciada!");
                    } else {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que deseja remover o utilizador selecionado?","Aviso", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION ) {
                            UserController.getUsers().remove(user);
                            UserController.saveUsersToFile();
                            JOptionPane.showMessageDialog(null, "Utilizador removido com sucesso!");
                            dispose();
                            CfgUsersView frame = new CfgUsersView();
                            frame.setVisible(true);
                        }
                    }
                }
            }
        });
		btnRemoveUser.setBounds(360, 232, 210, 35);
		panel.add(btnRemoveUser);

		JButton btnSave = new JButton("Guardar");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
		btnSave.setBackground(new Color(255, 165, 0));
		btnSave.addActionListener(e -> {
            if (list.getSelectedValue().equals("Novo Utilizador")) {
                String accessLevel = "";
                if (rdBtnAdministrator.isSelected()) {
                    accessLevel = "Administrador";
                }
                if (rdBtnManager.isSelected()) {
                    accessLevel = "Gestor";
                }
                if (rdBtnOperator.isSelected()) {
                    accessLevel = "Operador";
                }
                if (txtEmail.getText().equals("") || txtName.getText().equals("") || txtPassword.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Campos vazios!");
                } else {
                    String userNameString = UserController.checkUserName(txtName.getText());
                    if (userNameString.equals("Exists")) {
                        JOptionPane.showMessageDialog(null, "O utilizador já existe, introduza outro nome!");
                        txtName.setText("");
                    } else {
                        User user = new User(txtName.getText(), Arrays.toString(txtPassword.getPassword()), txtEmail.getText(), accessLevel);
                        UserController.getUsers().add(user);
                        JOptionPane.showMessageDialog(null, "Utilizador criado com sucesso!");
                        UserController.saveUsersToFile();
                        dispose();
                        CfgUsersView frame = new CfgUsersView();
                        frame.setVisible(true);
                    }
                }
            } else {
                for (int i = 0; i < UserController.getUsers().size(); i++) {
                    User user = UserController.getUsers().get(i);
                    if (list.getSelectedValue().equals(user.getName())) {
                        if (txtEmail.getText().equals("") || txtName.getText().equals("") || txtPassword.getPassword().length == 0) {
                            JOptionPane.showMessageDialog(null, "Campos vazios!");
                        } else {
                            String userNameString = UserController.checkUserName(txtName.getText());
                            if (LoginView.getAccessLevel().equals("Administrador") && !rdBtnAdministrator.isSelected()) {
                                Integer adminCount = 0;
                                for (User admin : UserController.getUsers()) {
                                    if (admin.getAccessLevel().equals("Administrador")) {
                                        adminCount++;
                                    }
                                }
                                if (adminCount == 1) {
                                    JOptionPane.showMessageDialog(null, "Não é possivel alterar o nivel de acesso do único administrador existente!");
                                    rdBtnAdministrator.setSelected(true);
                                } else {
                                    user.setName(txtName.getText());
                                    user.setPassword(new String(txtPassword.getPassword()));
                                    user.setEmail(txtEmail.getText());
                                    String accessLevel = "";
                                    if (rdBtnAdministrator.isSelected()) {
                                        accessLevel = "Administrador";
                                    }
                                    if (rdBtnManager.isSelected()) {
                                        accessLevel = "Gestor";
                                    }
                                    if (rdBtnOperator.isSelected()) {
                                        accessLevel = "Operador";
                                    }

                                    user.setAccessLevel(accessLevel);
                                    JOptionPane.showMessageDialog(null, "Utilizador alterado com sucesso!");
                                    UserController.saveUsersToFile();
                                    dispose();
                                    CfgUsersView frame = new CfgUsersView();
                                    frame.setVisible(true);
                                }
                            } else if (userNameString.equals("Exists") && !(txtName.getText().equals(list.getSelectedValue()))) {
                                JOptionPane.showMessageDialog(null, "O utilizador já existe, introduza outro nome!");
                                txtName.setText("");
                            } else {
                                user.setName(txtName.getText());
                                user.setPassword(new String(txtPassword.getPassword()));
                                user.setEmail(txtEmail.getText());
                                String accessLevel = "";
                                if (rdBtnAdministrator.isSelected()) {
                                    accessLevel = "Administrador";
                                }
                                if (rdBtnManager.isSelected()) {
                                    accessLevel = "Gestor";
                                }
                                if (rdBtnOperator.isSelected()) {
                                    accessLevel = "Operador";
                                }

                                user.setAccessLevel(accessLevel);
                                JOptionPane.showMessageDialog(null, "Utilizador alterado com sucesso!");
                                UserController.saveUsersToFile();
                                dispose();
                                CfgUsersView frame = new CfgUsersView();
                                frame.setVisible(true);
                            }
                        }
                    }
                }
            }
        });
		btnSave.setBounds(360, 279, 100, 40);
		panel.add(btnSave);

        JButton btnExit = new JButton("Voltar");
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.addActionListener(arg0 -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(470, 279, 100, 40);
        panel.add(btnExit);

		}
	}
