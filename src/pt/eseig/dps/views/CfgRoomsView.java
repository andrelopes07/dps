package pt.eseig.dps.views;

import pt.eseig.dps.models.Building;
import pt.eseig.dps.controllers.BuildingController;
import pt.eseig.dps.models.Room;
import pt.eseig.dps.controllers.RoomController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

class CfgRoomsView extends JFrame {

    private JTextField txtName;

	/**
	 * Create the frame.
	 */
	CfgRoomsView() {

		RoomController.loadRoomsFromFile();
		BuildingController.loadBuildingsFromFile();

        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Configurar Salas");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 380);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> roomsList = new JList<>(model);
		roomsList.setFont(new Font("Calibri", Font.PLAIN, 14));
		roomsList.setBorder(new LineBorder(new Color(255, 165, 0), 3));
        roomsList.setBounds(136, 55, 140, 140);
        contentPane.add(roomsList);

		JComboBox<String> cmbBoxBuildings = new JComboBox<>();
		cmbBoxBuildings.setBackground(new Color(255, 255, 255));
		cmbBoxBuildings.addActionListener(e -> {
            String selectedBuilding = Objects.requireNonNull(cmbBoxBuildings.getSelectedItem()).toString();
            for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
                model.clear();
                model.addElement("Nova Sala");
                roomsList.setSelectedIndex(0);
                for(int j = 0; j < RoomController.getRooms().size(); j++) {
                    Room room = RoomController.getRooms().get(j);
                    if (selectedBuilding.equals(room.getBuilding())) {
                        model.addElement(room.getName());
                    }
                }
            }
        });
        for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
            Building building = BuildingController.getBuildings().get(i);
            String buildingName = building.getName();
            cmbBoxBuildings.addItem(buildingName);
        }
		cmbBoxBuildings.setBounds(46, 56, 70, 20);
		contentPane.add(cmbBoxBuildings);

        txtName = new JTextField();
        txtName.setBounds(136, 221, 140, 20);
        contentPane.add(txtName);
        txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblName.setBounds(86, 224, 46, 14);
		contentPane.add(lblName);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setBackground(new Color(255, 165, 0));
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
		btnSave.addActionListener(arg0 -> {
            if (roomsList.getSelectedValue().equals("Nova Sala")) {
                if (txtName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campos vazios!");
                } else if (checksIfExists(txtName.getText())) {
                    JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                    txtName.setText("");
                } else {
                    Room room = new Room(txtName.getText(), Objects.requireNonNull(cmbBoxBuildings.getSelectedItem()).toString());
                    RoomController.getRooms().add(room);
                    JOptionPane.showMessageDialog(null, "Sala criada com sucesso!");
					RoomController.saveRoomToFile();
					dispose();
					CfgRoomsView frame = new CfgRoomsView();
					frame.setVisible(true);
                }
            } else {
                for (int i = 0; i < RoomController.getRooms().size(); i++) {
                    Room room = RoomController.getRooms().get(i);
                    if (roomsList.getSelectedValue().equals(room.getName())) {
                        if (txtName.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Campos vazios!");
                        } else if (checksIfExists(txtName.getText()) && !txtName.getText().equals(roomsList.getSelectedValue())) {
                            JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                            txtName.setText("");
                        } else {
							room.setBuilding(Objects.requireNonNull(cmbBoxBuildings.getSelectedItem()).toString());
							room.setName(txtName.getText());
							JOptionPane.showMessageDialog(null, "Sala alterada com sucesso!");
							RoomController.saveRoomToFile();
							dispose();
							CfgRoomsView frame = new CfgRoomsView();
							frame.setVisible(true);
						}
                    }
                }
            }
        });
		btnSave.setBounds(26, 279, 100, 40);
		contentPane.add(btnSave);
		
		JButton btnRemoveRoom = new JButton("Remover");
		btnRemoveRoom.setBackground(new Color(255, 165, 0));
		btnRemoveRoom.setForeground(new Color(255, 255, 255));
		btnRemoveRoom.setFont(new Font("Impact", Font.PLAIN, 14));
		btnRemoveRoom.addActionListener(arg0 -> {
            String selectedRoomName = roomsList.getSelectedValue();
            for (int i = 0; i < RoomController.getRooms().size(); i++) {
                Room room = RoomController.getRooms().get(i);
                if (selectedRoomName.equals(room.getName())) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que deseja remover a sala selecionado?","Aviso", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION ) {
                        RoomController.getRooms().remove(i);
                        JOptionPane.showMessageDialog(null, "Sala removida com sucesso!");
                        RoomController.saveRoomToFile();
                        dispose();
                        CfgRoomsView frame = new CfgRoomsView();
                        frame.setVisible(true);
                    }
                }
            }
        });
		btnRemoveRoom.setBounds(136, 279, 100, 40);
		contentPane.add(btnRemoveRoom);
			
		JLabel lblBuildings = new JLabel("Blocos:");
		lblBuildings.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblBuildings.setBounds(46, 31, 46, 14);
		contentPane.add(lblBuildings);
		
		JLabel lblRooms = new JLabel("Salas:");
		lblRooms.setHorizontalAlignment(SwingConstants.LEFT);
		lblRooms.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblRooms.setBounds(136, 31, 46, 14);
		contentPane.add(lblRooms);

        JButton btnExit = new JButton("Voltar");
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.addActionListener(e -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(246, 279, 100, 40);
        contentPane.add(btnExit);

	}

    private static boolean checksIfExists (String txtName) {
        for (Room room : RoomController.getRooms()) {
            if (room.getName().equals(txtName)) {
                return true;
            }
        }
        return false;
    }

}
