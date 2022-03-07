/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.IO.Levels;

import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Rocha de Oliveira (@dhorgas1) - dhorgas_software@hotmail.com
 */
public class Parede {

    public int x, y;
    public String sprite;

    public Parede(int px, int py) {
        x = px;
        y = py;
    }

    public Parede() {
    }

    public ImageIcon getSprite() {
        ImageIcon spr = null;
        if (sprite == null) {
            sprite = "";
        }
        sprite = sprite.toLowerCase();
        if ((!sprite.equals("")) && (!sprite.equals("padrao")) && (!sprite.equals("default"))) {
            spr = new ImageIcon(String.format("arquivos/Images/cenas/%s.cpn", sprite));
        } else {
            spr = new ImageIcon("arquivos/Images/wall.cpn");
        }
        return spr;
    }

}
