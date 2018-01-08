package pt.eseig.dps.views;

import pt.eseig.dps.controllers.AntennaController;
import pt.eseig.dps.controllers.BuildingController;
import pt.eseig.dps.controllers.RoomController;
import pt.eseig.dps.models.Antenna;
import pt.eseig.dps.models.Building;
import pt.eseig.dps.models.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.util.Objects;

class CgfAntennasView extends JFrame {

    private JTextField txtId;
	private JTextField txtName;

	/**
	 * Create the frame.
	 */
    CgfAntennasView() {

        AntennaController.loadAntennaDataFromFile();
        BuildingController.loadBuildingsFromFile();
        RoomController.loadRoomsFromFile();

		setBackground(new Color(255, 255, 255));
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setTitle("Configurar Antenas");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 380);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAntenna = new JLabel("Antenas:");
		lblAntenna.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblAntenna.setBounds(34, 36, 69, 16);
		contentPane.add(lblAntenna);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(338, 62, 172, 20);
		txtId.setColumns(10);
        contentPane.add(txtId);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(338, 93, 172, 20);
		contentPane.add(txtName);

		JLabel lblBuilding = new JLabel("Bloco:");
		lblBuilding.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBuilding.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblBuilding.setBounds(282, 130, 46, 14);
		contentPane.add(lblBuilding);

		JLabel lblId = new JLabel("Número:");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblId.setBounds(269, 64, 59, 16);
		contentPane.add(lblId);

		JLabel lblName = new JLabel("Nome:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblName.setBounds(288, 95, 40, 16);
		contentPane.add(lblName);

		JLabel lblRoom = new JLabel("Sala:");
		lblRoom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRoom.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblRoom.setBounds(269, 155, 59, 16);
		contentPane.add(lblRoom);

		JLabel lblState = new JLabel("Estado:");
		lblState.setHorizontalAlignment(SwingConstants.RIGHT);
		lblState.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblState.setBounds(269, 182, 59, 16);
		contentPane.add(lblState);

		JRadioButton rdBtnActive = new JRadioButton("Ativada");
		rdBtnActive.setBackground(new Color(255, 255, 255));
		rdBtnActive.setFont(new Font("Calibri", Font.PLAIN, 14));
		rdBtnActive.setBounds(338, 179, 109, 23);
		contentPane.add(rdBtnActive);

		JRadioButton rdBtnInactive = new JRadioButton("Desativada");
		rdBtnInactive.setBackground(new Color(255, 255, 255));
		rdBtnInactive.setFont(new Font("Calibri", Font.PLAIN, 14));
		rdBtnInactive.setBounds(338, 204, 109, 23);
		contentPane.add(rdBtnInactive);

		ButtonGroup group = new ButtonGroup();
		group.add(rdBtnActive);
        group.add(rdBtnInactive);

		JComboBox<String> roomList = new JComboBox<>();
		roomList.setBackground(new Color(255, 255, 255));
		roomList.setBounds(338, 153, 83, 20);
		contentPane.add(roomList);

		JComboBox<String> buildingList = new JComboBox<>();
		buildingList.setBackground(new Color(255, 255, 255));
		buildingList.addItemListener(arg0 -> {
            roomList.removeAllItems();
            for (int j = 0; j < RoomController.getRooms().size(); j++) {
                Room room = RoomController.getRooms().get(j);
                if(room.getBuilding().equals(Objects.requireNonNull(buildingList.getSelectedItem()).toString())) {
                    roomList.addItem(room.getName());
                }
            }
        });
        for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
            Building building = BuildingController.getBuildings().get(i);
            String buildingName = building.getName();
            buildingList.addItem(buildingName);
        }
        buildingList.setBounds(338, 124, 83, 20);
		contentPane.add(buildingList);

		DefaultListModel<String> model = new DefaultListModel<>();

