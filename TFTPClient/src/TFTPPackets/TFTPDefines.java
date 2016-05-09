package TFTPPackets;

public class TFTPDefines {
	public static final int RRQ = 1;
	public static final int WRQ = 2;
	public static final int DATA = 3;
	public static final int ACK = 4;
	public static final int ERROR = 5;
	
	public static final int SERVER_PORT = 69;
	public static final int INTERMEDIATE_PORT = 23;
	
	public enum CommunicationMode{
		NORMAL,
		TEST
	}
	
	static final CommunicationMode currentMode = CommunicationMode.NORMAL;
	
	public static final String ASCII_MODE = "netascii";
	public static final String OCTET_MODE = "octet";
	
	public static int getClientSendPort(){
		return currentMode == CommunicationMode.NORMAL ? SERVER_PORT : INTERMEDIATE_PORT;
	}
	
}
