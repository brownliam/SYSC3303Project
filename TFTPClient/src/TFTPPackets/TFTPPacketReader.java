package TFTPPackets;
import java.nio.*;
public class TFTPPacketReader {
	
	public static ITFTPPacket ReadData(byte[] data, int length) throws Exception
	{
 		if(length < 2 )
			throw new Exception("Invalid data format.");
		
		ByteBuffer buf;
		buf = ByteBuffer.allocate(length);
		buf.put(data, 0, length);
		buf.flip();
		
		if(buf.get() != 0)
			throw new Exception("Invalid data format.");

		length -= 2; //minus 2 bytes since 2 gets before
		switch(buf.get())
		{
		case TFTPDefines.RRQ:
			return new TFTPReadRequestPacket(buf.compact().array(), length);
		case TFTPDefines.WRQ:
			return new TFTPWriteRequestPacket(buf.compact().array(), length);
		case TFTPDefines.DATA:
			return new TFTPDataPacket(buf.compact().array(), length);
		case TFTPDefines.ACK:
			return new TFTPAcknowledgementPacket(buf.compact().array(), length);
		case TFTPDefines.ERROR:
			return new TFTPErrorPacket(buf.compact().array(), length);
		default:
			throw new Exception("Invalid Opcode");
		}
	}
	
}