		JList<String> antennasList = new JList<>(model);
		antennasList.setFont(new Font("Calibri", Font.PLAIN, 16));
		antennasList.setBorder(new LineBorder(new Color(255, 165, 0), 3));
		antennasList.setBackground(new Color(255, 255, 255));
		antennasList.addListSelectionListener(arg0 -> {
            if (antennasList.getSelectedValue().equals("Nova Antena")) {
                txtId.setEditable(false);
                txtName.setText("");
                String id = String.format("%03d", AntennaController.getAntennas().size() + 1);
                txtId.setText(id);
                rdBtnActive.setSelected(true);
            } else {
                txtId.setEditable(false);
                for (int i = 0; i < AntennaController.getAntennas().size(); i++) {
                    Antenna antenna = AntennaController.getAntennas().get(i);
                    if (antennasList.getSelectedValue().equals(antenna.getName())) {
                        txtName.setText(antenna.getName());
                        txtId.setText(antenna.getId());
                        if(antenna.getState().equals("Ativo")) {
                            rdBtnActive.setSelected(true);
                        }
                        if(antenna.getState().equals("Inativo")) {
                            rdBtnInactive.setSelected(true);
                        }
                        buildingList.setSelectedItem(antenna.getBuilding());
                        roomList.removeAllItems();
                        for (int j = 0; j < RoomController.getRooms().size(); j++) {
                            Room room = RoomController.getRooms().get(j);
                            if(room.getBuilding().equals(Objects.requireNonNull(buildingList.getSelectedItem()).toString())) {
                                roomList.addItem(room.getName());
                            }
                            roomList.setSelectedItem(antenna.getRoom());
                        }
                    }
                }
            }
        });
		antennasList.setBounds(34, 62, 195, 257);
		contentPane.add(antennasList);

		model.addElement("Nova Antena");
		antennasList.setSelectedIndex(0);

		for (int i = 0; i < AntennaController.getAntennas().size(); i++) {
			Antenna antenna = AntennaController.getAntennas().get(i);
			String antennaName = antenna.getName();
			model.addElement(antennaName);
		}
		JButton btnSave = new JButton("Guardar");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setBackground(new Color(255, 165, 0));
		btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
		btnSave.addActionListener(e -> {
            if (antennasList.getSelectedValue().equals("Nova Antena")) {
                String activity = "";
                if (rdBtnActive.isSelected()) {
                    activity = "Ativo";
                }
                if (rdBtnInactive.isSelected()) {
                    activity = "Inativo";
                }
                if (txtId.getText().equals("") || txtName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campos vazios!");
                } else if (txtName.getText().length() > 10) {
                    JOptionPane.showMessageDialog(null, "Nome demasiado comprido!");
                } else if (checksIfExists(txtName.getText())) {
                    JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                    txtName.setText("");
                } else {
                    Antenna antenna = new Antenna(
                            txtId.getText(),
                            txtName.getText(),
                            Objects.requireNonNull(buildingList.getSelectedItem()).toString(),
                            Objects.requireNonNull(roomList.getSelectedItem()).toString(),
                            activity
                    );
                    AntennaController.getAntennas().add(antenna);
                    JOptionPane.showMessageDialog(null, "Antena criada com sucesso!");
                    AntennaController.saveAntennaDataToFile();
                    dispose();
                    CgfAntennasView frame = new CgfAntennasView();
                    frame.setVisible(true);
                }
            } else {
                for (int i = 0; i < AntennaController.getAntennas().size(); i++) {
                    Antenna antenna = AntennaController.getAntennas().get(i);
                    if (antennasList.getSelectedValue().equals(antenna.getName())) {
                        if (txtId.getText().equals("") || txtName.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Campos vazios!");
                        } else if (txtName.getText().length() > 10) {
                            JOptionPane.showMessageDialog(null, "Nome demasiado comprido!");
                        } else if (checksIfExists(txtName.getText()) && !txtName.getText().equals(antennasList.getSelectedValue())) {
                            JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                            txtName.setText("");
                        } else {
                            antenna.setName(txtName.getText());
                            antenna.setBuilding(Objects.requireNonNull(buildingList.getSelectedItem()).toString());
                            antenna.setRoom(Objects.requireNonNull(roomList.getSelectedItem()).toString());
                            String activity = "";
                            if (rdBtnActive.isSelected()) {
                                activity = "Ativo";
                            }
                            if (rdBtnInactive.isSelected()) {
                                activity = "Inativo";
                            }
                            antenna.setState(activity);
                            JOptionPane.showMessageDialog(null, "Antena editada com sucesso!");
                            AntennaController.saveAntennaDataToFile();
                            dispose();
                            CgfAntennasView frame = new CgfAntennasView();
                            frame.setVisible(true);
                        }
                    }
                }
            }
        });
		btnSave.setBounds(300, 279, 100, 40);
		contentPane.add(btnSave);

        JButton btnExit = new JButton("Voltar");
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.addActionListener(e -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(410, 279, 100, 40);
        contentPane.add(btnExit);

	}

    private static boolean checksIfExists (String txtName) {
        for (Antenna antenna : AntennaController.getAntennas()) {
            if (antenna.getName().equals(txtName)) {
                return true;
            }
        }
        return false;
    }

}
