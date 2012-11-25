package GameScene;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameScene.Block.Block;

public class GameScoreLayer extends JPanel
{
	JTextField [][]nextBlockView;
	JLabel scoreText;
	public GameScoreLayer(Dimension size)
	{
		setLayout(null);
		setBounds(size.width/4+Block.blockEdge*20, 0, size.width-size.width/4-Block.blockEdge*20, size.height);
		
		scoreText=new JLabel("得分：0",JLabel.CENTER);

		scoreText.setForeground(Color.red);
		scoreText.setBounds(10, 400, 300, 300);
		scoreText.setFont(new Font(scoreText.getFont().getFontName(),scoreText.getFont().getStyle(),40));

		add(scoreText);
		
		nextBlockView=new JTextField[4][4];
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
			{
				nextBlockView[i][j]=new JTextField();
				nextBlockView[i][j].setEditable(false);
				nextBlockView[i][j].setBackground(this.getBackground());
				nextBlockView[i][j].setBounds(150-j*Block.blockEdge, 150-i*Block.blockEdge, Block.blockEdge, Block.blockEdge);
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

	public void setScore(int score)
	{
		scoreText.setText("得分："+new Integer(score).toString());
	}
}
