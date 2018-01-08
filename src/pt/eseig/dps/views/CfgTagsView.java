package pt.eseig.dps.views;

import pt.eseig.dps.models.DeviceType;
import pt.eseig.dps.controllers.DeviceTypeController;
import pt.eseig.dps.models.Tag;
import pt.eseig.dps.controllers.TagController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.Objects;

class CfgTagsView extends JFrame {

    private JTextField txtId;
	private JTextField txtTagName;
    private JTextField txtDeviceName;

	/**
     *
	 * Create the frame.
	 */
    CfgTagsView() {

        TagController.loadTagDataFromFile();
        DeviceTypeController.loadDeviceTypesFromFile();

        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
        setResizable(false);
		setTitle("Configurar Etiquetas");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 380);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 604, 351);
		contentPane.add(panel);

		JLabel lblTag = new JLabel("Etiquetas:");
		lblTag.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblTag.setBounds(34, 35, 77, 16);
		panel.add(lblTag);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		txtId.setBounds(398, 62, 172, 20);
		panel.add(txtId);

		txtDeviceName = new JTextField();
		txtDeviceName.setColumns(10);
		txtDeviceName.setBounds(398, 93, 172, 20);
		panel.add(txtDeviceName);

		txtTagName = new JTextField();
		txtTagName.setColumns(10);
		txtTagName.setBounds(398, 124, 172, 20);
		panel.add(txtTagName);

		JLabel lblId = new JLabel("Número:");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblId.setBounds(329, 64, 59, 16);
		panel.add(lblId);

		JLabel lblDeviceName = new JLabel("Nome do Equipamento:");
		lblDeviceName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeviceName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDeviceName.setBounds(233, 95, 155, 16);
		panel.add(lblDeviceName);

		JLabel lblTagName = new JLabel("Nome da Etiqueta:");
		lblTagName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTagName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblTagName.setBounds(271, 126, 117, 16);
		panel.add(lblTagName);

		JLabel lblState = new JLabel("Estado:");
		lblState.setHorizontalAlignment(SwingConstants.RIGHT);
		lblState.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblState.setBounds(329, 197, 59, 16);
		panel.add(lblState);

		JRadioButton rdBtnActive = new JRadioButton("Ativada");
		rdBtnActive.setFont(new Font("Calibri", Font.PLAIN, 14));
		rdBtnActive.setBackground(new Color(255, 255, 255));
		rdBtnActive.setBounds(398, 194, 109, 23);
		panel.add(rdBtnActive);

		JRadioButton rdBtnInactive = new JRadioButton("Desativada");
		rdBtnInactive.setFont(new Font("Calibri", Font.PLAIN, 14));
		rdBtnInactive.setBackground(new Color(255, 255, 255));
		rdBtnInactive.setBounds(398, 220, 109, 23);
		panel.add(rdBtnInactive);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdBtnActive);
		group.add(rdBtnInactive);

		JLabel lblDeviceType = new JLabel("Família:");
		lblDeviceType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeviceType.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDeviceType.setBounds(329, 162, 59, 16);
		panel.add(lblDeviceType);

		JComboBox<String> cmbDeviceType = new JComboBox<>();
		cmbDeviceType.setBackground(new Color(255, 255, 255));
		cmbDeviceType.setBounds(398, 159, 172, 20);
		panel.add(cmbDeviceType);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> tagList = new JList<>(model);
		tagList.setFont(new Font("Calibri", Font.PLAIN, 14));
		tagList.setBorder(new LineBorder(new Color(255, 165, 0), 3));
        tagList.setBounds(34, 62, 195, 257);
		for (int i = 0; i < DeviceTypeController.getDeviceTypes().size(); i++) {
			DeviceType deviceType = DeviceTypeController.getDeviceTypes().get(i);
			String deviceTypeName = deviceType.getName();
			cmbDeviceType.addItem(deviceTypeName);
		}
		tagList.addListSelectionListener(arg0 -> {
            if (tagList.getSelectedValue().equals("Nova Etiqueta")) {
                txtId.setEditable(false);
                txtTagName.setText("");
                txtDeviceName.setText("");
                String currentId = String.format("%04d", TagController.getTags().size() + 1);
                txtId.setText(currentId);
                rdBtnActive.setSelected(true);
            } else {
                for (int i = 0; i < TagController.getTags().size(); i++) {
                    Tag tag = TagController.getTags().get(i);
                    if (tagList.getSelectedValue().equals(tag.getName())) {
                        txtId.setEditable(false);
                        txtTagName.setText(tag.getName());
                        txtDeviceName.setText(tag.getDeviceName());
                        cmbDeviceType.setSelectedItem(tag.getDeviceType());
                        txtId.setText(tag.getId());
                        if(tag.getState().equals("Ativo")) {
                            rdBtnActive.setSelected(true);
                        } else {
                            rdBtnInactive.setSelected(true);
                        }
                    }
                }
            }
        });
        model.addElement("Nova Etiqueta");
        tagList.setSelectedIndex(0);
        for (Tag tag : TagController.getTags()) {
            String tagName = tag.getName();
            model.addElement(tagName);
        }
		panel.add(tagList);

        JButton btnSave = new JButton("Guardar");
        btnSave.setBounds(360, 279, 100, 40);
        btnSave.setBackground(new Color(255, 165, 0));
        btnSave.setForeground(new Color(255, 255, 255));
        btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
        btnSave.addActionListener(e -> {
            if (tagList.getSelectedValue().equals("Nova Etiqueta")) {
                String state;
                if (rdBtnActive.isSelected()) {
                    state = "Ativo";
                } else {
                    state = "Inativo";
                }
                if (txtId.getText().equals("") || txtTagName.getText().equals("") || txtDeviceName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campos vazios!");
                } else if (checksIfExists(txtTagName.getText())) {
                    JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                    txtTagName.setText("");
                } else {
                    Tag tag = new Tag(
                            txtId.getText(),
                            txtTagName.getText(),
                            txtDeviceName.getText(),
                            Objects.requireNonNull(cmbDeviceType.getSelectedItem()).toString(),
                            state
                    );
                    TagController.getTags().add(tag);
                    JOptionPane.showMessageDialog(null, "Etiqueta criada com sucesso!");
                    TagController.saveTagDataToFile();
                    dispose();
                    CfgTagsView frame = new CfgTagsView();
                    frame.setVisible(true);
                } } else {
                for (int i = 0; i < TagController.getTags().size(); i++) {
                    Tag tag = TagController.getTags().get(i);
                    if (tagList.getSelectedValue().equals(tag.getName())) {
                        if (txtId.getText().equals("") || txtDeviceName.getText().equals("") || txtTagName.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Campos vazios!");
                        } else if (checksIfExists(txtTagName.getText()) && !txtTagName.getText().equals(tagList.getSelectedValue())) {
                            JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                            txtTagName.setText("");
                        } else {
                            tag.setName(txtTagName.getText());
                            tag.setDeviceType(Objects.requireNonNull(cmbDeviceType.getSelectedItem()).toString());
                            tag.setDeviceName(txtDeviceName.getText());
                            tag.setId(txtId.getText());
                            if (rdBtnActive.isSelected()) {
                                tag.setState("Ativo");
                            }
                            if (rdBtnInactive.isSelected()) {
                                tag.setState("Inativo");
                            }
                            JOptionPane.showMessageDialog(null, "Etiqueta editada com sucesso!");
                            TagController.saveTagDataToFile();
                            dispose();
                            CfgTagsView frame = new CfgTagsView();
                            frame.setVisible(true);
                        }
                    }

                }
            }
        });
        panel.add(btnSave);

        JButton btnExit = new JButton("Voltar");
        btnExit.setBounds(470, 279, 100, 40);
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.addActionListener(arg0 -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        panel.add(btnExit);

	}

    private static boolean checksIfExists (String txtName) {
        for (Tag tag : TagController.getTags()) {
            if (tag.getName().equals(txtName)) {
                return true;
            }
        }
        return false;
    }

}
