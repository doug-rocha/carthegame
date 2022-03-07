/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.Entidades;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Douglas Rocha
 */
public class Itens {

    public short Xi, oldXi;
    public short Yi, oldYi;
    public short tipo;
    public boolean ativo;
    private Image nitro, nitroPlus, life, invi;

    public Itens() {
        Xi = 100;
        Yi = 100;
        tipo = 1;
        loadSprites();
    }

    public Image getSprite() {
        Image temporaria = null;
        if (tipo == 1) {
            temporaria = nitro;
        }
        if (tipo == 2) {
            temporaria = nitroPlus;
        }
        if (tipo == 3) {
            temporaria = life;
        }
        if (tipo == 4) {
            temporaria = invi;
        }
        return temporaria;
    }

    private void loadSprites() {
        Image temp;
        temp = new ImageIcon("arquivos/Images/nitro.cpn").getImage();
        nitro = temp;
        temp = new ImageIcon("arquivos/Images/nitro+.cpn").getImage();
        nitroPlus = temp;
        temp = new ImageIcon("arquivos/Images/life.cpn").getImage();
        life = temp;
        temp = new ImageIcon("arquivos/Images/invi.cpn").getImage();
        invi = temp;
    }
}
