/*
"Spelmotorn". En Controller som sköter gameloopen samt knapptryckningar
Kopplar samman model och View
Implementerar runnable för att kunna köra gameloopen på en tråd

*/

package bunnygame;

import bunnygame.entities.Carrot;
import bunnygame.entities.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Liz Dahlström
 */
public class GameEngine implements Runnable{
    //Instance variables
    private Thread thread;
    private boolean running = false;
    private Model model;
    private View view;
    private LinkedList <Carrot> carrots;
    
    private String TITLE;
    private int WIDTH;
    private int HEIGHT;
    private static boolean inGame = true;
    
    private Player player;
    
    private KeyHandler keyHandler;
    
    //Constructor
    public GameEngine(String TITLE, int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.TITLE = TITLE;
        keyHandler = new KeyHandler();
        view = new View(TITLE, WIDTH, HEIGHT);
        player = new Player(view.resX);
        carrots = new LinkedList();
        model = new Model(player, carrots);

        view.setPlayer(player);
        view.setCarrots(carrots);
        
    }
    
    @Override
    public void run(){
        view.init(); // initierar grafiken
        view.addKeyListener(keyHandler);
        view.addMouseListener(new MouseHandle());
        
        for(int i = 0; i < model.getCarrotCount(); i++){
                model.addCarrot(new Carrot(view.getDisplay().getWidth())); // Skapar morötter
            }
        
        int fps = 60;
        long lastTime = System.nanoTime();
        long now;
        double timeTick = 1000000000 / fps;
        double delta = 0;
        long aTimer = 0;
        int ticks = 0;
        
        //Game-loop 
        while(running){
            now = System.nanoTime();
            delta += ( now - lastTime) / timeTick;
            aTimer += now - lastTime;
            lastTime = now;
            
            if(delta >= 1){        
                update(); // uppdaterar model
                ticks++;
                delta--;
            }
           
            if(aTimer >= 1000000000 ){
                
                System.out.println("Tick:" + ticks); // Visar antalet tick i konsollen
                ticks = 0;
                aTimer = 0;
            }
        }
        stop();
    }
    
    
    private void update(){ // Uppdaterar data och grafik
        inGame = model.isInGame();
        view.getDisplay().setInGame(inGame);
        view.getDisplay().repaint();
        if(inGame){
        model.update();
        
        if(model.getCarrotEaten() >= model.getCarrotCount()){
            model.setCarrotCount(model.getCarrotCount() + 1 ); // Ökar mängden morötter för varje "våg"
            model.resetCarrotEaten();
            for(int i = 0; i < model.getCarrotCount(); i++){
                model.addCarrot(new Carrot(view.getDisplay().getWidth())); // Skapar morötter
            }
            
        }
        
        }
        else if(!inGame){
            // Game over text
            view.getDisplay().setInGame(inGame);
            
        }
        view.getDisplay().setScore(model.getScore()); // uppdaterar poängen
        
        
    }
    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this); // Skapar en ny tråd
        thread.start();
    }
    
    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void newGame(){ // Skapar ett nytt spel, återställer variabler

        carrots.clear();
        model = new Model(player, carrots);
        
        view.setPlayer(player);
        view.setCarrots(carrots); 
        
         for(int i = 0; i < model.getCarrotCount(); i++){
                model.addCarrot(new Carrot(view.getDisplay().getWidth())); // Skapar morötter
            }
    }
    
    public boolean isInGame(){
        return inGame;
    }
    
    //Intern klass som sköter knapptryckningar
    private class KeyHandler extends KeyAdapter{ 
       
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            
        }
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
    
    //Intern klass som sköter musklick
    public class MouseHandle implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            
            
            if(!model.isInGame()){
                if(x >= 289 && x <= 544){
                    //Om man trycker på play
                    if(y >= 280 && y <= 335){            
                    // Starta om spel, nytt spel
                        newGame();
                        model.setInGame();    
                    }
                    //Om man trycker på quit
                    if(y >= 340 && y <= 395){
                        System.exit(1);
                    }
                }
            }
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {  
        }

        @Override
        public void mouseEntered(MouseEvent e) {   
        }

        @Override
        public void mouseExited(MouseEvent e) {    
        }    
    }
}

