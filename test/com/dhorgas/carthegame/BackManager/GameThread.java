/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.BackManager;

import com.dhorgas.carthegame.IO.Fontes;
import com.dhorgas.carthegame.Game;
import com.dhorgas.carthegame.Options.FrPS;
import com.dhorgas.carthegame.SIZES;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Douglas Rocha
 */
public class GameThread implements Runnable {

    Game jogo;
    int tipo;
    BufferedImage gameBuffer;
    Graphics2D bgb;

    public GameThread(Game m) {
        jogo = m;
        gameBuffer = new BufferedImage(jogo.resol.tamX, jogo.resol.diferenca, BufferedImage.TYPE_INT_ARGB);
        bgb = (Graphics2D) gameBuffer.getGraphics();
    }

    @Override
    public void run() {        
        if (tipo == 1) {
            game();
        } else if (tipo == 2) {
            game2();
        }
    }

    public void setTipo(int tip) {
        tipo = tip;
    }

    private void game2() {

        do {
            if ((jogo.irdireita || jogo.irdireita2) && (jogo.irbaixo || jogo.irbaixo2)) {
                jogo.mapX += (SIZES._3 + jogo.propcar);
                jogo.mapY += (SIZES._3 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].x -= SIZES._3 + jogo.propcar;
                    jogo.inimigos[i].y -= SIZES._3 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastrox[j] -= SIZES._2_8;
                    jogo.rastroy[j] -= SIZES._2_8;
                }
            } else if ((jogo.irdireita || jogo.irdireita2) && (jogo.ircima || jogo.ircima2)) {
                jogo.mapX += (SIZES._3 + jogo.propcar);
                jogo.mapY -= (SIZES._3 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].x -= SIZES._3 + jogo.propcar;
                    jogo.inimigos[i].y += SIZES._3 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastrox[j] -= SIZES._2_8;
                    jogo.rastroy[j] += SIZES._2_8;
                }
            } else if ((jogo.iresquerda || jogo.iresquerda2) && (jogo.irbaixo || jogo.irbaixo2)) {
                jogo.mapX -= (SIZES._3 + jogo.propcar);
                jogo.mapY += (SIZES._3 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].x += SIZES._3 + jogo.propcar;
                    jogo.inimigos[i].y -= SIZES._3 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastrox[j] += SIZES._2_8;
                    jogo.rastroy[j] -= SIZES._2_8;
                }
            } else if ((jogo.iresquerda || jogo.iresquerda2) && (jogo.ircima || jogo.ircima2)) {
                jogo.mapX -= (SIZES._3 + jogo.propcar);
                jogo.mapY -= (SIZES._3 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].x += SIZES._3 + jogo.propcar;
                    jogo.inimigos[i].y += SIZES._3 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastrox[j] += SIZES._2_8;
                    jogo.rastroy[j] += SIZES._2_8;
                }
            } else if (jogo.irdireita || jogo.irdireita2) {
                jogo.mapX += (SIZES._6 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].x -= SIZES._6 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastrox[j] -= SIZES._4;
                }
            } else if (jogo.iresquerda || jogo.iresquerda2) {
                jogo.mapX -= (SIZES._6 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].x += SIZES._6 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastrox[j] += SIZES._4;
                }
            } else if (jogo.irbaixo || jogo.irbaixo2) {
                jogo.mapY += (SIZES._6 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].y -= SIZES._6 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastroy[j] -= SIZES._4;
                }
            } else if (jogo.ircima || jogo.ircima2) {
                jogo.mapY -= (SIZES._6 + jogo.propcar);
                for (int i = 0; i < jogo.quantidade; i++) {
                    jogo.inimigos[i].y += SIZES._6 + jogo.propcar;
                }
                for (int j = 0; j < 10; j++) {
                    jogo.rastroy[j] += SIZES._4;
                }
            }
            if ((!jogo.irbaixo) && (!jogo.ircima) && (!jogo.irdireita) && (!jogo.iresquerda) 
                    && (!jogo.irbaixo2) && (!jogo.ircima2) && (!jogo.irdireita2) && (!jogo.iresquerda2)) {
                jogo.ativado = false;
                jogo.propcar = 0;
            }
            jogo.verificarPegarItem2();
            jogo.posicaoParedeGame2();
            jogo.verificaposicao();
            jogo.posicaoParedeGame2();
            jogo.andaBot();
            jogo.bbg.setColor(jogo.fundo);
            jogo.bbg.fillRect(0, 0, jogo.janelaW, jogo.janelaH);
            bgb.setColor(jogo.fundo);
            bgb.fillRect(0, 0, gameBuffer.getWidth(), gameBuffer.getHeight());

            for (int i = 0; i < jogo.cenas.length; i++) {
                bgb.drawImage(jogo.cenas[i].getSprite(), jogo.cenas[i].x, jogo.cenas[i].y, jogo.cenas[i].tam, jogo.cenas[i].tam, jogo);
            }
            drawRastro();

            for (int i = 0; i < jogo.wall.length; i++) {
                bgb.drawImage(jogo.wall[i].getSprite(), jogo.wall[i].x, jogo.wall[i].y, SIZES._16, SIZES._16, jogo);
            }
            for (int i = 0; i < jogo.itensJg2.length; i++) {
                if (jogo.itensJg2[i].ativo) {
                    bgb.drawImage(jogo.itensJg2[i].getSprite(), jogo.itensJg2[i].Xi, jogo.itensJg2[i].Yi, SIZES._16, SIZES._16, jogo);
                }
            }
            for (int i = 0; i < jogo.chegadas.length; i++) {
                if (jogo.chegadas[i].ativo) {
                    bgb.drawImage(jogo.chegadas[i].getSprite(), jogo.chegadas[i].x, jogo.chegadas[i].y, SIZES._16, SIZES._16, jogo);
                }
            }
            for (int i = 0; i < jogo.destruidoscar.length; i++) {
                bgb.drawImage(jogo.destruidoscar[i].getSprite(), jogo.destruidoscar[i].x, jogo.destruidoscar[i].y, SIZES._16, SIZES._16, jogo);
            }
            for (int i = 0; i < jogo.caixo.length; i++) {
                bgb.drawImage(jogo.caixo[i].getSprite(), jogo.caixo[i].x, jogo.caixo[i].y, SIZES._16, SIZES._16, jogo);
            }
            if (jogo.invisivel) {
                bgb.drawImage(jogo.carro.spriteI, jogo.carro.x, jogo.carro.y, SIZES._16, SIZES._16, jogo);
                jogo.tempoinvisible--;
            } else {
                bgb.drawImage(jogo.carro.sprite, jogo.carro.x, jogo.carro.y, SIZES._16, SIZES._16, jogo); //CAR
            }
            for (int i = 0; i < jogo.quantidade; i++) {
                bgb.drawImage(jogo.inimigos[i].sprite, jogo.inimigos[i].getX(), jogo.inimigos[i].getY(), SIZES._16, SIZES._16, jogo); //INIMIGOS

            }

            if (jogo.at.informacoesdisp) {
                jogo.sortMus(1);

            }
            jogo.bbg.drawImage(gameBuffer, jogo.resol.minX, jogo.resol.minY, jogo);
            drawHud();
            jogo.bbg2.drawImage(jogo.backBuffer, 0, 0, jogo);
            if (jogo.invisivel && jogo.tempoinvisible <= 0) {
                jogo.invisivel = false;
            }
            if (jogo.frame >= 15) {
                jogo.frame = 0;
            }
            jogo.frame++;
            jogo.tempospawn++;
            if (jogo.tempospawn > 210) {
                jogo.nitro += 0.7;
                jogo.tempospawn = 0;
                if (jogo.nitro > 100) {
                    jogo.nitro = 100.0;
                }
            }
            if (jogo.cardamage >= 100) {
                jogo.yep = 4;
                jogo.GOMotivo = 100;

            }
            jogo.verificaRequisitos();
            try {
                Thread.sleep(1000 / FrPS._90t);
            } catch (InterruptedException ex) {
            }

        } while (jogo.yep == 1);
    }

    private void game() {

        long tmAtual = System.currentTimeMillis();
        int ticks = 0;
        do {
            double modVeloCar = jogo.nivelcar * 0.05;
            jogo.frame++;
            if ((jogo.irdireita || jogo.irdireita2) && (jogo.irbaixo || jogo.irbaixo2)) {
                jogo.carro.x += (SIZES._1_4 + modVeloCar + jogo.propcar);
                jogo.carro.y += (SIZES._1_4 + modVeloCar + jogo.propcar);
            } else if ((jogo.irdireita || jogo.irdireita2) && (jogo.ircima || jogo.ircima2)) {
                jogo.carro.x += (SIZES._1_4 + modVeloCar + jogo.propcar);
                jogo.carro.y -= (SIZES._1_4 + modVeloCar + jogo.propcar);
            } else if ((jogo.iresquerda || jogo.iresquerda2) && (jogo.irbaixo || jogo.irbaixo2)) {
                jogo.carro.x -= (SIZES._1_4 + modVeloCar + jogo.propcar);
                jogo.carro.y += (SIZES._1_4 + modVeloCar + jogo.propcar);
            } else if ((jogo.iresquerda || jogo.iresquerda2) && (jogo.ircima || jogo.ircima2)) {
                jogo.carro.x -= (SIZES._1_4 + modVeloCar + jogo.propcar);
                jogo.carro.y -= (SIZES._1_4 + modVeloCar + jogo.propcar);
            } else if (jogo.irdireita || jogo.irdireita2) {
                jogo.carro.x += (SIZES._2 + modVeloCar + jogo.propcar);
            } else if (jogo.iresquerda || jogo.iresquerda2) {
                jogo.carro.x -= (SIZES._2 + modVeloCar + jogo.propcar);
            } else if (jogo.irbaixo || jogo.irbaixo2) {
                jogo.carro.y += (SIZES._2 + modVeloCar + jogo.propcar);
            } else if (jogo.ircima || jogo.ircima2) {
                jogo.carro.y -= (SIZES._2 + modVeloCar + jogo.propcar);
            }
            if ((!jogo.irbaixo) && (!jogo.ircima) && (!jogo.irdireita) && (!jogo.iresquerda)
                    && (!jogo.irbaixo2) && (!jogo.ircima2) && (!jogo.irdireita2) && (!jogo.iresquerda2)) {
                jogo.ativado = false;
                jogo.propcar = 0;
            }
            jogo.verificaposicao();
            jogo.verificapegaritem();
            jogo.bbg.setColor(jogo.fundo);
            jogo.bbg.fillRect(0, 0, jogo.janelaW, jogo.janelaH);
            bgb.setColor(jogo.fundo);
            bgb.fillRect(0, 0, gameBuffer.getWidth(), gameBuffer.getHeight());
            
            for (int i = 0; i < jogo.cenas.length; i++) {
                bgb.drawImage(jogo.cenas[i].getSprite(), jogo.cenas[i].x, jogo.cenas[i].y, jogo.cenas[i].tam, jogo.cenas[i].tam, jogo);
            }
            if (jogo.exibindoitem) {
                bgb.drawImage(jogo.it.getSprite(), jogo.it.Xi, jogo.it.Yi, SIZES._16, SIZES._16, jogo);
            }

            for (int i = 0; i < jogo.wall.length; i++) {
                bgb.drawImage(jogo.wall[i].getSprite(), jogo.wall[i].x, jogo.wall[i].y, SIZES._16, SIZES._16, jogo);
            }
            
            for (int i = 0; i < jogo.destruidoscar.length; i++) {
                if (!jogo.destruidoscar[i].exploded) {
                    bgb.drawImage(jogo.destruidoscar[i].getSprite(), jogo.destruidoscar[i].x, jogo.destruidoscar[i].y, SIZES._16, SIZES._16, jogo);
                }
                if (jogo.destruidoscar[i].exploded && jogo.destruidoscar[i].explodindo >= 1) {
                    int deslocAtual = SIZES._16 / 2;
                    int tamatual = (int) (SIZES._36 / jogo.destruidoscar[i].explodindo);
                    int tamatual2 = (int) (tamatual / 2);
                    if (jogo.destruidoscar[i].explodindo > 4.5) {
                        bgb.drawImage(jogo.destruidoscar[i].normal, jogo.destruidoscar[i].x, jogo.destruidoscar[i].y, SIZES._16, SIZES._16, jogo);
                    }
                    bgb.drawImage(jogo.destruidoscar[i].getSprite(), jogo.destruidoscar[i].x + deslocAtual - tamatual + tamatual2, jogo.destruidoscar[i].y + deslocAtual - tamatual + tamatual2, tamatual, tamatual, jogo);
                    jogo.destruidoscar[i].explodindo -= 0.4;
                }
            }
            for (int i = 0; i < jogo.caixo.length; i++) {
                bgb.drawImage(jogo.caixo[i].getSprite(), jogo.caixo[i].x, jogo.caixo[i].y, SIZES._16, SIZES._16, jogo);
            }
            for (int i = 0; i < jogo.chegadas.length; i++) {
                if (jogo.chegadas[i].ativo) {
                    bgb.drawImage(jogo.chegadas[i].getSprite(), jogo.chegadas[i].x, jogo.chegadas[i].y, SIZES._16, SIZES._16, jogo);
                }
            }
            
            for (int i = 0; i < jogo.portal.length; i++) {
                if (jogo.portal[i].ativo) {
                    Graphics2D g2d = (Graphics2D) bgb.create();
                    g2d.translate(jogo.portal[i].x + (SIZES._16 / 2), jogo.portal[i].y + (SIZES._16 / 2));
                    g2d.rotate(jogo.portal[i].rotate);
                    g2d.translate(-(jogo.portal[i].x + (SIZES._16 / 2)), -(jogo.portal[i].y + (SIZES._16 / 2)));
                    g2d.drawImage(jogo.portal[i].getSprite(), jogo.portal[i].x, jogo.portal[i].y, SIZES._16, SIZES._16, jogo);
                    g2d = (Graphics2D) bgb.create();
                    g2d.translate(jogo.portal[i].destX + (SIZES._16 / 2), jogo.portal[i].destY + (SIZES._16 / 2));
                    g2d.rotate(jogo.portal[i].rotate * (-1));
                    g2d.translate(-(jogo.portal[i].destX + (SIZES._16 / 2)), -(jogo.portal[i].destY + (SIZES._16 / 2)));
                    g2d.drawImage(jogo.portal[i].getSprite(), jogo.portal[i].destX, jogo.portal[i].destY, SIZES._16, SIZES._16, jogo);
                    bgb.drawImage(jogo.portal[i].getExitSprite(), jogo.portal[i].destX, jogo.portal[i].destY, SIZES._16, SIZES._16, jogo);
                    jogo.portal[i].rotate += jogo.portal[i].rotateSpd;
                    if (jogo.portal[i].rotate >= 360.0) {
                        jogo.portal[i].rotate = 0.0;
                    }
                }
            }

            drawRastro();
            
            if (jogo.invisivel) {
                bgb.drawImage(jogo.carro.spriteI, jogo.carro.x, jogo.carro.y, SIZES._16, SIZES._16, jogo);
                jogo.tempoinvisible--;
            } else {
                bgb.drawImage(jogo.carro.sprite, jogo.carro.x, jogo.carro.y, SIZES._16, SIZES._16, jogo); //CAR
            }

            for (int i = 0; i < jogo.quantidade; i++) {
                bgb.drawImage(jogo.inimigos[i].sprite, jogo.inimigos[i].getX(), jogo.inimigos[i].getY(), SIZES._16, SIZES._16, jogo); //INIMIGOS

            }

            if (jogo.tempospawn >= 210) {
                jogo.tempospawn = 0;
                jogo.quantidade++;
                jogo.loopnivel++;
                jogo.loopnivelcar++;
                jogo.loopitem++;
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.CYAN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("GAME | TEMPO ITEM: %s", jogo.loopitem));
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println(String.format("INFO | TICKS: %s", ticks));
                }
                jogo.nitro += 0.7;
                if (jogo.nitro > 100) {
                    jogo.nitro = 100.0;
                }
            }
            if (jogo.loopnivel >= 7) {
                jogo.loopnivel = 1;
                jogo.nivel++;

            }
            if (jogo.loopnivelcar >= 4) {
                jogo.loopnivelcar = 1;
                jogo.nivelcar++;
            }
            if (jogo.loopitem >= 7) {
                if (jogo.exibindoitem == false) {
                    jogo.it.tipo = (short) ((int) 1 + Math.random() * 4);
                    jogo.it.Xi = (short) ((int) 10 + Math.random() * ((jogo.janelaW) - 40));
                    jogo.it.Yi = (short) ((int) 25 + Math.random() * ((jogo.janelaH) - 75));
                    jogo.exibindoitem = true;
                    jogo.loopitem = 0;
                    if (Game.DEBUGMODE) {
                        Game.TA = new TextAttributes(Color.CYAN);
                        Game.DEBUGCONSOLE.setTextAttributes(jogo.TA);
                        System.out.println(String.format("GAME | ITEM SPAWN: %s X/Y: %s %s", jogo.it.tipo, jogo.it.Xi, jogo.it.Yi));
                    }
                }
            }
            if (jogo.loopitem >= 5) {
                if (jogo.exibindoitem) {
                    jogo.exibindoitem = false;
                    jogo.loopitem = -7;
                }
            }
            jogo.bbg.drawImage(gameBuffer, jogo.resol.minX, jogo.resol.minY, jogo);
            drawHud();

            jogo.tempospawn++;
            if (jogo.at.informacoesdisp) {
                jogo.sortMus(1);
            }
            jogo.bbg2.drawImage(jogo.backBuffer, 0, 0, jogo);
            jogo.andaBot();
            if (jogo.invisivel && jogo.tempoinvisible <= 0) {
                jogo.invisivel = false;
            }
            if (jogo.frame >= 20) {
                jogo.frame = 0;

            }
            if (jogo.cardamage >= 100) {
                jogo.yep = 4;
                jogo.GOMotivo = 100;

            }
            jogo.verificaRequisitos();
            try {
                Thread.sleep(1000 / FrPS._90t);
            } catch (InterruptedException ex) {
            }
            /*long tmDecorrido = System.currentTimeMillis() - tmAtual;
            tmAtual = System.currentTimeMillis();
            ticks = (int) (1000 / tmDecorrido);
            jogo.setTitle("Car The Game - ticks: " + ticks + "FPS: " + jogo.FPS);*/
        } while (jogo.yep == 1);
    }

    private void drawRastro() {
        if (tipo == 1) {
            if (jogo.ativado) {
                bgb.setColor(new Color(200, 100, 100, 65));
                bgb.fillOval(jogo.carro.x - SIZES._4, jogo.carro.y - SIZES._4, SIZES._23, SIZES._23);
                jogo.nitro -= 0.2;
                if (jogo.nitro <= 0) {
                    jogo.ativado = false;
                    jogo.propcar = 0;
                }
                jogo.rastrox[0] = jogo.carro.x;
                jogo.rastroy[0] = jogo.carro.y;
                for (int i = 0; i < jogo.rastroSprite.length; i++) {
                    bgb.drawImage(jogo.rastroSprite[i], jogo.rastrox[i], jogo.rastroy[i], SIZES._16, SIZES._16, jogo);
                }
                for (int j = jogo.rastroSprite.length - 1; j > 0; j--) {
                    jogo.rastrox[j] = jogo.rastrox[j - 1];
                    jogo.rastroy[j] = jogo.rastroy[j - 1];
                }
            } else {
                for (int i = 9; i >= 0; i--) {
                    jogo.rastrox[i] = jogo.carro.x;
                    jogo.rastroy[i] = jogo.carro.y;
                }
            }
        } else if (tipo == 2) {
            if (jogo.ativado) {
                bgb.setColor(new Color(200, 100, 100, 65));
                bgb.fillOval(jogo.carro.x - SIZES._4, jogo.carro.y - SIZES._4, SIZES._23, SIZES._23);
                jogo.nitro -= 0.2;
                if (jogo.nitro <= 0) {
                    jogo.ativado = false;
                    jogo.propcar = 0;
                }
                jogo.rastrox[0] = jogo.carro.x;
                jogo.rastroy[0] = jogo.carro.y;
                for (int i = 0; i < jogo.rastroSprite.length; i++) {
                    bgb.drawImage(jogo.rastroSprite[i], jogo.rastrox[i], jogo.rastroy[i], SIZES._16, SIZES._16, jogo);
                }
                for (int j = jogo.rastroSprite.length - 1; j > 0; j--) {
                    jogo.rastrox[j] = jogo.rastrox[j - 1];
                    jogo.rastroy[j] = jogo.rastroy[j - 1];
                }
            } else {
                for (int i = 9; i >= 0; i--) {
                    jogo.rastrox[i] = jogo.carro.x;
                    jogo.rastroy[i] = jogo.carro.y;
                }

            }
        }
    }

    private void drawHud() {
        for (int i = 0; i < 5; i++) {
            if (jogo.exibindovida < jogo.cardamage) {
                jogo.exibindovida += 1;
            }
            if (jogo.exibindovida > jogo.cardamage) {
                jogo.exibindovida -= 1;
            }
        }
        int nitroI = (int) Math.round(jogo.nitro);
        if (jogo.nitroexibido < nitroI) {
            jogo.nitroexibido += 2;
        }
        if (jogo.nitroexibido > nitroI) {
            jogo.nitroexibido = nitroI;
        }
        int R = 0, B = 0, G = 255;
        G = (int) (((100 - jogo.cardamage) * 255) / 100);
        R = 255 - G;
        R += 50;
        if (G >= 200) {
            R = 0;
        }
        if (R >= 255) {
            R = 255;
            G = 0;
        }

        jogo.bbg.setColor(new Color(20, 20, 25));
        jogo.bbg.fillRect(0, jogo.janelaH - 32, jogo.janelaW, 32);
        jogo.bbg.fillRect(0, 0, jogo.janelaW, 23);
        jogo.bbg.fillRect(0, 0, jogo.resol.minX - 1, jogo.janelaH);
        jogo.bbg.fillRect((int) (jogo.resol.maxX + 1), 0, jogo.janelaW - jogo.resol.maxX, jogo.janelaH);
        jogo.bbg.setColor(new Color(175, 175, 175, 175));
        jogo.bbg.drawLine(jogo.resol.minX, jogo.resol.minY, jogo.resol.minX, jogo.resol.maxY);
        jogo.bbg.drawLine(jogo.resol.minX, jogo.resol.maxY, jogo.resol.maxX, jogo.resol.maxY);
        jogo.bbg.drawLine(jogo.resol.maxX, jogo.resol.maxY, jogo.resol.maxX, jogo.resol.minY);
        jogo.bbg.drawLine(jogo.resol.maxX, jogo.resol.minY, jogo.resol.minX, jogo.resol.minY);
        if (jogo.tempodano > 0) {
            int hue = (int) Math.round(jogo.tempodano / 100);
            Color coloracao = Color.black;
            if (hue % 2 == 1) {
                coloracao = Color.red;
            }
            if (hue % 2 == 0) {
                coloracao = Color.BLACK;
            }
            for (int i = 1; i <= 5; i++) {
                jogo.bbg.setColor(new Color(coloracao.getRed(), coloracao.getGreen(), coloracao.getBlue(), 250 / i));
                jogo.bbg.drawRoundRect(((jogo.janelaW / 2) - (41 + i)), ((jogo.janelaH) - (24 + i)), ((201) + (i * 2)), ((16) + (i * 2)), 5, 3);
            }
            jogo.tempodano -= 6;
        }
        if (jogo.tempovida > 0) {
            int hue = (int) Math.round(jogo.tempodano / 100);
            Color coloracao = Color.black;
            if (hue % 2 == 1) {
                coloracao = Color.green;
            }
            if (hue % 2 == 0) {
                coloracao = Color.BLACK;
            }
            for (int i = 1; i <= 5; i++) {
                jogo.bbg.setColor(new Color(coloracao.getRed(), coloracao.getGreen(), coloracao.getBlue(), 250 / i));
                jogo.bbg.drawRoundRect(((jogo.janelaW / 2) - (41 + i)), ((jogo.janelaH) - (24 + i)), ((201) + (i * 2)), ((16) + (i * 2)), 5, 3);
            }
            jogo.tempovida -= 6;
        }
        jogo.bbg.setColor(Color.BLUE);
        jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, 18));
        jogo.bbg.drawString("Car The Game " + jogo.ver, 6, 15);
        jogo.bbg.setColor(jogo.fonte);
        jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, 20));
        jogo.bbg.drawString("X: " + Math.round(jogo.carro.x/Game.MODRESOL) + " Y: " + Math.round(jogo.carro.y/Game.MODRESOL), 6, ((jogo.janelaH) - 10));
        jogo.bbg.drawString(jogo.iMsg.vida.texto, ((jogo.janelaW / 2) - jogo.iMsg.vida.posX), ((jogo.janelaH) - 10));
        jogo.bbg.setColor(new Color(R, G, B));
        jogo.bbg.fillRoundRect(((jogo.janelaW / 2) - 40), ((jogo.janelaH) - 23), 200 - (jogo.exibindovida * 2), 15, 1, 1); //BARRA DE VIDA
        jogo.bbg.setColor(new Color(0, 100, 255, 175));
        jogo.bbg.fillRect(jogo.resol.maxX - ((jogo.nitroexibido * 2)) - 10, jogo.janelaH - 57, jogo.nitroexibido * 2, 20); //BARRA DE NITRO
        jogo.bbg.setColor(new Color(150, 150, 255, 200));
        jogo.bbg.drawRect(jogo.resol.maxX - 210, jogo.janelaH - 57, 200, 20);//CONTORNO BARRA DE NITRO
        jogo.bbg.setColor(jogo.fonte);
        jogo.bbg.drawRoundRect(((jogo.janelaW / 2) - 41), ((jogo.janelaH) - 24), 201, 16, 5, 3);
        jogo.bbg.setColor(jogo.fonte);
        jogo.bbg.drawString(jogo.iMsg.inimigos.texto + jogo.quantidade, ((jogo.janelaW) - jogo.iMsg.inimigos.posX), ((jogo.janelaH) - 10));
        jogo.bbg.drawString(jogo.iMsg.nivel.texto + jogo.nivel, ((jogo.janelaW / 2) - jogo.iMsg.nivel.posX), ((jogo.janelaH) - 10));
        //INFORMAÇÕES MÚSICAS
        if (jogo.exibindoinfom) {
            jogo.bbg.setColor(new Color(75, 75, 75, 175));
            jogo.bbg.fillRoundRect(((jogo.janelaW) - 270 + (300 - jogo.jseg1)), 28, 265, 70, 3, 3);
            jogo.bbg.drawImage(jogo.AlbumCover, ((jogo.janelaW) - 265) + (30 - (jogo.jseg2 / 2)), 33 + (30 - (jogo.jseg2 / 2)), 60 - (60 - jogo.jseg2), 60 - (60 - jogo.jseg2), jogo);
            jogo.bbg.setColor(new Color(127, 127, 127, 127));
            jogo.bbg.drawRoundRect(((jogo.janelaW) - 270 + (300 - jogo.jseg1)), 28, 265, 70, 3, 3);
            jogo.bbg.setColor(jogo.fonte);
            jogo.bbg.setFont(Fontes.UBUNTU.deriveFont(Font.BOLD, 17));
            jogo.bbg.drawString(jogo.nomemus, ((jogo.janelaW) - 200 + (300 - jogo.jseg1)), 43);
            jogo.bbg.setFont(Fontes.UBUNTU.deriveFont(Font.PLAIN, 15));
            jogo.bbg.drawString(jogo.artimus, ((jogo.janelaW) - 200 + (300 - jogo.jseg1)), 63);
            jogo.bbg.drawString(jogo.albumus, ((jogo.janelaW) - 200 + (300 - jogo.jseg1)), 83);
            jogo.bbg.setColor(new Color(200, 200, 200, 150));
            jogo.bbg.setFont(Fontes.TIMES.deriveFont(Font.PLAIN, 10));
            jogo.bbg.drawString("DHORGAS' TrackX", (jogo.janelaW - 167 + (300 - jogo.jseg1)), 96);
            jogo.jtimeInfo++;
        }
        if (!jogo.jinfovoltando) {
            jogo.jseg1 += 6;
        } else {
            if (!jogo.jalbumArtVoltando) {
                jogo.jseg2 += 4;
            }
        }
        if (jogo.jseg1 >= 310) {
            jogo.jinfovoltando = true;
        }
        if (jogo.jseg2 >= 65) {
            jogo.jalbumArtVoltando = true;
        }
        if (jogo.jalbumArtVoltando && jogo.jseg2 > 60) {
            jogo.jseg2--;
        }
        if (jogo.jinfovoltando && jogo.jseg1 > 300) {
            jogo.jseg1 -= 2;
        }
        if (jogo.jtimeInfo > 500) {
            if (jogo.jseg2 > 0) {
                jogo.jseg2 -= 5;
            }
            if (jogo.jseg2 <= 0) {
                jogo.jseg1 -= 7;
            }
            if (jogo.jseg1 <= 0) {
                jogo.exibindoinfom = false;
            }

        }
        //FIM INFORMAÇÕES
    }

}
