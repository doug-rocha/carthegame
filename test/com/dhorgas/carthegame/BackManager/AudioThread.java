/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.BackManager;

import com.dhorgas.carthegame.Game;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author Douglas Rocha
 */
public class AudioThread implements Runnable {

    public Player pl;
    public String url;
    public boolean informacoesdisp = false, pause = false;
    private File iStream;
    public int origem;
    public File musica;
    //File SFX;
    public int posicaomusica = 0;

    public AudioThread(File SFXOri) {
        iStream = SFXOri;
        this.origem = 3;
    }

    public AudioThread() {

    }

    private void play() {
        try {
            try {
                informacoesdisp = false;
                pl = new Player(new FileInputStream(url));
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("INFO | PLAYING %s", url));
                }
                pl.setGain(0.0f);
                pl.play();
                if (!pause) {
                    informacoesdisp = true;
                }

            } catch (FileNotFoundException ex) {
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.RED);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("ERRO | NÃO FOI POSSÍVEL ENCONTRAR O ARQUIVO %s", url));
                }
            }
        } catch (JavaLayerException ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("ERRO | FORMATO DE ARQUIVO INCORRETO %s", url));
            }
        }
    }

    @Override
    public void run() {
        if (origem == 3) { //SFX
            playSFX();
        } else {
            pause = false;
            play();
        }

    }

    public void setUrl(String URL, int org) {
        this.url = URL;
        musica = new File(url);
        this.origem = org;
        posicaomusica = 0;
    }

    private void playSFX() {
        try {
            try {
                new AdvancedPlayer(new FileInputStream(iStream)).play();
            } catch (FileNotFoundException ex) {
            }
        } catch (JavaLayerException ex) {
        }
    }

}
