package TFTPPackets;
import java.net.*;

public interface ITFTPPacket {
	
	//Returns a constant operation code for a packet
	byte[] getOpcode();
	
	//Returns the port this packet will be sent to
	int getPort();
	
	//Returns the address this packet will be sent to
	InetAddress getAddress();
	
	//Generates the DatagramPcket to be sent
	DatagramPacket getPacket();
	
}
