package GameScene.Block;

import java.awt.Color;

public class SquareBlock extends Block
{
	public SquareBlock()
	{
		blockShape=BlockShape.SQUARE;
		blockForm=0;
		blockView=102;			//0110 0110
		blockColor=Color.blue;
		blockPosition.row=1;
		blockPosition.column=6;
		addBlock();
	}
	public void changeForm()
	{
		return ;
	}
}
