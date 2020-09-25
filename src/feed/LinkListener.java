package feed;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
/**
 * Clase para que los links que aparecen en la entrada sean abiertos en el navegador por defecto
 * @author Julio Navarro Lara
 */
public class LinkListener implements HyperlinkListener {

    Discorporate madre;

    public LinkListener(Discorporate madre){
        this.madre = madre;
    }

    public void hyperlinkUpdate(HyperlinkEvent e){
        if(e.getEventType()!=HyperlinkEvent.EventType.ACTIVATED)
            return;
        String href = e.getDescription();
        try{
          //Ejecutamos la ruta que tiene windows para abrir webs (en el navegador por defecto)
          String[] cmd = new String[4];
          cmd[0] = "cmd.exe";
          cmd[1] = "/C";
          cmd[2] = "start";
          cmd[3] = href;
          Process p = Runtime.getRuntime().exec(cmd);
        }catch(IOException except){
              JOptionPane.showMessageDialog(madre, "Error en la apertura del link", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
