import java.net.*;

import TFTPPackets.TFTPAcknowledgementPacket;
import TFTPPackets.TFTPCompleteListener;
import TFTPPackets.TFTPConnection;

public class TFTPWorker implements Runnable {
	
	DatagramSocket sendSocket;
	TFTPConnection connection;
	
	boolean isRead;
	String fileName;
	TFTPCompleteListener listener;
	
	public TFTPWorker(TFTPConnection connection, TFTPCompleteListener listener, boolean isRead, String fileName){
		this.connection = connection;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TFTPAcknowledgementPacket packet = null;//
		try{
			sendSocket = new DatagramSocket ();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		packet  = new TFTPAcknowledgementPacket((byte)0, connection.getAddress(), connection.getPort());
		
		try{
			sendSocket.send(packet.getPacket());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		OnCompleted();
	}
	
	private void OnCompleted(){
		this.listener.onTFTPWorkerCompleted(connection);
	}
}

