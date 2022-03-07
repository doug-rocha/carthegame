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
 * @author douglas
 */
public class DestroyedCar {

    public Image normal, explodido;
    public boolean exploded;
    public short x, y, oldX, oldY;
    public float durabilidade;
    public float explodindo;

    public DestroyedCar() {
        iniciar();
    }

    public DestroyedCar(int px, int py) {
        x = (short) px;
        y = (short) py;
        iniciar();
    }

    private void iniciar() {
        normal = new ImageIcon("arquivos/Images/destroy.cpn").getImage();
        explodido = new ImageIcon("arquivos/Images/expl.cpn").getImage();
        exploded = false;
        explodindo = (float) 10.0;
        durabilidade = (float) 1.0;
    }

    public Image getSprite() {
        Image retorno = normal;
        if (exploded) {
            retorno = explodido;
        }
        return retorno;
    }

    public boolean isExploded() {
        return exploded;
    }

}
