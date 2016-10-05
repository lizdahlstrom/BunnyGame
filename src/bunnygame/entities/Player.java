/*
Player.java
Sköter all logik bakom player-objektet
Storlek, utseende, rörelser, knapptryckningar
*/

package bunnygame.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Liz Dahlström
 */
public class Player{
    //Instance Variables
    private double dx= 0, dy =0;
    private double x,y;
    private int width, height;
    private final int GROUND_Y = 218; // Kaninens "mark"
    
    private final String imgPath_right = "/textures/bunny_right.png";
    private final String imgPath_left = "/textures/bunny_left.png";
    private ImageIcon playerIcon;
    private Image playerImg;
    
    private boolean isFalling = true;
    private boolean isJumping = false;
    private float gravity = 0.5f;
    private final int MAX_VEL = 15;
    private int HEALTH = 50;
    
    private int displayWidth;

    
    //Constructor
    public Player(int displayWidth){
        setImg(imgPath_right);
        this.width = playerImg.getWidth(null);
        this.height = playerImg.getHeight(null);
        this.displayWidth = displayWidth;
        resetPlayer();
        
    }
    // Setters and getters
    public double getY(){
        return y;
    }
    
    public double getX(){
        return x;
    }    
    
    public Image getImg(){
        return playerImg;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getHealth(){
        return HEALTH;
    }
    
    private void setImg(String imgPathIn){ // Bestämmer spelarens bild
        playerIcon= new ImageIcon(this.getClass().getResource(imgPathIn));
        playerImg = playerIcon.getImage();
    }
    
    public void takeDmg(){
        HEALTH--;
    }
    
    public void update(){ // Uppdaterar spelarens rörelser         
        y+=dy;
        x +=dx;
        if(isFalling || isJumping){
            
                dy += gravity;

                if(dy >= MAX_VEL){
                    dy = MAX_VEL;
                }
            
        }
        if(y >= GROUND_Y){ // Håller spelaren på marken (staketet)
            y = GROUND_Y;
            dy = 0;
            isFalling = false;
            isJumping = false;
        }
        else{
            isFalling = true;
        }
        
        if(x <= 0){
            x =0;
        }
        if(x >= displayWidth- width){
            x = displayWidth - width;
        }
        if(y < 0){
            y = 0;
        }
        
    }
    
    public Rectangle getBounds() { // returnerar rektangel runt objektet
        return new Rectangle((int)x, (int)y, width, height);
    }
    
    public void resetPlayer(){
        x = (displayWidth- width) /2; // centrerar spelaren
        y = GROUND_Y - 50;
        HEALTH = 50;
    }
    
   
     //Hanterar knapptryckningar

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_UP){
            if(!isJumping){
                this.dy = -14;
                isJumping = true;
            }
            
        }
        if(key == KeyEvent.VK_RIGHT){
            this.dx = 5;
            setImg(imgPath_right);
            
        }
        if(key == KeyEvent.VK_LEFT){

            this.dx = -5;
            setImg(imgPath_left);
            
        }
    }
    
    public void keyReleased(KeyEvent e){ // återställer delta x och y när man släpper tangenten
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_RIGHT){
            this.dx = 0;
            
        }
        if(key == KeyEvent.VK_LEFT){
            this.dx = 0;
        }
        if(key == KeyEvent.VK_UP){
            isJumping = false;
            this.dy =0;
           
            
        }
    }
}
