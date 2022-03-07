/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.IO.Levels;

import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Rocha de Oliveira (@dhorgas1) - dhorgas_software@hotmail.com
 */
public class CarDestruido {
    public int x, y;
    public String sprite, spriteExplodido;
    public float durabilidade;

    public ImageIcon getSpr() {
        ImageIcon spr = null;
        if (sprite == null) {
            sprite = "";
        }
        if ((!sprite.equals("")) && (!sprite.equals("padrao")) && (!sprite.equals("default"))) {
            spr = new ImageIcon(String.format("arquivos/Images/cenas/%s.cpn", sprite));
        } else {
            spr = new ImageIcon("arquivos/Images/destroy.cpn");
        }
        return spr;
    }

    public ImageIcon getSprDestruido() {
        ImageIcon spr = null;
        if (spriteExplodido == null) {
            spriteExplodido = "";
        }
        if ((!spriteExplodido.equals("")) && (!spriteExplodido.equals("padrao")) && (!spriteExplodido.equals("default"))) {
            spr = new ImageIcon(String.format("arquivos/Images/cenas/%s.cpn", spriteExplodido));
        } else {
            spr = new ImageIcon("arquivos/Images/expl.cpn");
        }
        return spr;
    }
}
