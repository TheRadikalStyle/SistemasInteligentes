import java.net.MalformedURLException;
import java.net.URL;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


public class Sounds {
    static BasicPlayer player = new BasicPlayer();

public static void PlaySounds(String soundName){
//String pathToMp3 = System.getProperty("user.dir") +"/audio/"+soundName+".mp3";
URL es = Sounds.class.getResource("audio/"+soundName+".mp3");
         try{
         	player.open(es);
         	player.play();
         }catch(BasicPlayerException e){
         	e.printStackTrace();
         	//System.out.println("Error basic player "+e.getMessage());
         }
	 }
}
