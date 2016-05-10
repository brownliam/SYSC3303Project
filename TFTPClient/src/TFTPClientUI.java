import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class TFTPClientUI {

	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private TFTPClient server;
	private JRadioButton normalMode, testMode;

	public TFTPClientUI(TFTPClient c) {
		server = c;
		UI();
	}

	private void UI() {
		frame = new JFrame("TFTP Client");
		setCloseListener();
		addComponentsToPane(frame.getContentPane());

	}

	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridLayout(5, 3));
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(server.getPacketInfo(),
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.add(scrollPane);
		addRadioButton();
		frame.setSize(400, 500);
		frame.setLocation(0, 0);
		frame.setVisible(true);

	}

	private void setCloseListener() {
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				server.getSendReceiveSocket().close();
				frame.dispose();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void addRadioButton() {
		JLabel label = new JLabel("Mode");
		normalMode = new JRadioButton("Normal");
		normalMode.setSelected(true);
		//setup listener of normal mode;
		normalMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		testMode = new JRadioButton("Test");
		//setup listener of test mode
		testMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		frame.getContentPane().add(label);
		frame.getContentPane().add(normalMode);
		frame.getContentPane().add(testMode);
	}
}
