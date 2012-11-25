package GameScene.Block;

import java.awt.Color;

public class MiddleOneThreeBlock extends Block
{
	public MiddleOneThreeBlock()
	{
		blockShape=BlockShape.MIDDLE_ONE_THREE;
		blockForm=0;
		blockView=39;			//0010 0111
		blockColor=Color.yellow;
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
			if (!checkForm(1639)) return ;	//0110 0110 0111
			deleteBlock();
			blockForm=1;
			blockView=562;	     //0010 0011 0010
			addBlock();
		}
		else if (blockForm==1)				
		{
			if (!checkForm(887)) return ;	//0011 0111 0111
			deleteBlock();
			blockForm=2;
			blockView=114;					//111 0010
			addBlock();
		}
		else if (blockForm==2)
		{
			if (!checkForm(1655)) return ;	//0110 0111 0111;
			deleteBlock();
			blockForm=3;
			blockView=610;					//0010 0110 0010
			addBlock();
		}
		else if (blockForm==3)
		{
			if (!checkForm(887)) return ;	//11 0111 0111
			deleteBlock();				
			blockForm=0;
			blockView=39;					//0010 0111
			addBlock();
		}
	}
}
