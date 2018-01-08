package pt.eseig.dps.views;

import pt.eseig.dps.models.DeviceType;
import pt.eseig.dps.controllers.DeviceTypeController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.util.ListIterator;

class CfgDeviceTypesView extends JFrame {

    private JTextField txtName;

	/**
	 * Create the frame.
	 */
	CfgDeviceTypesView() {

        DeviceTypeController.loadDeviceTypesFromFile();

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setTitle("Configurar Famílias");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 380);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(158, 245, 113, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> deviceTypeList = new JList<>(model);
		deviceTypeList.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		deviceTypeList.setFont(new Font("Calibri", Font.PLAIN, 14));
		model.addElement("Nova Família");
		deviceTypeList.setSelectedIndex(0);
		for (int i = 0; i < DeviceTypeController.getDeviceTypes().size(); i++) {
			DeviceType deviceType = DeviceTypeController.getDeviceTypes().get(i);
			model.addElement(deviceType.getName());
		}
		deviceTypeList.addListSelectionListener(arg0 -> {
            if (deviceTypeList.getSelectedValue().equals("Nova Família")) {
                txtName.setText("");
            } else {
                for (int i = 0; i < DeviceTypeController.getDeviceTypes().size(); i++) {
                    DeviceType deviceType = DeviceTypeController.getDeviceTypes().get(i);
                    if (deviceTypeList.getSelectedValue().equals(deviceType.getName())) {
                        txtName.setText(deviceType.getName());
                    }
                }
            }
        });
		deviceTypeList.setBounds(111, 64, 160, 160);
		contentPane.add(deviceTypeList);

		JButton btnSave = new JButton("Guardar");
		btnSave.setBackground(new Color(255, 165, 0));
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
		btnSave.addActionListener(e -> {
            if (deviceTypeList.getSelectedValue().equals("Nova Família")) {
				if (txtName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Campos vazios!");
				} else if (checksIfExists(txtName.getText())) {
                    JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                    txtName.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Família adicionada com sucesso!");
					DeviceType deviceType = new DeviceType(txtName.getText());
					DeviceTypeController.getDeviceTypes().add(deviceType);
					DeviceTypeController.saveDeviceTypesToFile();
					dispose();
					CfgDeviceTypesView frame = new CfgDeviceTypesView();
					frame.setVisible(true);
				}
            } else {
                for(int i = 0; i < DeviceTypeController.getDeviceTypes().size(); i++) {
                    DeviceType deviceType = DeviceTypeController.getDeviceTypes().get(i);
                    if (deviceType.getName().equals(deviceTypeList.getSelectedValue())) {
                        if (txtName.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Campos vazios!");
                        } else if (checksIfExists(txtName.getText()) && !txtName.getText().equals(deviceTypeList.getSelectedValue())) {
                            JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                            txtName.setText("");
                        } else {
                            deviceType.setName(txtName.getText());
                            JOptionPane.showMessageDialog(null, "Família alterada com sucesso!");
                            DeviceTypeController.saveDeviceTypesToFile();
                            dispose();
                            CfgDeviceTypesView frame = new CfgDeviceTypesView();
                            frame.setVisible(true);
                        }
                    }
                }
            }
        });
		btnSave.setBounds(25, 289, 100, 40);
		contentPane.add(btnSave);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblName.setBounds(111, 247, 53, 17);
		contentPane.add(lblName);
		
		JLabel lblDeviceTypes = new JLabel("Famílias:");
		lblDeviceTypes.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblDeviceTypes.setBounds(111, 36, 113, 17);
		contentPane.add(lblDeviceTypes);
		
		JButton btnRemoveDeviceType = new JButton("Remover");
		btnRemoveDeviceType.setForeground(Color.WHITE);
		btnRemoveDeviceType.setFont(new Font("Impact", Font.PLAIN, 14));
		btnRemoveDeviceType.setBackground(new Color(255, 165, 0));
		btnRemoveDeviceType.setBounds(135, 289, 100, 40);
        btnRemoveDeviceType.addActionListener(e -> {
            String deviceTypeNameString = deviceTypeList.getSelectedValue();
            ListIterator<DeviceType> deviceTypeListIterator = DeviceTypeController.getDeviceTypes().listIterator();
            while (deviceTypeListIterator.hasNext()) {
                DeviceType deviceType = deviceTypeListIterator.next();
                if (deviceTypeNameString.equals(deviceType.getName())) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que deseja remover a família selecionada?","Aviso", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION ) {
                        deviceTypeListIterator.remove();
                        JOptionPane.showMessageDialog(null, "Família removida com sucesso!");
                        DeviceTypeController.saveDeviceTypesToFile();
                        dispose();
                        CfgDeviceTypesView frame = new CfgDeviceTypesView();
                        frame.setVisible(true);
                    }
                }
            }
        });
		contentPane.add(btnRemoveDeviceType);

        JButton btnExit = new JButton("Voltar");
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.addActionListener(e -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(246, 289, 100, 40);
        contentPane.add(btnExit);

	}

	private static boolean checksIfExists (String txtName) {
		for (DeviceType deviceType : DeviceTypeController.getDeviceTypes()) {
			if (deviceType.getName().equals(txtName)) {
				return true;
			}
		}
		return false;
	}

}
