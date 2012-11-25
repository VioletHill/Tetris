package GameScene.Block;

import java.awt.Color;

public class PointBlock extends Block
{
	public PointBlock()
	{
		blockShape=BlockShape.POINT;
		blockForm=0;
		blockView=2;			//0000 0010
		blockColor=Color.ORANGE;
		blockPosition.row=1;
		blockPosition.column=6;
		addBlock();
	}
	public void changeForm()
	{
		return ;
	}
}
