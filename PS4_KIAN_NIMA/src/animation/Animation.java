package animation;

import java.awt.Color;
import java.awt.Graphics;

public class Animation
{
    /**
     * This is the method that you need to rewrite to create a custom animation. This method is called repeatedly as the
     * animation proceeds. It needs to draw on g how the animation should look after time milliseconds have passed.
     * 
     * @param g Graphics object on which to draw
     * @param time Number of milliseconds that have passed since animation started
     */
    public static void paintFrame (Graphics g, int time, int height, int width)
    {
        
        paintBackGround(g, time);
        drawStickFigure(g, time, 5, 400);
        drawStickFigure2(g,time,5,400);
        drawFinishLine(g,time,5,400);
        drawSUN(g, time, 0, 0);

    }

    public static void drawStickFigure(Graphics g, int time, int x, int y)
    {
      
      
      if (time <= 10000) {
        x = time / 10;
      }
      else {
        
        x = 500;
        y = 500;
        
      }
      g.setColor(Color.BLACK);
      g.fillOval(x + 100, y + 300, 35, 35);
      g.setColor(Color.BLACK);
      g.drawOval(x + 100, y + 300, 35, 35);
      
      
      

      g.drawLine(x + 115, y + 330, x + 115, y + 385);
      
      g.drawLine(x + 115, y + 340, x + 130, y + 360);
      g.drawLine(x + 115, y + 340, x + 100, y + 360);
      
      g.drawLine(x + 115, y + 385, x + 130, y + 415);
      g.drawLine(x + 115, y + 385, x + 100, y + 415);
      

      
    }
    
    public static void drawStickFigure2(Graphics g, int time, int x, int y)
    {
      
      
      if (time <= 10000) {
        x = time / 8;
      }
      else {
        x = 500;
        y = 500;
      }
      g.setColor(Color.BLACK);
      g.fillOval(x + 100, y, 35, 35);
      g.setColor(Color.BLACK);
      g.drawOval(x + 100, y, 35, 35);
      
      
      

      g.drawLine(x + 115, y + 30, x + 115, y + 85);
      
      g.drawLine(x + 115, y + 40, x + 130, y + 60);
      g.drawLine(x + 115, y + 40, x + 100, y + 60);
      
      g.drawLine(x + 115, y + 85, x + 130, y + 115);
      g.drawLine(x + 115, y + 85, x + 100, y + 115);
      

    }
    public static void drawFinishLine(Graphics g, int time, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x + 900, y - 50, 50, 50);
        
        g.setColor(Color.BLACK);
        g.fillRect(x + 900, y, 50, 50);
        
        g.setColor(Color.WHITE);
        g.fillRect(x+ 900, y + 50, 50, 50);
        
        g.setColor(Color.BLACK);
        g.fillRect(x + 900, y + 100, 50, 50);
        
        g.setColor(Color.WHITE);
        g.fillRect(x+ 900, y + 150, 50, 50);
        
        g.setColor(Color.BLACK);
        g.fillRect(x + 900, y + 200, 50, 50);
        
        g.setColor(Color.WHITE);
        g.fillRect(x+ 900, y + 250, 50, 50);
        
        g.setColor(Color.BLACK);
        g.fillRect(x + 900, y + 300, 50, 50);
        
        g.setColor(Color.WHITE);
        g.fillRect(x + 900, y + 350, 50, 50);
        
        g.setColor(Color.BLACK);
        g.fillRect(x + 900, y + 400, 50, 50);
      }
    
    public static void drawSUN(Graphics g, int time, int x, int y)
    {
      if(time <= 10000)  {
          x = time / -10;
      }
      
      
      g.setColor(Color.BLACK);
      g.drawOval(x + 800, y + 50, 100, 100);
      
      if (time % 3 == 1) {
        g.setColor(Color.RED);
      }
      else if (time % 3 == 0) {
        g.setColor(Color.YELLOW);
      }
      else {
        g.setColor(Color.ORANGE);
      }
      g.fillOval(x + 800, y + 50, 100, 100);
    }
    
    public static void paintBackGround(Graphics g, int time) {
        
        g.setColor(Color.BLUE);
        if(time == 6500) {
            g.setColor(Color.RED);
        }
        g.fillRect(0, 0, 1000, 350);

        
        g.setColor(Color.GREEN);
        g.fillRect(0, 350, 1000, 500);
    

    }
  }
    
