import java.net.DatagramPacket;

public class OutputHelper {
	public static void DatagramPacketInfo(DatagramPacket packet, byte[] data){
		if(packet == null){
			System.out.println("Packet is NULL");
			return;
		}
		if(data == null){
			System.out.println("Data is NULL");
			return;
		}
		
		System.out.println("*****************************************");
		System.out.println("From IP: " + packet.getAddress());
		System.out.println("From Port: " + packet.getPort());
		System.out.println("Packet Length: " + packet.getLength());
		System.out.println("Packet String: " + new String(data,0,  packet.getLength()));
		System.out.print("Packet Bytes: ");
		System.out.println(data);
		System.out.println("*****************************************");

	}
}
