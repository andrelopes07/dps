package pt.eseig.dps.views;


import pt.eseig.dps.controllers.*;
import pt.eseig.dps.models.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

class TrackRoomView extends JFrame {

	/**
	 * Create the frame.
	 */
    TrackRoomView() {

        ReadingController.loadRegisterDataFromFile();

		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Localizar Equipamentos por Sala");
		setBounds(100, 100, 550, 380);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
        setContentPane(contentPane);

		JLabel lblBuildings = new JLabel("Bloco:");
		lblBuildings.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblBuildings.setBounds(33, 35, 97, 16);
		contentPane.add(lblBuildings);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setForeground(new Color(0, 0, 0));
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(140, 35, 365, 226);
		contentPane.add(scrollPane);

		JTable listTable = new JTable();
		scrollPane.setViewportView(listTable);

		String[] columns = { "NOME", "DATA", "HORA", "FAMÍLIA"};

		DefaultTableModel tableModel = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}

		};

		listTable.setModel(tableModel);

        JLabel lblRoom = new JLabel("Room:");
        lblRoom.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblRoom.setBounds(33, 93, 97, 16);
        contentPane.add(lblRoom);

		JComboBox<String> cmbRooms = new JComboBox<>();
		cmbRooms.setBackground(new Color(255, 255, 255));
		cmbRooms.setToolTipText("Selecione uma sala.");
		cmbRooms.setBounds(30, 117, 82, 20);
		contentPane.add(cmbRooms);

		JComboBox<String> cmbBuildings = new JComboBox<>();
		cmbBuildings.setBackground(new Color(255, 255, 255));
		cmbBuildings.addActionListener(arg0 -> {
            cmbRooms.removeAllItems();
            String selectedBuilding = Objects.requireNonNull(cmbBuildings.getSelectedItem()).toString();
            for(int j = 0; j< RoomController.getRooms().size(); j++) {
                Room room = RoomController.getRooms().get(j);
                if (selectedBuilding.equals(room.getBuilding())) {
                    cmbRooms.addItem(room.getName());
                }
            }
        });
		cmbBuildings.setToolTipText("Selecione um Bloco.");
		for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
			Building building = BuildingController.getBuildings().get(i);
			String buildingName = building.getName();
			cmbBuildings.addItem(buildingName);
		}
		cmbBuildings.setBounds(30, 62, 82, 20);
		contentPane.add(cmbBuildings);

		JButton btnList = new JButton("Listar");
		btnList.setBackground(new Color(255, 165, 0));
		btnList.setForeground(new Color(255, 255, 255));
		btnList.setFont(new Font("Impact", Font.PLAIN, 14));
		btnList.addActionListener(e -> {
            tableModel.setRowCount(0);

            String selectedBuilding = Objects.requireNonNull(cmbBuildings.getSelectedItem()).toString();
            String selectedRoom = Objects.requireNonNull(cmbRooms.getSelectedItem()).toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");

            ArrayList<String> tempTags = new ArrayList<>();

            for (Antenna antenna : AntennaController.getAntennas()) {
                if (selectedBuilding.equals(antenna.getBuilding()) && selectedRoom.equals(antenna.getRoom())) {

                    ReadingController.getReadings().sort(Comparator.comparing(Reading::getDateTime).reversed());

                    for (Reading reading : ReadingController.getReadings()) {
                        if (reading.getAntennaId().equals(antenna.getId())) {
                            for (Tag tag : TagController.getTags()) {

                                String dateTime = dateFormat.format(reading.getDateTime());
                                String[] timeFields = dateTime.split("#");

                                if (!tempTags.contains(reading.getTagId()) && tag.getState().equals("Ativo") && reading.getTagId().equals(tag.getId())) {
                                    Object[] data = {
                                            tag.getDeviceName(),
                                            timeFields[0],
                                            timeFields[1],
                                            tag.getDeviceType()
                                    };
                                    tableModel.addRow(data);
                                    tempTags.add(reading.getTagId());
                                }
                            }
                        }
                    }
                }
            }
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null,"Não foram encontrados equipamentos nesta sala! Verifique se as antenas estão ativas.");
            }
        });
		btnList.setBounds(30, 172, 82, 40);
		contentPane.add(btnList);

        JButton btnExit = new JButton("Voltar");
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.addActionListener(arg0 -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(403, 282, 102, 40);
        contentPane.add(btnExit);

	}

}
