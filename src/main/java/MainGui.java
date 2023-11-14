import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainGui {
	
	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public MainGui() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][grow]"));
		
		XmlEditorPanel xmlPanel = new XmlEditorPanel("XML");
		XmlEditorPanel xqueryPanel = new XmlEditorPanel("XQuery");
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, xmlPanel, xqueryPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5d);
		
		frame.getContentPane().add(splitPane, "cell 0 0,grow");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 0 1,grow");
		
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 0,grow");
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(XqueryExecutor.execute(xmlPanel.getValue(), xqueryPanel.getValue()));
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnExecute);
	}
	
}
