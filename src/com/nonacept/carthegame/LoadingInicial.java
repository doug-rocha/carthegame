/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame;

import com.nonacept.carthegame.ETC.CredBlocks;
import com.nonacept.carthegame.Options.Resolutions;
import com.nonacept.carthegame.Options.DefResolutions;
import com.nonacept.carthegame.Idiomas.TutoLang;
import com.nonacept.carthegame.IO.ResolParserXML;
import com.nonacept.carthegame.IO.SaveGame;
import com.nonacept.carthegame.Options.OpcoesVar;
import com.nonacept.carthegame.Idiomas.Idioma;
import com.nonacept.carthegame.Entidades.Bots;
import com.nonacept.carthegame.Entidades.Cars;
import com.nonacept.carthegame.IO.Fontes;
import com.nonacept.carthegame.IO.LevelParser;
import com.nonacept.carthegame.IO.LevelsList;
import com.nonacept.carthegame.Idiomas.Creditos;
import com.nonacept.carthegame.Idiomas.TextLang;
import com.thoughtworks.xstream.XStream;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Rocha
 */
public class LoadingInicial implements Runnable {

    File MusicaDiretorio, sSDiretorio, LevelDiretorio;
    File[] Musicasd;
    int countMus, largura, altura, motivo, Fps, masterVolume;
    ImageIcon carroIc, fundoImIc, comemoracaoIc, iconeIc, carroiIc;
    Image carro, fundoIm, comemoracao, icone, carroi;
    String Infos;
    Game jogo;
    int tipo, idiomaid, nxtlvl;
    String saveName = "save";
    LevelParser lvlParser;

    boolean completo = false, mostra = false, mostrou = false, telacheia = false, aAliasing = true;
    File menuMusicaDiretorio;

    @Override
    public void run() {
        lvlParser = new LevelParser(jogo);
        completo = false;
        switch (tipo) {
            case 0:
                load();
                break;
            case 1:
                loadGame(true);
                break;
            case 2:
                //loadLevel(nxtlvl);
                lvlParser.loadLevel(nxtlvl);
                completo = true;
                break;
            case 3:
                LoadSaveGame();
                break;
        }
    }

    boolean loadsaved;

