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
import javax.swing.JPanel;

import GameScene.GameScene;


public class MainFrame extends JFrame
{
	MainFrame frame=this;
	JPanel mainPanel;
	JPanel selectPanel;
	int select=0;
	void setButton(JButton button)
	{
		button.setFont(new Font(button.getFont().getFontName(),button.getFont().getStyle(),40));
		button.setMargin(new Insets(1,1,1,1));
		button.setForeground(Color.white);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		
	}
	
	void setSelectPanel()
	{
		selectPanel=new JPanel();
		selectPanel.setOpaque(false);
		selectPanel.setBounds(0, 0, 500, 700);
		selectPanel.setLayout(null);
		
		JButton easy=new JButton("简单游戏");
		setButton(easy);
		easy.setBounds(150, 100, 200, 200);
		easy.addActionListener(setSelectListener);
		selectPanel.add(easy);
		
		JButton mid=new JButton("中等游戏");
		setButton(mid);
		mid.setBounds(150,250,200,200);
		mid.addActionListener(setSelectListener);
		selectPanel.add(mid);
		
		JButton hard=new JButton("困难游戏");
		setButton(hard);
		hard.setBounds(150, 400, 200, 200);
		hard.addActionListener(setSelectListener);
		selectPanel.add(hard);
		
		selectPanel.setVisible(false);
	}
	
	void setMainPanel()
	{
		mainPanel=new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setBounds(0, 0, 500, 700);
		mainPanel.setLayout(null);
		JButton startGame;
		startGame=new JButton("开始游戏");	
		startGame.setBounds(150, 100, 200, 200);
		setButton(startGame);
		startGame.addActionListener(startGameButtonListener);

		
		JButton exitButton;
		exitButton=new JButton("退出游戏");
		exitButton.setBounds(150, 400, 200, 200);
		setButton(exitButton);
		exitButton.addActionListener(exitButtonListener);
		
		JButton selectButton;
		selectButton=new JButton("选择难度");
		selectButton.setBounds(150,250,200,200);
		setButton(selectButton);
		selectButton.addActionListener(selectButtonListener);
		
		mainPanel.add(startGame);
		mainPanel.add(selectButton);
		mainPanel.add(exitButton);	
	}
	
	public MainFrame()
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		
		ImageIcon img = new ImageIcon("Resource/MainFrame/MainBackground.jpg");
		JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)this.getContentPane();
        jp.setOpaque(false);
		

		setMainPanel();
		add(mainPanel);
		
		setSelectPanel();
		add(selectPanel);
	
		setResizable(false);
		setVisible(true);
	}
	void gameStart()
	{
		frame.dispose();
		new GameScene(select);
	}
	ActionListener startGameButtonListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			gameStart();
		}				
	};
	ActionListener exitButtonListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	};
	ActionListener selectButtonListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			mainPanel.setVisible(false);
			selectPanel.setVisible(true);
			frame.repaint();
		}
	};
	ActionListener setSelectListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton button=(JButton)e.getSource();
			if (button.getText()=="简单游戏")
			{
				select=0;
			}
			else if (button.getText()=="中等游戏")
			{
				select=8;
			}
			else
			{
				select=15;
			}
			gameStart();
		}
	};
}
