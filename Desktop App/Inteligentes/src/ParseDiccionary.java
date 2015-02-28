import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ParseDiccionary {

public void Lee(){
	File archivo = null;
	try {
	archivo = new File("archivo.txt");//"archivo.txt" es el archivo que va a leer
	String linea;
	FileReader fr = new FileReader (archivo);
	BufferedReader br = new BufferedReader(fr);
	int i,j,a=0;
	while((linea=br.readLine())!=null) {
	for(i=0;i<linea.length();i++)
	{if(i==0)
	   {if(linea.charAt(i)!=' ')
	    a++;
	   }
	   else
	   {if(linea.charAt(i-1)==' ')
	     if(linea.charAt(i)!=' ')	
	       a++;
	    
	   }	
	}
	}

	System.out.println("son "+a+" palabras");

	fr.close();
	}
	catch(IOException a){
	System.out.println(a);
	}
}	
	
	
}
