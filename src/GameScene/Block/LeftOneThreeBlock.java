package GameScene.Block;

import java.awt.Color;

public class LeftOneThreeBlock extends Block
{
	public LeftOneThreeBlock()
	{
		blockShape=BlockShape.LEFT_ONE_THREE;
		blockForm=0;
		blockView=71;			//0100 0111
		blockColor=Color.pink;
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
			if (!checkForm(1895)) return ;	//111 0110 0111
			deleteBlock();
			blockForm=1;
			blockView=802;	     //0011 0010 0010
			addBlock();
		}
		else if (blockForm==1)
		{
			if (!checkForm(887)) return ;	//11 0111 0111
			deleteBlock();
			blockForm=2;
			blockView=113;					//111 0001
			addBlock();
		}
		else if (blockForm==2)
		{
			if (!checkForm(1907)) return ;	//111 0111 0011;
			deleteBlock();
			blockForm=3;
			blockView=275;					//1 0001 0011
			addBlock();
		}
		else if (blockForm==3)
		{
			if (!checkForm(343)) return ;	//0001 0101 0111
			deleteBlock();				
			blockForm=0;
			blockView=71;					//0100 0111
			addBlock();
		}
	}
}
