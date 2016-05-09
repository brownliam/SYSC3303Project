package TFTPPackets;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;


public class TFTPWriteRequestPacket extends TFTPPacket {

	String filename;
	String mode;
	
	public String getFilename(){
		return filename;
	}
	
	public String getMode(){
		return mode;
	}
    
	
	@Override
	public byte[] getOpcode(){
		return new byte[]{0, TFTPDefines.WRQ};
	}
	
	@Override
	public DatagramPacket getPacket() {
		DatagramPacket packet = null;
		int dataLength = 0;
		byte[] file = getFilename().getBytes();
		byte[] mode = getMode().getBytes();
		
		dataLength += getOpcode().length;
		dataLength += file.length + 1; //+ 1 for zeroByte
		dataLength += mode.length + 1; //+ 1 for zeroByte
		
		ByteBuffer buf = ByteBuffer.allocate(dataLength);
		
		buf.put(getOpcode());
		buf.put(file);
		buf.put((byte)0);
		buf.put(mode);
		buf.put((byte)0);
		
		buf.flip();
		
		try{
			packet = new DatagramPacket(buf.array(),dataLength, getAddress(), getPort());
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
		return packet;
	}
	
	public TFTPWriteRequestPacket(String filename, String mode, InetAddress address, int port) throws Exception
	{
		super(address, port);
		if(filename == null || mode == null)
		{
			throw new Exception("Invalid Arguments.");
		}
		this.filename = filename;
		this.mode = mode;
	}
	
	public TFTPWriteRequestPacket(byte[] data, int length){
		super(data, length);
	}
	
	
	@Override
	protected void CreateFromData(byte[] data, int length) throws Exception
	{
		ByteBuffer buf;
		byte c;
		StringBuilder builder = new StringBuilder();
		try{
			buf = ByteBuffer.allocate(length);
			buf.put(data, 0, length);
			buf.flip();
			
			while((c = buf.get()) != 0){
				builder.append(new String(new byte[]{c}));
			}
			
			this.filename = builder.toString();
			builder = new StringBuilder();
			
			while((c = buf.get()) != 0){
				builder.append(new String(new byte[]{c}));
				}
			this.mode = builder.toString();
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Invalid write request format.");
		}
	}




}
