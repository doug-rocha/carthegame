/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.IO.Levels;

import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira (@dhorgas1) - dhorgas_software@hotmail.com
 */
public class DecoracoesArea extends ParedeArea {

    public int tam;

    public Decoracoes[] getDecoracoes() {
        if (tam == 0) {
            tam = 16;
        }
        ArrayList<Decoracoes> decos = new ArrayList<>();
        if (!random) {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    Decoracoes deco = new Decoracoes(0, 0);
                    deco.x = x + (tam * posX);
                    deco.y = y + (tam * posY);
                    deco.sprite = sprite;
                    deco.tam = tam;
                    decos.add(deco);
                }
            }
        } else {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    Decoracoes deco = new Decoracoes(0, 0);
                    deco.x = (int) ((x + (tam * posX)) + (Math.random() * tam));
                    deco.y = (int) ((y + (tam * posY)) + (Math.random() * tam));
                    deco.sprite = sprite;
                    deco.tam = tam;
                    decos.add(deco);
                }
            }
        }
        Decoracoes[] decoracao = decos.toArray(new Decoracoes[decos.size()]);
        return decoracao;
    }

}
