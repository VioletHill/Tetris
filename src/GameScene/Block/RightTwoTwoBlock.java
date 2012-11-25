package GameScene.Block;

import java.awt.Color;

public class RightTwoTwoBlock extends Block
{
	public RightTwoTwoBlock()
	{
		blockShape=BlockShape.RIGHT_TWO_TOW;
		blockForm=0;
		blockView=54;			//0011 0110
		blockColor=Color.darkGray;
		blockPosition.row=1;
		blockPosition.column=6;
		addBlock();
	}
	public void changeForm()
	{
		if (blockForm==0)
		{
			if (!checkForm(1910)) return ;	//0111 0111 0110
			deleteBlock();
			blockForm=1;
			blockView=1122;					//0100 0110 0010
			addBlock();
		}
		else if (blockForm==1)				
		{
			if (!checkForm(1910)) return ;	//0111 0111 0110
			deleteBlock();
			blockForm=0;
			blockView=54;					//0011 0110
			addBlock();
		}
	}
}
