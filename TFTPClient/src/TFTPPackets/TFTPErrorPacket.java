package TFTPPackets;
import java.net.*;
import java.nio.*;

public class TFTPErrorPacket extends TFTPPacket {

	public enum ErrorCode
	{
		NotDefined,
		FileNotFound,
		AccessViolation,
		DiskFullorAllocationExceeded,
		IllegalTFTPOperation,
		UnknownTID,
		FileAlreadyExists,
		NoSuchUser
	}
	
	short errorcode;
	String errormsg;
	
	public String getErrorMessage()
	{
		return errormsg;
	}
	
	public short getErrorCode(){
		return errorcode;
	}
	
	@Override
	public byte[] getOpcode(){
		return new byte[]{0, TFTPDefines.ERROR};
	}
	
	@Override
	public DatagramPacket getPacket() {
		DatagramPacket packet = null;
		int datalength = getOpcode().length + getErrorMessage().length() + 3;//2 for errcode + 1 for zerobyte
		ByteBuffer buf = ByteBuffer.allocate(datalength);
		
		buf.put(getOpcode());
		buf.putShort(getErrorCode());
		buf.put(getErrorMessage().getBytes());
		buf.put((byte)0);
		buf.flip();
		
		try{
			packet = new DatagramPacket(buf.array(), datalength, getAddress(), getPort());
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
		
		return packet;
	}
	
	public TFTPErrorPacket(String errormsg, short errorcode, InetAddress address, int port) throws Exception{
		super(address, port);
		
		if(errormsg == null){
			throw new Exception("Invalid Error Message.");
		}
		
		this.errormsg = errormsg;
		this.errorcode = errorcode;		
	}
	
	public TFTPErrorPacket(byte[] data, int length){
		super(data, length);
	}
	
	@Override
	public void CreateFromData(byte[] data, int length) throws Exception
	{
		if(length < 3)
		{
			throw new Exception("Invalid Length.");
		}
		ByteBuffer buf;
		char c;
		StringBuilder builder = new StringBuilder();
		try{
			buf = ByteBuffer.allocate(length);
			buf.put(data, 0, length);
			buf.flip();
			
			this.errorcode = buf.getShort();
			
			while((c = buf.getChar()) != (char)0){
				builder.append(c);
			}
			
			this.errormsg = builder.toString();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Invalid Error Packet format.");
		}	
	}


}
