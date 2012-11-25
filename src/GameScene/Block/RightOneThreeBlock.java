package GameScene.Block;

import java.awt.Color;

public class RightOneThreeBlock extends Block
{
	public RightOneThreeBlock()
	{
		blockShape=BlockShape.RIGHT_ONE_THREE;
		blockForm=0;
		blockView=23;			//0001 0111
		blockColor=Color.red;
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
			if (!checkForm(1143)) return ;	//0100 0111 0111
			deleteBlock();
			blockForm=1;
			blockView=1094;	     //0100 0100 0110
			addBlock();
		}
		else if (blockForm==1)
		{
			if (!checkForm(1910)) return ;	//0111 0111 0110
			deleteBlock();
			blockForm=2;
			blockView=116;					//0111 0001
			addBlock();
		}
		else if (blockForm==2)
		{
			if (!checkForm(1909)) return ;	//0111 0111 0101
			deleteBlock();
			blockForm=3;
			blockView=785;					//0011 0001 0001
			addBlock();
		}
		else if (blockForm==3)
		{
			if (!checkForm(823)) return ;	//0011 0011 0111
			deleteBlock();				
			blockForm=0;
			blockView=23;					//0001 0111
			addBlock();
		}
	}
}
