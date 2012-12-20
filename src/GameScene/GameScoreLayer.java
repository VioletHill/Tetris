package GameScene;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameScene.Block.Block;

public class GameScoreLayer extends JPanel
{
	JTextField [][]nextBlockView;
	JLabel scoreCountText;
	JLabel deleteCountText;
	JLabel speedCountText;

	public GameScoreLayer(Dimension size)
	{
		setOpaque(false);
		setLayout(null);
		setBounds(size.width/4+Block.blockEdge*20, 0, size.width-size.width/4-Block.blockEdge*20, size.height);
		
		scoreCountText=new JLabel("000000",JLabel.CENTER);
		scoreCountText.setForeground(Color.red);
		scoreCountText.setBounds(110, 170, 300, 300);
		scoreCountText.setFont(new Font(scoreCountText.getFont().getFontName(),scoreCountText.getFont().getStyle(),40));
		add(scoreCountText);
		
		deleteCountText=new JLabel("0000",JLabel.CENTER);
		deleteCountText.setForeground(Color.green);
		deleteCountText.setBounds(120, 60, 300, 300);
		deleteCountText.setFont(new Font(deleteCountText.getFont().getFontName(),deleteCountText.getFont().getStyle(),40));
		add(deleteCountText);
		
		speedCountText=new JLabel("00",JLabel.CENTER);
		speedCountText.setForeground(Color.white);
		speedCountText.setFont(new Font(speedCountText.getFont().getFontName(),speedCountText.getFont().getStyle(),40));
		speedCountText.setBounds(70, 40, 300, 100);
		add(speedCountText);
		nextBlockView=new JTextField[4][4];
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
			{
				nextBlockView[i][j]=new JTextField();
				nextBlockView[i][j].setEditable(false);
				nextBlockView[i][j].setBackground(this.getBackground());
				nextBlockView[i][j].setBounds(180-j*Block.blockEdge, 580-i*Block.blockEdge, Block.blockEdge, Block.blockEdge);
				add(nextBlockView[i][j]);
			}
	}
	public void setNextblock(int nextBlockShape)
	{
		Color color;
		int view=0;
		switch (nextBlockShape)
		{
		case 0:
			view=240;
			color=Color.green;
			break;
		case 1:
			view=71;
			color=Color.pink;
			break;
		case 2:
			view=39;
			color=Color.yellow;
			break;
		case 3:
			view=23;
			color=Color.red;
			break;
		case 4:
			view=99;
			color=Color.black;
			break;
		case 5:
			view=54;
			color=Color.darkGray;
			break;
		case 6:
			view=2;
			color=Color.orange;
			break;
		default:
			view=102;
			color=Color.blue;
		}
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
			{
				if (	(view & (1<<(i*4+j))) !=0	)
				{
					nextBlockView[i][j].setBackground(color);
					nextBlockView[i][j].setVisible(true);
				}
				else 
				{
					nextBlockView[i][j].setVisible(false);
					nextBlockView[i][j].setBackground(this.getBackground());
				}
			}
	}

	public void setScoreCount(int score)
	{
		String str="000000"+new Integer(score).toString();
		scoreCountText.setText(str.substring(str.length()-6,str.length()));
	}
	
	public void setDeleteCount(int count)
	{
		String str="0000"+new Integer(count).toString();
		deleteCountText.setText(str.substring(str.length()-4,str.length()));
	}
	
	public void setSpeedCount(int speed)
	{
		String str="00"+new Integer(speed).toString();
		speedCountText.setText(str.substring(str.length()-2, str.length()));
	}
}
