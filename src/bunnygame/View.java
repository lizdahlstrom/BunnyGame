/*
Sköter all övergripande grafik
Extends JFrame, skapar ett Display-objekt dvs JPanel som innehåller själva spelplanen
*/
package bunnygame;

import bunnygame.entities.Carrot;
import bunnygame.entities.Player;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author Liz Dahlström
 */
public class View extends JFrame{

    private Display display;
    private Player player;
    
    private LinkedList <Carrot> carrots;

    String title;
    int resX, resY;
    
    public View(String title, int resX, int resY){

        this.resX = resX;
        this.resY = resY;
        this.title = title;
    
    }
    
    public void init(){
               
        this.setTitle(title);
        this.setSize(resX, resY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null); // Centrerar    
        
        this.setFocusable(true);
      
        display = new Display(resX, resY);
        display.setPlayer(player);
        display.setCarrots(carrots);
       
        this.add(display);
        this.pack();
        this.setVisible(true);  // Visar frame
    }
    
    public void setPlayer(Player player){
        this.player = player;
    }
    
    public void setCarrots(LinkedList carrots){
        this.carrots = carrots;
    }
    
    public Display getDisplay(){
        return display;
    }
    

   
    
}

