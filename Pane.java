import javax.swing.*;

public class Pane extends JPanel {
    protected JLabel percAndSpeedLabel;
    protected JLabel ipLabel;
    protected JLabel radioLabel;
    protected JScrollPane infoScrollPane;
    protected JScrollPane logScrollPane;
    protected JTextArea ipTextArea;
    protected JTextArea portTextArea;
    protected JTextArea infoTextArea;
    protected JTextArea logTextArea;
    protected JProgressBar progressBar;
    protected JRadioButton radioConnect;
    protected JRadioButton radioWait;

    public JLabel getPercAndSpeedLabel() {
        return percAndSpeedLabel;
    }
    public void setPercAndSpeedLabel(JLabel percAndSpeedLabel) {
        this.percAndSpeedLabel = percAndSpeedLabel;
    }
    public JLabel getIpLabel() {
        return ipLabel;
    }

    public void setIpLabel(JLabel ipLabel) {
        this.ipLabel = ipLabel;
    }

    public JLabel getRadioLabel() {
        return radioLabel;
    }

    public void setRadioLabel(JLabel radioLabel) {
        this.radioLabel = radioLabel;
    }

    public JScrollPane getInfoScrollPane() {
        return infoScrollPane;
    }

    public void setInfoScrollPane(JScrollPane infoScrollPane) {
        this.infoScrollPane = infoScrollPane;
    }
    public JScrollPane getLogScrollPane() {
        return logScrollPane;
    }
    public void setLogScrollPane(JScrollPane logScrollPane) {
        this.logScrollPane = logScrollPane;
    }
    public JTextArea getIpTextArea() {
        return ipTextArea;
    }
    public void setIpTextArea(JTextArea ipTextArea) {
        this.ipTextArea = ipTextArea;
    }
    public JTextArea getPortTextArea() {
        return portTextArea;
    }
    public void setPortTextArea(JTextArea portTextArea) {
        this.portTextArea = portTextArea;
    }
    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }
    public void setInfoTextArea(JTextArea infoTextArea) {
        this.infoTextArea = infoTextArea;
    }
    public JTextArea getLogTextArea() {
        return logTextArea;
    }
    public void setLogTextArea(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }
    public JProgressBar getProgressBar() {
        return progressBar;
    }
    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public JRadioButton getRadioConnect() {
        return radioConnect;
    }
    public void setRadioConnect(JRadioButton radioConnect) {
        this.radioConnect = radioConnect;
    }
    public JRadioButton getRadioWait() {
        return radioWait;
    }
    public void setRadioWait(JRadioButton radioWait) {
        this.radioWait = radioWait;
    }
}
