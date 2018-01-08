package pt.eseig.dps.views;

import com.sun.org.apache.regexp.internal.RE;
import pt.eseig.dps.controllers.AntennaController;
import pt.eseig.dps.controllers.DeviceTypeController;
import pt.eseig.dps.controllers.ReadingController;
import pt.eseig.dps.controllers.TagController;
import pt.eseig.dps.models.Antenna;
import pt.eseig.dps.models.DeviceType;
import pt.eseig.dps.models.Reading;
import pt.eseig.dps.models.Tag;

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

class TrackDeviceTypesView extends JFrame {

    /**
	 * Create the frame.
	 */
    TrackDeviceTypesView() {

        ReadingController.loadRegisterDataFromFile();

		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Localizar Famílias de Equipamentos");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 380);
		setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
        setContentPane(contentPane);

		JLabel lblDeviceTypes = new JLabel("Famílias:");
		lblDeviceTypes.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblDeviceTypes.setBounds(24, 35, 85, 16);
		contentPane.add(lblDeviceTypes);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(24, 62, 565, 206);
		contentPane.add(scrollPane);

        JTable listTable = new JTable();
		scrollPane.setViewportView(listTable);
		
		String[] columns = { "NOME", "DATA", "HORA", "BLOCO / SALA"};

		DefaultTableModel tableModel = new DefaultTableModel(null, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		listTable.setModel(tableModel);

		JComboBox<String> cmbDeviceTypes = new JComboBox<>();
		cmbDeviceTypes.setBackground(new Color(255, 255, 255));
		cmbDeviceTypes.setToolTipText("Selecione uma família existente");
		for (int i = 0; i < DeviceTypeController.getDeviceTypes().size(); i++) {
			DeviceType deviceType = DeviceTypeController.getDeviceTypes().get(i);
			String deviceTypeName = deviceType.getName();
			cmbDeviceTypes.addItem(deviceTypeName);
		}
		cmbDeviceTypes.setBounds(98, 34, 147, 20);
		contentPane.add(cmbDeviceTypes);

		JButton btnList = new JButton("Listar");
		btnList.setForeground(new Color(255, 255, 255));
		btnList.setBackground(new Color(255, 165, 0));
		btnList.setFont(new Font("Impact", Font.PLAIN, 14));
		btnList.addActionListener(arg0 -> {
            tableModel.setRowCount(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");
            String selectedDeviceType = Objects.requireNonNull(cmbDeviceTypes.getSelectedItem()).toString();

			ArrayList<String> tempTags = new ArrayList<>();

            for (Tag tag : TagController.getTags()) {
                if (tag.getState().equals("Ativo") && selectedDeviceType.equals(tag.getDeviceType())) {

                    ReadingController.getReadings().sort(Comparator.comparing(Reading::getDateTime).reversed());

                    for (Reading reading : ReadingController.getReadings()) {
                        if (tag.getId().equals(reading.getTagId())) {

                            for (Antenna antenna : AntennaController.getAntennas()) {

                                String dateTime = dateFormat.format(reading.getDateTime());
                                String[] timeFields = dateTime.split("#");

                                if (!tempTags.contains(reading.getTagId()) && antenna.getId().equals(reading.getAntennaId())) {
                                    Object[] data = {
                                            tag.getDeviceName(),
                                            timeFields[0],
                                            timeFields[1],
                                            (antenna.getBuilding() + antenna.getRoom())};
                                    tableModel.addRow(data);
                                    tempTags.add(reading.getTagId());
                                }
                            }
                        }
                    }
                }
            }
			if (tableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null,"Não foram encontrados equipamentos desta família! Verifique o estado das antenas.");
			}
        });
		btnList.setBounds(24, 272, 120, 23);
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
		btnExit.setBounds(489, 279, 100, 40);
		contentPane.add(btnExit);
		
	}

}
