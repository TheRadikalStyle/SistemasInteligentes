import java.net.MalformedURLException;
import java.net.URL;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class ThreadAbout extends Thread{

	public static boolean holding;
    public static int seconds;
    static BasicPlayer player = new BasicPlayer();
    //static String pathToMp3 = System.getProperty("user.dir") +"/audio/noStrings.mp3";
    URL pathToMp3 = Sounds.class.getResource("audio/noStrings.mp3");
    public void run(){

            while(holding && seconds <= 3){
                seconds++;
                try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
                
                if(seconds == 3){
                    System.out.println("34st3r 399");
                    try{
                    	player.open(pathToMp3);
                    	player.play();
                    }catch(BasicPlayerException e){
                    	e.printStackTrace();
                    	//System.out.println("Error basic player "+e.getMessage());
                    }
                }
            }
        }
}
