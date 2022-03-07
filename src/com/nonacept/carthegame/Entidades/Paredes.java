/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.Entidades;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Rocha
 */
public class Paredes {

    public short x, oldX;
    public short y, oldY;
    public ImageIcon sprite;

    public Image getSprite() {
        return sprite.getImage();
    }

    public Paredes(int px, int py) {
        sprite = new ImageIcon("arquivos/Images/wall.cpn");
        x = (short)px;
        y = (short)py;
    }
}
