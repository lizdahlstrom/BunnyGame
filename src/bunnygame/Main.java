/*
Main controllern
-Skapar kontrollern : GameEngine
*/

package bunnygame;

/**
 *
 * @author Liz Dahlström
 */
public class Main {
    
    private final String TITLE = "Bunny Game";
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private GameEngine engine;
    
    //Constructor
    public Main(){
        
        engine = new GameEngine(TITLE, WIDTH, HEIGHT);    
        engine.start();
  
    }
    
    public static void main(String []args){
        Main main = new Main();
    }
    
}
