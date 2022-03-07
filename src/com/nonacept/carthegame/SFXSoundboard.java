/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame;

import java.io.File;

/**
 *
 * @author Douglas
 */
public class SFXSoundboard {

    public File intro0;
    public File intro1;
    public File intro2;
    public File wind;
    public File rajada;
    String ssPath;

    public SFXSoundboard(String ssDirectoryPath) {
        ssPath = ssDirectoryPath;
        loadInicio();
    }

    public void loadAll() {
        loadInicio();
        loadMenus();
        loadGame();
    }

    public void loadInicio() {
        intro0 = new File(ssPath + "/intro0.ctgm");
        intro1 = new File(ssPath + "/intro1.ctgm");
        intro2 = new File(ssPath + "/intro2.ctgm");
        wind = new File(ssPath + "/wind.ctgm");
        rajada = new File(ssPath + "/rajada.ctgm");
    }

    public void unloadInicio() {
        intro0 = null;
        intro1 = null;
        intro2 = null;
        wind = null;
        rajada = null;
    }

    public void loadMenus() {

    }

    public void loadGame() {

    }

}
