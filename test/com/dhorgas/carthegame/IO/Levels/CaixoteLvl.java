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
public class CaixoteLvl {

    public int x, y;
    public String sprite, spriteDestruido;
    public float durabilidade;

    public ImageIcon getSpr() {
        ImageIcon spr = null;
        if (sprite == null) {
            sprite = "";
        }
        if ((!sprite.equals("")) && (!sprite.equals("padrao")) && (!sprite.equals("default")) && (sprite != null)) {
            spr = new ImageIcon(String.format("arquivos/Images/cenas/%s.cpn", sprite));
        } else {
            spr = new ImageIcon("arquivos/Images/cxc.cpn");
        }
        return spr;
    }

    public ImageIcon getSprDestruido() {
        ImageIcon spr = null;
        if (spriteDestruido == null) {
            spriteDestruido = "";
        }
        if ((!spriteDestruido.equals("")) && (!spriteDestruido.equals("padrao")) && (!spriteDestruido.equals("default"))) {
            spr = new ImageIcon(String.format("arquivos/Images/cenas/%s.cpn", spriteDestruido));
        } else {
            spr = new ImageIcon("arquivos/Images/cxc-destroy.cpn");
        }
        return spr;
    }

}
