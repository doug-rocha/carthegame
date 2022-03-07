/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.Options;

/**
 *
 * @author douglas
 */
public class Resolutions {

    public int minX, maxX, minY, maxY;
    public final double multiplicador = 1.429864253;

    public int diferenca, diferenca2, tamX, tamX2;

    public Resolutions(int altura, int largura, double modresol) {

        minY = 24;
        maxY = (int) (altura - 33);

        diferenca = maxY - minY;
        diferenca2 = (int) (diferenca - (16 * modresol));
        tamX = (int) (diferenca * multiplicador);
        tamX2 = (int) (tamX - (16 * modresol));
        //y=527
        //x=772
        //fator=1.464895636
        
        minX = (int) ((largura / 2) - (tamX / 2));
        maxX = (int) (minX + tamX);


    }
}
