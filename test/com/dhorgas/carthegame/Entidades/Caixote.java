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
public class Caixote {

    public Image normal, destruido;
    public boolean destroyed;
    public short x, y, oldX, oldY;
    public float durabilidade;

    public Caixote() {
        iniciar();
    }

    public Caixote(int px, int py) {
        iniciar();
        x = (short)px;
        y = (short)py;
    }

    private void iniciar() {
        normal = new ImageIcon("arquivos/Images/cxc.cpn").getImage();
        destruido = new ImageIcon("arquivos/Images/cxc-destroy.cpn").getImage();
        destroyed = false;
    }

    public Image getSprite() {
        Image retorno = normal;
        if (destroyed) {
            retorno = destruido;
        }
        return retorno;
    }
    
    public boolean isDestroyed(){
        return destroyed;
    }
}
