import java.io.*;
import java.net.*;


public class TFTPIntermediateHost {
	
	DatagramSocket clientSendReceiveSocket;
	DatagramSocket serverSendReceiveSocket;
	
	final int serverPort = 69;
	final int clientPort = 23;
	int clientSendPort;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TFTPIntermediateHost host = new TFTPIntermediateHost();
		host.Run();
	}
	
	public TFTPIntermediateHost(){
		try{
			clientSendReceiveSocket = new DatagramSocket(clientPort);
			serverSendReceiveSocket = new DatagramSocket();
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void Run(){
		while(true){
			SendToServer(WaitAndReceiveClient());
			SendToClient(WaitAndReceiveServer());
		}
	}
	
	private DatagramPacket WaitAndReceiveClient(){
		byte[] data = new byte[100];
		DatagramPacket packet = null;
		DatagramPacket sendPacket = null;
		try{
			packet = new DatagramPacket(data, data.length);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			System.exit(-1);
		}
		
		try{
			clientSendReceiveSocket.receive(packet);
		}catch(Exception exception)
		{
			exception.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Received From Client");
		DatagramPacketInfo(packet, data);
		clientSendPort = packet.getPort();
		try{
			sendPacket = new DatagramPacket(data, packet.getLength(), InetAddress.getLocalHost(), serverPort);
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(-1);
		}
		
		return sendPacket;
	}
	
	private DatagramPacket WaitAndReceiveServer(){
		byte[] data = new byte[100];
		DatagramPacket packet = null;
		DatagramPacket sendPacket = null;
		try{
			packet = new DatagramPacket(data, data.length);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			System.exit(-1);
		}
		
		try{
			serverSendReceiveSocket.receive(packet);
		}catch(Exception exception)
		{
			exception.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Received From Server");
		DatagramPacketInfo(packet, data);
		
		try{
			sendPacket = new DatagramPacket(data, packet.getLength(), InetAddress.getLocalHost(), clientSendPort);
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(-1);
		}
		
		return sendPacket;
	}
	
	private void SendToServer(DatagramPacket sendPacket){
		
		if(sendPacket == null)
		{
			System.out.println("Packet to send is NULL");
			System.exit(-1);
		}
		System.out.println("Sending To Server.");
		DatagramPacketInfo(sendPacket, sendPacket.getData());
		
		try{
			serverSendReceiveSocket.send(sendPacket);
		}catch(IOException exception){
			exception.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void SendToClient(DatagramPacket sendPacket){
		if(sendPacket == null)
		{
			System.out.println("Packet to send is NULL");
			System.exit(-1);
		}
		System.out.println("Sending To Client.");
		DatagramPacketInfo(sendPacket, sendPacket.getData());
		
		try{
			serverSendReceiveSocket.send(sendPacket);
		}catch(IOException exception){
			exception.printStackTrace();
			System.exit(-1);
		}
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
		
		System.out.println("*******************Host**********************");
		System.out.println("From IP: " + packet.getAddress());
		System.out.println("From Port: " + packet.getPort());
		System.out.println("Packet Length: " + packet.getLength());
		System.out.println("Packet String: " + new String(data,0,  packet.getLength()));
		System.out.print("Packet Bytes: ");
		System.out.println(data);
	}
}
