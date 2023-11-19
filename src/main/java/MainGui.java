import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class MainGui {

	private JFrame frmXquerytest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
					window.frmXquerytest.setVisible(true);
				} catch (Exception e) {
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
		frmXquerytest = new JFrame();
		frmXquerytest.setTitle("XQueryTester");
		frmXquerytest.setBounds(100, 100, 606, 534);
		frmXquerytest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXquerytest.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

		XmlEditorPanel xmlPanel = new XmlEditorPanel("XML");
		XmlEditorPanel xqueryPanel = new XmlEditorPanel("XQuery");

		JSplitPane splitQueryPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, xmlPanel, xqueryPanel);
		splitQueryPane.setOneTouchExpandable(true);
		splitQueryPane.setResizeWeight(0.5d);

		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "", ""));
		panel.add(splitQueryPane, "cell 0 0,grow");

		JPanel panelResult = new JPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitQueryPane, panelResult);
		splitPane.setResizeWeight(0.5d);

		frmXquerytest.getContentPane().add(splitPane, "cell 0 0,grow");
		panelResult.setLayout(new MigLayout("", "[404px,grow]", "[top][216px,grow,top]"));

		JPanel panelBtn = new JPanel();
		panelResult.add(panelBtn, "cell 0 0,growx,aligny top");
		panelBtn.setLayout(new MigLayout("", "[71px,grow]", "[23px]"));

		JButton btnExecute = new JButton("Execute");
		panelBtn.add(btnExecute, "cell 0 0,alignx center,aligny top");

		JScrollPane scrollPane = new JScrollPane();
		panelResult.add(scrollPane, "cell 0 1,grow");

		JTextArea textAreaResult = new JTextArea();
		scrollPane.setViewportView(textAreaResult);
		
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textAreaResult.setText(XqueryExecutor.execute(xmlPanel.getValue(), xqueryPanel.getValue()));
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		frmXquerytest.pack();
	}

}
