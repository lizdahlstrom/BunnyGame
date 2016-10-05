/*
Display.java
Sköter all övergripande grafik genom att skapa en JFrame och JPanel
ritar upp fönstret med createView(), samt spelobjekten med overridad paint() metod
*/
package bunnygame;



import bunnygame.entities.Carrot;
import bunnygame.entities.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Liz Dahlström
 */
public class Display extends JPanel{
    //Instance variables
    
    
    private final String bgPath = "/textures/bg.png";
    private ImageIcon bgIcon;
    private Image background;
    private int score =0;
    
    private int width, height;
    
    private LinkedList <Carrot> carrots;
    private Player player;
    private Carrot tempCarrot;
    
    private boolean isInGame;
    
    //Constructor
    public Display(int width, int height){
        this.width = width;
        this.height = height;
        
        bgIcon= new ImageIcon(this.getClass().getResource(bgPath));
        background = bgIcon.getImage();
        
        createDisplay();
    }
    
    private void createDisplay(){     
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        
        setFocusable(false);
        setDoubleBuffered(true);
        setVisible(true);
    }
    
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(background, 0,0,null);
        Graphics2D g2d = (Graphics2D)g;
        
        drawEntity(g2d, player.getImg(), (int)player.getX(),(int)player.getY()); // ritar spelaren
        
        for(int i = 0; i < carrots.size(); i++){
            tempCarrot = carrots.get(i);
            drawEntity(g2d, tempCarrot.getImg(), tempCarrot.getX(), tempCarrot.getY()); // ritar morötter
        }
        Font font = new Font("courier", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Carrots devoured: " + Integer.toString(score), 20, 25);
        g.drawString("Health: " + player.getHealth(), 20, 50);
        
        
        if(!isInGame){ // När man inte spelar visas en meny som ritas här
            g.setColor(Color.black);
            g.fillRect(800/2 - 250/2, 600 /2 - 300/2, 250, 300);
            g.setColor(Color.white);
            font = new Font("courier", Font.BOLD, 30);
            g.setFont(font);
            
            
            g.drawString("Bunny Game", (width/2 - 250/2) + 30, (height/2 - 300/2)+ 40);
            font = new Font("courier", Font.BOLD, 16);
            g.setFont(font);
            g.setColor(Color.red);
            g.drawString("Watch out for green carrots!",  (width/2 - 250/2) + 25, (height/2 - 300/2)+ 250);
            g.setColor(Color.white);
            g.fillRect((width/2 - 250/2) + 14, (height/2 - 300/2) + 90, 225, 55);
            g.fillRect((width/2 - 250/2) + 14, (height/2 - 300/2) + 150, 225, 55);
            g.setColor(Color.gray);
            font = new Font("courier", Font.BOLD, 25);
            g.setFont(font);
            g.drawString("New Game", (width/2 - 250/2) + 62, (height/2 - 300/2)+ 125);
           
            g.drawString("Quit", (width/2 - 250/2) + 95, (height/2 - 300/2)+ 185);
        }
        
        g2d.dispose();
        //repaint();
        
        
    }
    
    public void setPlayer(Player player){
        this.player = player;
    }
    
    public void setScore(int score){
        this.score = score;
    }
   

    public void drawEntity(Graphics2D g, Image img, int x, int y){
        g.drawImage(img, x, y, null);
    }
    
    public void setCarrots(LinkedList<Carrot> carrots){
        this.carrots = carrots;
    }
     
    public void setInGame(Boolean bool){
        isInGame = bool;
    }

}
