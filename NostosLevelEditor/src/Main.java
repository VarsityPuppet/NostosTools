import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class Main {

	private static JFrame mainFrame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		mainFrame = new JFrame();
		mainFrame.add(new MainPanel());
		mainFrame.setSize(950, 500);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		System.out.printf("It at least ran");
	}
}

class MainPanel extends JLayeredPane{
	
	private static final int TOOLPANELWIDTH = 150;
	private static final int SCROLLPANEWIDTH = 800;
	private static final int FRAMEHEIGHT = 500;
	private static final int DRAWPANELHEIGHT = 2000;
	private static final Dimension dTool = new Dimension(TOOLPANELWIDTH, FRAMEHEIGHT );
	private static final Dimension dDraw = new Dimension(SCROLLPANEWIDTH - 100, DRAWPANELHEIGHT);
	private static final Dimension dView = new Dimension(SCROLLPANEWIDTH, DRAWPANELHEIGHT);
	private static final Dimension dScroll = new Dimension(SCROLLPANEWIDTH, FRAMEHEIGHT);
	private JPanel toolPanel;
	private JPanel viewPanel;
	private JScrollPane scrollPane;
	private JButton button1;
	private DrawPanel drawPanel;
	private Border border;
	private JComboBox comboBox;
	private JTextField jLevelName;
	private JButton button2;
	private JTextField jPathName;
	private JTextField jAIName;
	private JLabel jlbPathName;
	private JLabel jlbAIName;
	private JLabel jlbSelected;
	private JLabel jlbLevel;
	private ImageIcon imageAddEnemy;

	public MainPanel(){
		
		this.setLayout(new BorderLayout());
		

		drawPanel = new DrawPanel();
		toolPanel = new JPanel();
		viewPanel = new JPanel();
		border = BorderFactory.createEmptyBorder(50, 50, 50, 50);
		
		viewPanel.setLayout(new BorderLayout());
		viewPanel.setPreferredSize(dView);
		viewPanel.setBackground(Color.white);
		toolPanel.setSize(dTool);
		
		drawPanel.setSize(dDraw);
		drawPanel.setBackground(Color.black);
	
		viewPanel.setBorder(border);
		viewPanel.add(drawPanel, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane(viewPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setSize(dScroll);
		
		toolPanel.setBackground(Color.gray);
		
		setupToolBar();
		
		this.add(toolPanel, BorderLayout.WEST);
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	
	private void setupToolBar(){
		
		imageAddEnemy = new ImageIcon("Image/bugs_maggot.png");
		toolPanel.setLayout( new GridLayout(10,1,10,10) );
		button1 = new JButton("Open Level");
		button2 = new JButton("Open Level");
		String[] list = new String[]{"enemy 1", "enemy 2"};
		comboBox = new JComboBox(list);
		jlbLevel = new JLabel("No Level loaded");
		jLevelName = new JTextField();
		jlbSelected = new JLabel("Selected:");
		jlbPathName = new JLabel("Path");
		jPathName = new JTextField();
		jlbAIName = new JLabel("AI Profile");
		jAIName =  new JTextField();
		
		
		toolPanel.add(jlbLevel);
		toolPanel.add(button1);
		toolPanel.add(jLevelName);
		
		toolPanel.add(comboBox);
		
		toolPanel.add(new JLabel(imageAddEnemy));
		toolPanel.add(jlbSelected);
		toolPanel.add(jlbPathName);
		toolPanel.add(jPathName);
		toolPanel.add(jlbAIName);
		toolPanel.add(jAIName);
		
		
	}
}