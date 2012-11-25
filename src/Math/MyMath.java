package Math;

public class MyMath
{
	public static int log2(int num)  //½øÊÊºÏ2^N
	{
		for (int i=0; i>=0; i++)
		{
			if (((1<<i) & num )!=0) return i;
		}
		return 0;
	}
}
