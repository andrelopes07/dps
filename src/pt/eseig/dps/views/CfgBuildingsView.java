package pt.eseig.dps.views;

import pt.eseig.dps.models.Building;
import pt.eseig.dps.controllers.BuildingController;
import pt.eseig.dps.controllers.RoomController;
import pt.eseig.dps.models.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

import java.util.ListIterator;

class CfgBuildingsView extends JFrame {

    private JTextField txtName;

	/**
	 * Create the frame.
	 */
	CfgBuildingsView() {

		BuildingController.loadBuildingsFromFile();
		RoomController.loadRoomsFromFile();

        setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setResizable(false);
		setTitle("Configurar Blocos");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 380);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(154, 241, 116, 20);
		txtName.setColumns(10);
        contentPane.add(txtName);
		
		DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Novo Bloco");

		JList<String> buildingsList = new JList<>(model);
		buildingsList.setFont(new Font("Calibri", Font.PLAIN, 14));
		buildingsList.setBorder(new LineBorder(new Color(255, 165, 0), 3));
        buildingsList.setSelectedIndex(0);
        for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
            Building building = BuildingController.getBuildings().get(i);
            String buildingName = building.getName();
            model.addElement(buildingName);
        }
		buildingsList.addListSelectionListener(arg0 -> {
            if (buildingsList.getSelectedValue().equals("Novo Bloco")) {
                txtName.setText("");
            } else {
                for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
                    Building building = BuildingController.getBuildings().get(i);
                    if (buildingsList.getSelectedValue().equals(building.getName())) {
                        txtName.setText(building.getName());
                    }
                }
            }
        });
		buildingsList.setBounds(110, 70, 160, 160);
		contentPane.add(buildingsList);
		
		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setBounds(110, 244, 46, 14);
		contentPane.add(lblName);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setBackground(new Color(255, 165, 0));
		btnSave.setFont(new Font("Impact", Font.PLAIN, 14));
		btnSave.addActionListener(e -> {
            if (buildingsList.getSelectedValue().equals("Novo Bloco")) {
                if (txtName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Campos vazios!");
                } else if (checksIfExists(txtName.getText())) {
                    JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                    txtName.setText("");
                } else {
                    Building building = new Building(txtName.getText());
                    BuildingController.getBuildings().add(building);
                    JOptionPane.showMessageDialog(null, "Bloco criado com sucesso!");
					BuildingController.saveBuildingsToFile();
                    dispose();
                    CfgBuildingsView frame = new CfgBuildingsView();
                    frame.setVisible(true);
                }
            } else {
                for (int i = 0; i < BuildingController.getBuildings().size(); i++) {
                    Building building = BuildingController.getBuildings().get(i);
                    if (buildingsList.getSelectedValue().equals(building.getName())){
                        if (txtName.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Campos vazios!");
                        } else if (checksIfExists(txtName.getText()) && !txtName.getText().equals(buildingsList.getSelectedValue())) {
                            JOptionPane.showMessageDialog(null, "Nome já existente! Escolha outro nome.");
                            txtName.setText("");
                        } else {
                            for (Room room : RoomController.getRooms()) {
                                if (room.getBuilding().equals(building.getName())) {
                                    room.setBuilding(txtName.getText());
                                }
                            }
                            building.setName(txtName.getText());
                            JOptionPane.showMessageDialog(null, "Bloco alterado com sucesso!");
                            RoomController.saveRoomToFile();
							BuildingController.saveBuildingsToFile();
                            dispose();
                            CfgBuildingsView frame = new CfgBuildingsView();
                            frame.setVisible(true);
                        }
                    }
                }
            }
        });
		btnSave.setBounds(25, 289, 100, 40);
		contentPane.add(btnSave);
		
        JButton btnRemoveBuilding = new JButton("Remover");
        btnRemoveBuilding.setForeground(new Color(255, 255, 255));
        btnRemoveBuilding.setBackground(new Color(255, 165, 0));
        btnRemoveBuilding.setFont(new Font("Impact", Font.PLAIN, 14));
        btnRemoveBuilding.addActionListener(e -> {
            String buildingNameString = buildingsList.getSelectedValue();
            ListIterator<Building> buildingListIterator = BuildingController.getBuildings().listIterator();
            while (buildingListIterator.hasNext()) {
                Building building = buildingListIterator.next();
                if (buildingNameString.equals(building.getName())) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Tem a certeza que deseja remover o bloco selecionado?","Aviso", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION ) {
                        RoomController.getRooms().removeIf(room -> room.getBuilding().equals(buildingNameString));
                        buildingListIterator.remove();
                        JOptionPane.showMessageDialog(null, "Bloco e respetivas salas removidas com sucesso!");
                        RoomController.saveRoomToFile();
                        BuildingController.saveBuildingsToFile();
                        dispose();
                        CfgBuildingsView frame = new CfgBuildingsView();
                        frame.setVisible(true);
                    }
                }
            }
        });
        btnRemoveBuilding.setBounds(135, 289, 100, 40);
        contentPane.add(btnRemoveBuilding);
			
        JLabel lblBuilding = new JLabel("Blocos:");
        lblBuilding.setHorizontalAlignment(SwingConstants.LEFT);
        lblBuilding.setFont(new Font("Calibri", Font.PLAIN, 18));
        lblBuilding.setBounds(110, 45, 100, 14);
        contentPane.add(lblBuilding);

        JButton btnExit = new JButton("Voltar");
        btnExit.setBackground(new Color(255, 165, 0));
        btnExit.setForeground(new Color(255, 255, 255));
        btnExit.setFont(new Font("Impact", Font.PLAIN, 14));
        btnExit.addActionListener(e -> {
            dispose();
            Home frame = new Home();
            frame.setVisible(true);
        });
        btnExit.setBounds(245, 289, 100, 40);
        contentPane.add(btnExit);
	}

    private static boolean checksIfExists (String txtName) {
        for (Building building : BuildingController.getBuildings()) {
            if (building.getName().equals(txtName)) {
                return true;
            }
        }
        return false;
    }

}
