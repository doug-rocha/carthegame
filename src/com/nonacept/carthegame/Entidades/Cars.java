/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.Entidades;

import com.nonacept.carthegame.Game;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;

/**
 *
 * @author Douglas
 */
public class Cars {

    public int x, y;
    public Image sprite, spriteI, face, cor;
    public Image[] sprites, spritesI, faces, cores;
    public int oldX, oldY;
    public double speed, resistencia, peso;
    public String carModel, carFact;

    public int idCar;
    private File path;
    private String pathName;

    public int corId;

    public Cars(int id) {
        idCar = id;
    }

    public Cars(int id, boolean loadAll) {
        idCar = id;
        loadAll();
    }

    public void loadProperties() {
        speed = 1.0;
        resistencia = 1.0;
        corId = 0;
        File carsIds = null;
        path = null;
        try {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println("INFO | CAR LD | CARREGANDO CARROS");
            }
            carsIds = new File("arquivos/cars/cars.ctgi");

            FileReader leitor = new FileReader(carsIds);

            BufferedReader entrada = new BufferedReader(leitor);
            String linha;
            boolean noFound = true;
            StringTokenizer entrar;
            while (((linha = entrada.readLine()) != null) && noFound) {
                entrar = new StringTokenizer(linha, ";");
                int tmpID = Integer.parseInt(entrar.nextToken());
                if (tmpID == idCar) {
                    pathName = entrar.nextToken();
                    path = new File(String.format("arquivos/cars/%s", pathName));
                    noFound = false;
                }
            }

            File properties = new File(String.format("%s/%s.ctgi", path.getPath(), pathName));
            leitor = new FileReader(properties);
            entrada = new BufferedReader(leitor);
            while ((linha = entrada.readLine()) != null) {
                entrar = new StringTokenizer(linha, ";");
                String item = entrar.nextToken();
                switch (item) {
                    case "spd":
                        speed = Double.valueOf(entrar.nextToken());
                        break;
                    case "rst":
                        resistencia = Double.valueOf(entrar.nextToken());
                        break;
                    case "nme":
                        carModel = entrar.nextToken();
                        break;
                    case "fbr":
                        carFact = entrar.nextToken();
                        break;
                }
            }

            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("INFO | CAR LD | CARRO CARREGADO %s", pathName));
            }

        } catch (FileNotFoundException ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("ERRO | CAR LD | ARQUIVO %s NÃO EXISTE", carsIds.getName()));
            }
        } catch (IOException ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("ERRO | CAR LD | %s", ex.getMessage()));
            }
        }

    }

    public void loadImages() {
        ImageIcon tex = new ImageIcon(String.format("%s/texturas/%d.cpn", path.getPath(), corId));
        ImageIcon texI = new ImageIcon(String.format("%s/texturas/invi/%d.cpn", path.getPath(), corId));
        ImageIcon fac = new ImageIcon(String.format("%s/faces/%d.cpn", path.getPath(), corId));
        ImageIcon cr = new ImageIcon(String.format("%s/cores/%d.cpn", path.getPath(), corId));
        sprite = tex.getImage();
        spriteI = texI.getImage();
        face = fac.getImage();
        cor = cr.getImage();
        if (Game.DEBUGMODE) {
            Game.TA = new TextAttributes(Color.GREEN);
            Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            System.out.println("INFO | CAR LD | SPRITES CARREGADOS");
        }
    }

    public void loadImageSelector() {

    }

    private void loadAll() {
        loadProperties();
        loadImages();
    }
}
