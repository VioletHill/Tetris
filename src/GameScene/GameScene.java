package GameScene;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

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
	class BackgroundMusic extends Applet
	{
		//AudioClip background=Applet.newAudioClip(Music.class.getResource("Rich08.mid"));
		AudioClip background=Applet.newAudioClip(Music.class.getResource("background.mid"));
		BackgroundMusic()
		{
			background.loop();
		}
		public void stopBackgroundMusic()
		{
			background.stop();
		}
	}
	
	public GameScene()
	{
		//backgroundMusic=new BackgroundMusic();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		GraphicsDevice device=environment.getDefaultScreenDevice(); 
		DisplayMode displayMode=new DisplayMode(1366,768,16,60);
	//	device.setDisplayMode(displayMode); 
		setVisible(true);
		setLayout(null);
		
		ImageIcon img = new ImageIcon("Resource/MainFrame/scene.png");
		JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight());
        getLayeredPane().add(imgLabel,new Integer(Integer.MIN_VALUE));
        JPanel jp=(JPanel)this.getContentPane();
        jp.setOpaque(false);
        
		gameScoreLayer=new GameScoreLayer(Toolkit.getDefaultToolkit().getScreenSize());
		add(gameScoreLayer);
		gameScoreLayer.setOpaque(false);
		
		gameLayer=new GameLayer(Toolkit.getDefaultToolkit().getScreenSize(),this);
		add(gameLayer);
		gameLayer.setOpaque(false);

		gameMenuLayer=new GameMenuLayer(Toolkit.getDefaultToolkit().getScreenSize(),this);
		add(gameMenuLayer);
		gameMenuLayer.setOpaque(false);
		

	}
}
