/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.Entidades;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

/**
 *
 * @author Douglas Rocha
 */
public class Teleportes {

    public short x;
    public short y;
    public short destX;
    public short destY;
    public ImageIcon sprite, spriteexit;
    public Random rdm = new Random();
    public boolean ativo;
    public double rotate, rotateSpd;
    public short color;

    public Image getSprite() {
        return sprite.getImage();
    }

    public Image getExitSprite() {
        return spriteexit.getImage();
    }

    public Teleportes(int px, int py, int dx, int dy) {
        rdm.setSeed((int) (Math.random() * 1000));
        color = (short) rdm.nextInt(5);
        sprite = new ImageIcon("arquivos/Images/portal" + color + ".cpn");
        spriteexit = new ImageIcon("arquivos/Images/portale.cpn");
        x = (short) px;
        y = (short) py;
        ativo = true;
        rotateSpd = Math.random() * 0.055;
    }
}
