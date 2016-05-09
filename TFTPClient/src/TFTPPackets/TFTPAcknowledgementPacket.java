package TFTPPackets;
import java.net.*;
import java.nio.*;

public class TFTPAcknowledgementPacket extends TFTPPacket {

	short blocknum;
	
	public short getBlocknum(){
		return blocknum;
	}
	
	@Override
	public byte[] getOpcode(){
		return new byte[]{0, TFTPDefines.ACK};
	}
	
	@Override
	public DatagramPacket getPacket() {
		DatagramPacket packet = null;
		int datalength = 4; //2 bytes for opcode + 2 bytes for blocknum
		ByteBuffer buf = ByteBuffer.allocate(datalength);
		buf.put(getOpcode());
		buf.putShort(getBlocknum());
		buf.flip();
		
		try{
			packet = new DatagramPacket(buf.array(), datalength, getAddress(), getPort());
		}catch(Exception ex){
			ex.printStackTrace();			
		}
		return packet;
	}

	public TFTPAcknowledgementPacket(short blocknum, InetAddress address, int port){
		super(address, port);
		this.blocknum = blocknum;
	}
	
	public TFTPAcknowledgementPacket(byte[] data, int length){
		super(data, length);
	}
	
	@Override
	public void CreateFromData(byte[] data, int length) throws Exception
	{
		try{
			if(length != 2)
				throw new Exception("Length of data is invalid.");
			this.blocknum = (short)(data[0] | data[1] << 8);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Invalid read request format.");
		}		
	}
	
	
}
