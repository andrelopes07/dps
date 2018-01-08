package pt.eseig.dps.views;

import pt.eseig.dps.controllers.AntennaController;
import pt.eseig.dps.controllers.ReadingController;
import pt.eseig.dps.controllers.TagController;
import pt.eseig.dps.models.Antenna;
import pt.eseig.dps.models.Reading;
import pt.eseig.dps.models.Tag;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

class TrackByDeviceNameView extends JFrame {

	/**
	 * Create the frame.
	 */
    TrackByDeviceNameView() {

        ReadingController.loadRegisterDataFromFile();

		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Localizar Equipamentos por Nome");
		setBounds(100, 100, 620, 380);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDevice = new JLabel("Equipamento:");
		lblDevice.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDevice.setBounds(30, 25, 100, 16);
		contentPane.add(lblDevice);

        JLabel lblDays = new JLabel("Ultimas");
        lblDays.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblDays.setBounds(355, 25, 45, 16);
        contentPane.add(lblDays);

        JComboBox<Integer> cmbLocalsSpan = new JComboBox<>();
        cmbLocalsSpan.setBackground(new Color(255, 255, 255));
        for(int i = 0; i<=29; i++) {
            cmbLocalsSpan.addItem(i + 1);
        }
        cmbLocalsSpan.setBounds(405, 22, 40, 20);
        contentPane.add(cmbLocalsSpan);

        JLabel lblLocalsSpan = new JLabel("localizações a mostrar:");
        lblLocalsSpan.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLocalsSpan.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblLocalsSpan.setBounds(440, 25, 140, 16);
        contentPane.add(lblLocalsSpan);

		JComboBox<String> cmbDevices = new JComboBox<>();
		cmbDevices.setBackground(new Color(255, 255, 255));
		cmbDevices.setToolTipText("Selecione um equipamento a localizar");
		for (int i = 0; i < TagController.getTags().size(); i++) {
			Tag tag = TagController.getTags().get(i);
			String tagName = tag.getName();
			cmbDevices.addItem(tagName);
		}
		cmbDevices.setBounds(115, 22, 130, 20);
		contentPane.add(cmbDevices);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(25, 50, 564, 211);
		contentPane.add(scrollPane);

        JTable listTable = new JTable();
		scrollPane.setViewportView(listTable);

		String[] columns = { "NOME", "DATA", "HORA", "FAMÍLIA", "SALA" };

		DefaultTableModel tableModel = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		listTable.setModel(tableModel);

		JButton btnList = new JButton("Listar");
		btnList.setBackground(new Color(255, 165, 0));
		btnList.setForeground(new Color(255, 255, 255));
		btnList.setFont(new Font("Impact", Font.PLAIN, 14));
		btnList.addActionListener(e -> {
            tableModel.setRowCount(0);

            String selectedDevice = Objects.requireNonNull(cmbDevices.getSelectedItem()).toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");

            ArrayList<String> tempAntennas = new ArrayList<>();

            for (Tag tag : TagController.getTags()) {
                if (tag.getState().equals("Ativo") && selectedDevice.equals(tag.getName())) {

                    ReadingController.getReadings().sort(Comparator.comparing(Reading::getDateTime).reversed());

                    for (Reading reading : ReadingController.getReadings()) {
                        if (tableModel.getRowCount() < cmbLocalsSpan.getSelectedIndex() + 1 && tag.getId().equals(reading.getTagId())) {

                            for (Antenna antenna : AntennaController.getAntennas()) {

                                String dateTime = dateFormat.format(reading.getDateTime());
                                String[] timeFields = dateTime.split("#");

                                if (!tempAntennas.contains(reading.getAntennaId()) && reading.getAntennaId().equals(antenna.getId())) {
                                    Object[] deviceData = {
                                            tag.getDeviceName(),
                                            timeFields[0],
                                            timeFields[1],
                                            tag.getDeviceType(),
                                            (antenna.getBuilding() + antenna.getRoom())
                                    };
                                    tableModel.addRow(deviceData);
                                    tempAntennas.add(reading.getAntennaId());
                                }
                            }
                        }
                    }
                }
            }
			if (tableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null,"Não foram encontradas localizações para este equipamento! Verifique o estado das antenas.");
			}
        });
		btnList.setBounds(25, 276, 113, 23);
		contentPane.add(btnList);

		JButton btnExit = new JButton("Voltar");
		btnExit.setBackground(new Color(255, 165, 0));
		btnExit.setForeground(new Color(255, 255, 255));
		btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
		btnExit.addActionListener(arg0 -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
		btnExit.setBounds(488, 288, 102, 40);
		contentPane.add(btnExit);

	}

}
