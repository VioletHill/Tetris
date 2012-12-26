package GameScene;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameScene.Block.Block;
import GameScene.Block.Block.BlockShape;
import GameScene.Block.LeftOneThreeBlock;
import GameScene.Block.LeftTwoTwoBlock;
import GameScene.Block.LongFourBlock;
import GameScene.Block.MiddleOneThreeBlock;
import GameScene.Block.PointBlock;
import GameScene.Block.RightOneThreeBlock;
import GameScene.Block.RightTwoTwoBlock;
import GameScene.Block.SquareBlock;
import MainFrame.MainFrame;

class Music extends Applet
{
	AudioClip deleteBlockSound;
	boolean isCloseMusic=false;
	Music()
	{
		URL url=null;
		try 
		{
			File file=new File("Resource/Music/deleteBlock.wav");
			url=new URL("file:"+file.getAbsolutePath());
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		deleteBlockSound=Applet.newAudioClip(url);
	}
	void playDeleteBlockSound()
	{
		if (isCloseMusic) return ;
		deleteBlockSound.play();
	}
}

public class GameLayer extends JPanel
{
	
	GameScene gameScene;
	public static final int gameLayerWide=20;
	public static final int gameLayerHeight=25;
	public int []view=new int [gameLayerHeight];
	public JTextField [][]blockView=new JTextField[gameLayerHeight][gameLayerWide];
	
	Music music=new Music();
	boolean isPause;
	boolean isGameOver;
	Block superBlock;
	int nextBlock;
	int score;
	int deleteCount;
	int nextLevel;
	Dimension scrSize;
	Timer timer=null;
	JTextField userName;
	int select;
	boolean isNeedSetScore;
	
	private Color getRandomColor() 
	{
		float r = (float) (Math.random() * 60)+10;
		float y = (float) (Math.random() * 60)+10;
		float b = (float) (Math.random() * 60)+10;
		return Color.getHSBColor(r, y, b);
	}
	
	void startView(int hard)
	{
		for (int i=0; i<hard; i++)
			addRow();
	}
	
	void addRow()
	{
		if (view[0]!=0)
		{
			gameOver();
			return ;
		}
		
		for (int i=1; i<gameLayerHeight; i++)
		{
			for (int k=0; k<gameLayerWide; k++)
			{
				if ((view[i] & (1<<k))==0 ) continue;
				blockView[i][k].setLocation(blockView[i][k].getLocation().x, blockView[i][k].getLocation().y-Block.blockEdge);
				blockView[i-1][k]=blockView[i][k];
				blockView[i][k]=null;
			}
			view[i-1]=view[i];
			view[i]=0;
		}

		view[gameLayerHeight-1]=Math.abs( new Random().nextInt() )%((1<<gameLayerWide)-5)+1;
		for (int j=0; j<gameLayerWide; j++)
		{
			if ( (view[gameLayerHeight-1] & (1<<j) )!=0)
			{
				blockView[gameLayerHeight-1][j]=new JTextField();
				blockView[gameLayerHeight-1][j].setBackground(getRandomColor());
				blockView[gameLayerHeight-1][j].setEditable(false);
				blockView[gameLayerHeight-1][j].setBounds(j*Block.blockEdge, (gameLayerHeight-1)*Block.blockEdge, Block.blockEdge, Block.blockEdge);
				add(blockView[gameLayerHeight-1][j]);
			}
		}
		repaint();
	}
	
	void initialize()
	{
		removeAll();
		repaint();
		for (int i=0; i<gameLayerHeight; i++)
			for (int j=0; j<gameLayerWide; j++)
				blockView[i][j]=null;
		for (int i=0; i<gameLayerHeight; i++)
			view[i]=0;
		
		startView(select);
		Block.speed=1000;
		score=0;
		deleteCount=0;
		nextLevel=5000;
		
		isPause=false;
		isGameOver=false;
	}
	
	public GameLayer(Dimension size,GameScene gameScene,int userSelect)
	{
		select=userSelect;
		this.gameScene=gameScene;
		setOpaque(false);
				
		setLayout(null);
		scrSize=size;
		setBounds(scrSize.width/4, (scrSize.height-Block.blockEdge*gameLayerHeight)/2, Block.blockEdge*gameLayerWide, Block.blockEdge*gameLayerHeight);
		Block.gameLayer=this;
		
		gameStart();
	}
	
