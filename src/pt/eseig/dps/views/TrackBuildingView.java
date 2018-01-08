package pt.eseig.dps.views;


import pt.eseig.dps.controllers.AntennaController;
import pt.eseig.dps.controllers.BuildingController;
import pt.eseig.dps.controllers.ReadingController;
import pt.eseig.dps.controllers.TagController;
import pt.eseig.dps.models.Antenna;
import pt.eseig.dps.models.Building;
import pt.eseig.dps.models.Reading;
import pt.eseig.dps.models.Tag;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

class TrackBuildingView extends JFrame {

    /**
	 * Create the frame.
	 */
    TrackBuildingView() {

        ReadingController.loadRegisterDataFromFile();

		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Localizar Equipamentos por Bloco");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 380);
		setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
        setContentPane(contentPane);
		
		JLabel lblBuilding = new JLabel("Bloco:");
		lblBuilding.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblBuilding.setBounds(30, 35, 97, 16);
		contentPane.add(lblBuilding);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(131, 35, 452, 234);
		contentPane.add(scrollPane);

        JTable resultsList = new JTable();
		scrollPane.setViewportView(resultsList);

		String[] columns = { "NOME", "DATA", "HORA", "FAMÍLIA", "SALA"};

		DefaultTableModel tableModel = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}

		};

		resultsList.setModel(tableModel);

		JComboBox<String> cmbBuildings = new JComboBox<>();
		cmbBuildings.setBackground(new Color(255, 255, 255));
		cmbBuildings.setToolTipText("Selecione um Bloco.");
		for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
			Building building = BuildingController.getBuildings().get(i);
			String buildingName = building.getName();
			cmbBuildings.addItem(buildingName);
		}
		cmbBuildings.setBounds(30, 62, 70, 20);
		contentPane.add(cmbBuildings);

		JButton btnList = new JButton("Listar");
		btnList.setBackground(new Color(255, 165, 0));
		btnList.setForeground(new Color(255, 255, 255));
		btnList.setFont(new Font("Impact", Font.PLAIN, 14));
		btnList.addActionListener(e -> {
            tableModel.setRowCount(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");
            String selectedBuilding = Objects.requireNonNull(cmbBuildings.getSelectedItem()).toString();

			ArrayList<String> tempTags = new ArrayList<>();

            for (Antenna antenna : AntennaController.getAntennas()) {
                if(selectedBuilding.equals(antenna.getBuilding())) {

                    ReadingController.getReadings().sort(Comparator.comparing(Reading::getDateTime).reversed());

                    for (Reading reading : ReadingController.getReadings()) {
                        if (antenna.getId().equals(reading.getAntennaId())) {

                            for (Tag tag : TagController.getTags()) {

                                String dateTime = dateFormat.format(reading.getDateTime());
                                String[] timeFields = dateTime.split("#");

                                if (!tempTags.contains(reading.getTagId()) && tag.getState().equals("Ativo") && reading.getTagId().equals(tag.getId())) {
                                    Object[] data = {
                                            tag.getDeviceName(),
                                            timeFields[0],
                                            timeFields[1],
                                            tag.getDeviceType(),
                                            antenna.getRoom()
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
                JOptionPane.showMessageDialog(null,"Não foram encontrados equipamentos neste bloco! Verifique se as antenas estão ativas.");
            }
        });
		btnList.setBounds(30, 93, 70, 40);
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
		btnExit.setBounds(473, 286, 110, 40);
		contentPane.add(btnExit);

	}

}
