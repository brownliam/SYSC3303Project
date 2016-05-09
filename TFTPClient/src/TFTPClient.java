import java.io.*;
import java.net.*;
import java.nio.*;
public class TFTPClient {
	
	DatagramPacket receivePacket;
	DatagramSocket sendReceiveSocket;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TFTPClient client = new TFTPClient();
		client.Run();
	}

	public  TFTPClient(){
		try{
			sendReceiveSocket = new DatagramSocket();
		}catch(SocketException exception){
			exception.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void Run()
	{
		SendRequest();
		WaitForACK();
	}
	
	private boolean WaitForACK(){
		byte []data = new byte[1024];
		
		try{
			receivePacket = new DatagramPacket(data, data.length);
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(-1);
		}
		
		try{
			sendReceiveSocket.receive(receivePacket);
		}
		catch(IOException exception){
			exception.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Response Packet:");
		DatagramPacketInfo(receivePacket, data);
		
		try {
			ITFTPPacket tftpPacket = TFTPPacketReader.ReadData(data, receivePacket.getLength());
			if(tftpPacket.getOpcode()[1] == TFTPDefines.ACK){
				System.out.println("SUCCESSFULLY ACKed!");
				return true;
			}
			return false;
		} catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
		return false;
	}
	

	private void SendRequest(){
		
		TFTPPacket tftpPacket = null;
		DatagramPacket datagramPacket = null;
		try{
			tftpPacket = new TFTPReadRequestPacket("HELLO.java", 
					TFTPDefines.ASCII_MODE, 
					InetAddress.getLocalHost(), 
					TFTPDefines.getClientSendPort());
			
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(-1);
		}
		
		try{
			datagramPacket = tftpPacket.getPacket();
			sendReceiveSocket.send(tftpPacket.getPacket());
		}catch(IOException exception){
			exception.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Sending To HostServer");
		DatagramPacketInfo(datagramPacket, datagramPacket.getData());

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
		System.out.println("*******************Client**********************");
		System.out.println("From IP: " + packet.getAddress());
		System.out.println("From Port: " + packet.getPort());
		System.out.println("Packet Length: " + packet.getLength());
		System.out.println("Packet String: " + new String(data,0,  packet.getLength()));
		System.out.print("Packet Bytes: ");
		System.out.println(data);
	}
	
	
}
