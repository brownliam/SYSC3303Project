package TFTPPackets;

public interface TFTPCompleteListener {
	void onTFTPWorkerCompleted(TFTPConnection connection);
}
