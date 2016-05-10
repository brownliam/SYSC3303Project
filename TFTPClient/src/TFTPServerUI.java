import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class TFTPServerUI {

	private JFrame frame;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private TFTPServer server;

	public TFTPServerUI(TFTPServer s) {
		server = s;
		UI();
	}

	private void UI() {
		frame = new JFrame("TFTP Server");
		setCloseListener();
		addComponentsToPane(frame.getContentPane());

	}
	
	private void addComponentsToPane(Container pane) {
		pane.setLayout(new GridLayout(1, 1));
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(server.getPacketInfo(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.add(scrollPane);
		frame.setSize(400, 150);
		frame.setLocation(0, 0);
		frame.setVisible(true);

	}
	private void setCloseListener(){
		frame.addWindowListener(new WindowListener(){
			@Override
			public void windowActivated(WindowEvent arg0){
			}
			@Override
			public void windowClosing(WindowEvent arg0){
				server.getReceiveSocket().close();
				frame.dispose();
			}
			@Override
			public void windowDeactivated(WindowEvent arg0){
			}
			@Override
			public void windowOpened(WindowEvent arg0){
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
}
