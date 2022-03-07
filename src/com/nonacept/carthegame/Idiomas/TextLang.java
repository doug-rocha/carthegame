/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.Idiomas;

import java.awt.FontMetrics;

/**
 *
 * @author Douglas  Rocha
 */
public class TextLang {
    public String texto;
    public short posX;
    
    public int getLargura(FontMetrics f){
        return f.stringWidth(this.texto);
    }
}
