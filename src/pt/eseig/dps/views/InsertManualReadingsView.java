package pt.eseig.dps.views;

import pt.eseig.dps.controllers.AntennaController;
import pt.eseig.dps.controllers.ReadingController;
import pt.eseig.dps.controllers.TagController;
import pt.eseig.dps.models.Antenna;
import pt.eseig.dps.models.Tag;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

class InsertManualReadingsView extends JFrame {

    /**
	 * Create the frame.
	 */
    InsertManualReadingsView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Adiconar leitura manualmente");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 290);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

        JLabel lblDate = new JLabel("Data:");
        lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDate.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblDate.setBounds(44, 25, 46, 14);
        contentPane.add(lblDate);

		JComboBox<String> cmbDay = new JComboBox<>();
		cmbDay.setBackground(new Color(255, 255, 255));
		cmbDay.setBounds(100, 22, 45, 20);
		contentPane.add(cmbDay);
		
		JComboBox<String> cmbMonth = new JComboBox<>();
		cmbMonth.setBackground(new Color(255, 255, 255));
		cmbMonth.addActionListener(arg0 -> {
		    String month = Objects.requireNonNull(cmbMonth.getSelectedItem()).toString();
            if (cmbMonth.getSelectedItem().toString().equals("02")) {
                cmbDay.removeAllItems();
                for (int i = 1; i <= 28; i++) {
                    cmbDay.addItem(String.format("%02d", i));
                }
            } else if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
                cmbDay.removeAllItems();
                for (int i = 1; i <= 30; i++) {
                    cmbDay.addItem(String.format("%02d", i));
                }
            } else {
                cmbDay.removeAllItems();
                for (int i = 1; i <= 31; i++) {
                    cmbDay.addItem(String.format("%02d", i));
                }
            }
        });
		for (int i = 1; i <= 12; i++) {
			cmbMonth.addItem(String.format("%02d", i));
		}
		cmbMonth.setBounds(155, 22, 45, 20);
		contentPane.add(cmbMonth);
		
		JComboBox<String> cmbYear = new JComboBox<>();
		cmbYear.setBackground(new Color(255, 255, 255));
		for (int i = 2014; i <= 2018; i++) {
			cmbYear.addItem(String.format("%04d", i));
		}
		cmbYear.setBounds(210, 22, 65, 20);
		contentPane.add(cmbYear);

        JLabel lblHour = new JLabel("Hora:");
        lblHour.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHour.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblHour.setBounds(44, 56, 46, 14);
        contentPane.add(lblHour);
		
		JComboBox<String> cmbHours = new JComboBox<>();
		cmbHours.setBackground(new Color(255, 255, 255));
		for (Integer i = 0; i < 24; i++) {
			cmbHours.addItem(String.format("%02d", i));
		}
		cmbHours.setBounds(100, 53, 46, 20);
		contentPane.add(cmbHours);
		
		JComboBox<String> cmbMinutes = new JComboBox<>();
		cmbMinutes.setBackground(new Color(255, 255, 255));
		for (int i = 0; i <= 59; i++) {
			cmbMinutes.addItem(String.format("%02d", i));
		}
		cmbMinutes.setBounds(156, 53, 47, 20);
		contentPane.add(cmbMinutes);
		
		JComboBox<String> cmbSeconds = new JComboBox<>();
		cmbSeconds.setBackground(new Color(255, 255, 255));
		for (int i = 0; i <= 59; i++) {
			cmbSeconds.addItem(String.format("%02d", i));
		}
		cmbSeconds.setBounds(213, 53, 47, 20);
		contentPane.add(cmbSeconds);
		
		JLabel lblAntenna = new JLabel("Antena:");
		lblAntenna.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAntenna.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblAntenna.setBounds(44, 102, 46, 14);
		contentPane.add(lblAntenna);

        JComboBox<String> cmbAntenna = new JComboBox<>();
        cmbAntenna.setBackground(new Color(255, 255, 255));
        for (Antenna antenna : AntennaController.getAntennas()) {
            String antennaName = antenna.getName();
            cmbAntenna.addItem(antennaName);
        }
        cmbAntenna.setBounds(100, 99, 160, 20);
        contentPane.add(cmbAntenna);
		
		JLabel lblTag = new JLabel("Etiqueta:");
		lblTag.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTag.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblTag.setBounds(26, 134, 64, 14);
		contentPane.add(lblTag);

        JComboBox<String> cmbTag = new JComboBox<>();
        cmbTag.setBackground(new Color(255, 255, 255));
		for (Tag tag : TagController.getTags()) {
			String tagName = tag.getName();
			cmbTag.addItem(tagName);
		}
        cmbTag.setBounds(100, 130, 160, 20);
        contentPane.add(cmbTag);

        JButton btnSave = new JButton("Registar");
        btnSave.setForeground(new Color(255, 255, 255));
        btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
        btnSave.setBackground(new Color(255, 165, 0));
        btnSave.addActionListener(arg0 -> {
            String selectedTagId = "";
            String selectedAntennaId = "";
            for(Tag tag : TagController.getTags()) {
                if (tag.getName().equals(cmbTag.getSelectedItem())) {
                    selectedTagId = tag.getId();
                }
            }
            for(Antenna antenna : AntennaController.getAntennas()) {
                if (antenna.getName().equals(cmbAntenna.getSelectedItem())) {
                    selectedAntennaId = antenna.getId();
                }
            }
            String readingString =
                    cmbDay.getSelectedItem()     + "-" +
                    cmbMonth.getSelectedItem()   + "-" +
                    cmbYear.getSelectedItem()    + "#" +
                    cmbHours.getSelectedItem()   + ":" +
                    cmbMinutes.getSelectedItem() + ":" +
                    cmbSeconds.getSelectedItem() + "==" +
                    selectedAntennaId            + "==" +
                    selectedTagId;
            ReadingController.saveManualReadingToFile(readingString);
            JOptionPane.showMessageDialog(null, "Leitura registada com sucesso!");
            dispose();
            InsertManualReadingsView frame = new InsertManualReadingsView();
            frame.setVisible(true);
        });
        btnSave.setBounds(61, 184, 101, 40);
        contentPane.add(btnSave);

        JButton btnExit = new JButton("Voltar");
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.addActionListener(arg0 -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(172, 184, 101, 40);
        contentPane.add(btnExit);

	}

}
