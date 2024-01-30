import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class FileTransfer {
	JFrame mainFrame = new JFrame();
	JPanel mainPanel = new JPanel();
	ReceiverPane receiverPane = new ReceiverPane();
	SenderPane senderPane = new SenderPane();
	CardLayout cl = new CardLayout();
	Sender senderBackend = new Sender();
	Receiver receiverBackend = new Receiver();
    boolean isOSWIN = false;
	Thread startReceiver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                new FileTransfer();
            } catch (Exception ignore) {}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileTransfer() {
		System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
			isOSWIN = true;
		}

		mainPanel.setLayout(cl);
		mainPanel.add(receiverPane, "1");
		mainPanel.add(senderPane, "2");
		cl.show(mainPanel, "1");
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
				mainFrame.dispose();
				System.exit(0);
		    }
		});
		mainFrame.setBounds(100, 100, 740, 600);
		mainFrame.getContentPane().add(mainPanel);
		mainFrame.setVisible(true);
		initReceive();

		/*
		  ReceiverPane
		 */
		
		receiverPane.getRadioConnect().addActionListener(e -> {
            cl.show(mainPanel, "2");
            receiverBackend = null;

            //reset other panes logs
            receiverPane.getInfoTextArea().setText("");
            receiverPane.getLogTextArea().setText("");

            senderBackend = new Sender();
            senderBackend.setLogTextArea(senderPane.getLogTextArea());
            senderBackend.setInfoTextArea(senderPane.getInfoTextArea());
            senderPane.getSelectFileButton().setEnabled(false);
            senderPane.getSendButton().setEnabled(false);

            receiverPane.getRadioConnect().setSelected(false);
            });
		
		/*
		  SenderPane
		 */
		senderPane.getRadioWait().addActionListener(e -> {
            cl.show(mainPanel, "1");

            //reset other panes logs
            senderPane.getInfoTextArea().setText("");
            senderPane.getLogTextArea().setText("");

            receiverBackend = new Receiver();
            initReceive();

            senderBackend = null;
            senderPane.getRadioWait().setSelected(false);

            });
		
		receiverPane.getWaitButton().addActionListener(arg0 -> {
            receiverBackend.setIP_ADDRESS(receiverPane.getIpTextArea().getText());
            receiverBackend.setPORT(Integer.parseInt(receiverPane.getPortTextArea().getText()));

            startReceiver = new Thread(() -> {
                receiverBackend.startWaiter();
                receiverBackend.receive();
            });

            startReceiver.start();
            receiverPane.getWaitButton().setEnabled(false);
            receiverPane.getRadioConnect().setEnabled(false);
        });


		receiverPane.getAcceptButton().addActionListener(arg0 -> {
            Thread pbUpdate= new Thread(() -> {
                receiverBackend.startingTimestamp = System.currentTimeMillis();

                try {

                    while(receiverPane.getProgressBar().getValue()<100) {
                        if(receiverBackend.getFileSize()<=0) {
                            receiverPane.getProgressBar().setValue(100);
                            receiverPane.getPercAndSpeedLabel().setText("100% ");
                        }
                        else{
                            int value = (int)((receiverBackend.getReceivedSize()*100)/(receiverBackend.getFileSize() <=0 ? 1 : receiverBackend.getFileSize()));
                            double passedTime  =   TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - receiverBackend.startingTimestamp));
                            if(passedTime>2) {
                                receiverPane.getPercAndSpeedLabel().setText(value + "% " + Math.round(receiverBackend.getReceivedSize()/(passedTime*1024*1024)) + "MB/s");
                            }
                            else{
                                receiverPane.getPercAndSpeedLabel().setText(value + "% ");
                            }
                            receiverPane.getProgressBar().setValue(Math.min(value, 100));
                        }
                    }
                    Thread.sleep(500);
                    receiverBackend.log("[+] " + receiverBackend.fileSizeToMB(receiverBackend.getFileSize()) +  " received in " + elapsedTime(receiverBackend.startingTimestamp +500) + " seconds.");
                    receiverPane.getPercAndSpeedLabel().setText(0 + "% ");
                    receiverPane.getProgressBar().setValue(0);


                    receiverBackend.reset();
                    receiverBackend.receive();
                } catch (Exception ignore) {}

            });

            Thread receive = new Thread(() -> {
                receiverBackend.accept();
                receiverBackend.receiveData();
            });
            receive.start();
            pbUpdate.start();
        });
		
		receiverPane.getDeclineButton().addActionListener(arg0 -> {
            receiverBackend.decline();
            Thread receive = new Thread(() -> {
                receiverBackend.reset();
                receiverBackend.receive();
            });
            receive.start();
        });


		senderPane.getConnectButton().addActionListener(arg0 -> {
            senderBackend.setIP_ADDRESS(senderPane.getIpTextArea().getText());
            senderBackend.setPORT(Integer.parseInt(senderPane.getPortTextArea().getText()));

            boolean isConnected = senderBackend.connect();
            if(isConnected) {
                senderPane.getRadioWait().setEnabled(false);
                senderPane.getConnectButton().setEnabled(false);
                senderPane.getSelectFileButton().setEnabled(true);
            }
        });
		
		senderPane.getSelectFileButton().addActionListener(e -> {
            senderPane.getInfoTextArea().setText("");
            try {
                LookAndFeel previousLF = UIManager.getLookAndFeel();
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                JFileChooser fileChooser = new JFileChooser();
                UIManager.setLookAndFeel(previousLF);
                fileChooser.setDialogTitle("Select a File or Folder");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    senderBackend.setFilePath((fileChooser.getSelectedFile().getAbsolutePath()));
                    senderPane.getSendButton().setEnabled(true);
                }
            } catch (Exception ignore) {}
        });

		senderPane.getSendButton().addActionListener(e -> {

            Thread pbUpdate = new Thread(() -> {
                try {
                    while (senderPane.getProgressBar().getValue()<100) {
                        if(senderBackend.getFileSize()<=0) {
                            senderPane.getProgressBar().setValue(100);
                            senderPane.getPercAndSpeedLabel().setText("100% ");
                        }

                        else{
                            double passedTime  =  (double) ((((System.currentTimeMillis() - senderBackend.getStartingTimestamp())) <= 1 )? 1 : TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - senderBackend.getStartingTimestamp())) );
                            int value = (int)((senderBackend.getSentSize()*100)/senderBackend.getFileSize());

                            if(passedTime>2) {
                                senderPane.getPercAndSpeedLabel().setText(value + "% " + Math.round((double) senderBackend.getSentSize()/(1024*1024*passedTime)) + "MB/s");
                            }
                            else{
                                senderPane.getPercAndSpeedLabel().setText(value + "% ");
                            }
                            senderPane.getProgressBar().setValue(Math.min(value, 100));
                        }
                    }
                    Thread.sleep(500);
                    senderBackend.log("[+] "+ senderBackend.fileSizeToMB(senderBackend.getFileSize()) + " sent in " + elapsedTime(senderBackend.getStartingTimestamp() +500));
                    senderPane.getPercAndSpeedLabel().setText(0 + "%");
                    senderPane.getProgressBar().setValue(0);
                    senderPane.getSelectFileButton().setEnabled(true);
                    senderBackend.reset();
                } catch (Exception ignore) {}
                            });

                senderBackend.sendRequest();
                if (senderBackend.isAccepted()) {
                    pbUpdate.start();
                    Thread sending = new Thread(() -> senderBackend.startTransmisson());
                    sending.start();
                    senderPane.getSelectFileButton().setEnabled(false);
                }
                else {
                    senderBackend.reset();
                }
            senderPane.getSendButton().setEnabled(false);

        });

	}
	public void initReceive() {
		receiverBackend.setLogTextArea(receiverPane.getLogTextArea());
		receiverBackend.setInfoTextArea(receiverPane.getInfoTextArea());
		receiverBackend.setAcceptButton(receiverPane.getAcceptButton());
		receiverBackend.setDeclineButton(receiverPane.getDeclineButton());
        receiverBackend.setSlash(isOSWIN ? "\\" : "/");

		receiverPane.getAcceptButton().setEnabled(false);
		receiverPane.getDeclineButton().setEnabled(false);

	}
	public String elapsedTime(Long startTime) {
		return new DecimalFormat("#.##").format((double) (System.currentTimeMillis()-startTime)/1000) + " seconds.";
	}
}