	void gameStart()
	{
		initialize();
		//createFirstBlock
		Random random = new Random();
		int nowBlock=Math.abs( random.nextInt() )%Block.totalBlcok;
		createBlock(nowBlock);
		getNextBlock();
		
		//增加下落时间
		timer=new Timer();
		timer.schedule(new FallDown(), Block.speed,Block.speed);	

		//设置监听器
		gameScene.addKeyListener(KeyPress);
		gameScene.requestFocusInWindow();
		gameScene.setFocusable(true);
	}
	
	void getNextBlock()
	{
		Random random = new Random();
		nextBlock=Math.abs( random.nextInt() ) %Block.totalBlcok;
		GameScene.gameScoreLayer.setNextblock(nextBlock);
	}
	
	void createBlock(int blockShape)
	{
		Block block=null;
		switch (blockShape)
		{
		case 0:
			block=new LongFourBlock();
			break;
		case 1:
			block=new LeftOneThreeBlock();
			break;
		case 2:
			block=new MiddleOneThreeBlock();
			break;
		case 3:
			block=new RightOneThreeBlock();
			break;
		case 4:
			block=new LeftTwoTwoBlock();
			break;
		case 5:
			block=new RightTwoTwoBlock();
			break;
		case 6:
			block=new PointBlock();
			break;
		default:
			block=new SquareBlock();	
		}
		superBlock=block;
		if (superBlock.checkIsGameOver()) gameOver();
	}
	
	void updateScore(int row)
	{
		if (row==0) return ;
		deleteCount+=row;
		music.playDeleteBlockSound();
		switch (row)
		{
		case 1:
			score+=100;
			break;
		case 2:
			score+=300;
			break;
		case 3:
			score+=600;
			break;
		case 4:
			score+=1600;
			break;
		default:
			score+=2000;
			break;
		}
		gameScene.gameScoreLayer.setScoreCount(score);
		gameScene.gameScoreLayer.setDeleteCount(deleteCount);
		if (score>=nextLevel)
		{
			if (Block.speed<=100) return ; 
			timer.cancel();
			timer=null;
			Block.speed-=100;
			gameScene.gameScoreLayer.setSpeedCount(nextLevel/5000);
			nextLevel+=5000;
			if (select==15) addRow();
			timer=new Timer();
			timer.schedule(new FallDown(), Block.speed,Block.speed);
		}
	}
	
	public void deleteBlock()
	{
		int deleteRow=0;
		for (int i=gameLayerHeight-1; i>=0; i--)
		{
			if (view[i]==((1<<gameLayerWide)-1))
			{
				deleteRow++;
				for (int j=0; j<gameLayerWide; j++)
				{
					remove(blockView[i][j]);
					blockView[i][j]=null;
				}
				for (int j=i-1; j>=0; j--)
				{
					for (int k=0; k<gameLayerWide; k++)
					{
						if ((view[j] & (1<<k))==0 ) continue;
						blockView[j][k].setLocation(blockView[j][k].getLocation().x, blockView[j][k].getLocation().y+Block.blockEdge);
						blockView[j+1][k]=blockView[j][k];
						blockView[j][k]=null;
					}
					view[j+1]=view[j];
					view[j]=0;
				}
				i++;
			}
		}
		repaint();
		updateScore(deleteRow);
	}
	
	public void newBlock()
	{
		createBlock(nextBlock);
		getNextBlock();
	}
	
