package GameScene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GameMenuLayer extends JPanel
{
	
	public static GameScene gameScene;
	
	public MainPanel mainPanel;
	public HighScorePanel highScorePanel;
	public SettingPanel settingPanel;
	boolean isPause=false;
	Dimension scrSize;
	
	GameMenuLayer(Dimension size,GameScene mainGameScene)
	{
		setOpaque(false);
		setBounds(0, 0, size.width/4, size.height);
		scrSize=size;
		gameScene=mainGameScene;
		this.setLayout(null);
		mainPanel=new MainPanel();
		add(mainPanel);
		
		highScorePanel=new HighScorePanel();
		add(highScorePanel);
		
		settingPanel=new SettingPanel();
		add(settingPanel);
	}
	public void setButton(JButton button)
	{
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
	}
	
	public boolean isEnterHighScore(int userScore)
	{
		try 
		{
			return highScorePanel.isEnterHighScore(userScore);
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	public void setScore(int userScore,String userName)
	{
		try 
		{
			highScorePanel.setScore(userScore, userName);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	class MainPanel extends JPanel
	{
		JButton pauseButton;
		MainPanel()
		{
			setOpaque(false);
			setBounds(0, 0, scrSize.width/4, scrSize.height);
			setLayout(new GridLayout(4,1));
			
			pauseButton=new JButton (new ImageIcon("Resource/Button/pause.png"));
			setButton(pauseButton);
			pauseButton.addActionListener(pauseButtonListener);
			add(pauseButton);
			
			JButton highScore=new JButton(new ImageIcon("Resource/Button/highScore.png"));
			setButton(highScore);
			highScore.addActionListener(highScoreButtonListener);
			add(highScore);
			
			JButton setting=new JButton(new ImageIcon("Resource/Button/setting.png"));
			setButton(setting);
			setting.addActionListener(settingButtonListener);
			add(setting);
			
			JButton returnMain=new JButton(new ImageIcon("Resource/Button/exit.png"));
			returnMain.setBounds(100, 100, 260, 100);
			setButton(returnMain);
			returnMain.addActionListener(returnMainButtonListener);
			add(returnMain);
		}
		
		ActionListener pauseButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (!isPause)
				{
					gameScene.gameLayer.pause();
					if (!gameScene.gameLayer.isGameOver)
					{
						isPause=true;
						pauseButton.setIcon(new  ImageIcon("Resource/Button/resume.png"));
					}
				}
				else
				{
					gameScene.gameLayer.resume();
					pauseButton.setIcon(new  ImageIcon("Resource/Button/pause.png"));
					isPause=false;
				}
			}				
		};
		
		ActionListener settingButtonListener= new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mainPanel.setVisible(false);
				settingPanel.setVisible(true);
				repaint();
				gameScene.requestFocus();
			}
		};
		ActionListener highScoreButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				try {
					highScorePanel.getScore();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				mainPanel.setVisible(false);
				highScorePanel.setVisible(true);
				repaint();
				gameScene.requestFocus();
			}				
		};
		
		ActionListener returnMainButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gameScene.gameLayer.returnMain();
			}				
		};

	}
	
	class HighScorePanel extends JPanel
	{
		final int totScore=10;
		HighScorePanel()
		{
			setOpaque(false);
			setBounds(0, 0, scrSize.width/4, scrSize.height);
			setLayout(null);
			setVisible(false);
		}
		
		void initializeFile() throws FileNotFoundException, IOException
		{
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(new File("hightScore.txt")));
			int []scorePlace=new int[totScore];
			String []namePlace=new String[totScore];
			for (int i=0; i<10; i++)
			{
				scorePlace[i]=0;
				namePlace[i]=new String("无名大侠");
			}
			out.writeObject(namePlace);
			out.writeObject(scorePlace);
			out.close();
		}
		
		public void getScore() throws IOException, ClassNotFoundException
		{
			File file=new File("hightScore.txt");
			if (!file.exists())
			{
				file.createNewFile();
				initializeFile();
			}
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			String []namePlace=(String[])in.readObject();
			int []scorePlace=(int [])in.readObject();
			in.close();
			
			//修改名字长度
			for (int i=0; i<totScore; i++)
			{
				namePlace[i]=namePlace[i];
				if (namePlace[i].length()>6) namePlace[i]=namePlace[i].substring(0, 6);
				JLabel text=new JLabel(namePlace[i],JLabel.LEFT);
				text.setFont(new Font(text.getFont().getFontName(),text.getFont().getStyle(),20));
				text.setForeground(Color.blue);
				text.setBounds(80, i*38+125, 200, 38);
				add(text);
				
				JLabel score=new JLabel(new Integer(scorePlace[i]).toString(),JLabel.LEFT);
				score.setFont(text.getFont());
				score.setForeground(Color.red);
				score.setBounds(220, i*38+125, 100,38);
				add(score);
			}
			
			JLabel img=new JLabel(new ImageIcon("Resource/highScoreWin.png"),JLabel.LEFT);
			img.setBounds(0, 0, 450, 600);
			add(img);
			
			JButton returnMainButton=new JButton(new ImageIcon("Resource/Button/back.png"));
			setButton(returnMainButton);
			returnMainButton.setBounds(170, 600, 200, 200);
			returnMainButton.addActionListener(lastPanelButtonListener);
			add(returnMainButton);
		}
		
		//判断是否进入高分榜
		public boolean isEnterHighScore(int userScore) throws IOException, ClassNotFoundException
		{
			File file=new File("hightScore.txt");
			if (!file.exists())
			{
				file.createNewFile();
				initializeFile();
			}
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			
			String []namePlace=(String[])in.readObject();
			int []scorePlace=(int [])in.readObject();
			in.close();
			
			for (int i=0; i<totScore; i++)
			{
				if (userScore>=scorePlace[i]) return true;
			}
			return false;
		}
		
		//用userName 的 userScore 刷新高分榜
		public void setScore(int userScore,String userName) throws FileNotFoundException, IOException, ClassNotFoundException
		{
			File file=new File("hightScore.txt");
			if (!file.exists())
			{
				file.createNewFile();
				initializeFile();
			}
			
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			
			String []namePlace=(String[])in.readObject();
			int []scorePlace=(int [])in.readObject();
			in.close();	
			
			for (int i=0; i<totScore; i++)
			{
				if (userScore>=scorePlace[i])
				{
					for (int j=totScore-1; j>i; j--)
					{
						scorePlace[j]=scorePlace[j-1];
						namePlace[j]=namePlace[j-1];
					}
					scorePlace[i]=userScore;
					namePlace[i]=userName;
					break;
				}
			}
			
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(namePlace);
			out.writeObject(scorePlace);
			out.close();

		}
		ActionListener lastPanelButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				highScorePanel.setVisible(false);
				highScorePanel.removeAll();
				mainPanel.setVisible(true);
			}				
		};
	}
	
	class SettingPanel extends JPanel
	{
		JButton musicButton[];

		SettingPanel()
		{
			setOpaque(false);
			setBounds(0, 0, scrSize.width/4, scrSize.height);
			setLayout(null);
			JLabel title=new JLabel(new ImageIcon("Resource/Button/setting.png"));
			title.setBounds(0, 0, 300, 200);
			add(title);
			
			musicButton=new JButton[4];
			for (int i=0; i<4; i++)
			{
				musicButton[i]=new JButton(new ImageIcon("Resource/"+"music"+new Integer(i+1).toString()+".png"));
				musicButton[i].setBounds(50, 100*i+150, 200, 100);
				musicButton[i].setBorderPainted(false);
				musicButton[i].setFocusPainted(false);
				musicButton[i].setContentAreaFilled(false);
				musicButton[i].addActionListener(settingMusicButtonListener);
				add(musicButton[i]);
			}
			
			JButton closeMusicButton=new JButton(new ImageIcon("Resource/closeMusic.png"));
			closeMusicButton.setBounds(50, 100*4+150, 200, 100);
			closeMusicButton.setBorderPainted(false);
			closeMusicButton.setFocusPainted(false);
			closeMusicButton.setContentAreaFilled(false);
			closeMusicButton.addActionListener(closeMusicButtonListener);
			add(closeMusicButton);
			
			JButton returnMainButton=new JButton(new ImageIcon("Resource/Button/back.png"));
			returnMainButton.setBorderPainted(false);
			returnMainButton.setContentAreaFilled(false);
			returnMainButton.setFocusPainted(false);
			returnMainButton.addActionListener(lastPanelButtonListener);
			returnMainButton.setBounds(170, 600, 200, 200);
			
			add(returnMainButton);
			setVisible(false);
		}
		ActionListener settingMusicButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gameScene.gameLayer.music.isCloseMusic=false;
				for (int i=0; i<4; i++)
				{
					if (e.getSource()==musicButton[i])
						gameScene.backgroundMusic.selectMusic(new Integer(i+1).toString());
				}
				gameScene.requestFocus();
			}				
		};
		
		ActionListener closeMusicButtonListener=new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gameScene.backgroundMusic.stopBackgroundMusic();
				gameScene.gameLayer.music.isCloseMusic=true;
				gameScene.requestFocus();
			}
		};
		ActionListener lastPanelButtonListener=new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				settingPanel.setVisible(false);
				mainPanel.setVisible(true);
			}		
		};
	}
	
}


