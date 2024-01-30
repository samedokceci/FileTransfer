import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Paths;


public class Sender extends Client {
    private String filePath;
    private double fileSize = 0;
    private long sentSize = 0;
    private String dataType;


    public boolean connect() {
        try {
            logInfo("[+] Connecting to " + IP_ADDRESS + ":" + PORT);
            Socket socket = new Socket(IP_ADDRESS, PORT);
            logInfo("[+] Connected to: " + socket.getInetAddress() + ":" + socket.getPort());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            log("[-] Error " + e.getMessage());
            return false;
        }
    }
    public void reset() {
    	fileSize = 0;
    	sentSize = 0;
    	filePath = null;
    	dataType = null;
        infoTextArea.setText(null);
        startingTimestamp = 0;
    }
    public void getFileDetails() {
    	try {
            fileSize = 0;
			calculateFileSize(filePath);
			
			if (new File(filePath).isFile()) {
				dataType = "FILE";
			}
			else if (new File(filePath).isDirectory()) {
				dataType = "FOLDER";
			}	
			
		} catch (Exception ignore) {}
    }
    public void sendRequest() {
		try {
			dataOutputStream.writeUTF("^DATA>" + returnRelativePath(filePath) + ">" + fileSize + ">" + dataType);
		} catch (Exception ignore) {}
    }
    private void calculateFileSize(String path) throws Exception{
    	File folder = new File(path);

    	if (folder.isFile()) {
    		fileSize = folder.length();
    	}
    	if (folder.isDirectory()) {
            for (File file: folder.listFiles()) {
                if (file.isFile()) {
                	fileSize += file.length();
                }
                else if (file.isDirectory()) {
                    calculateFileSize(file.getAbsolutePath());
                }
            }
    	}
    }
    public boolean isAccepted() {
        try {
            String response = dataInputStream.readUTF();
            if (response.equals("^Yes")) {
                logInfo("[+] Data transfer has accepted.");
                logInfo("[+] File to send: " + Paths.get(filePath).getFileName().toString() + " | " + fileSizeToMB(fileSize));
                return true;
            }
            else if(response.equals("^No")) {
                log("[-] Data has rejected. Cancelling...");
            }
        } catch (Exception ignore) {}
        return false;
    }
    public void startTransmisson() {
        try {
            startingTimestamp = System.currentTimeMillis();
            sendData(filePath);
            dataOutputStream.writeUTF("^Finished:");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendData(String path){
        try {
            File folder = new File(path);

            if (folder.isFile()) {
                sendFile(folder.getAbsolutePath());
            }
            else {
    			dataOutputStream.writeUTF("^Folder:" + returnRelativePath(folder));
    	        for (File file: folder.listFiles()) {
    	            if (file.isFile()) {
    	                sendFile(file.getAbsolutePath());
    	            }
    	            else if (file.isDirectory()) {
    	                dataOutputStream.writeUTF("^Folder:" + returnRelativePath(file));
                        System.out.println(returnRelativePath(file));
    	                sendData(file.getAbsolutePath());
    	            }
    	        }
            }
		} catch (Exception ignore) {}
    }
    private void sendFile(String absolutePath) throws Exception{
        File file = new File(absolutePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        dataOutputStream.writeUTF("^File:"+returnRelativePath(file));

        log("[+] Sending: " + returnRelativePath(file) + " | " + fileSizeToMB(file.length()));

        dataOutputStream.writeLong(file.length());

        int bufferSize = 0;
        if (file.length() >= 1024000 && file.length() <= 102400000 ) {
            bufferSize = 32*1024;
        }
        else if(file.length() > 102400000) {
            bufferSize = 256*1024;
        }
        else {
            bufferSize = 4*1024;
        }

        dataOutputStream.writeInt(bufferSize);

        int bytes = 0;
        byte[] buffer = new byte[bufferSize];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            sentSize += bytes;
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }
    public String returnRelativePath(String pathOfFile) {
    	return Paths.get(filePath).getParent().relativize(Paths.get(pathOfFile)).toString();

    }
    public String returnRelativePath(File file) {
    	return (Paths.get(filePath).getParent().relativize(Paths.get(file.getAbsolutePath()))).toString();
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
        getFileDetails();
        logInfo(filePath + " | " + fileSizeToMB(fileSize));
    }
    public String getFilePath() {
        return filePath;
    }
    public double getFileSize() {
        return fileSize;
    }
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
    public long getSentSize() {
        return sentSize;
    }
    public void setSentSize(long sentSize) {
        this.sentSize = sentSize;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public long getStartingTimestamp() {
        return startingTimestamp;
    }
}


