import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {

	private JFrame frame;
	private JPanel panel;
	private JFileChooser fileChooser;
	private JLabel employeeLbl;
	private JTextField employeeFile;
	private JButton employeeChooseFile;
	private JLabel recordLbl;
	private JTextField recordFile;
	private JButton recordChooseFile;
	private JLabel startTimeLbl;
	private JComboBox<String> startItemsHours;
	private JComboBox<String> startItemsMins;
	private JLabel endTimeLbl;
	private JComboBox<String> endItemsHours;
	private JComboBox<String> endItemsMins;

	private JButton compute;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Payroll System");
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBounds(0, 0, 450, 360);
		panel.setLayout(null);
		frame.add(panel);

		fileChooser = new JFileChooser(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

		recordLbl = new JLabel("Records: ");
		recordLbl.setBounds(10, 60, 100, 30);
		panel.add(recordLbl);

		recordFile = new JTextField();
		recordFile.setBounds(70, 60, 240, 30);
		recordFile.setEditable(false);
		panel.add(recordFile);

		recordChooseFile = new JButton("Select File");
		recordChooseFile.setBounds(320, 60, 100, 30);
		recordChooseFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser.showOpenDialog(frame);
				if (fileChooser.getSelectedFile() != null) {
					if(fileChooser.getSelectedFile().getAbsolutePath().contains(".csv"))
						recordFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
					else
						JOptionPane.showMessageDialog(frame, "Invalid File");
					
				} 

			}
		});
		panel.add(recordChooseFile);

		startTimeLbl = new JLabel("Start Time: ");
		startTimeLbl.setBounds(10, 100, 100, 30);
		panel.add(startTimeLbl);

		String[] hours = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23" };
		String[] mins = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
				"32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
				"49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" };

		startItemsHours = new JComboBox<String>(hours);
		startItemsHours.setBounds(80, 100, 40, 30);
		startItemsHours.setSelectedItem("07");
		panel.add(startItemsHours);

		startItemsMins = new JComboBox<String>(mins);
		startItemsMins.setBounds(120, 100, 40, 30);
		panel.add(startItemsMins);

		endTimeLbl = new JLabel("End Time: ");
		endTimeLbl.setBounds(165, 100, 100, 30);
		panel.add(endTimeLbl);

		endItemsHours = new JComboBox<String>(hours);
		endItemsHours.setBounds(230, 100, 40, 30);
		endItemsHours.setSelectedItem("16");
		panel.add(endItemsHours);

		endItemsMins = new JComboBox<String>(mins);
		endItemsMins.setBounds(270, 100, 40, 30);
		panel.add(endItemsMins);

		compute = new JButton("Payroll");
		compute.setBounds(320, 100, 100, 30);
		compute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fileChooser.showOpenDialog(frame);
				try {
					String fileName = fileChooser.getSelectedFile().getAbsolutePath();
					if (!fileName.contains(".csv"))
						fileName += ".csv";
					
					String s = (String) startItemsHours.getSelectedItem() + ":"
							+ (String) startItemsMins.getSelectedItem();
					String e = (String) endItemsHours.getSelectedItem() + ":" + (String) endItemsMins.getSelectedItem();
					Driver d = new Driver();

					if (recordFile.getText().equals(""))
						JOptionPane.showMessageDialog(frame, "Missing File");
					else {
						String employeeFileString = javax.swing.filechooser.FileSystemView.getFileSystemView()
								.getHomeDirectory().getAbsolutePath() + "/PRS/Employee-Masterlist.csv";
						
						int error = d.run(employeeFileString, recordFile.getText(), fileName, s, e);

						if (error == 0)
							JOptionPane.showMessageDialog(frame, "Payroll is now saved to " + fileName);
						else if(error == 1)
							JOptionPane.showMessageDialog(frame, "The process cannot access the file because it is being used by another process.");
						else
							JOptionPane.showMessageDialog(frame, "Something went wrong. Please try again.");

					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Something went wrong. Please try again.");
				}
			}
		});
		panel.add(compute);

	}

}
