import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class XmlEditorPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private RSyntaxTextArea textArea;

	public XmlEditorPanel() {
		this(null);
	}

	public XmlEditorPanel(String title) {
		setLayout(new BorderLayout());

		textArea = new RSyntaxTextArea(20, 60);
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		textArea.setCodeFoldingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		add(sp);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		JButton btnOpen = new JButton("Ouvrir...");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFileChooser();
			}
		});
		panel.add(btnOpen);

		if (title != null && title.isEmpty()) {
			JLabel lblTitle = new JLabel(title);
			sp.setColumnHeaderView(lblTitle);
		}
	}

	private void showFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "xml", "xqy");
		fileChooser.setFileFilter(filter);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			displayFileContents(selectedFile);
		}
	}

	private void displayFileContents(File file) {
		StringBuilder content = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		textArea.setText(content.toString());
	}

	public String getValue() {
		return textArea.getText();
	}

}
