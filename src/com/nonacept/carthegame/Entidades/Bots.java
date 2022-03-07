/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.Entidades;

import com.nonacept.carthegame.Game;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Rocha
 */
public class Bots {

    public short x = 100;
    public short y = 100;
    public float fx = 100;
    public float fy = 100;
    public short oldX;
    public short oldY;
    public int velocidade = 1;
    private ImageIcon spr = new ImageIcon("arquivos/Images/ini.cpn");
    public Image sprite;

    public Bots() {
        velocidade = 1;
        x = 100;
        y = 100;
        try {
            sprite = spr.getImage();
        } catch (Exception ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println("ERRO | O SPRITE PARA OS BOTS NÃO FORAM ENCONTRADOS");
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(short y) {
        this.y = y;
    }
}
