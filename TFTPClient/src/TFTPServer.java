import java.net.*;
import java.util.Hashtable;
import java.util.concurrent.locks.*;

import TFTPPackets.ITFTPPacket;
import TFTPPackets.TFTPCompleteListener;
import TFTPPackets.TFTPConnection;
import TFTPPackets.TFTPDefines;
import TFTPPackets.TFTPPacketReader;
import TFTPPackets.TFTPReadRequestPacket;
import TFTPPackets.TFTPWriteRequestPacket;

public class TFTPServer implements TFTPCompleteListener {

	DatagramSocket receiveSocket;
	Hashtable<TFTPConnection, TFTPWorker> connections = new Hashtable<TFTPConnection, TFTPWorker>();
	final Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TFTPServer server = new TFTPServer();
		server.Run();
	}
	
	public TFTPServer(){
		try{
			receiveSocket = new DatagramSocket(TFTPDefines.SERVER_PORT);
		}
		catch(Exception exception){
			exception.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void Run(){
		while(true){
			DatagramPacket packet = WaitForHostResponse();
			ITFTPPacket tftpPacket = null;
			try{
				tftpPacket = TFTPPacketReader.ReadData(packet.getData(), packet.getLength());
			}catch(Exception ex){
				ex.printStackTrace();
			}
			if(tftpPacket.getOpcode()[1] == TFTPDefines.RRQ ||
					tftpPacket.getOpcode()[1] == TFTPDefines.WRQ){
				TFTPConnection connection = new TFTPConnection(packet.getAddress(), packet.getPort());
				this.SpawnConnection(connection, tftpPacket);
			}		
		}
	}
	
	private DatagramPacket WaitForHostResponse()
	{
		byte[] data = new byte[1024];
		DatagramPacket packet = null;
		
		try{
			packet = new DatagramPacket(data, data.length);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			System.exit(-1);
		}
		
		try{
			receiveSocket.receive(packet);
		}catch(Exception exception)
		{
			exception.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Received");
		DatagramPacketInfo(packet, data);

		return packet;
	}
	
	private void DatagramPacketInfo(DatagramPacket packet, byte[] data){
		if(packet == null){
			System.out.println("Packet is NULL");
			return;
		}
		if(data == null){
			System.out.println("Data is NULL");
			return;
		}
		
		System.out.println("*******************SERVER**********************");
		System.out.println("From IP: " + packet.getAddress());
		System.out.println("From Port: " + packet.getPort());
		System.out.println("Packet Length: " + packet.getLength());
		System.out.println("Packet String: " + new String(data,0,  packet.getLength()));
		System.out.print("Packet Bytes: ");
		System.out.println(data);
	}

	@Override
	public void onTFTPWorkerCompleted(TFTPConnection connection) {
		lock.lock();
		if(connections.get(connection) != null){
			connections.remove(connection);
		}
		lock.unlock();
	}
	
	
	///Checks if the packet
	private void SpawnConnection(TFTPConnection connection, ITFTPPacket packet){
		boolean isRead = packet.getOpcode()[1] == TFTPDefines.RRQ;
		String fileName;
		if(packet instanceof TFTPReadRequestPacket ||
				packet instanceof TFTPWriteRequestPacket){
			
			fileName = isRead ? ((TFTPReadRequestPacket)packet).getFilename() :
				((TFTPWriteRequestPacket)packet).getFilename();
			
			lock.lock();
			if(connections.get(connection) == null)
			{
				TFTPWorker worker = new TFTPWorker(connection, this, isRead, fileName);
				connections.put(connection,worker );
				(new Thread(worker)).start();
			}
			lock.unlock();
		}
			
	}

}
