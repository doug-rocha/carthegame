/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.IO;

import com.dhorgas.carthegame.IO.Levels.*;
import com.dhorgas.carthegame.Entidades.*;
import com.dhorgas.carthegame.Game;
import com.dhorgas.carthegame.Options.FrPS;
import com.dhorgas.carthegame.SIZES;
import com.thoughtworks.xstream.XStream;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.io.File;

/**
 *
 * @author Douglas
 */
public class LevelParser {

    public static double ver = 9.7;

    private boolean requisitos, setInimigos;
    private final Game jogo;
    private Map mp;
    public boolean completo = false;

    public LevelParser(Game game) {
        jogo = game;
    }

    public void loadLevel(int levelNumber) {
        String name = String.format("lvl%s.clvl", String.valueOf(levelNumber));
        jogo.lvlAtual = levelNumber;
        loadLevel(name);
    }

    public void loadLevel(String levelFileName) {
        if (Game.DEBUGMODE) {
            Game.TA = new TextAttributes(Color.BLUE);
            Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            System.out.println(String.format("LDLV | CARREGANDO MAPA | %s", levelFileName));
        }
        int errorCount = 0;
        String errorMsg = "";
        try {
            requisitos = false;
            jogo.walls.clear();
            jogo.portals.clear();
            jogo.linhasChega.clear();
            jogo.caixos.clear();
            jogo.destruidos.clear();
            jogo.itensCenario.clear();
            jogo.lvlName = "";
            jogo.lvlDescri = "";
            File arqLvl = new File(String.format("%s/%s", jogo.levelDirectory, levelFileName));
            XStream xmlStream = new XStream();
            xmlStream.alias("CTGMap", Map.class);
            xmlStream.alias("Parede", Parede.class);
            xmlStream.alias("ParedeArea", ParedeArea.class);
            xmlStream.alias("Portal", Teleport.class);
            xmlStream.alias("PortalArea", TeleportArea.class);
            xmlStream.alias("Chegada", com.dhorgas.carthegame.IO.Levels.Chegada.class);
            xmlStream.alias("ChegadaArea", ChegadaArea.class);
            xmlStream.alias("Caixa", com.dhorgas.carthegame.IO.Levels.CaixoteLvl.class);
            xmlStream.alias("CaixaArea", CaixoteArea.class);
            xmlStream.alias("Destruido", CarDestruido.class);
            xmlStream.alias("DestruidosArea", CarDestruidoArea.class);
            xmlStream.alias("Requisitos", Requisitos.class);
            xmlStream.alias("Decoracao", Decoracoes.class);
            xmlStream.alias("DecoracaoArea", DecoracoesArea.class);
            xmlStream.alias("Item", Item.class);

            mp = (Map) xmlStream.fromXML(arqLvl);

            if (mp.gameTipo == 1) {
                procParedes1();
                procPortais1();
                procChegadas1();
                procSpawn1();
                procNitro1();
                procInvisible1();
                procRequisitos1();
                procCaixote1();
                procCarsDestruidos1();
                procDecoracoes1();
            }
            if (mp.gameTipo == 2) {
                setInimigos = false;
                procParedes2();
                procChegadas2();
                procConfig2();
                procNitro2();
                procInvisible2();
                procSpawn2();
                procItens2();
                procCaixotes2();
                procCarsDestruidos2();
                procDecoracoes2();
                if (!setInimigos) {
                    jogo.quantidade = 12;
                    jogo.nivel = 7;
                }
                jogo.carro.x = (int) (jogo.resol.tamX / 2 - (8 * Game.MODRESOL));
                jogo.carro.y = (int) (jogo.resol.diferenca / 2 - (8 * Game.MODRESOL));
                jogo.itensJg2 = jogo.listItensJg2.toArray(new Itens[jogo.listItensJg2.size()]);
            }
            if (!requisitos) {
                jogo.reqInimigos = 10;
                jogo.reqNivel = 1;
            }
            jogo.wall = jogo.walls.toArray(new Paredes[jogo.walls.size()]);
            jogo.portal = jogo.portals.toArray(new Teleportes[jogo.portals.size()]);
            jogo.chegadas = jogo.linhasChega.toArray(new com.dhorgas.carthegame.Entidades.Chegada[jogo.linhasChega.size()]);
            jogo.caixo = jogo.caixos.toArray(new Caixote[jogo.caixos.size()]);
            jogo.destruidoscar = jogo.destruidos.toArray(new DestroyedCar[jogo.destruidos.size()]);
            jogo.cenas = jogo.itensCenario.toArray(new Cenario[jogo.itensCenario.size()]);
            jogo.lvlName = mp.levelName;
            jogo.lvlDescri = mp.levelTexto;
            jogo.gameTipo = mp.gameTipo;
        } catch (Exception e) {
            errorCount++;
            errorMsg += String.format("%s \n", e.getMessage());
        }
        if (jogo.yep == 6) {
            completo = true;
        }
        if (Game.DEBUGMODE) {
            Game.TA = new TextAttributes(Color.BLUE);
            Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            System.out.println("LDLV | CARREGAMENTO COMPLETO");
            int entidades = jogo.wall.length + jogo.portal.length + jogo.chegadas.length + jogo.caixo.length + jogo.destruidoscar.length + jogo.cenas.length;
            Game.TA = new TextAttributes(Color.CYAN);
            Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            System.out.println(String.format("LDLV | ENTIDADES NÃO MÓVEIS CARREGADAS: %d", entidades));
            if (errorCount > 0) {
                Game.TA = new TextAttributes(Color.ORANGE);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("LDLV | HOUVERAM %d ERROS AO CARREGAR O MAPA %s:", errorCount, levelFileName));
                System.out.println("=============================================================");
                Game.TA = new TextAttributes(Color.RED);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(errorMsg);
            }else{
                System.out.println("LDLV | NENHUM ERRO REPORTADO");
            }
        }
    }

    private void procParedes1() {
        if (mp.paredes != null) {
            for (int i = 0; i < mp.paredes.length; i++) {
                int parX = (short) (mp.paredes[i].x * Game.MODRESOL);
                int parY = (short) (mp.paredes[i].y * Game.MODRESOL);
                Paredes par = new Paredes(parX, parY);
                par.sprite = mp.paredes[i].getSprite();
                jogo.walls.add(par);
            }
        }
        if (mp.areaParedes != null) {
            for (int i = 0; i < mp.areaParedes.length; i++) {
                Parede[] pars = mp.areaParedes[i].getParedes();
                for (int j = 0; j < pars.length; j++) {
                    int parX = (int) (pars[j].x * Game.MODRESOL);
                    int parY = (int) (pars[j].y * Game.MODRESOL);
                    Paredes par = new Paredes(parX, parY);
                    par.sprite = pars[j].getSprite();
                    jogo.walls.add(par);
                }
            }
        }
    }

    private void procPortais1() {
        if (mp.portals != null) {
            for (int i = 0; i < mp.portals.length; i++) {
                Teleportes port = new Teleportes(0, 0, 10, 10);
                int xPort = mp.portals[i].x;
                int yPort = mp.portals[i].y;
                int destXPort = mp.portals[i].destX;
                int destYPort = mp.portals[i].destY;
                port.x = (short) (xPort * Game.MODRESOL);
                port.y = (short) (yPort * Game.MODRESOL);
                port.destX = (short) (destXPort * Game.MODRESOL);
                port.destY = (short) (destYPort * Game.MODRESOL);
                jogo.portals.add(port);
            }
        }
        if (mp.areaPortals != null) {
            for (int i = 0; i < mp.areaPortals.length; i++) {
                Teleport port = mp.areaPortals[i].getTeleport();
                Teleportes tp = new Teleportes(0, 0, 10, 10);
                tp.x = (short) (port.x * Game.MODRESOL);
                tp.y = (short) (port.y * Game.MODRESOL);
                tp.destX = (short) (port.destX * Game.MODRESOL);
                tp.destY = (short) (port.destY * Game.MODRESOL);
                jogo.portals.add(tp);
            }
        }
    }

    private void procChegadas1() {
        if (mp.chegadas != null) {
            for (int i = 0; i < mp.chegadas.length; i++) {
                com.dhorgas.carthegame.Entidades.Chegada cheg = new com.dhorgas.carthegame.Entidades.Chegada(0, 0);
                cheg.x = (short) (mp.chegadas[i].x * Game.MODRESOL);
                cheg.y = (short) (mp.chegadas[i].y * Game.MODRESOL);
                cheg.ativo = false;
                jogo.linhasChega.add(cheg);
            }
        }
        if (mp.areaChegadas != null) {
            for (int i = 0; i < mp.areaChegadas.length; i++) {
                com.dhorgas.carthegame.IO.Levels.Chegada[] chegs = mp.areaChegadas[i].getChegadas();
                for (int j = 0; j < chegs.length; j++) {
                    com.dhorgas.carthegame.Entidades.Chegada cheg = new com.dhorgas.carthegame.Entidades.Chegada(0, 0);
                    cheg.x = (short) (chegs[j].x * Game.MODRESOL);
                    cheg.y = (short) (chegs[j].y * Game.MODRESOL);
                    cheg.ativo = false;
                    jogo.linhasChega.add(cheg);
                }
            }
        }
    }

    private void procSpawn1() {
        if ((mp.spawnX != null && !mp.spawnX.equals("")) && (mp.spawnY != null && !mp.spawnY.equals(""))) {
            String carXS = mp.spawnX.toLowerCase();
            String carYS = mp.spawnY.toLowerCase();
            if (carXS.equals("center") || carXS.equals("centro")) {
                jogo.carro.x = (jogo.janelaW / 2) - SIZES._8;
            } else {
                int carXI = Integer.parseInt(carXS);
                jogo.carro.x = (int) (carXI * Game.MODRESOL);
            }
            if (carYS.equals("center") || carYS.equals("centro")) {
                jogo.carro.y = (jogo.janelaH / 2) - SIZES._8;
            } else {
                int carYI = Integer.parseInt(carYS);
                jogo.carro.y = (int) (carYI * Game.MODRESOL);
            }
        } else {
            throw new UnsupportedOperationException("O nível não possui posições corretas");
        }
    }

    private void procNitro1() {
        if (mp.nitroInicial != null && !mp.nitroInicial.equals("")) {
            double ntr;
            boolean litros = false;
            mp.nitroInicial = mp.nitroInicial.toLowerCase();
            if (mp.nitroInicial.contains("%")) {
                mp.nitroInicial = mp.nitroInicial.replace("%", "");
                mp.nitroInicial = mp.nitroInicial.trim();
            }
            if (mp.nitroInicial.contains("l")) {
                mp.nitroInicial = mp.nitroInicial.replace("l", "");
                mp.nitroInicial = mp.nitroInicial.trim();
                litros = true;
            }
            ntr = Double.valueOf(mp.nitroInicial);

            if (litros) {

                ntr = ntr * 10.0;
            }
            if (ntr > 100) {
                ntr = 100.0;
            }
            jogo.nitro = ntr;
        }
    }

    private void procInvisible1() {
        if (mp.invTempo != null && !mp.invTempo.equals("")) {
            jogo.invisivel = true;
            if (mp.invTempo.endsWith("s")) {
                mp.invTempo = mp.invTempo.replace("s", "");
                mp.invTempo = mp.invTempo.trim();
                int inviTempo = Integer.parseInt(mp.invTempo);
                inviTempo = inviTempo * FrPS._90t;
                jogo.tempoinvisible = inviTempo;
            } else {
                mp.invTempo = mp.invTempo.trim();
                jogo.tempoinvisible = Integer.parseInt(mp.invTempo);
            }
        }
    }

    private void procRequisitos1() {
        if (mp.requisitos != null) {
            jogo.reqInimigos = mp.requisitos.bot;
            jogo.reqNivel = mp.requisitos.nivel;
            requisitos = true;
        }
    }

    private void procCaixote1() {
        if (mp.caixotes != null) {
            for (int i = 0; i < mp.caixotes.length; i++) {
                Caixote cxc = new Caixote();
                cxc.x = (short) (mp.caixotes[i].x * Game.MODRESOL);
                cxc.y = (short) (mp.caixotes[i].y * Game.MODRESOL);
                cxc.durabilidade = mp.caixotes[i].durabilidade;
                cxc.normal = mp.caixotes[i].getSpr().getImage();
                cxc.destruido = mp.caixotes[i].getSprDestruido().getImage();
                jogo.caixos.add(cxc);
            }
        }
        if (mp.areaCaixotes != null) {
            for (int i = 0; i < mp.areaCaixotes.length; i++) {
                CaixoteLvl[] cxcs = mp.areaCaixotes[i].getCaixotes();
                for (int j = 0; j < cxcs.length; j++) {
                    Caixote cxc = new Caixote();
                    cxc.x = (short) (cxcs[j].x * Game.MODRESOL);
                    cxc.y = (short) (cxcs[j].y * Game.MODRESOL);
                    cxc.durabilidade = cxcs[j].durabilidade;
                    cxc.normal = cxcs[j].getSpr().getImage();
                    cxc.destruido = cxcs[j].getSprDestruido().getImage();
                    jogo.caixos.add(cxc);
                }
            }
        }
    }

    private void procCarsDestruidos1() {
        if (mp.carrosDestruidos != null) {
            for (int i = 0; i < mp.carrosDestruidos.length; i++) {
                DestroyedCar dCar = new DestroyedCar();
                dCar.x = (short) (mp.carrosDestruidos[i].x * Game.MODRESOL);
                dCar.y = (short) (mp.carrosDestruidos[i].y * Game.MODRESOL);
                dCar.durabilidade = mp.carrosDestruidos[i].durabilidade;
                jogo.destruidos.add(dCar);
            }
        }
        if (mp.areaCarrosDestruidos != null) {
            for (int i = 0; i < mp.areaCarrosDestruidos.length; i++) {
                CarDestruido[] dCars = mp.areaCarrosDestruidos[i].getCarDestruidos();
                for (int j = 0; j < dCars.length; j++) {
                    DestroyedCar dCar = new DestroyedCar();
                    dCar.x = (short) (dCars[j].x * Game.MODRESOL);
                    dCar.y = (short) (dCars[j].y * Game.MODRESOL);
                    dCar.durabilidade = dCars[j].durabilidade;
                    jogo.destruidos.add(dCar);
                }
            }
        }
    }

    private void procDecoracoes1() {
        if (mp.decoracoes != null) {
            for (int i = 0; i < mp.decoracoes.length; i++) {
                Cenario cena = new Cenario(0, 0);
                cena.x = (short) (mp.decoracoes[i].x * Game.MODRESOL);
                cena.y = (short) (mp.decoracoes[i].y * Game.MODRESOL);
                cena.sprite = mp.decoracoes[i].getSprite();
                if (mp.decoracoes[i].tam > 0) {
                    cena.tam = (int) (mp.decoracoes[i].tam * Game.MODRESOL);
                } else {
                    cena.tam = (int) (16 * Game.MODRESOL);
                }
                jogo.itensCenario.add(cena);
            }
        }
        if (mp.areaDecoracoes != null) {
            for (int i = 0; i < mp.areaDecoracoes.length; i++) {
                Decoracoes[] decos = mp.areaDecoracoes[i].getDecoracoes();
                for (int j = 0; j < decos.length; j++) {
                    Cenario cena = new Cenario(0, 0);
                    cena.x = (short) (decos[j].x * Game.MODRESOL);
                    cena.y = (short) (decos[j].y * Game.MODRESOL);
                    cena.sprite = decos[j].getSprite();
                    cena.tam = (int) (decos[j].tam * Game.MODRESOL);
                    jogo.itensCenario.add(cena);
                }
            }
        }
    }

    private void procParedes2() {
        if (mp.paredes != null) {
            for (int i = 0; i < mp.paredes.length; i++) {
                Paredes par = new Paredes(0, 0);
                int parX = mp.paredes[i].x;
                int parY = mp.paredes[i].y;
                par.oldX = (short) parX;
                par.oldY = (short) parY;
                par.sprite = mp.paredes[i].getSprite();
                jogo.walls.add(par);
            }
        }
        if (mp.areaParedes != null) {
            for (int i = 0; i < mp.areaParedes.length; i++) {
                Parede[] pars = mp.areaParedes[i].getParedes();
                for (int j = 0; j < pars.length; j++) {
                    Paredes par = new Paredes(0, 0);
                    int parX = mp.paredes[j].x;
                    int parY = mp.paredes[j].y;
                    par.oldX = (short) (parX);
                    par.oldY = (short) (parY);
                    par.sprite = pars[i].getSprite();
                    jogo.walls.add(par);
                }
            }
        }
    }

    private void procChegadas2() {
        if (mp.chegadas != null) {
            for (int i = 0; i < mp.chegadas.length; i++) {
                com.dhorgas.carthegame.Entidades.Chegada cheg = new com.dhorgas.carthegame.Entidades.Chegada(0, 0);
                cheg.oldX = (short) (mp.chegadas[i].x);
                cheg.oldY = (short) (mp.chegadas[i].y);
                cheg.ativo = true;
                jogo.linhasChega.add(cheg);
            }
        }
        if (mp.areaChegadas != null) {
            for (int i = 0; i < mp.areaChegadas.length; i++) {
                com.dhorgas.carthegame.IO.Levels.Chegada[] chegs = mp.areaChegadas[i].getChegadas();
                for (int j = 0; j < chegs.length; j++) {
                    com.dhorgas.carthegame.Entidades.Chegada cheg = new com.dhorgas.carthegame.Entidades.Chegada(0, 0);
                    cheg.oldX = (short) (chegs[j].x);
                    cheg.oldY = (short) (chegs[j].y);
                    cheg.ativo = true;
                    jogo.linhasChega.add(cheg);
                }
            }
        }
    }

    private void procConfig2() {
        if (mp.requisitos != null) {
            jogo.quantidade = mp.requisitos.bot;
            jogo.nivel = mp.requisitos.nivel;
            setInimigos = true;
        }
    }

    private void procNitro2() {
        procNitro1();
    }

    private void procInvisible2() {
        procInvisible1();
    }

    private void procSpawn2() {
        int x = Integer.parseInt(mp.spawnX);
        int y = Integer.parseInt(mp.spawnY);
        jogo.mapX = (int) (x * Game.MODRESOL);
        jogo.mapY = (int) (y * Game.MODRESOL);
    }

    private void procItens2() {
        if (mp.itens != null) {
            for (int i = 0; i < mp.itens.length; i++) {
                Itens it = new Itens();
                it.tipo = mp.itens[i].tipo;
                it.oldXi = (short) mp.itens[i].x;
                it.oldYi = (short) mp.itens[i].y;
                it.ativo = true;
                jogo.listItensJg2.add(it);
            }
        }
    }

    private void procCaixotes2() {
        if (mp.caixotes != null) {
            for (int i = 0; i < mp.caixotes.length; i++) {
                Caixote cxc = new Caixote();
                cxc.oldX = (short) (mp.caixotes[i].x);
                cxc.oldY = (short) (mp.caixotes[i].y);
                cxc.durabilidade = mp.caixotes[i].durabilidade;
                cxc.normal = mp.caixotes[i].getSpr().getImage();
                cxc.destruido = mp.caixotes[i].getSprDestruido().getImage();
                jogo.caixos.add(cxc);
            }
        }
        if (mp.areaCaixotes != null) {
            for (int i = 0; i < mp.areaCaixotes.length; i++) {
                CaixoteLvl[] cxcs = mp.areaCaixotes[i].getCaixotes();
                for (int j = 0; j < cxcs.length; j++) {
                    Caixote cxc = new Caixote();
                    cxc.oldX = (short) (cxcs[j].x);
                    cxc.oldY = (short) (cxcs[j].y);
                    cxc.durabilidade = cxcs[j].durabilidade;
                    cxc.normal = cxcs[j].getSpr().getImage();
                    cxc.destruido = cxcs[j].getSprDestruido().getImage();
                    jogo.caixos.add(cxc);
                }
            }
        }
    }

    private void procCarsDestruidos2() {
        if (mp.carrosDestruidos != null) {
            for (int i = 0; i < mp.carrosDestruidos.length; i++) {
                DestroyedCar dCar = new DestroyedCar();
                dCar.oldX = (short) (mp.carrosDestruidos[i].x);
                dCar.oldY = (short) (mp.carrosDestruidos[i].y);
                dCar.durabilidade = mp.carrosDestruidos[i].durabilidade;
                jogo.destruidos.add(dCar);
            }
        }
        if (mp.areaCarrosDestruidos != null) {
            for (int i = 0; i < mp.areaCarrosDestruidos.length; i++) {
                CarDestruido[] dCars = mp.areaCarrosDestruidos[i].getCarDestruidos();
                for (int j = 0; j < dCars.length; j++) {
                    DestroyedCar dCar = new DestroyedCar();
                    dCar.oldX = (short) (dCars[j].x);
                    dCar.oldY = (short) (dCars[j].y);
                    dCar.durabilidade = dCars[j].durabilidade;
                    jogo.destruidos.add(dCar);
                }
            }
        }
    }

    private void procDecoracoes2() {
        if (mp.decoracoes != null) {
            for (int i = 0; i < mp.decoracoes.length; i++) {
                Cenario cena = new Cenario(0, 0);
                cena.oldX = (short) (mp.decoracoes[i].x);
                cena.oldY = (short) (mp.decoracoes[i].y);
                cena.sprite = mp.decoracoes[i].getSprite();
                if (mp.decoracoes[i].tam > 0) {
                    cena.tam = (int) (mp.decoracoes[i].tam * Game.MODRESOL);
                } else {
                    cena.tam = (int) (16 * Game.MODRESOL);
                }
                jogo.itensCenario.add(cena);
            }
        }
        if (mp.areaDecoracoes != null) {
            for (int i = 0; i < mp.areaDecoracoes.length; i++) {
                Decoracoes[] decos = mp.areaDecoracoes[i].getDecoracoes();
                for (int j = 0; j < decos.length; j++) {
                    Cenario cena = new Cenario(0, 0);
                    cena.oldX = (short) (decos[j].x);
                    cena.oldY = (short) (decos[j].y);
                    cena.sprite = decos[j].getSprite();
                    cena.tam = (int) (decos[j].tam * Game.MODRESOL);
                    jogo.itensCenario.add(cena);
                }
            }
        }
    }
}
