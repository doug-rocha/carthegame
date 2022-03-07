/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame;

import java.io.File;

/**
 *
 * @author Douglas
 */
public class SFXSoundboard {

    public File intro0;
    public File intro1;
    public File inicio;
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
        inicio = new File(ssPath + "/inicio.ctgm");
    }
    
    public void unloadInicio(){
        intro0=null;
        intro1=null;
        inicio=null;
    }
    
    public void loadMenus(){
        
    }
    
    public void loadGame(){
    
    }

}
