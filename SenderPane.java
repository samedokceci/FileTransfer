import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class SenderPane extends Pane {

	private JLabel portLabel;

	private JButton selectFileButton;
	private JButton sendButton;
	private JButton connectButton;




	/**
	 * Create the panel.
	 */
	public SenderPane() {
		setBackground(UIManager.getColor("ToggleButton.select"));
		setForeground(Color.WHITE);
		setLayout(null);
		
		
		
		progressBar = new JProgressBar();
		progressBar.setLocation(24, 236);
		progressBar.setSize(599, 33);
		add(progressBar);
		
		 radioConnect = new JRadioButton("Connect");
		 radioConnect.setSelected(true);
		radioConnect.setFont(new Font("Dialog", Font.BOLD, 20));
		radioConnect.setBounds(131, 21, 107, 36);
		radioConnect.setEnabled(false);
		add(radioConnect);
		
		 radioWait = new JRadioButton("Wait");
		radioWait.setFont(new Font("Dialog", Font.BOLD, 20));
		radioWait.setBounds(257, 21, 71, 36);
		add(radioWait);
		
		 ipLabel = new JLabel("IP Address:");
		ipLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		ipLabel.setBounds(35, 83, 122, 27);
		add(ipLabel);
		
		 radioLabel = new JLabel("Connect");
		 radioLabel.setForeground(UIManager.getColor("RadioButtonMenuItem.acceleratorForeground"));
		radioLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		radioLabel.setBounds(35, 25, 88, 28);
		add(radioLabel);
		
		ipTextArea = new JTextArea("127.0.0.1");
		ipTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		ipTextArea.setBounds(160, 79, 206, 35);
		add(ipTextArea);
		ipTextArea.setColumns(10);
		
		 portLabel = new JLabel("Port:");
		portLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		portLabel.setBounds(377, 83, 60, 27);
		add(portLabel);
		
		portTextArea = new JTextArea("50532");
		portTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		portTextArea.setBounds(439, 79, 60, 35);
		add(portTextArea);
		portTextArea.setColumns(10);
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		connectButton.setFont(new Font("Dialog", Font.BOLD, 20));
		connectButton.setBounds(515, 77, 116, 38);
		add(connectButton);
		
		 selectFileButton = new JButton("Select File");
		 selectFileButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 	}
		 });
		selectFileButton.setFont(new Font("Dialog", Font.BOLD, 20));
		selectFileButton.setBounds(579, 135, 134, 38);
		add(selectFileButton);
		
		 sendButton = new JButton("Send");
		 sendButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 	}
		 });
		sendButton.setFont(new Font("Dialog", Font.BOLD, 20));
		sendButton.setBounds(579, 179, 83, 38);
		add(sendButton);
		
		infoTextArea = new JTextArea();
		infoTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		infoTextArea.setBounds(24, 135, 543, 84);
		//add(textfieldInfo);
		//textfieldInfo.setColumns(10);
		
		logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		logTextArea.setBounds(24, 293, 689, 231);
		//add(textfieldLog);

		logScrollPane = new JScrollPane(logTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logScrollPane.setBounds(24, 293, 689, 231);
		logScrollPane.setVisible(true);
		add(logScrollPane);

		infoScrollPane = new JScrollPane(infoTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollPane.setBounds(24, 135, 543, 84);
		infoScrollPane.setVisible(true);
		add(infoScrollPane);
		
		percAndSpeedLabel = new JLabel("0%");
		percAndSpeedLabel.setBounds(625, 236, 91, 33);
		add(percAndSpeedLabel);
	}

	public JLabel getPortLabel() {
		return portLabel;
	}

	public void setPortLabel(JLabel portLabel) {
		this.portLabel = portLabel;
	}

	public JButton getSelectFileButton() {
		return selectFileButton;
	}

	public void setSelectFileButton(JButton selectFileButton) {
		this.selectFileButton = selectFileButton;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

	public JButton getConnectButton() {
		return connectButton;
	}

	public void setConnectButton(JButton connectButton) {
		this.connectButton = connectButton;
	}
}
