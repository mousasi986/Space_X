package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static com.company.Enemy.ex;
import static com.company.Enemy.ey;


public class GamePanel extends JFrame implements KeyListener{

    protected Image background = new ImageIcon("textures/1.gif").getImage();
    protected Image boom = new ImageIcon("textures/bom.gif").getImage();
    protected Image hero = new ImageIcon("textures/Hero.gif").getImage();
    protected Image enemy = new ImageIcon("textures/Enemy.gif").getImage();




    private Thread thread;
    protected static int xMax,yMax,xMin,yMin;
    private static final int DIR_STEP = 10;
    protected static boolean isCross;
    private boolean isLeft = false;
    private boolean isRight = false;
    private boolean isUp = false;
    private boolean isDown = false;
    protected static int x, y;
    static JFrame frame = new JFrame("SpaceX");
    Enemy En = new Enemy();

    public GamePanel(int width, int height) {
        this.setSize(width, height);
        xMax=width-50;
        yMax=height-200;
        xMin=0;
        yMin=20;
        x = width/2;
        y = height-200;
        this.addKeyListener(this);
        thread = new MoveThread(this);
        thread.start();

    }


    public static void main(String[] args) {
        frame = new GamePanel(1366,780);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        Sound.playSound("sountrack.wav");


    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_LEFT) isLeft = true;
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) isRight = true;
        if (e.getKeyCode()==KeyEvent.VK_UP) isUp = true;
        if (e.getKeyCode()==KeyEvent.VK_DOWN) isDown = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_LEFT) isLeft = false;
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) isRight = false;
        if (e.getKeyCode()==KeyEvent.VK_UP) isUp = false;
        if (e.getKeyCode()==KeyEvent.VK_DOWN) isDown = false;
    }

    @Override
    public void keyTyped(KeyEvent arg0) {}

    @Override
    public void paint(Graphics gr) {
        Graphics2D g2d = (Graphics2D)gr;
        if(x>xMax) x=xMin;
        if(x<xMin) x=xMax;
        if(y>yMax) y=yMax;
        if(y<yMin) y=yMin;
        if(x==ex & y==ey){ g2d.drawImage(boom,ex,ey,this);
        }
        boolean life = true;
        isCross = Math.sqrt(Math.pow(Math.abs(x - ex),2)+Math.pow(Math.abs(y - ey),2))>159.0d;


            if (isCross == false) {
                //g2d.drawImage(boom, x / 2, y / 2, 500, 500, this);
                background = new ImageIcon("textures/gameover.gif").getImage();
                g2d.drawImage(background, 0, 0, 1400, 1200, null);
                Sound.stop();
                thread.stop();
                repaint();


            }  else {
                g2d.drawImage(hero, x, y, 150, 200, this);
                g2d.drawImage(enemy, ex, ey, 200, 200, this);
                g2d.drawImage(background, 0, 0, 1400, 1200, null);
                repaint();
            }

        repaint();
    }


    public void animate() {
        En.Enemy();
        if (isLeft) x-=DIR_STEP;
        if (isRight) x+=DIR_STEP;
        if (isUp) y-=DIR_STEP;
        if (isDown) y+=DIR_STEP;



    }

    private  class MoveThread extends Thread{
        GamePanel runKeyboard;

        public MoveThread(GamePanel runKeyboard) {
            super("MoveThread");
            this.runKeyboard = runKeyboard;
        }

        public void run(){

              while (true) {

                  runKeyboard.animate();
                  runKeyboard.repaint();


                  try {
                      if (!isInterrupted())
                          Thread.sleep(5);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
            }

    }

}


