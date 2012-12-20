package GameScene.Block;

import java.awt.Color;

import javax.swing.JTextField;

import GameScene.GameLayer;
import Math.MyMath;

public abstract class Block 
{
	public static boolean isBlockMove=false;
	public static final int blockEdge=30;
	public static final int totalBlcok=8;
	public static GameLayer gameLayer;
	public enum BlockShape
	{
		LONG_FOUR,				//----
		LEFT_ONE_THREE,			//|_ _ _
		MIDDLE_ONE_THREE,
		RIGHT_ONE_THREE,		//_ _ _|
		LEFT_TWO_TWO,
		RIGHT_TWO_TOW,
		POINT,
		SQUARE
	}
	
	public class BlockPosition
	{
		int row;
		int column;
		int getRowPosition()
		{
			return row*blockEdge;
		}
		int getRowPosition(int rowValue)
		{
			return  rowValue*blockEdge;
		}
		int getColumnPosition()
		{
			return column*blockEdge;
		}
		int getColumnPosition(int columnValue)
		{
			return columnValue*blockEdge;
		}

	}
	

	BlockShape blockShape;
	public static int speed;
	int blockView;
	JTextField []block;
	int blockForm;
	Color blockColor;
	BlockPosition blockPosition=new BlockPosition();
	
	public BlockShape whoAmI()
	{
		return blockShape;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int speedValue)
	{
		speed=speedValue;
	}
	
	void setPosition()
	{
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int i=num/4;
			int j=4-num%4;
			block[i*4+j].setBounds(blockPosition.getColumnPosition(blockPosition.column+j),blockPosition.getRowPosition(blockPosition.row-i), blockEdge, blockEdge);
			view=view-(view & (view ^ (view -1)));
		}
	}
	
	public boolean checkIsGameOver()
	{
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int blockRow=blockPosition.row-num/4;
			int blockColumn=blockPosition.column+4-num%4;
			if (blockRow<0) return true;
			if ( (gameLayer.view[blockRow] & ( 1<<(blockColumn) ) )!=0) return true;
			view=view-(view & (view ^ (view -1)));
		}
		return false;
	}
	
	boolean checkLeftMove()
	{
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int blockRow=blockPosition.row-num/4;
			int blockColumn=blockPosition.column+4-num%4;
			if (blockColumn==0) return false;
			if (blockRow<0) return false;
			if ( (gameLayer.view[blockRow] & ( 1<<(blockColumn-1) ) )!=0) return false;
			view=view-(view & (view ^ (view -1)));
		}
		return true;
	}
	public void leftMove()
	{
		if (isBlockMove) return ;
		isBlockMove=true;
		if (!checkLeftMove())
		{
			isBlockMove=false;
			return ;
		}
		blockPosition.column--;
		setPosition();
		isBlockMove=false;
	}
	
	boolean checkRightMove()
	{
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int blockRow=blockPosition.row-num/4;
			int blockColumn=blockPosition.column+4-num%4;
			if (blockColumn==GameLayer.gameLayerWide-1) return false;
			if (blockRow<0) return false;
			if ((gameLayer.view[blockRow] & ( 1<<(blockColumn+1) ) )!=0) return false;
			view=view-(view & (view ^ (view -1)));
		}
		return true;
	}
	
	public void rightMove()
	{
		if (isBlockMove) return ;
		isBlockMove=true;
		if (!checkRightMove())
		{
			isBlockMove=false;
			return ;
		}
		blockPosition.column++;
		setPosition();
		isBlockMove=false;
	}
	
	public boolean checkDownMove()
	{
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int blockRow=blockPosition.row-num/4;
			int blockColumn=blockPosition.column+4-num%4;
			if (blockRow==GameLayer.gameLayerHeight-1)	return false;
			if ((gameLayer.view[blockRow+1] & (1<<blockColumn))!=0) return false;
			view=view-(view & (view ^ (view -1)));
		}
		return true;
	}
	
	void setFallDown()
	{
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int blockRow=blockPosition.row-num/4;
			int blockColumn=blockPosition.column+4-num%4;
			gameLayer.view[blockRow]=gameLayer.view[blockRow]+(1<<blockColumn);
			gameLayer.blockView[blockRow][blockColumn]=block[num/4*4+4-num%4];
			view=view-(view & (view ^ (view -1)));
		}
	}
	
	public void downMove()
	{
		if (isBlockMove) return ;
		isBlockMove=true;
		if (!checkDownMove())
		{
			setFallDown();
			gameLayer.deleteBlock();
			gameLayer.newBlock();
		}
		else
		{
			blockPosition.row++;
			setPosition();
		}
		isBlockMove=false;
	}
	
	void addBlock()
	{
		if (block==null) block=new JTextField[16];
		int view=blockView;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int i=num/4;
			int j=4-num%4;
			block[i*4+j]=new JTextField();
			block[i*4+j].setBackground(blockColor);
			block[i*4+j].setEditable(false);
			gameLayer.add(block[i*4+j]);
			view=view-(view & (view ^ (view -1)));
		}
		setPosition();
	}
	
	public void deleteBlock()
	{
		int view=blockView;
		blockView=0;
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int i=num/4;
			int j=4-num%4;
			gameLayer.remove(block[i*4+j]);
			block[i*4+j]=null;
			view=view-(view & (view ^ (view -1)));
		}
		gameLayer.repaint();
	}
	
	public boolean checkForm(int view)
	{
		while (view!=0)
		{
			int num=MyMath.log2(view & (view ^ (view-1)));
			int blockRow=blockPosition.row-num/4;
			int blockColumn=blockPosition.column+4-num%4;
			if (blockRow<0 || blockRow>=GameLayer.gameLayerHeight) return false;
			if (blockColumn<0 || blockColumn>=GameLayer.gameLayerWide) return false;
			if ((gameLayer.view[blockRow] & (1<<blockColumn))!=0 ) return false;
			view=view-(view & (view ^ (view -1)));
		}
		return true;
	}
}

