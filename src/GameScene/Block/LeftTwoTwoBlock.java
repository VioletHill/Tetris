package GameScene.Block;

import java.awt.Color;

public class LeftTwoTwoBlock extends Block
{
	public LeftTwoTwoBlock()
	{
		blockShape=BlockShape.LEFT_TWO_TWO;
		blockForm=0;
		blockView=99;			//0110 0011
		blockColor=Color.black;
		blockPosition.row=1;
		blockPosition.column=6;
		addBlock();
	}
	public boolean checkForm(int form)
	{
		return super.checkForm(form);	
	}
	public void changeForm()
	{
		if (blockForm==0)
		{
			if (!checkForm(1907)) return ;	//0111 0111 0011
			deleteBlock();
			blockForm=1;
			blockView=306;					//0001 0011 0010
			addBlock();
		}
		else if (blockForm==1)				
		{
			if (!checkForm(1907)) return ;	//0111 0111 0011
			deleteBlock();
			blockForm=0;
			blockView=99;				//0110 0011
			addBlock();
		}
	}
}
