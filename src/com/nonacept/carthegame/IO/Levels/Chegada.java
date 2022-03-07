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
public class Chegada {
    public int x, y;
    public int destino;

    public ImageIcon getSprite() {
        ImageIcon spr = new ImageIcon("arquivos/Images/chegada.cpn");
        return spr;
    }
}
