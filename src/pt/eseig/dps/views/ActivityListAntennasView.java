package pt.eseig.dps.views;

import pt.eseig.dps.controllers.*;
import pt.eseig.dps.models.Antenna;
import pt.eseig.dps.models.Reading;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

class ActivityListAntennasView extends JFrame {

	/**
	 * Create the frame.
	 */
    ActivityListAntennasView() {

		TagController.loadTagDataFromFile();
		RoomController.loadRoomsFromFile();
		BuildingController.loadBuildingsFromFile();
		AntennaController.loadAntennaDataFromFile();
		ReadingController.loadRegisterDataFromFile();

		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		setResizable(false);
		setTitle("Listar Ocorrências Antenas");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 380);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(26, 28, 562, 247);
		contentPane.add(scrollPane);

		JTable listTable = new JTable();
		scrollPane.setViewportView(listTable);

		String[] columns = { "NOME", "DATA ÚLTIMA LEITURA", "HORA ÚLTIMA LEITURA", "BLOCO / SALA"};

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

            Integer daysChosen;
            String dialogInput = JOptionPane.showInputDialog("Número de dias sem reportar leituras", 5 );
            if (dialogInput.equals("")) {
                daysChosen = 0;
            } else {
                daysChosen = Integer.parseInt(dialogInput);
            }

            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime currentDateMinusDaysChosen = currentDate.minusDays(daysChosen);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");

            currentDate.format(formatter);
            currentDateMinusDaysChosen.format(formatter);

			ReadingController.getReadings().sort(Comparator.comparing(Reading::getDateTime).reversed());

            ArrayList<Antenna> tempAntennas = new ArrayList<>();

            for (Reading reading : ReadingController.getReadings()) {

                String dateTimeString = dateFormat.format(reading.getDateTime());
                String[] timeFields = dateTimeString.split("#");
                String dateToCompare = timeFields[0] + " " + timeFields[1];

                LocalDateTime dateTime = LocalDateTime.parse(dateToCompare, formatter);

                for (Antenna antenna : AntennaController.getAntennas()) {
                    if (!tempAntennas.contains(antenna) && antenna.getState().equals("Ativo") && antenna.getId().equals(reading.getAntennaId())) {
                        tempAntennas.add(antenna);

                        if (!(dateTime.isAfter(currentDateMinusDaysChosen)) && dateTime.isBefore(currentDate)) {
                            Object[] data = {
                                    antenna.getName(),
                                    timeFields[0],
                                    timeFields[1],
                                    antenna.getBuilding() + antenna.getRoom()
                            };
                            tableModel.addRow(data);
                        }
                    }
                }
            }
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null,"Todas as antenas reportaram leituras no intervalo indicado! ");
            }
        });
		btnList.setBounds(26, 279, 120, 23);
		contentPane.add(btnList);

		JButton btnExit = new JButton("Voltar");
		btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
		btnExit.setForeground(new Color(255, 255, 255));
		btnExit.setBackground(new Color(255, 165, 0));
		btnExit.addActionListener(arg0 -> {
                dispose();
                Home frame = new Home();
                frame.setVisible(true);
            });
		btnExit.setBounds(489, 286, 99, 39);
		contentPane.add(btnExit);

	}
	
}
