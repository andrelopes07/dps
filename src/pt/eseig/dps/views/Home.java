package pt.eseig.dps.views;

import pt.eseig.dps.models.Reading;
import pt.eseig.dps.Simulator;
import pt.eseig.dps.controllers.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.ComponentOrientation;

public class Home extends JFrame {

    /**
	 * Create the frame.
	 */
	public Home() {

        BuildingController.loadBuildingsFromFile();
        RoomController.loadRoomsFromFile();
        DeviceTypeController.loadDeviceTypesFromFile();
        AntennaController.loadAntennaDataFromFile();
        TagController.loadTagDataFromFile();

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/logo.png"));
		setTitle("Device Positioning System");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 415);
		setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.BLACK);
		contentPane.setLayout(null);
        setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		menuBar.setBorderPainted(false);
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(new Color(255, 165, 0));
		menuBar.setBounds(0, 0, 614, 33);
		contentPane.add(menuBar);
		
		JMenu menuConfig = new JMenu("Configurar");
		menuConfig.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		menuConfig.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuConfig.setForeground(Color.WHITE);
		menuBar.add(menuConfig);
		
		JMenuItem menuItemAntennas = new JMenuItem("Antenas");
		menuItemAntennas.setBackground(Color.WHITE);
		menuItemAntennas.addActionListener(e -> {
            if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if(RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else {
				dispose();
				CgfAntennasView frame = new CgfAntennasView();
				frame.setVisible(true);
            }
        });
		menuConfig.add(menuItemAntennas);
		
		JMenuItem menuItemTags = new JMenuItem("Etiquetas");
		menuItemTags.setBackground(Color.WHITE);
		menuItemTags.addActionListener(arg0 -> {
            if (DeviceTypeController.getDeviceTypes().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma familia de equipamentos criada!");
            } else {
                dispose();
                CfgTagsView frame = new CfgTagsView();
                frame.setVisible(true);
            }
        });
		menuConfig.add(menuItemTags);
		
		JMenuItem menuItemUsers = new JMenuItem("Utilizadores");
		menuItemUsers.setBackground(Color.WHITE);
		menuItemUsers.addActionListener(arg0 -> {
            dispose();
            CfgUsersView frame1 = new CfgUsersView();
            frame1.setVisible(true);

        });
		menuConfig.add(menuItemUsers);
		
		JMenu menuBuildingsRooms = new JMenu("Salas / Blocos");
		menuBuildingsRooms.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		menuBuildingsRooms.setBorder(null);
		menuBuildingsRooms.setBackground(new Color(255, 255, 255));
		menuConfig.add(menuBuildingsRooms);
		
		JMenuItem menuItemRooms = new JMenuItem("Salas");
		menuItemRooms.setBackground(Color.WHITE);
		menuItemRooms.addActionListener(e -> {
			if (BuildingController.getBuildings().size() == 0) {
				JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
			} else {
				dispose();
				CfgRoomsView frame = new CfgRoomsView();
				frame.setVisible(true);
			}
        });
		menuBuildingsRooms.add(menuItemRooms);
		
		JMenuItem menuItemBuildings = new JMenuItem("Blocos");
		menuItemBuildings.setBackground(Color.WHITE);
		menuItemBuildings.addActionListener(e -> {
            dispose();
            CfgBuildingsView frame = new CfgBuildingsView();
            frame.setVisible(true);
        });
		menuBuildingsRooms.add(menuItemBuildings);
		
		JMenuItem menuItemDeviceTypes = new JMenuItem("Famílias");
		menuItemDeviceTypes.setBackground(Color.WHITE);
		menuItemDeviceTypes.addActionListener(arg0 -> {
            dispose();
            CfgDeviceTypesView frame = new CfgDeviceTypesView();
            frame.setVisible(true);
        });
		menuConfig.add(menuItemDeviceTypes);
		
		JSeparator separator = new JSeparator();
		menuConfig.add(separator);
		
		JMenuItem menuItemLogout = new JMenuItem("Terminar Sessão");
		menuItemLogout.setBackground(Color.WHITE);
		menuItemLogout.addActionListener(e -> {
            dispose();
            LoginView window = new LoginView();
            window.frmDpsDevice.setVisible(true);
        });
		menuConfig.add(menuItemLogout);
		
		JMenuItem menuItemExit = new JMenuItem("Sair");
		menuItemExit.setBackground(Color.WHITE);
		menuItemExit.addActionListener(arg0 -> System.exit(0));
		menuConfig.add(menuItemExit);
		
		JMenu menuTrack = new JMenu("Localizar");
		menuTrack.setForeground(Color.WHITE);
		menuTrack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.add(menuTrack);
		
		JMenu menuTrackDevices = new JMenu("Equipamentos");
		menuTrackDevices.setBackground(Color.WHITE);
		menuTrack.add(menuTrackDevices);

		JMenuItem menuItemTrackDevices = new JMenuItem("Por Nome");
		menuItemTrackDevices.setBackground(Color.WHITE);
		menuItemTrackDevices.addActionListener(e -> {
			if (AntennaController.getAntennas().size() == 0) {
				JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
			} else if (BuildingController.getBuildings().size() == 0) {
				JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
			} else if (RoomController.getRooms().size() == 0) {
				JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
			} else if (TagController.getTags().size() == 0) {
				JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma etiqueta criada!");
			} else {
				dispose();
				TrackByDeviceNameView frame = new TrackByDeviceNameView();
				frame.setVisible(true);
			}
		});
		menuTrackDevices.add(menuItemTrackDevices);
		
		JMenuItem menuItemTrackBuildings = new JMenuItem("Por Bloco");
		menuItemTrackBuildings.setBackground(Color.WHITE);
		menuItemTrackBuildings.addActionListener(e -> {
            if (AntennaController.getAntennas().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
            } else if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if (RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else if (TagController.getTags().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma etiqueta criada!");
            } else {
				dispose();
				TrackBuildingView frame = new TrackBuildingView();
				frame.setVisible(true);
            }
        });
		menuTrackDevices.add(menuItemTrackBuildings);

        JMenuItem menuItemTrackRooms = new JMenuItem("Por Sala");
        menuItemTrackRooms.setBackground(Color.WHITE);
        menuItemTrackRooms.addActionListener(arg0 -> {
            if (AntennaController.getAntennas().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
            } else if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if (RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else if (TagController.getTags().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma etiqueta criada!");
            } else {
                dispose();
                TrackRoomView frame = new TrackRoomView();
                frame.setVisible(true);
            }
        });
        menuTrackDevices.add(menuItemTrackRooms);
		
		JMenuItem menuItemTrackDeviceTypes = new JMenuItem("Familias");
		menuItemTrackDeviceTypes.setBackground(Color.WHITE);
		menuItemTrackDeviceTypes.addActionListener(arg0 -> {
            if (AntennaController.getAntennas().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
            } else if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if (RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else if (TagController.getTags().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma etiqueta criada!");
            } else {
                dispose();
                TrackDeviceTypesView frame = new TrackDeviceTypesView();
                frame.setVisible(true);
            }
        });
		menuTrack.add(menuItemTrackDeviceTypes);
		
		JMenu menuTroubleshoot = new JMenu("Alertas");
		menuTroubleshoot.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuTroubleshoot.setForeground(Color.WHITE);
		menuTroubleshoot.addActionListener(e -> {

        });
		menuBar.add(menuTroubleshoot);
		
		JMenuItem menuItemTroubleshootAntennas = new JMenuItem("Antenas");
		menuItemTroubleshootAntennas.setBackground(Color.WHITE);
		menuItemTroubleshootAntennas.addActionListener(e -> {
            if (AntennaController.getAntennas().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
            } else if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if (RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else {
                dispose();
                ActivityListAntennasView frame = new ActivityListAntennasView();
                frame.setVisible(true);
            }
        });
		menuTroubleshoot.add(menuItemTroubleshootAntennas);
		
		JMenuItem menuItemTroubleshootTags = new JMenuItem("Etiquetas");
		menuItemTroubleshootTags.setBackground(Color.WHITE);
		menuItemTroubleshootTags.addActionListener(e -> {
            if (AntennaController.getAntennas().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
            } else if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if (RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else {
                dispose();
                ActivityListTagsView frame = new ActivityListTagsView();
                frame.setVisible(true);
            }
        });
		menuTroubleshoot.add(menuItemTroubleshootTags);
		
		JMenu menuImport = new JMenu("Importar");
		menuImport.setForeground(Color.WHITE);
		menuImport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuImport.setBackground(Color.WHITE);
		menuBar.add(menuImport);
		
		JMenuItem menuItemImportReadings = new JMenuItem("Leituras");
		menuItemImportReadings.setBackground(Color.WHITE);
		menuItemImportReadings.addActionListener(arg0 -> {
            if (AntennaController.getAntennas().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma antena criada!");
            } else if (BuildingController.getBuildings().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos um bloco criado!");
            } else if (RoomController.getRooms().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma sala criada!");
            } else if (TagController.getTags().size() == 0) {
                JOptionPane.showMessageDialog(null, "Tem de ter pelo menos uma etiqueta criada!");
            } else {
                String entries = JOptionPane.showInputDialog(null, "Quantas leituras deseja importar?");
                if (!entries.equals("")) {
                    Simulator.generateFile(Integer.parseInt(entries));
                    ReadingController.loadRegisterDataFromFile();
                    JOptionPane.showMessageDialog(null, "Foram importadas " + entries + " leituras.");
                    for (Reading reading : ReadingController.getReadings()) {
                        System.out.println(reading.getDateTime() + " " + reading.getAntennaId() + " " + reading.getTagId());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor introduza um valor válido!");
                }

            }
        });
		menuImport.add(menuItemImportReadings);
		
		JMenuItem menuItemImportManualReading = new JMenuItem("Manualmente");
		menuItemImportManualReading.setBackground(Color.WHITE);
        menuItemImportManualReading.addActionListener(arg0 -> {
            dispose();
            InsertManualReadingsView frame = new InsertManualReadingsView();
            frame.setVisible(true);
        });
		menuImport.add(menuItemImportManualReading);
		
		JMenu menuAbout = new JMenu("Sobre");
		menuAbout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuAbout.setForeground(Color.WHITE);
		menuBar.add(menuAbout);

        JMenuItem menuItemAboutDPS = new JMenuItem("Sobre DPS");
        menuItemAboutDPS.setBackground(Color.WHITE);
        menuItemAboutDPS.addActionListener(arg0 -> {
            JOptionPane.showMessageDialog(null,"Device Positioning System v1.1.0 \nTrabalho realizado por André Lopes e Gonçalo Reais \nESEIG 2015");

        });
        menuAbout.add(menuItemAboutDPS);

		// SET VISIBLE ITEMS ACCORDING TO ACCESS LEVEL
		if (LoginView.getAccessLevel().equals("Gestor")) {
			menuImport.setVisible(false);
		}

		if (LoginView.getAccessLevel().equals("Operador")) {
			menuItemAntennas.setVisible(false);
			menuItemTags.setVisible(false);
			menuItemUsers.setVisible(false);
			menuItemDeviceTypes.setVisible(false);
			separator.setVisible(false);
			menuImport.setVisible(false);
			menuBuildingsRooms.setVisible(false);
		}
		
		JLabel lblUserName = new JLabel("Utilizador:");
		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserName.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblUserName.setBounds(84, 287, 70, 14);
		contentPane.add(lblUserName);

        JLabel lblLoggedUserName = new JLabel(LoginView.getName());
        lblLoggedUserName.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblLoggedUserName.setBounds(164, 287, 210, 14);
        contentPane.add(lblLoggedUserName);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblEmail.setBounds(84, 312, 70, 14);
		contentPane.add(lblEmail);

        JLabel lblLoggedEmail = new JLabel(LoginView.getEmail());
        lblLoggedEmail.setFont(new Font("Calibri", Font.PLAIN, 14));
        lblLoggedEmail.setBounds(164, 312, 210, 14);
        contentPane.add(lblLoggedEmail);
		
		JLabel lblAccessLevel = new JLabel("Perfil:");
		lblAccessLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccessLevel.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblAccessLevel.setBounds(40, 337, 114, 14);
		contentPane.add(lblAccessLevel);

		JLabel lblLoggedAccessLevel = new JLabel(LoginView.getAccessLevel());
		lblLoggedAccessLevel.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblLoggedAccessLevel.setBounds(164, 337, 210, 14);
		contentPane.add(lblLoggedAccessLevel);

        JTable tblLoggedData = new JTable();
        tblLoggedData.setEnabled(false);
        tblLoggedData.setBorder(new LineBorder(new Color(255, 165, 0), 2));
        tblLoggedData.setBounds(85, 275, 289, 88);
        contentPane.add(tblLoggedData);
		
		JLabel lblBackgroundImage = new JLabel("");
		lblBackgroundImage.setIcon(new ImageIcon("./img/background.png"));
		lblBackgroundImage.setBounds(44, 0, 614, 363);
		contentPane.add(lblBackgroundImage);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