	public void gameOver()
	{
		isGameOver=true;
		if (timer!=null)	timer.cancel();
		timer=null;
		removeAll();
		gameScene.removeKeyListener(KeyPress);
		JLabel gameOverText=new JLabel("游戏结束",JLabel.CENTER);
		gameOverText.setBounds(0,20,  Block.blockEdge*gameLayerWide, 200);
		gameOverText.setBackground(Color.white);
		gameOverText.setBorder(null);
		gameOverText.setFont(new Font(gameOverText.getFont().getFontName(),gameOverText.getFont().getStyle(),50));
		add(gameOverText);
		
		isNeedSetScore=false;
		if (gameScene.gameMenuLayer.isEnterHighScore(score))
		{
			isNeedSetScore=true;
			gameOverText.setText("恭喜你，进入了高分榜");
			gameOverText.setForeground(Color.white);
			userName=new JTextField("请在这里输入你的大名",JTextField.CENTER);
			
			userName.setFont(new Font(userName.getFont().getFontName(),userName.getFont().getStyle(),40));
			userName.setBounds(100, 200,  400, 100);
			add(userName);
		}
		
		JButton replay=new JButton ("再来一局");
		JButton returnMain=new JButton ("返回主菜单");
		replay.setForeground(Color.white);
		replay.setBounds(200, 500, 200, 100);
		replay.addActionListener(Replay);
		replay.setMargin(new Insets(1,1,1,1));
		replay.setFont(new Font(replay.getFont().getFontName(),replay.getFont().getStyle(),30));
		replay.setBorderPainted(false);
		replay.setFocusPainted(false);
		replay.setContentAreaFilled(false);
		add(replay);
		
		returnMain.setBounds(200, 600, 200, 100);
		returnMain.addActionListener(ReturnMain);
		returnMain.setForeground(Color.white);
		returnMain.setMargin(new Insets(1,1,1,1));
		returnMain.setFont(new Font(returnMain.getFont().getFontName(),returnMain.getFont().getStyle(),30));
		returnMain.setBorderPainted(false);
		returnMain.setFocusPainted(false);
		returnMain.setContentAreaFilled(false);
		add(returnMain);
		repaint();
	}
	
	
	
	void rePlay()
	{
		gameStart();	
	}
	
	void returnMain()
	{
		if (timer!=null)
		{
			timer.cancel();
			timer=null;
		}
		new MainFrame();
		gameScene.backgroundMusic.stopBackgroundMusic();
		gameScene.dispose();
	}
	
	public void pause()
	{
		if (isGameOver) return ;
		if (isPause) return ;
		isPause=true;
		if (timer!=null)	timer.cancel();
		timer=null;
		gameScene.removeKeyListener(KeyPress);
		gameScene.setFocusable(false);

		repaint();
	}
	
	public void resume()
	{
		if (!isPause) return ;
		isPause=false;	
		repaint();
		
		gameScene.setFocusable(true);
		gameScene.addKeyListener(KeyPress);
		gameScene.requestFocus();
		repaint();
		timer=new Timer();
		timer.schedule(new FallDown(), Block.speed,Block.speed);	
	}
	
	class FallDown extends TimerTask
	{
		public void run()
		{
			superBlock.downMove();
		}
	}
	
	KeyListener KeyPress=new KeyListener()
	{
		public void keyTyped(KeyEvent e) 
		{ 
           
        } 
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				superBlock.leftMove();
			}
			if (e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				superBlock.rightMove();
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				superBlock.downMove();
			}
			if (e.getKeyCode()==KeyEvent.VK_UP)
			{
				if (Block.isBlockMove) return ;
				Block.isBlockMove=true;
				if (superBlock.whoAmI()==BlockShape.LONG_FOUR)
				{
					((LongFourBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.SQUARE)
				{
					((SquareBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.LEFT_ONE_THREE)
				{
					((LeftOneThreeBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.RIGHT_ONE_THREE)
				{
					((RightOneThreeBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.MIDDLE_ONE_THREE)
				{
					((MiddleOneThreeBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.LEFT_TWO_TWO)
				{
					((LeftTwoTwoBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.RIGHT_TWO_TOW)
				{
					((RightTwoTwoBlock) superBlock).changeForm();
				}
				if (superBlock.whoAmI()==BlockShape.POINT)
				{
					((PointBlock) superBlock).changeForm();
				}
				Block.isBlockMove=false;
			}

		}
		public void keyReleased(KeyEvent e)
		{
		}

	};
	
	ActionListener Replay = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if (isNeedSetScore) gameScene.gameMenuLayer.setScore(score, userName.getText());
		    Block.gameLayer.rePlay();
		}				
	};
	
	ActionListener ReturnMain=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (isNeedSetScore) gameScene.gameMenuLayer.setScore(score, userName.getText());
			returnMain();
		}
	};
	
	ActionListener Resume=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			resume();
		}
	};
}




