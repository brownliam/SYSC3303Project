package TFTPPackets;
import java.net.*;
import java.nio.*;

public class TFTPDataPacket extends TFTPPacket {

	short blocknum;
	byte[] data;
	boolean endoftransfer = false;//end of transfer
	public short getBlocknum(){
		return blocknum;
	}
	
	public byte[] getData(){
		return data;
	}	
	
	@Override
	public byte[] getOpcode(){
		return new byte[]{0, TFTPDefines.DATA};
	}
	
	@Override
	public DatagramPacket getPacket() {
		DatagramPacket packet = null;
		int dataLength = 0;
		dataLength = getOpcode().length + getData().length + 2;/*2 bytes for blocknum*/
		ByteBuffer buf = ByteBuffer.allocate(dataLength);
		buf.put(getOpcode());
		buf.putShort(getBlocknum());
		buf.put(getData());
		
		try{
			packet = new DatagramPacket(buf.array(), dataLength, getAddress(), getPort());
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);		
		}
		return packet;
	}
	
	public TFTPDataPacket(short blocknum, byte[] data, InetAddress address, int port) throws Exception{
		super(address, port);
		if(data == null)
			throw new Exception("Invalid Data!");
		
		this.blocknum = blocknum;
		this.data = data;
	}
	
	public TFTPDataPacket(byte[] data, int length){
		super(data,length);
	}
	
	@Override
	public void CreateFromData(byte[] data, int length) throws Exception
	{
		//2 bytes for opcode and at least 1 byte for data
		if(length < 3)
			throw new Exception("Length of Data is invalid.");
		
		try{
			this.blocknum = (short)(data[0] | data[1] << 8);
			System.arraycopy(data, 2, this.data, 0, length - 2);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Invalid Data Packet Format.");
		}
	}

}
