/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.IO.Levels;

import com.nonacept.carthegame.Game;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class CenarioChanger extends com.nonacept.carthegame.Entidades.CenarioChanger {

    public void preProc() {
        this.x = (int) (this.x * Game.MODRESOL);
        this.y = (int) (this.y * Game.MODRESOL);
        this.destX = (int) (this.destX * Game.MODRESOL);
        this.destY = (int) (this.destY * Game.MODRESOL);
        this.tamX = (int) (this.tamX * Game.MODRESOL);
        this.tamY = (int) (this.tamY * Game.MODRESOL);
    }
}
