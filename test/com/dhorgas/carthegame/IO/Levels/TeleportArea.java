/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.IO.Levels;

import java.util.Random;

/**
 *
 * @author Douglas Rocha de Oliveira (@dhorgas1) - dhorgas_software@hotmail.com
 */
public class TeleportArea {

    public int x, y;
    public int areaX, areaY;
    public int destX, destY;
    public int areaDestX, areaDestY;
    private final Random rdm = new Random();

    public Teleport getTeleport() {
        rdm.setSeed((int) (Math.random() * 1000000000));
        Teleport ter = new Teleport();
        ter.x = x + (rdm.nextInt(areaX));
        ter.y = y + (rdm.nextInt(areaY));
        ter.destX = destX + (rdm.nextInt(areaDestX));
        ter.destY = destY + (rdm.nextInt(destY));
        
        return ter;
    }
}
