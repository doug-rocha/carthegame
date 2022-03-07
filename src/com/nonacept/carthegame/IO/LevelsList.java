/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.IO;

import com.nonacept.carthegame.Game;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class LevelsList {

    public int ID;
    public String Location;

    public static LevelsList[] loadLista() {
        ArrayList<LevelsList> lista = new ArrayList<>();
        File arqList = new File("arquivos/Levels/levels.ctgi");
        try {
            FileReader leitor = new FileReader(arqList);
            BufferedReader entrada = new BufferedReader(leitor);
            String linha;
            StringTokenizer entrar;
            while ((linha = entrada.readLine()) != null) {
                entrar = new StringTokenizer(linha, ";");
                LevelsList list = new LevelsList();
                list.ID = Integer.parseInt(entrar.nextToken());
                list.Location = entrar.nextToken();
                lista.add(list);
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("INFO | ADD LVL %d - %s", list.ID, list.Location));
                }
            }
        } catch (FileNotFoundException ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("ERRO | O arquivo %s não pode ser encontrado", arqList.getName()));
            }
        } catch (IOException ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("ERRO | %s", ex.getMessage()));
            }
        }
        return lista.toArray(new LevelsList[lista.size()]);
    }

}
