package MainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import GameScene.GameScene;


public class MainFrame extends JFrame
{
	MainFrame frame=this;
	public MainFrame()
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(scrSize.width/2-250, scrSize.height/2-350, 500, 700);
		setLayout(null);
		
		ImageIcon img = new ImageIcon("Resource/MainFrame/MainBackground.jpg");
		JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        
		JButton startGame;
		startGame=new JButton("¿ªÊ¼ÓÎÏ·");	
		startGame.setBounds(150, 100, 200, 200);
		startGame.setFont(new Font(startGame.getFont().getFontName(),startGame.getFont().getStyle(),40));
		startGame.setMargin(new Insets(1,1,1,1));
		startGame.setForeground(Color.white);
		startGame.setBorderPainted(false);
		startGame.setContentAreaFilled(false);
		startGame.setFocusPainted(false);

		startGame.addActionListener(startGameButtonPress);
		add(startGame);
		add(imgLabel);
		setResizable(false);
		setVisible(true);
	}
	ActionListener startGameButtonPress = new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			frame.dispose();
			new GameScene();
		}				
	};
}
