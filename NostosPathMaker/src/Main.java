import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MainPanel().setVisible(true);

	}

	public void buildGraphics() {

	}

}

class MainPanel extends JFrame implements ActionListener {

	static DrawPanel drawpanel;
	// static JFrame frame;
	static JFrame windowFrame;
	static JPanel buttons;
	static JPanel panel;
	static JLabel label;
	static JPanel dropboxPanel;
	static JComboBox jBox;

	static int SCREENWIDTH = 480;
	static int SCREENHEIGHT = 320;

	static JButton button1;
	static JButton button2;
	static JTextField fText;

	static Graphics g;

	String fileName = "/Users/ryaninman/Desktop/test.plist";
	private Document doc;
	private Element xRoot;
	private Element xPath;

	public MainPanel() {
		windowFrame = new JFrame();

		this.setSize(SCREENWIDTH + 200, SCREENHEIGHT + 262);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setSize(this.getSize());
		this.add(panel);

		drawpanel = new DrawPanel();
		drawpanel.setBackground(Color.BLACK);
		// drawpanel.setSize(SCREENHEIGHT / 2, SCREENWIDTH / 2);
		panel.add(drawpanel, BorderLayout.CENTER);
		panel.setBackground(Color.WHITE);

		buttons = new JPanel();
		buttons.setSize(680, 200);
		panel.add(buttons, BorderLayout.NORTH);

		label = new JLabel("No file Loaded");

		fText = new JTextField(10);
		jBox = new JComboBox();
		button1 = new JButton("Open File");
		button2 = new JButton("Save File");

		button1.addActionListener(this);
		button2.addActionListener(this);

		buttons.add(button1);
		buttons.add(fText);
		buttons.add(label);
		buttons.add(button2);

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == button1) {
			// Open File dialog

			FileDialog fd = new FileDialog(this, "Open .plist", FileDialog.LOAD);
			fd.setFilenameFilter(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					if (name.toLowerCase().endsWith(".plist"))
						return true;
					else
						return false;
				}

			});

			fd.setVisible(true);

			String openFile = fd.getDirectory() + fd.getFile();

			label.setText(fd.getFile() + " loaded");

			File f = new File(openFile);

			SAXBuilder sx = new SAXBuilder();

			try {
				doc = sx.build(f);
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			xRoot = doc.getRootElement().getChild("dict");
			List list = xRoot.getChildren();

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				if (node.getText().equals("Paths")) {
					xPath = (Element) list.get(i + 1);
					i = list.size() + 1;
				}
			}
			
			// List<Element> cList = xRoot.getContent

		} else if (arg0.getSource() == button2) {
			// Write GFF file

			if (fText.getText() != "") {
				Element xKey = new Element("key");
				Element xArray = new Element("array");

				System.out.printf(fText.getText());
				String s = fText.getText();

				xKey.setText(s);
				xPath.addContent(xKey);
				xPath.addContent(xArray);

				for (int i = 0; i < drawpanel.curve.pts.npoints; i++) {

					Element xString = new Element("string");

					xString.setText(String
							.format("{ %d,%d }",
									drawpanel.curve.pts.xpoints[i] -100,
											(520 -drawpanel.curve.pts.ypoints[i]) - 100));

					xArray.addContent(xString);

					System.out.print("iteration ran");

				}

				// Create the document
				XMLOutputter xOut = new XMLOutputter();

				try {
					xOut.output(doc, new FileWriter(fileName));

					System.out.printf("Written to %s", fileName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				label.setText(fText.getText() + " added to plist");
			}
			else{
				label.setText("Specify a name!");
		
			}

		}
	}

}

class PListFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return false;
	}
}

class DrawPanel extends JPanel {

	protected ControlCurve curve;
	protected Color bgcolor;
	protected MouseEvent mouseEvent;

	public DrawPanel() {

		super();

		this.build();
	}

	public void build() {

		Color bgcolor = Color.BLACK;

		/* dynamically load the class for the curve basis */
		try {
			String curveName = "CatmullRom";

			curve = (ControlCurve) Class.forName(curveName).newInstance();
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found: " + e.getMessage());
		} catch (InstantiationException e) {
			System.err.println("Couldn't Instance " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.err.println("Couldn't Instance " + e.getMessage());
		}
		/* initial control points from parameter controlPoints */
		String s = "33 189 54 39 182 42 249 113";
		if (s != null) {
			StringTokenizer st = new StringTokenizer(s, " \t");
			try {
				while (st.hasMoreTokens()) {
					int x = Integer.parseInt(st.nextToken());
					int y = Integer.parseInt(st.nextToken());
					curve.addPoint(x, y);
				}
			} catch (NoSuchElementException e) {
				System.err.println("Bad controlPoints parameter "
						+ e.getMessage());
			} catch (NumberFormatException e) {
				System.err.println("Bad controlPoints parameter "
						+ e.getMessage());
			}
		}

		setLayout(new BorderLayout(0, 0));
		mouseEvent = new MouseEvent();
		CurveCanvas canvas = new CurveCanvas(mouseEvent, curve);
		add("Center", canvas);
		Thread canvasThread = new Thread(canvas);
		canvasThread.start();
		canvasThread.setPriority(Thread.MIN_PRIORITY);

	}

	public boolean handleEvent(Event e) {
		if (e.id == Event.MOUSE_DOWN || e.id == Event.MOUSE_DRAG
				|| e.id == Event.MOUSE_UP) {
			mouseEvent.put(e);
			return true;
		} else {
			return false;
		}
	}

}