    void LoadSaveGame() {
        loadsaved = false;
        try {
            SaveGame saved;
            File arqSave = null;
            if (jogo.OSystem.contains("linux")) {
                arqSave = new File(System.getProperty("user.home") + "/.config/CTG/saves/" + saveName + ".xsav");
            } else if (jogo.OSystem.contains("windows")) {
                arqSave = new File(System.getProperty("user.home") + "/CTG/saves/" + saveName + ".xsav");
            }
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("INFO | CARREGANDO SAVE | EXISTE: %s", String.valueOf(arqSave.exists())));
            }
            if (arqSave.exists()) {
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("INFO | CARREGANDO SAVE | %s", arqSave.getAbsolutePath()));
                }
                XStream xsave = new XStream();
                xsave.alias("SaveCTG", SaveGame.class);
                saved = (SaveGame) xsave.fromXML(arqSave);
                nxtlvl = saved.LvlAtual;
                if (jogo.verint == saved.GameBuild) {
                    loadGame(false);
                    //lvlParser.loadLevel(saved.LvlAtual);
                    if ((Game.MODRESOL != saved.ModResol) && (saved.GameTipo != 2)) {
                        for (int i = 0; i < saved.botveloc.length; i++) {
                            jogo.inimigos[i].velocidade = saved.botveloc[i];
                            jogo.inimigos[i].x = (short) ((saved.botx[i] / saved.ModResol) * Game.MODRESOL);
                            jogo.inimigos[i].y = (short) ((saved.boty[i] / saved.ModResol) * Game.MODRESOL);
                            jogo.inimigos[i].oldX = (short) ((saved.oldbotx[i] / saved.ModResol) * Game.MODRESOL);
                            jogo.inimigos[i].oldY = (short) ((saved.oldboty[i] / saved.ModResol) * Game.MODRESOL);
                        }
                        for (int i = 0; i < saved.cxcDestroy.length; i++) {
                            jogo.caixo[i].destroyed = saved.cxcDestroy[i];
                            jogo.caixo[i].durabilidade = (float) saved.cxcDurab[i];
                            //jogo.caixo[i].oldX = (short) ((saved.oldCxcX[i] / saved.ModResol) * Game.MODRESOL);
                            //jogo.caixo[i].oldY = (short) ((saved.oldCxcY[i] / saved.ModResol) * Game.MODRESOL);
                            //jogo.caixo[i].x = (short) ((saved.cxcX[i] / saved.ModResol) * Game.MODRESOL);
                            //jogo.caixo[i].y = (short) ((saved.cxcY[i] / saved.ModResol) * Game.MODRESOL);
                        }
                        for (int i = 0; i < saved.dcarExplodido.length; i++) {
                            jogo.destruidoscar[i].durabilidade = (float) saved.dcarDurab[i];
                            jogo.destruidoscar[i].exploded = saved.dcarExplodido[i];
                            if (saved.dcarExplodido[i]) {
                                jogo.destruidoscar[i].explodindo = (float) 0.9;
                            }
                            //jogo.destruidoscar[i].oldX = (short) ((saved.oldDcarX[i] / saved.ModResol) * Game.MODRESOL);
                            //jogo.destruidoscar[i].oldY = (short) ((saved.oldDcarY[i] / saved.ModResol) * Game.MODRESOL);
                            //jogo.destruidoscar[i].x = (short) ((saved.dcarX[i] / saved.ModResol) * Game.MODRESOL);
                            //jogo.destruidoscar[i].y = (short) ((saved.dcarY[i] / saved.ModResol) * Game.MODRESOL);
                        }
                        jogo.carro.x = (int) ((saved.carro.x / saved.ModResol) * Game.MODRESOL);
                        jogo.carro.y = (int) ((saved.carro.y / saved.ModResol) * Game.MODRESOL);
                    } else {
                        for (int i = 0; i < saved.botveloc.length; i++) {
                            jogo.inimigos[i].velocidade = saved.botveloc[i];
                            jogo.inimigos[i].x = (short) saved.botx[i];
                            jogo.inimigos[i].y = (short) saved.boty[i];
                            jogo.inimigos[i].oldX = (short) saved.oldbotx[i];
                            jogo.inimigos[i].oldY = (short) saved.oldboty[i];
                        }
                        for (int i = 0; i < saved.cxcDestroy.length; i++) {
                            jogo.caixo[i].destroyed = saved.cxcDestroy[i];
                            jogo.caixo[i].durabilidade = (float) saved.cxcDurab[i];
                            //jogo.caixo[i].oldX = (short) saved.oldCxcX[i];
                            //jogo.caixo[i].oldY = (short) saved.oldCxcY[i];
                            //jogo.caixo[i].x = (short) saved.cxcX[i];
                            //jogo.caixo[i].y = (short) saved.cxcY[i];
                        }
                        for (int i = 0; i < saved.dcarExplodido.length; i++) {
                            jogo.destruidoscar[i].durabilidade = (float) saved.dcarDurab[i];
                            jogo.destruidoscar[i].exploded = saved.dcarExplodido[i];
                            if (saved.dcarExplodido[i]) {
                                jogo.destruidoscar[i].explodindo = (float) 0.9;
                            }
                            //jogo.destruidoscar[i].oldX = (short) saved.oldDcarX[i];
                            //jogo.destruidoscar[i].oldY = (short) saved.oldDcarY[i];
                            //jogo.destruidoscar[i].x = (short) saved.dcarX[i];
                            //jogo.destruidoscar[i].y = (short) saved.dcarY[i];
                        }
                        jogo.mapX = (int) ((saved.MapX / saved.ModResol) * Game.MODRESOL);
                        jogo.mapY = (int) ((saved.MapY / saved.ModResol) * Game.MODRESOL);
                        jogo.carro.x = (int) ((saved.carro.x / saved.ModResol) * Game.MODRESOL);
                        jogo.carro.y = (int) ((saved.carro.y / saved.ModResol) * Game.MODRESOL);
                    }
                    for (int j = 0; j < saved.portalativo.length; j++) {
                        jogo.portal[j].ativo = saved.portalativo[j];
                    }
                    for (int i = 0; i < saved.chegadaativa.length; i++) {
                        jogo.chegadas[i].ativo = saved.chegadaativa[i];
                    }
                    for (int i = 0; i < saved.itensJG2Ativo.length; i++) {
                        jogo.itensJg2[i].ativo = saved.itensJG2Ativo[i];
                        jogo.itensJg2[i].tipo = (short) saved.itensJG2Tipo[i];
                    }
                    jogo.it.ativo = saved.itAtivo;
                    jogo.it.tipo = (short) saved.itTipo;
                    jogo.carro.corId = saved.carro.corId;
                    jogo.carro.idCar = saved.carro.idCar;
                    jogo.nivel = saved.nivel;
                    jogo.propcar = saved.PropCar;
                    jogo.exibindoinfom = saved.exibindoinfom;
                    jogo.exibindoitem = saved.exibindoitem;
                    jogo.ativado = saved.ativado;
                    jogo.invisivel = saved.invisivel;
                    jogo.tempoinfom = saved.tempoinfom;
                    jogo.tempospawn = saved.tempospawn;
                    jogo.loopitem = saved.loopitem;
                    jogo.tempoativacao = saved.tempoativacao;
                    jogo.tempoinvisible = saved.tempoinvisible;
                    jogo.frame = saved.frame;
                    jogo.quantidade = saved.quantidade;
                    jogo.nitroexibido = saved.nitroexibido;
                    jogo.exibindovida = saved.exibindovida;
                    jogo.nitro = saved.nitro;
                    jogo.cardamage = saved.cardamage;
                    jogo.tempodano = saved.tempodano;
                    jogo.tempovida = saved.tempovida;
                    jogo.loopitem = saved.loopitem;
                    jogo.loopnivelcar = saved.loopnivelcar;
                    jogo.nivelcar = saved.nivelcar;
                    completo = true;
                    loadsaved = true;
                } else {
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.YELLOW);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("ALRT | O ARQUIVO SALVO NÃO É COMPATÍVEL");
                        Game.TA = new TextAttributes(Color.GREEN);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("INFO | INICIANDO NOVO JOGO");
                    }
                    setLvl(1);
                    loadGame(true);

                }

            } else {
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.YELLOW);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println("ALRT | NÃO HÁ NENHUM PROGRESSO SALVO");
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println("INFO | INICIANDO NOVO JOGO");
                }
                setLvl(1);
                loadGame(true);
            }
        } catch (Exception ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            }
            System.out.println(String.format("ERRO | ERRO DURANTE O CARREGAMENTO DO JOGO %s\nERRO | DETALHES: %s", ex.getMessage(), ex.toString()));
        }
        completo = true;
    }

    void setSaveName(String name) {
        saveName = name;
    }

    void setTipoLoad(int tip, Game tela) {
        jogo = tela;
        tipo = tip;
    }

    void setLvl(int lv) {
        nxtlvl = lv;
    }

    void mostrarJOtionPane(boolean mostrar, int mot) {
        mostra = mostrar;
        motivo = mot;
        mostrou = false;
    }

    boolean mostrouJOP() {
        return mostrou;
    }

    void loadGame(boolean exComp) {
        jogo.SalvarOpcoes();
        jogo.resol = new Resolutions(jogo.janelaH, jogo.janelaW, Game.MODRESOL);
        try {
            loadMusicas();
            jogo.carro = new Cars(0, true);
            jogo.carro.x = 10;
            jogo.carro.y = 22;
            jogo.quantidade = 1;
            jogo.tempospawn = 0;
            jogo.nivel = 0;
            jogo.nivelcar = 0;
            jogo.loopnivel = 1;
            jogo.loopnivelcar = 1;
            jogo.loopitem = 0;
            jogo.exibindoitem = false;
            jogo.tempoinvisible = 0;
            jogo.invisivel = false;
            for (int j = 0; j < jogo.inimigos.length; j++) {
                jogo.inimigos[j] = new Bots();
                jogo.inimigos[j].setX((short) ((int) 1 + Math.random() * jogo.resol.tamX2));
                jogo.inimigos[j].setY((short) ((int) 1 + Math.random() * jogo.resol.diferenca2));
            }
            jogo.cardamage = 0;
            jogo.nitro = 50;
            jogo.nitroexibido = 0;
            jogo.exibindovida = 100;
            //loadLevel(nxtlvl);
            lvlParser.loadLevel(nxtlvl);
            for (int i = 0; i < jogo.rastroSpriteIc.length; i++) {
                jogo.rastroSpriteIc[i] = new ImageIcon("arquivos/Images/r" + i + ".cpn");
                jogo.rastroSprite[i] = jogo.rastroSpriteIc[i].getImage();
                jogo.rastrox[i] = jogo.carro.x;
                jogo.rastroy[i] = jogo.carro.y;
            }
            if (exComp) {
                completo = true;
            }
        } catch (Exception e) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            }
            System.out.println("ERRO | " + e.getCause().toString());
        }

        jogo.player.stop();
        jogo.exibindoinfom = false;
        jogo.musicaanterior = -1;

    }

    /*void loadLevel(int lvl) {
        boolean reqs = false;
        jogo.walls.clear();
        jogo.portals.clear();
        jogo.linhasChega.clear();
        jogo.caixos.clear();
        jogo.destruidos.clear();
        jogo.itensCenario.clear();
        File lvlArq = new File(jogo.levelDirectory + "/lvl" + lvl + ".clvl");
        try {
            FileReader leitor = new FileReader(lvlArq);
            BufferedReader entrada = new BufferedReader(leitor);
            String linha = entrada.readLine();
            if (linha.equals("gameTipo 1")) {
                jogo.gameTipo = 1;
            } else if (linha.equals("gameTipo 2")) {
                jogo.gameTipo = 2;
            }
            jogo.lvlAtual = lvl;
            jogo.lvlName = entrada.readLine();
            jogo.lvlDescri = entrada.readLine();
            boolean setInimigos = false;
            if (Main.debugmode) {
                Main.ta = new TextAttributes(Color.ORANGE);
                Main.debugConsole.setTextAttributes(Main.ta);
                System.out.println("LDLV | GAME TIPO = " + jogo.gameTipo);
            }
            while ((linha = entrada.readLine()) != null) {
                if (jogo.gameTipo == 1) {
                    StringTokenizer entrar = new StringTokenizer(linha, ";");
                    int obj;
                    obj = Integer.parseInt(entrar.nextToken());
                    if (obj == 0) { //PAREDES
                        Paredes par = new Paredes(0, 0);
                        int xpar = Integer.parseInt(entrar.nextToken());
                        int ypar = Integer.parseInt(entrar.nextToken());
                        par.x = (short) (xpar * Main.MODRESOL);
                        par.y = (short) (ypar * Main.MODRESOL);
                        if (entrar.hasMoreTokens()) {
                            String path = entrar.nextToken();
                            if ((!path.equals("default")) && (!path.equals("padrao"))) {
                                par.sprite = new ImageIcon("arquivos/Images/cenas/" + path);
                            }
                        }
                        jogo.walls.add(par);
                    }
                    if (obj == 1) { //PORTAIS
                        Teleportes port = new Teleportes(0, 0, 100, 100);
                        int xport = Integer.parseInt(entrar.nextToken());
                        int yport = Integer.parseInt(entrar.nextToken());
                        int destxport = Integer.parseInt(entrar.nextToken());
                        int destyport = Integer.parseInt(entrar.nextToken());
                        port.x = (short) (xport * Main.MODRESOL);
                        port.y = (short) (yport * Main.MODRESOL);
                        port.destX = (short) (destxport * Main.MODRESOL);
                        port.destY = (short) (destyport * Main.MODRESOL);
                        jogo.portals.add(port);
                        if (Main.debugmode) {
                            Main.ta = new TextAttributes(Color.ORANGE);
                            Main.debugConsole.setTextAttributes(Main.ta);
                            System.out.println(String.format("LDLV | COR ALEATÓRIA DOS PORTAIS %s: %s", (jogo.portals.size() - 1), port.color));
                        }
                    }
                    if (obj == 2) { //CHEGADAS
                        Chegada cheg = new Chegada(0, 0);
                        int xcheg = Integer.parseInt(entrar.nextToken());
                        int ycheg = Integer.parseInt(entrar.nextToken());
                        cheg.x = (short) (xcheg * Main.MODRESOL);
                        cheg.y = (short) (ycheg * Main.MODRESOL);
                        cheg.ativo = false;
                        jogo.linhasChega.add(cheg);
                    }
                    if (obj == -1) { //POSICAO INICIAL
                        String resul;
                        resul = entrar.nextToken();
                        if (resul.equals("center")) {
                            jogo.carro.x = (jogo.janelaW / 2) - SIZES._8;
                        } else {
                            int carxi = Integer.parseInt(resul);
                            jogo.carro.x = (int) (carxi * Main.MODRESOL);
                        }
                        resul = entrar.nextToken();
                        if (resul.equals("center")) {
                            jogo.carro.y = (jogo.janelaH / 2) - SIZES._8;
                        } else {
                            int caryi = Integer.parseInt(resul);
                            jogo.carro.y = (int) (caryi * Main.MODRESOL);
                        }
                    }
                    if (obj == -2) { //QUANTIDADE NITRO
                        double qnitro = Double.valueOf(entrar.nextToken());
                        if (qnitro > 100) {
                            qnitro = 100;
                        }
                        jogo.nitro = qnitro;
                    }
                    if (obj == -3) { //TEMPO INVISIVEL
                        jogo.invisivel = true;
                        String resul = entrar.nextToken();
                        if (resul.endsWith("s")) {
                            resul = resul.replace("s", "");
                            int reulint = Integer.parseInt(resul) * 90;
                            jogo.tempoinvisible = reulint;
                            if (Main.debugmode) {
                                Main.ta = new TextAttributes(Color.ORANGE);
                                Main.debugConsole.setTextAttributes(Main.ta);
                                System.out.println("LDLV | TEMPO INVI " + reulint);
                            }
                        } else {
                            jogo.tempoinvisible = Integer.parseInt(resul);
                        }
                        if (Main.debugmode) {
                            Main.ta = new TextAttributes(Color.ORANGE);
                            Main.debugConsole.setTextAttributes(Main.ta);
                            System.out.println("LDLV | TEMPO INVI " + jogo.tempoinvisible);
                        }
                    }
                    if (obj == -6) { //REQUISITOS
                        jogo.reqInimigos = Integer.parseInt(entrar.nextToken());
                        jogo.reqNivel = Integer.parseInt(entrar.nextToken());
                        reqs = true;
                    }
                    if (obj == 3) { //CAIXOTE
                        Caixote cxc = new Caixote();
                        int xcxc = Integer.parseInt(entrar.nextToken());
                        int ycxc = Integer.parseInt(entrar.nextToken());
                        float durcxc = Float.parseFloat(entrar.nextToken());
                        cxc.x = (short) (xcxc * Main.MODRESOL);
                        cxc.y = (short) (ycxc * Main.MODRESOL);
                        cxc.durabilidade = durcxc;
                        if (entrar.hasMoreTokens()) {
                            String path1 = entrar.nextToken();
                            if ((!path1.equals("default")) && (!path1.equals("padrao"))) {
                                cxc.normal = new ImageIcon("arquivos/Images/cenas/" + path1).getImage();
                            }
                            String path2 = entrar.nextToken();
                            if ((!path2.equals("default")) && (!path2.equals("padrao"))) {
                                cxc.destruido = new ImageIcon("arquivos/Images/cenas/" + path2).getImage();
                            }
                        }
                        jogo.caixos.add(cxc);
                    }
                    if (obj == 4) { //CARROS DESTRUIDOS
                        DestroyedCar dcar = new DestroyedCar();
                        int xdcar = Integer.parseInt(entrar.nextToken());
                        int ydcar = Integer.parseInt(entrar.nextToken());
                        float durdcar = Float.parseFloat(entrar.nextToken());
                        dcar.x = (short) (xdcar * Main.MODRESOL);
                        dcar.y = (short) (ydcar * Main.MODRESOL);
                        dcar.durabilidade = durdcar;
                        jogo.destruidos.add(dcar);
                    }
                    if (obj == 5) {  //TEXTURAS DECORATIVAS
                        Cenario cena = new Cenario(0, 0);
                        int xcena = Integer.parseInt(entrar.nextToken());
                        int ycena = Integer.parseInt(entrar.nextToken());
                        cena.x = (short) (xcena * Main.MODRESOL);
                        cena.y = (short) (ycena * Main.MODRESOL);
                        cena.sprite = new ImageIcon("arquivos/Images/cenas/" + entrar.nextToken());
                        if (entrar.hasMoreTokens()) {
                            int tmpTam = Integer.parseInt(entrar.nextToken());
                            cena.tam = (int) (tmpTam * Main.MODRESOL);
                        } else {
                            cena.tam = (int) (16 * Main.MODRESOL);
                        }
                        jogo.itensCenario.add(cena);

                    }

                } else if (jogo.gameTipo == 2) {
                    StringTokenizer entrar = new StringTokenizer(linha, ";");
                    int obj;
                    obj = Integer.parseInt(entrar.nextToken());
                    if (obj == 0) { //PAREDES
                        Paredes par = new Paredes(0, 0);
                        int xpar = Integer.parseInt(entrar.nextToken());
                        int ypar = Integer.parseInt(entrar.nextToken());
                        par.oldX = (short) xpar;
                        par.oldY = (short) ypar;
                        if (entrar.hasMoreTokens()) {
                            String path = entrar.nextToken();
                            if ((!path.equals("default")) && (!path.equals("padrao"))) {
                                par.sprite = new ImageIcon("arquivos/Images/cenas/" + path);
                            }
                        }
                        jogo.walls.add(par);
                    }
                    if (obj == 2) { //CHEGADAS
                        Chegada cheg = new Chegada(0, 0);
                        int xcheg = Integer.parseInt(entrar.nextToken());
                        int ycheg = Integer.parseInt(entrar.nextToken());
                        cheg.oldX = (short) xcheg;
                        cheg.oldY = (short) ycheg;
                        cheg.ativo = true;
                        jogo.linhasChega.add(cheg);
                    }
                    if (obj == -1) { //CONFIG NIVEL
                        int nvl = Integer.parseInt(entrar.nextToken());
                        int quan = Integer.parseInt(entrar.nextToken());
                        jogo.nivel = nvl;
                        jogo.quantidade = quan;
                        setInimigos = true;
                    }
                    if (obj == -2) { //QUANTIDADE NITRO
                        double qnitro = Double.valueOf(entrar.nextToken());
                        if (qnitro > 100) {
                            qnitro = 100;
                        }
                        jogo.nitro = qnitro;
                    }
                    if (obj == -3) { //TEMPO INVISIVEL
                        jogo.invisivel = true;
                        String resul = entrar.nextToken();
                        if (resul.endsWith("s")) {
                            resul = resul.replace("s", "");
                            int reulint = Integer.parseInt(resul) * 90;
                            jogo.tempoinvisible = reulint;
                            if (Main.debugmode) {
                                Main.ta = new TextAttributes(Color.GREEN);
                                Main.debugConsole.setTextAttributes(Main.ta);
                                System.out.println("LDLV | TEMPO INVI " + reulint);
                            }
                        } else {
                            jogo.tempoinvisible = Integer.parseInt(resul);
                        }
                        if (Main.debugmode) {
                            Main.ta = new TextAttributes(Color.GREEN);
                            Main.debugConsole.setTextAttributes(Main.ta);
                            System.out.println("LDLV | TEMPO INVI " + jogo.tempoinvisible);
                        }
                    }
                    if (obj == -4) { //POSICAO INICIAL MAPA
                        int resul = Integer.parseInt(entrar.nextToken());
                        jogo.mapX = (int) (resul * Main.MODRESOL);
                        resul = Integer.parseInt(entrar.nextToken());
                        jogo.mapY = (int) (resul * Main.MODRESOL);
                    }
                    if (obj == -5) { //ITENS
                        Itens it = new Itens();
                        it.tipo = Short.parseShort(entrar.nextToken());
                        it.oldXi = Short.parseShort(entrar.nextToken());
                        it.oldYi = Short.parseShort(entrar.nextToken());
                        it.ativo = true;
                        jogo.listItensJg2.add(it);
                    }
                    if (obj == -6) { //REQUISITOS
                        jogo.reqInimigos = Integer.parseInt(entrar.nextToken());
                        jogo.reqNivel = Integer.parseInt(entrar.nextToken());
                        reqs = true;
                    }
                    if (obj == 3) { //CAIXOTE
                        Caixote cxc = new Caixote();
                        int xcxc = Integer.parseInt(entrar.nextToken());
                        int ycxc = Integer.parseInt(entrar.nextToken());
                        float durcxc = Float.parseFloat(entrar.nextToken());
                        cxc.oldX = (short) xcxc;
                        cxc.oldY = (short) ycxc;
                        cxc.durabilidade = durcxc;
                        if (entrar.hasMoreTokens()) {
                            String path1 = entrar.nextToken();
                            if ((!path1.equals("default")) && (!path1.equals("padrao"))) {
                                cxc.normal = new ImageIcon("arquivos/Images/cenas/" + path1).getImage();
                            }
                            String path2 = entrar.nextToken();
                            if ((!path2.equals("default")) && (!path2.equals("padrao"))) {
                                cxc.destruido = new ImageIcon("arquivos/Images/cenas/" + path2).getImage();
                            }
                        }
                        jogo.caixos.add(cxc);
                    }
                    if (obj == 4) { //CARROS DESTRUIDOS
                        DestroyedCar dcar = new DestroyedCar();
                        int xdcar = Integer.parseInt(entrar.nextToken());
                        int ydcar = Integer.parseInt(entrar.nextToken());
                        float durdcar = Float.parseFloat(entrar.nextToken());
                        dcar.oldX = (short) xdcar;
                        dcar.oldY = (short) ydcar;
                        dcar.durabilidade = durdcar;
                        jogo.destruidos.add(dcar);
                    }
                    if (obj == 5) {  //TEXTURAS DECORATIVAS
                        Cenario cena = new Cenario(0, 0);
                        int xcena = Integer.parseInt(entrar.nextToken());
                        int ycena = Integer.parseInt(entrar.nextToken());
                        cena.oldX = (short) (xcena);
                        cena.oldY = (short) (ycena);
                        cena.sprite = new ImageIcon("arquivos/Images/cenas/" + entrar.nextToken());
                        if (entrar.hasMoreTokens()) {
                            int tmpTam = Integer.parseInt(entrar.nextToken());
                            cena.tam = (int) (tmpTam * Main.MODRESOL);
                        } else {
                            cena.tam = (int) (16 * Main.MODRESOL);
                        }
                        jogo.itensCenario.add(cena);

                    }
                    if (!setInimigos) {
                        jogo.quantidade = 12;
                        jogo.nivel = 7;
                    }
                    jogo.carro.x = (int) (jogo.resol.tamX / 2 - (8 * Main.MODRESOL));
                    jogo.carro.y = (int) (jogo.resol.diferenca / 2 - (8 * Main.MODRESOL));
                    jogo.itensJg2 = jogo.listItensJg2.toArray(new Itens[jogo.listItensJg2.size()]);
                }
                if (reqs == false) {
                    jogo.reqInimigos = 10;
                    jogo.reqNivel = 1;

                }

            }
            jogo.wall = jogo.walls.toArray(new Paredes[jogo.walls.size()]);
            jogo.portal = jogo.portals.toArray(new Teleportes[jogo.portals.size()]);
            jogo.chegadas = jogo.linhasChega.toArray(new Chegada[jogo.linhasChega.size()]);
            jogo.caixo = jogo.caixos.toArray(new Caixote[jogo.caixos.size()]);
            jogo.destruidoscar = jogo.destruidos.toArray(new DestroyedCar[jogo.destruidos.size()]);
            jogo.cenas = jogo.itensCenario.toArray(new Cenario[jogo.itensCenario.size()]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o jogo!\nEstamos tentando resolve-lo, abra o jogo novamente!\n\n" + e, "ERRO", JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        }
        if (jogo.yep == 6) {
            completo = true;
        }
    }*/
    void load() {
        if (mostra) {
            if (motivo == 0) {
                jogo.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "A Resolução contida nas configurações do jogo é de: " + jogo.janelaW + "X" + jogo.janelaH + ".\nA Resolução mínima suportada é de: 690X480.\nO jogo será executado na resolução mínima.", jogo.janelaW + "X" + jogo.janelaH + " -> 690X480", 1);
            }
            if (motivo == 1) {
                jogo.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "A Resolução contida nas configurações do jogo é de: " + jogo.janelaW + "X" + jogo.janelaH + ".\nIsso ultrapassa a resolução de sua tela.\nO jogo será executado na resolução maxima.", jogo.janelaW + "X" + jogo.janelaH + " -> " + jogo.tela.width + "x" + jogo.tela.height, 1);
            }
            mostrou = true;
        } else {
            fundoImIc = new ImageIcon("arquivos/Images/fundo.cpn");
            iconeIc = new ImageIcon("arquivos/Images/icon.cpn");
            fundoIm = fundoImIc.getImage();
            icone = iconeIc.getImage();

            Fontes.loadFonts();

            try {
                File arqinfo = null;
                if (jogo.OSystem.contains("linux")) {
                    arqinfo = new File(System.getProperty("user.home") + "/.config/CTG/info.ctgi");
                } else if (jogo.OSystem.contains("windows")) {
                    arqinfo = new File(System.getProperty("user.home") + "/CTG/info.ctgi");
                }
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println("INFO | ARQUIVO DE INFORMAÇÕES DE PASTA: " + arqinfo.getAbsolutePath());
                }
                if (!arqinfo.exists()) {
                    File pasta = null;
                    if (jogo.OSystem.contains("linux")) {
                        pasta = new File(System.getProperty("user.home") + "/.config/CTG");
                    } else if (jogo.OSystem.contains("windows")) {
                        pasta = new File(System.getProperty("user.home") + "/CTG");
                    }
                    if (!pasta.exists()) {
                        pasta.mkdir();
                    }
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.YELLOW);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println(String.format("ALRT | ARQUIVO %s NÃO EXISTE", arqinfo.getPath()));
                        Game.TA = new TextAttributes(Color.GREEN);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println(String.format("INFO | CRIANDO ARQUIVO %s.", arqinfo.getName()));
                    }
                    arqinfo.createNewFile();
                    FileWriter escritor = new FileWriter(arqinfo);
                    PrintWriter pw = new PrintWriter(escritor);
                    String escrita = "arquivos/version.ctgv;arquivos/Sons/musicas;arquivos/Sons;960;720;arquivos/Sons/Inf;false;arquivos/Levels;90;0;true;80;";
                    pw.append(escrita);
                    escritor.close();
                    pw.close();
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.GREEN);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.print(String.format("...", arqinfo.getName()));
                    }
                }
                FileReader leitor = new FileReader(arqinfo);
                BufferedReader entrada = new BufferedReader(leitor);
                String linha;
                while ((linha = entrada.readLine()) != null) {
                    StringTokenizer entrar = new StringTokenizer(linha, ";");
                    FileReader versao = new FileReader(entrar.nextToken());
                    MusicaDiretorio = new File(entrar.nextToken());
                    sSDiretorio = new File(entrar.nextToken());
                    menuMusicaDiretorio = new File(sSDiretorio.getAbsolutePath() + "/menu");
                    largura = Integer.parseInt(entrar.nextToken());
                    altura = Integer.parseInt(entrar.nextToken());
                    Infos = entrar.nextToken();
                    telacheia = Boolean.valueOf(entrar.nextToken());
                    LevelDiretorio = new File(entrar.nextToken());
                    Fps = Integer.parseInt(entrar.nextToken());
                    idiomaid = Integer.parseInt(entrar.nextToken());
                    aAliasing = Boolean.valueOf(entrar.nextToken());
                    masterVolume = Integer.parseInt(entrar.nextToken());
                }
                loadDefResols();
                File arqOps = null;
                if (jogo.OSystem.contains("linux")) {
                    arqOps = new File(System.getProperty("user.home") + "/.config/CTG/ops.ops");
                } else if (jogo.OSystem.contains("windows")) {
                    arqOps = new File(System.getProperty("user.home") + "/CTG/ops.ops");
                }
                XStream xstream = new XStream();
                xstream.alias("opcoesCTG", OpcoesVar.class);
                boolean loadOpsFail = false;
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("INFO | ARQUIVO DE OPCÕES %s", arqOps.getAbsolutePath()));
                    System.out.println("INFO | VERIFICANDO EXISTENCIA DO ARQUIVO DE OPÇÕES");
                }
                if (arqOps.exists()) {
                    OpcoesVar ops = (OpcoesVar) xstream.fromXML(arqOps);
                    jogo.janelaH = ops.resolucaoY;
                    jogo.janelaW = ops.resolucaoX;
                    jogo.telacheia = ops.telaCheia;
                    jogo.FPS = ops.FPS;
                    idiomaid = ops.idIdioma;
                    String gameid = ops.GameId;
                    jogo.InstrucoesView = ops.InstrucaoView;
                    jogo.antiAliasing = ops.antiAliasing;
                    jogo.masterVolume = ops.masterVolume;
                    if (!gameid.equals(jogo.gameID + com.nonacept.carthegame.Options.OpcoesEmbut.VER)) {
                        arqOps.delete();
                        loadOpsFail = true;
                        if (Game.DEBUGMODE) {
                            Game.TA = new TextAttributes(Color.YELLOW);
                            Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                            System.out.println("ALRT | ARQUIVOS DE OPÇÕES EXISTE, MAS APRESENTA CONFIGURAÇÕES DESCONHECIDAS");
                        }
                    }
                } else {
                    loadOpsFail = true;
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.YELLOW);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("ALRT | ARQUIVOS DE OPÇÕES NÃO EXISTE");
                    }
                }
                if (loadOpsFail) {
                    jogo.FPS = Fps;
                    jogo.janelaH = altura;
                    jogo.janelaW = largura;
                    jogo.telacheia = telacheia;
                    jogo.InstrucoesView = false;
                    jogo.antiAliasing = aAliasing;
                    jogo.masterVolume = masterVolume;
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.GREEN);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("INFO | USANDO OPÇÕES PADRÃO");
                    }
                } else {
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.GREEN);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("INFO | ARQUIVO DE OPÇÕES ENCONTRADO E CARREGADO");
                    }
                }
                jogo.musicDiretory = MusicaDiretorio;
                jogo.sSDiretory = sSDiretorio;
                jogo.sfx = new SFXSoundboard(jogo.sSDiretory.getPath());
                jogo.menuMusicDiretory = menuMusicaDiretorio;
                jogo.icone = icone;
                jogo.fundoIm = fundoIm;
                jogo.levelDirectory = LevelDiretorio;
                jogo.Infosm = Infos;

                loadIdioma();
                comemoracaoIc = new ImageIcon("arquivos/Images/come-" + langarq + ".cpn");
                comemoracao = comemoracaoIc.getImage();
                jogo.comemoracao = comemoracao;
                jogo.musicasMD = menuMusicaDiretorio.listFiles();
                jogo.countmenumus = jogo.musicasMD.length;
                jogo.musicsMenu = new ArrayList();
                for (int i = 0; i < jogo.countmenumus; i++) {
                    jogo.musicsMenu.add(i);
                }
                verificarSaveExist();
                jogo.listaLevels = LevelsList.loadLista();
                completo = true;
            } catch (Exception e) {
                File arqinfo = null;
                if (jogo.OSystem.contains("linux")) {
                    arqinfo = new File(System.getProperty("user.home") + "/.config/CTG/info.ctgi");
                } else if (jogo.OSystem.contains("windows")) {
                    arqinfo = new File(System.getProperty("user.home") + "/CTG/info.ctgi");
                }
                try {
                    if (arqinfo.exists()) {
                        arqinfo.delete();
                    } else {
                        File pasta = null;
                        if (jogo.OSystem.contains("linux")) {
                            pasta = new File(System.getProperty("user.home") + "/.config/CTG");
                        } else if (jogo.OSystem.contains("windows")) {
                            pasta = new File(System.getProperty("user.home") + "/CTG");
                        }
                        pasta.mkdir();
                    }

                    arqinfo.createNewFile();
                    FileWriter escritor = new FileWriter(arqinfo);
                    PrintWriter pw = new PrintWriter(escritor);
                    String escrita = "arquivos/version.ctgv;arquivos/Sons/musicas;arquivos/Sons;960;720;arquivos/Sons/Inf;false;arquivos/Levels;90;0;true;80;";
                    pw.append(escrita);
                    escritor.close();
                    pw.close();
                } catch (IOException ex) {
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.RED);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    }
                    System.out.println("ERRO | " + ex.getMessage());
                }
                jogo.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, "Erro ao carregar o jogo!\nEstamos tentando resolve-lo, abra o jogo novamente!\n\n", "ERRO", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    boolean loadCompleto() {
        return completo;
    }
    String langarq;

    private void loadIdioma() {
        String arq = "";
        try {
            File leitorf = new File("arquivos/lang/langs.ctgi");
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("INFO | ARQUIVO DE LINGUAS: %s", leitorf.getAbsolutePath()));
            }
            InputStream opa = new FileInputStream(leitorf);
            Charset cs = Charset.forName("UTF-8");
            InputStreamReader entra = new InputStreamReader(opa, cs);
            BufferedReader entrada = new BufferedReader(entra);
            String linha;
            while ((linha = entrada.readLine()) != null) {
                StringTokenizer entrar = new StringTokenizer(linha, "%");
                if (idiomaid == Integer.parseInt(entrar.nextToken())) {
                    arq = entrar.nextToken();
                }
            }
            File xLangArq = new File("arquivos/lang/" + arq + ".xlang");
            XStream xstreamIdioma = new XStream();
            xstreamIdioma.alias("idiomaArq", Idioma.class);
            xstreamIdioma.alias("tutoIdiomaArq", TutoLang.class);
            xstreamIdioma.alias("TextLang", TextLang.class);
            xstreamIdioma.alias("credArq", Creditos.class);
            xstreamIdioma.alias("block", CredBlocks.class);
            jogo.iMsg = (Idioma) xstreamIdioma.fromXML(xLangArq);
            if (jogo.iMsg.tutoFile == null || jogo.iMsg.tutoFile.equals("")) {
                jogo.iMsg.tutoFile = arq + "0";
            }
            File xTutorialLangArq = new File("arquivos/lang/" + jogo.iMsg.tutoFile + ".xlang");
            jogo.tutoMsg = (TutoLang) xstreamIdioma.fromXML(xTutorialLangArq);
            if (jogo.iMsg.credFile == null || jogo.iMsg.credFile.equals("")) {
                jogo.iMsg.credFile = arq + "1";
            }
            File xCredArq = new File("arquivos/lang/" + jogo.iMsg.credFile + ".xlang");
            jogo.credMsg = (Creditos) xstreamIdioma.fromXML(xCredArq);
            for (int i = 0; i < jogo.credMsg.blocos.length; i++) {
                jogo.credMsg.blocos[i].titulo = jogo.credMsg.blocos[i].titulo.toUpperCase();
            }
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.BLUE);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("INFO | IDIOMA %s", arq));
            }
            langarq = arq;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar configurações" + e.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void verificarSaveExist() {
        try {
            SaveGame sg;
            File arqSave = null;
            if (jogo.OSystem.contains("linux")) {
                arqSave = new File(System.getProperty("user.home") + "/.config/CTG/saves/" + saveName + ".xsav");
            } else if (jogo.OSystem.contains("windows")) {
                arqSave = new File(System.getProperty("user.home") + "/CTG/saves/" + saveName + ".xsav");
            }
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println("INFO | VERIFICANDO SE EXISTE JOGO SALVO...");
            }
            if (arqSave.exists()) {
                if (Game.DEBUGMODE) {
                    System.out.println("INFO | JOGO SALVO ENCONTRADO, VERIFICANDO COMPATIBILIDADE...");
                }
                XStream xsave = new XStream();
                xsave.alias("SaveCTG", SaveGame.class);
                sg = (SaveGame) xsave.fromXML(arqSave);
                if (jogo.verint == sg.GameBuild) {
                    jogo.noSave = false;
                    if (Game.DEBUGMODE) {
                        System.out.println("INFO | VERSÃO CORRETA, OPÇÃO DE CARREGAR JOGO DISPONÍVEL");
                    }
                } else {
                    jogo.noSave = true;
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.YELLOW);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("INFO | VERSÃO INCORRETA, OPÇÃO DE CARREGAR JOGO INDISPONÍVEL");
                    }
                }
                xsave = null;
                sg = null;
            } else {
                jogo.noSave = true;
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.YELLOW);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println("INFO | NENHUM JOGO SALVO, OPÇÃO DE CARREGAR JOGO INDISPONIVEL");
                }
            }
            arqSave = null;
        } catch (Exception ex) {
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            }
            System.out.println("ERRO | CarTheGame.LoadingInicial.LoadSaveGame()" + ex.getMessage());
        }
    }

    private void loadDefResols() {
        ArrayList<DefResolutions> resolCanFit = new ArrayList<>();
        resolCanFit.clear();
        XStream xstreamer = new XStream();
        xstreamer.alias("defaultScreens", ResolParserXML.class);
        xstreamer.alias("DefResolutions", DefResolutions.class);
        ResolParserXML resolucoesArq = (ResolParserXML) xstreamer.fromXML(new File("arquivos/defscr.ctgi"));
        boolean resolausente = true;
        for (int i = 0; i < resolucoesArq.resols.length; i++) {
            if ((resolucoesArq.resols[i].X <= jogo.tela.getWidth()) && (resolucoesArq.resols[i].Y <= jogo.tela.getHeight())) {
                resolCanFit.add(resolucoesArq.resols[i]);
                if ((resolucoesArq.resols[i].X == jogo.tela.getWidth()) && (resolucoesArq.resols[i].Y == jogo.tela.getHeight())) {
                    resolausente = false;
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.GREEN);
                        Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                        System.out.println("LOAD | RESOL | RESOLUÇÃO DE TELA CONTIDA NOS MODELOS");
                    }
                }
            }
        }
        if (resolausente) {
            DefResolutions defRsl = new DefResolutions();
            defRsl.Nome = jogo.tela.width + "x" + jogo.tela.height;
            defRsl.X = jogo.tela.width;
            defRsl.Y = jogo.tela.height;
            resolCanFit.add(defRsl);
        }
        jogo.resolsDisp = resolCanFit.toArray(new DefResolutions[resolCanFit.size()]);
        if (Game.DEBUGMODE) {
            for (int i = 0; i < jogo.resolsDisp.length; i++) {
                System.out.print("LOAD | RESOL | NOME " + jogo.resolsDisp[i].Nome);
                System.out.print(" | X " + jogo.resolsDisp[i].X);
                System.out.println(" | Y " + jogo.resolsDisp[i].Y + " ADICIONADA");
            }
        }
    }

    private void loadMusicas() {
        File[] music_files = MusicaDiretorio.listFiles();
        ArrayList<File> music_list = new ArrayList<>();
        File unplayble_folder = null;
        if (jogo.OSystem.contains("linux")) {
            unplayble_folder = new File(System.getProperty("user.home") + "/.config/CTG/unplayable");
        } else if (jogo.OSystem.contains("windows")) {
            unplayble_folder = new File(System.getProperty("user.home") + "/CTG/unplayabe");
        }
        if (!unplayble_folder.exists()) {
            unplayble_folder.mkdirs();
        }
        for (int i = 0; i < music_files.length; i++) {
            String music_nome = music_files[i].getName();
            File unplayable = new File(unplayble_folder.getAbsolutePath() + "/" + music_nome.replace(".ctgm", ".dis"));
            if (!unplayable.exists()) {
                music_list.add(music_files[i]);
            }
        }
        jogo.musicFiles = music_list.toArray(new File[music_list.size()]);
        jogo.countmus = jogo.musicFiles.length;
        jogo.musics = new ArrayList();
        for (int i = 0; i < jogo.countmus; i++) {
            jogo.musics.add(i);
            if (Game.DEBUGMODE) {
                TextAttributes ta = new TextAttributes(Color.BLUE);
                Game.DEBUGCONSOLE.setTextAttributes(ta);
                System.out.println(String.format("MISC | MUSICA: %s", jogo.musicFiles[i].getName()));
            }
        }
    }
}
