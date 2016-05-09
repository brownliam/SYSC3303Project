package TFTPPackets;
import java.net.InetAddress;

public abstract class TFTPPacket implements ITFTPPacket {

	int port;
	InetAddress address;
	
	
	@Override
	public int getPort() {
		return port;
	}

	@Override
	public InetAddress getAddress() {
		return address;
	}
	
	public TFTPPacket(InetAddress address, int port){
		this.address = address;
		this.port = port;
	}
	
	public TFTPPacket(byte[] data, int length){
		try{
			CreateFromData(data, length);
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	protected abstract void CreateFromData(byte[] data, int length) throws Exception;
}
