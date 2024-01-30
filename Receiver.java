import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver extends Client {
    private double fileSize = 0;
    private long receivedSize = 0;
	private String fileName;
    JButton acceptButton;
    JButton declineButton;
    private String slash = "/";

    public void startWaiter() {
        try{
            InetAddress inetAddress = InetAddress.getByName(IP_ADDRESS);
            ServerSocket serverSocket = new ServerSocket(PORT, 50, inetAddress);
            logInfo("[+] Listening on port: " + PORT);
            Socket clientSocket = serverSocket.accept();
            logInfo("[+] " + clientSocket+" connected.");
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception ignored){}
    }
    
    public void reset() {
    	infoTextArea.setText(null);
    	fileSize = 0;
    	receivedSize = 0;
        startingTimestamp = 0;
    }
    
    public void receive() {
    	try {
    		String message = this.dataInputStream.readUTF();
            if (message.startsWith("^DATA")) {
                fileName = message.split(">")[1];
                fileSize = Double.parseDouble(message.split(">")[2]);
                String filetype = message.split(">")[3];
                infoTextArea.setText("");
                logInfo("[+] File request. " + fileName + " | " + fileSizeToMB(fileSize) + " | " + filetype);
                logInfo("[?] ACCEPT/DECLINE file?");
                acceptButton.setEnabled(true);
                declineButton.setEnabled(true);
        	}
    	}catch(Exception ignored) {}
    }

    public void accept() {
		try {
			dataOutputStream.writeUTF("^Yes");
            logInfo("[+] File accepted. Receiving: " + fileName + " | " + fileSizeToMB(fileSize));
            acceptButton.setEnabled(false);
            declineButton.setEnabled(false);
		} catch (Exception ignored) {}
    }

    public void decline() {
		try {
			dataOutputStream.writeUTF("^No");
            logInfo("[-] File declined.");
            acceptButton.setEnabled(false);
            declineButton.setEnabled(false);
		} catch (Exception ignored) {}
    }
    void receiveData() {
        startingTimestamp = System.currentTimeMillis();
        String command;
    	try {
            while (true) {
                command = dataInputStream.readUTF();
                if (command.startsWith("^File:")) {
                    receiveFile(command.substring(6));
                }
                else if(command.startsWith("^Folder:")) {
                    log("[+] Folder: " + command.substring(8));
                    if (!(new File(command.substring(8)).exists())) {
                        new File(fixSlash(command.substring(8))).mkdirs();
                    }
                }
                else if(command.startsWith("^Finished:")) {
                	break;
                }
            }
    	}
    	catch (Exception ignored) {}
    }

	private void receiveFile(String command) throws Exception{
        String relativePath = fixSlash(command);
        FileOutputStream fileOutputStream = new FileOutputStream(relativePath);
        
        long size = dataInputStream.readLong();     // read file size
        int bufferSize = dataInputStream.readInt();
        log("[+] File: " + command + " | " + fileSizeToMB((double)size));

        byte[] buffer = new byte[bufferSize];
        int bytes = 0;
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            receivedSize += bytes;
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;     
        }
        fileOutputStream.close();
	}

    public JButton getDeclineButton() {
        return declineButton;
    }
    public void setDeclineButton(JButton declineButton) {
        this.declineButton = declineButton;
    }
    public JButton getAcceptButton() {
        return acceptButton;
    }
    public void setAcceptButton(JButton acceptButton) {
        this.acceptButton = acceptButton;
    }
    private String fixSlash(String pathToCheck) {
        if (!pathToCheck.contains(slash)) {
            return pathToCheck.replace(((slash.equals("/")) ? "\\" : "/"), slash);
        }
        return pathToCheck;
    }
	public void setSlash(String slash) {
		this.slash = slash;
	}
    public double getFileSize() {
        return fileSize;
    }
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
    public long getReceivedSize() {
        return receivedSize;
    }
    public void setReceivedSize(long receivedSize) {
        this.receivedSize = receivedSize;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getSlash() {
        return slash;
    }
}
