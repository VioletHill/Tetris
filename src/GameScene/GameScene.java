package GameScene;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScene extends JFrame
{
	public static GameScoreLayer gameScoreLayer;
	public static GameLayer gameLayer;
	public static GameMenuLayer gameMenuLayer;
	public static BackgroundMusic backgroundMusic;
	int select;
	class BackgroundMusic extends Applet
	{
		AudioClip background;
		
		BackgroundMusic()
		{
			setMusic("1");
		}
		public void stopBackgroundMusic()
		{
			if (background!=null)
			{
				background.stop();
				background=null;
			}
		}
		void setMusic(String str)
		{
			URL url=null;
			try {
				File file=new File("Resource/Music/"+str+".wav");
				url=new URL("file:"+file.getAbsolutePath());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			background=Applet.newAudioClip(url);
			background.loop();
		}
		public void selectMusic(String str)
		{
			if (background!=null)	background.stop();
			background=null;
			if (str=="πÿ±’“Ù¿÷")  return ;
			setMusic(str);
		}
	}
	
	public GameScene(int userSelect)
	{
		select=userSelect;
		backgroundMusic=new BackgroundMusic();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		setVisible(true);
		setLayout(null);
		
		ImageIcon img = new ImageIcon("Resource/MainFrame/use.png");
		JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)this.getContentPane();
        jp.setOpaque(false);
        
		gameScoreLayer=new GameScoreLayer(Toolkit.getDefaultToolkit().getScreenSize());
		add(gameScoreLayer);
		
		gameLayer=new GameLayer(Toolkit.getDefaultToolkit().getScreenSize(),this,select);
		add(gameLayer);

		gameMenuLayer=new GameMenuLayer(Toolkit.getDefaultToolkit().getScreenSize(),this);
		add(gameMenuLayer);		

	}
}
