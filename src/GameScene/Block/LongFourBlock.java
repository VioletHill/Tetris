package GameScene.Block;

import java.awt.Color;

public class LongFourBlock extends Block
{
	public LongFourBlock()
	{
		blockShape=BlockShape.LONG_FOUR;
		blockForm=0;
		blockView=240;			//11110000
		blockColor=Color.green;
		blockPosition.row=1;
		blockPosition.column=6;
		addBlock();
	}
	public boolean checkForm()
	{
		return super.checkForm(30708);	//0100 0100 0100 0100
	}
	public void changeForm()
	{
		if (blockForm==0)
		{
			if (!checkForm()) return ;
			deleteBlock();
			blockForm=1;
			blockView=17476;	     //0100 0100 0100 0100
			addBlock();
		}
		else if (blockForm==1)
		{
			if (!checkForm()) return ;
			deleteBlock();
			blockForm=0;
			blockView=240;
			addBlock();
		}
	}
}
