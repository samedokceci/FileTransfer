import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.DecimalFormat;

public class Client {
    protected DataOutputStream dataOutputStream;
    protected DataInputStream dataInputStream;
    protected String IP_ADDRESS = "127.0.0.1";
    protected int PORT = 50532;
    protected JTextArea logTextArea;
    protected JTextArea infoTextArea;
    protected long startingTimestamp = 0;

    public String fileSizeToMB(double fileSize) {
        return new DecimalFormat("#.##").format((double) fileSize/(1024*1024))  + "MB";
    }
    protected void logInfo(String newInfo) {
        log(newInfo);
        infoTextArea.setText(infoTextArea.getText() + newInfo + "\n");
    }
    protected void log(String newLog) {
        logTextArea.setText(logTextArea.getText() + newLog + "\n");
    }
    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }
    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }
    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }
    public String getIP_ADDRESS() {
        return IP_ADDRESS;
    }
    public void setIP_ADDRESS(String IP_ADDRESS) {
        this.IP_ADDRESS = IP_ADDRESS;
    }
    public int getPORT() {
        return PORT;
    }
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }
    public JTextArea getLogTextArea() {
        return logTextArea;
    }
    public void setLogTextArea(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }
    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }
    public void setInfoTextArea(JTextArea infoTextArea) {
        this.infoTextArea = infoTextArea;
    }
    public long getStartingTimestamp() {
        return startingTimestamp;
    }
    public void setStartingTimestamp(long startingTimestamp) {
        this.startingTimestamp = startingTimestamp;
    }
}
