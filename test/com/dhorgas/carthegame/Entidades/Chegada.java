/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.Entidades;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Rocha
 */
public class Chegada {

    public short x, oldX;
    public short y, oldY;
    public Image sprite;
    public boolean ativo;

    public Image getSprite() {
        return sprite;
    }

    public Chegada(int px, int py) {
        ImageIcon spr = new ImageIcon("arquivos/Images/chegada.cpn");
        sprite = spr.getImage();
        x = (short)px;
        y = (short)py;
        ativo = true;
    }
}
