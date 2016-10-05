/*
Här ligger den centrala spellogiken
Kontrollerar kollision mellan objekt
Håller poäng
Håller lista med morötter
Håller information om spelet är igång eller meny
*/
package bunnygame;

import bunnygame.entities.Carrot;
import bunnygame.entities.Player;
import java.util.LinkedList;

/**
 *
 * @author Liz Dahlström
 */
public class Model {
    

    private int score = 0;
    private int carrots_count = 2;
    private int carrots_killed = 0;
    private Player player;
    private LinkedList <Carrot> carrots;
    private Carrot tempCarrot;
    
    private boolean isInGame = false;
    
    public Model(Player player, LinkedList <Carrot> carrots){
        this.player = player;
        this.carrots = carrots;
        player.resetPlayer();
    
    }
    
        public int getScore(){
        return score;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public int getCarrotCount(){
        return carrots_count;
    }
    public void setCarrotCount(int carrots_count){
        this.carrots_count = carrots_count;
    }
    
    public int getCarrotEaten(){
        return carrots_killed;
    }
    
    public void resetCarrotEaten(){
        carrots_killed = 0;
    }
    
    public boolean collision(){
       for(int i= 0; i < carrots.size(); i++){
        if(carrots.get(i).getBounds().intersects(player.getBounds())){ // Kontrollerar kollision
            removeCarrot(carrots.get(i));         
            return true;
        }
           }
       return false; 
    }
    
    public boolean sickCollision(){
        for(int i= 0; i < carrots.size(); i++){
            if(carrots.get(i).getBounds().intersects(player.getBounds()) && carrots.get(i).isSick()){ // Kontrollerar kollision         
            return true;
            }
        }
    return false; 
        
    }
    
    public boolean isInGame(){
        return isInGame;
    }
    
    public void setInGame(){
        isInGame = true;
    }    
    
    public void addCarrot(Carrot carrot){
        carrots.add(carrot);
        
    }
    
    public void removeCarrot (Carrot carrot){
        carrots.remove(carrot);
    }
    
    public LinkedList<Carrot> getCarrots(){
        return carrots;
    }

    public void update(){
        //Kallas i GameEngine för att uppdatera spelet varje tick
        player.update();
        for(int i = 0; i <carrots.size(); i++){
            tempCarrot = carrots.get(i);
            tempCarrot.update();
            if(tempCarrot.isSick() && tempCarrot.getY() <= -tempCarrot.getHeight()){              
                carrots.remove(tempCarrot);
                carrots_killed++;              
            }
        }
        if(sickCollision()){
            player.takeDmg();
            System.out.println("Taking dmg");
            if(player.getHealth() <= 0){
                isInGame = false;
                System.out.println("health = zero");
            }
        }
        else if(collision()){
            carrots_killed++;
            score++;
        }
//        if(carrots.size() <= 1 && tempCarrot.isSick()){
//            tempCarrot.setSpeed(8);
//        }
            
        
       
        
    }
    
}
