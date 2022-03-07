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
public class ParedeArea {

    public int x, y;
    public int quantX, quantY;
    public boolean random;
    public String sprite;

    public Parede[] getParedes() {
        int tam = 16;
        ArrayList<Parede> pars = new ArrayList<>();
        if (!random) {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    Parede par = new Parede();
                    par.x = x + (tam * posX);
                    par.y = y + (tam * posY);
                    par.sprite = sprite;
                    pars.add(par);
                }
            }
        } else {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    Parede par = new Parede();
                    par.x = (int) ((x + (tam * posX)) + (Math.random() * tam));
                    par.y = (int) ((y + (tam * posY)) + (Math.random() * tam));
                    par.sprite = sprite;
                    pars.add(par);
                }
            }
        }

        Parede[] parede = pars.toArray(new Parede[pars.size()]);
        return parede;
    }
}
