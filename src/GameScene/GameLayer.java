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
	AudioClip deleteBlockSound=Applet.newAudioClip(Music.class.getResource("deleteBlock.wav"));
	
	void playDeleteBlockSound()
	{
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
	int nextLevel;
	Dimension scrSize;
	Timer timer=null;
	JTextField userName;
	boolean isNeedSetScore;
	JButton resumeButton=new JButton("������Ϸ");
	
	public GameLayer(Dimension size,GameScene gameScene)
	{
		this.gameScene=gameScene;
		
		setLayout(null);
		scrSize=size;
		setBackground(Color.white);
		setBounds(scrSize.width/4, (scrSize.height-Block.blockEdge*gameLayerHeight)/2, Block.blockEdge*gameLayerWide, Block.blockEdge*gameLayerHeight);
		Block.gameLayer=this;
		
		resumeButton.setBounds(0, 0, Block.blockEdge*gameLayerWide, Block.blockEdge*gameLayerHeight);
		resumeButton.setBackground(Color.white);
		resumeButton.setForeground(Color.black);
		resumeButton.setMargin(new Insets(1,1,1,1));
		resumeButton.setFont(new Font(resumeButton.getFont().getFontName(),resumeButton.getFont().getStyle(),100));
		resumeButton.setFocusable(false);
		resumeButton.addActionListener(Resume);
		gameStart();
		
	}
	
	void gameStart()
	{
		removeAll();
		repaint();
		for (int i=0; i<gameLayerHeight; i++)
			for (int j=0; j<gameLayerWide; j++)
				blockView[i][j]=null;
		for (int i=0; i<gameLayerHeight; i++)
			view[i]=0;
		
		Block.speed=1000;
		score=0;
		nextLevel=10;
		
		isPause=false;
		isGameOver=false;
		//createFirstBlock
		Random random = new Random();
		int nowBlock=Math.abs( random.nextInt() )%Block.totalBlcok;
		createBlock(nowBlock);
		getNextBlock();
		
		//��������ʱ��
		timer=new Timer();
		timer.schedule(new FallDown(), Block.speed,Block.speed);	

		//���ü�����
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
		music.playDeleteBlockSound();
		switch (row)
		{
		case 1:
			score++;
			break;
		case 2:
			score+=3;
			break;
		case 3:
			score+=6;
			break;
		case 4:
			score+=16;
			break;
		default:
			score+=20;
			break;
		}
		gameScene.gameScoreLayer.setScore(score);
		if (score>=nextLevel)
		{
			timer.cancel();
			timer=null;
			if (Block.speed>100) Block.speed-=100;
			nextLevel+=10;
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
		JLabel gameOverText=new JLabel("��Ϸ����",JLabel.CENTER);
		gameOverText.setBounds(0,20,  Block.blockEdge*gameLayerWide, 200);
		gameOverText.setBackground(Color.white);
		gameOverText.setBorder(null);
		gameOverText.setFont(new Font(gameOverText.getFont().getFontName(),gameOverText.getFont().getStyle(),50));
		add(gameOverText);
		
		isNeedSetScore=false;
		if (gameScene.gameMenuLayer.isEnterHighScore(score))
		{
			isNeedSetScore=true;
			gameOverText.setText("��ϲ�㣬�����˸߷ְ�");
			userName=new JTextField("��������������Ĵ���");
			userName.setHorizontalAlignment(JTextField.CENTER);
			userName.setFont(new Font(userName.getFont().getFontName(),userName.getFont().getStyle(),40));
			userName.setBounds(100, 200,  400, 100);
			add(userName);
		}
		
		JButton replay=new JButton ("����һ��");
		JButton returnMain=new JButton ("�������˵�");
		replay.setBackground(Color.white);
		replay.setBounds(200, 500, 200, 100);
		replay.addActionListener(Replay);
		replay.setMargin(new Insets(1,1,1,1));
		replay.setFont(new Font(replay.getFont().getFontName(),replay.getFont().getStyle(),30));
		replay.setBorderPainted(false);
		replay.setFocusPainted(false);
		add(replay);
		
		returnMain.setBackground(Color.white);
		returnMain.setBounds(200, 600, 200, 100);
		returnMain.addActionListener(ReturnMain);
		returnMain.setMargin(new Insets(1,1,1,1));
		returnMain.setFont(new Font(returnMain.getFont().getFontName(),returnMain.getFont().getStyle(),30));
		returnMain.setBorderPainted(false);
		returnMain.setFocusPainted(false);
		add(returnMain);
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
		add(resumeButton);
		resumeButton.setVisible(true);
		repaint();
	}
	
	public void resume()
	{
		if (!isPause) return ;
		isPause=false;	
		this.remove(resumeButton);
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



