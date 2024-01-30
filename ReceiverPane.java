import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.SwingConstants;
public class ReceiverPane extends Pane {
    private JButton acceptButton;
    private JButton declineButton;
    private JButton waitButton;

	/**
	 * Create the panel.
	 */
	public ReceiverPane() {
		setBackground(Color.ORANGE);
		setForeground(Color.WHITE);
		
		progressBar = new JProgressBar();
		 progressBar.setBounds(24, 249, 599, 33);
		
		radioConnect = new JRadioButton("Connect");
		radioConnect.setBounds(76, 24, 114, 36);
		radioConnect.setFont(new Font("Dialog", Font.BOLD, 20));
		
		radioWait = new JRadioButton("Wait");
		radioWait.setBounds(200, 24, 73, 36);
		radioWait.setSelected(true);
		radioWait.setFont(new Font("Dialog", Font.BOLD, 20));
		radioWait.setEnabled(false);
		
	    ipLabel = new JLabel("IP/PORT:");
	    ipLabel.setBounds(283, 29, 122, 27);
		ipLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		 radioLabel = new JLabel("Wait");
		 radioLabel.setBounds(24, 24, 52, 36);
		 radioLabel.setForeground(UIManager.getColor("OptionPane.warningDialog.border.background"));
		radioLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		ipTextArea = new JTextArea("127.0.0.1");
		ipTextArea.setBounds(377, 28, 134, 35);
		ipTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		ipTextArea.setColumns(10);
		
		portTextArea = new JTextArea("50532");
		portTextArea.setBounds(516, 28, 64, 35);
		portTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		portTextArea.setColumns(10);
		
		waitButton = new JButton("Wait");
		waitButton.setHorizontalAlignment(SwingConstants.LEFT);
		waitButton.setBounds(592, 31, 91, 27);
		waitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		waitButton.setFont(new Font("Dialog", Font.BOLD, 15));
		
		infoTextArea = new JTextArea();
		infoTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		infoTextArea.setBounds(24, 120, 689, 68);

		acceptButton = new JButton("Accept");
		acceptButton.setBounds(248, 205, 102, 35);
		acceptButton.setFont(new Font("Dialog", Font.BOLD, 20));
		
		 declineButton = new JButton("Decline");
		 declineButton.setBounds(362, 205, 114, 35);
		declineButton.setFont(new Font("Dialog", Font.BOLD, 20));
		
		logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Dialog", Font.PLAIN, 20));
		logTextArea.setBounds(24, 293, 689, 231);


		logScrollPane = new JScrollPane(logTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logScrollPane.setBounds(24, 293, 689, 231);
		logScrollPane.setVisible(true);

		infoScrollPane = new JScrollPane(infoTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollPane.setBounds(24, 86, 689, 107);
		infoScrollPane.setVisible(true);
		
		 percAndSpeedLabel = new JLabel("0%");
		percAndSpeedLabel.setBounds(625, 249, 91, 33);
		setLayout(null);
		add(progressBar);
		add(radioConnect);
		add(radioWait);
		add(ipLabel);
		add(radioLabel);
		add(ipTextArea);
		add(portTextArea);
		add(waitButton);
		add(acceptButton);
		add(declineButton);
		add(logScrollPane);
		add(infoScrollPane);
		add(percAndSpeedLabel);

	}

	public JButton getAcceptButton() {
		return acceptButton;
	}
	public void setAcceptButton(JButton acceptButton) {
		this.acceptButton = acceptButton;
	}
	public JButton getDeclineButton() {
		return declineButton;
	}
	public void setDeclineButton(JButton declineButton) {
		this.declineButton = declineButton;
	}
	public JButton getWaitButton() {
		return waitButton;
	}
	public void setWaitButton(JButton waitButton) {
		this.waitButton = waitButton;
	}
}
