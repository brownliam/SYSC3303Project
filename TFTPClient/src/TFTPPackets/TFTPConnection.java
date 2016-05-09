package TFTPPackets;
import java.net.*;


public class TFTPConnection {
	int port;
	InetAddress address;
	
	public int getPort(){
		return port;
	}
	
	public InetAddress getAddress(){
		return address;
	}
	
	public TFTPConnection(InetAddress address, int port){
		this.address = address;
		this.port = port;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof TFTPConnection)
		{
			if(((TFTPConnection)obj).getPort() ==
					getPort() &&
					((TFTPConnection)obj).getAddress() == getAddress()){
				return true;
			}
		}
		return false;
	}
	
}
