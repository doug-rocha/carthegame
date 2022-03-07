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
public class ChegadaArea {

    public int x, y;
    public int quantX, quantY;

    public Chegada[] getChegadas() {
        int tam = 16;
        ArrayList<Chegada> chegs = new ArrayList<>();
        for (int posX = 0; posX < quantX; posX++) {
            for (int posY = 0; posY < quantY; posY++) {
                Chegada cheg = new Chegada();
                cheg.x = x + (tam * posX);
                cheg.y = y + (tam * posY);
                chegs.add(cheg);
            }
        }
        Chegada[] chegada = chegs.toArray(new Chegada[chegs.size()]);
        return chegada;
    }

}
