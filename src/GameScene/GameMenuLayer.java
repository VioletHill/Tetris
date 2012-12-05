package GameScene;

import java.applet.Applet;
import java.applet.AudioClip;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMenuLayer extends JPanel
{
	
	public static GameScene gameScene;
	
	public MainPanel mainPanel;
	public HighScorePanel highScorePanel;
	Dimension scrSize;
	
	GameMenuLayer(Dimension size,GameScene mainGameScene)
	{
		setBounds(0, 0, size.width/4, size.height);
		scrSize=size;
		gameScene=mainGameScene;
		this.setLayout(null);
		mainPanel=new MainPanel();
		add(mainPanel);
		
		highScorePanel=new HighScorePanel();
		add(highScorePanel);
		highScorePanel.setVisible(false);
	}
	public void setButton(JButton button)
	{
		button.setFont(new Font(button.getFont().getFontName(),button.getFont().getStyle(),40));
		button.setMargin(new Insets(1,1,1,1));
		button.setForeground(Color.red);
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
			setBounds(0, 0, scrSize.width/4, scrSize.height);
		//	setLayout(new GridLayout(4,1));
			
		
			
			System.out.print(this.size().width);
			System.out.print(this.size().height);
			pauseButton=new JButton ("暂停游戏");
			setButton(pauseButton);
			pauseButton.addActionListener(pauseButtonListener);
			add(pauseButton);
			
			JButton highScore=new JButton("高分榜");
			setButton(highScore);
			highScore.addActionListener(highScoreButtonListener);
			add(highScore);
			
			JButton returnMain=new JButton("主菜单");
			setButton(returnMain);
			returnMain.addActionListener(returnMainButtonListener);
			add(returnMain);
		}
		
		ActionListener pauseButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (pauseButton.getText().contentEquals("暂停游戏"))
				{
					gameScene.gameLayer.pause();
					if (!gameScene.gameLayer.isGameOver)pauseButton.setText("继续游戏");
				}
				else
				{
					gameScene.gameLayer.resume();
					pauseButton.setText("暂停游戏");
				}
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
			setBounds(0, 0, scrSize.width/4, scrSize.height);
			setLayout(new GridLayout(12,1));
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

			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			String []namePlace=(String[])in.readObject();
			int []scorePlace=(int [])in.readObject();
			in.close();
			
			
			JLabel title=new JLabel("游戏高分榜",JLabel.CENTER);
			title.setFont(new Font(title.getFont().getFontName(),title.getFont().getStyle(),40));
			title.setForeground(Color.red);
			add(title);
			for (int i=0; i<totScore; i++)
			{
				namePlace[i]=namePlace[i]+"                    ";
				if (namePlace[i].length()>10) namePlace[i]=namePlace[i].substring(0, 10);
				JLabel text=new JLabel("  "+new Integer(i+1).toString()+"  :  "+namePlace[i]+"    "+new Integer(scorePlace[i]).toString(),JLabel.LEFT);
				text.setFont(new Font(text.getFont().getFontName(),text.getFont().getStyle(),20));
				add(text);
			}
			JButton returnMainButton=new JButton("返回上一层");
			setButton(returnMainButton);
			returnMainButton.addActionListener(LastPanelButtonListener);
			add(returnMainButton);
		}
		
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
		ActionListener LastPanelButtonListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				highScorePanel.setVisible(false);
				highScorePanel.removeAll();
				mainPanel.setVisible(true);
				repaint();
			}				
		};
	}
	
	
}


