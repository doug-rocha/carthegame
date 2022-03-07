/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.IO.Levels;

import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira (@dhorgas1) - dhorgas_software@hotmail.com
 */
public class CaixoteArea {

    public int x, y;
    public int quantX, quantY;
    public boolean random;
    public String sprite, spriteDestruido;
    public float durabilidade;

    public CaixoteLvl[] getCaixotes() {
        int tam = 16;
        ArrayList<CaixoteLvl> cxcs = new ArrayList<>();
        if (!random) {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    CaixoteLvl cxc = new CaixoteLvl();
                    cxc.x = x + (tam * posX);
                    cxc.y = y + (tam * posY);
                    cxc.sprite=sprite;
                    cxc.spriteDestruido=spriteDestruido;
                    cxc.durabilidade=durabilidade;
                    cxcs.add(cxc);
                }
            }
        }else{
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    CaixoteLvl cxc = new CaixoteLvl();
                    cxc.x = (int) ((x + (tam * posX)) + (Math.random() * tam));
                    cxc.y = (int) ((y + (tam * posY)) + (Math.random() * tam));
                    cxc.sprite = sprite;
                    cxcs.add(cxc);
                }
            }
        }
        CaixoteLvl[] caixote = cxcs.toArray(new CaixoteLvl[cxcs.size()]);
        return caixote;
    }
}
