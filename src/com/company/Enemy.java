package com.company;
import java.util.Random;

import static com.company.GamePanel.*;

public class Enemy{
    protected static int ex;
    protected static int ey=0;
    public void Random(){
        Random rand = new Random();
        ex = rand.nextInt(1800);
        if(ex>xMax)ex=xMax-150;
        if(ex<xMin)ex=xMin;

    }


    public void Enemy(){
       ey+=5;
       if(ey>yMax){
           ey=0;
           Random();
       }
    }

}