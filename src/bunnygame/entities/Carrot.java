/*
Sköter morotobjekten
Med konstruktören skapas antingen en sjuk eller "nyttig" morot
Morötterna kan bara röra sig uppåt och får slumpmässig x-koordinat

*/

package bunnygame.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Liz Dahlström
 */
public class Carrot{
    //Intance Variables
    
    private int x,y;
    private int width, height;
    
    private Random rand= new Random();
    private int speed;
    private final int MAX_SPD = 4;
    private final int MIN_SPD = 1;
    private final int START_POS_Y = 600;
    
    private boolean isSick;
    
    private String imgPath = "/textures/carrot.png";
    private String altImgPath = "/textures/sick_carrot.png";
    private ImageIcon carrotIcon;
    private Image carrotImg;
    
    private int displayWidth;
    
    //Constructor
    public Carrot(int displayWidth){
        isSick = rand.nextBoolean();  // Morötter har 50/50 chans att bli "sjuka"
        if(!isSick){
           createIcon(imgPath);
        }
        else if(isSick){
            createIcon(altImgPath);
        }
        this.width = carrotImg.getWidth(null);
        this.height = carrotImg.getHeight(null);
        this.displayWidth = displayWidth;
        
        spawn();
      
    }
    
    //Positions
    public int getY(){
        return y;
    }
    
    public int getX(){
        return x;
    }    
    
    public Image getImg(){
        return carrotImg;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    public void update(){ // Rör moroten uppåt
        if(x <= 0){
            x = 0;
        }
        if(y < (-height)){
            spawn();
        }
        
        y -= speed;
        
    }
    
    
    //Methods
    private void spawn(){
        
        speed = rand.nextInt((MAX_SPD - MIN_SPD) + MIN_SPD); // Random hastighet
        if(speed <= 0)
            speed = 3;
        
        
        x = rand.nextInt(displayWidth); // Random position
        if(x <= width){
            x += width;
        }
        if(x >= displayWidth - (width -1)){ // Håller dem innanför fönstret
            x -= displayWidth - width;
        }     
        
        y = START_POS_Y; // börjar "under"  panelen
    }
    
    private void createIcon(String path){
        carrotIcon= new ImageIcon(this.getClass().getResource(path));
            carrotImg = carrotIcon.getImage();
    }
    
    public Rectangle getBounds() { // returnerar rektangel runt objektet
        return new Rectangle(x, y, width, height);
    }
    
    public boolean isSick(){
        return isSick;
    }
   
}
