/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.ETC;

import com.nonacept.audio.mp3.Player;
import com.nonacept.carthegame.Game;
import com.nonacept.carthegame.IO.Fontes;
import com.nonacept.carthegame.SIZES;
import com.nonacept.image.BlurredImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class AnimacaoIntro {

    private final String LOGO = "NONACEPT";

    private String[] LogoParts;
    private Game jogo;
    private BufferedImage car;

    private double CarX, CarY, FinalCX, FinalCY;
    private double incrementoX, incrementoY, incrementoSize, incrementoFont;
    private double[] LogoPartsSize;
    private double atualSize;
    private int CarSize, startSize, fontSize;
    private int[] LogoPartsWidth, LogoPartsPosition;
    private int LogoTotalSize, LogoXPosition;
    private final double TheSpeed = 1.2;

    private int frames_p1 = 60, frames_p2 = 60;

    public void start(Game g) {
        this.jogo = g;
        new Player(jogo.sfx.wind, jogo.masterVolFloat).play();
        new Player(jogo.sfx.intro2, jogo.masterVolFloat).play();
        CarSize = SIZES._320;
        startSize = SIZES._32;
        atualSize = startSize;
        CarX = jogo.janelaW;
        CarY = SIZES._100;
        FinalCX = -CarSize;
        FinalCY = SIZES.MEIO_H;
        car = drawCar();
        incrementoX = (FinalCX - CarX) / (frames_p1 / TheSpeed);
        incrementoY = (FinalCY - CarY) / (frames_p1 / TheSpeed);
        incrementoSize = (CarSize - startSize) / (frames_p1 + frames_p2);
        animation();
    }

    private void start2() {
        FinalCX = jogo.janelaW + SIZES._45;
        FinalCY = SIZES.MEIO_H - (CarSize / 2);
        incrementoX = (FinalCX - CarX) / (frames_p2 / TheSpeed);
        incrementoY = (FinalCY - CarY) / (frames_p2 / TheSpeed);
        LogoParts = LOGO.split("");
        LogoPartsSize = new double[LogoParts.length];
        LogoPartsWidth = new int[LogoParts.length];
        LogoPartsPosition = new int[LogoParts.length];
        fontSize = SIZES._120;
        incrementoFont = (fontSize - SIZES._10) / (12 / TheSpeed);
        jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, fontSize));
        LogoTotalSize = jogo.bbg.getFontMetrics().stringWidth(LOGO);
        LogoXPosition = (jogo.janelaW / 2) - (LogoTotalSize / 2);
        for (int i = 0; i < LogoParts.length; i++) {
            LogoPartsWidth[i] = jogo.bbg.getFontMetrics().stringWidth(LogoParts[i]);
        }
        for (int i = 0; i < LogoParts.length; i++) {
            if (i == 0) {
                LogoPartsPosition[i] = LogoXPosition;
            } else {
                LogoPartsPosition[i] = LogoPartsPosition[i - 1] + LogoPartsWidth[i - 1];
            }
        }
        for (int i = 0; i < LogoPartsSize.length; i++) {
            LogoPartsSize[i] = SIZES._10;
        }
    }

    private void animation() {
        firstPart();
        animPause(0.5);
        start2();
        secondPart();
        lastPart();
    }

    private BufferedImage drawCar() {
        BufferedImage img = new BufferedImage(CarSize, CarSize, BufferedImage.TYPE_INT_ARGB);
        img = clearBuffer(img);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(SIZES._20, SIZES._20, CarSize - SIZES._40, CarSize - SIZES._40);
        g2d.setColor(Color.BLACK);
        //linhas de rodas
        int next = SIZES._20;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int the_y = CarSize - SIZES._20;
                if (j == 0) {
                    the_y = j;
                }
                g2d.fillRect(next, the_y, SIZES._40, SIZES._20);
            }
            next += SIZES._80;
        }
        //colunas de rodas
        next = SIZES._20;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int the_x = CarSize - SIZES._20;
                if (j == 0) {
                    the_x = j;
                }
                g2d.fillRect(the_x, next, SIZES._20, SIZES._40);
            }
            next += SIZES._80;
        }
        g2d.setFont(Fontes.ARIAL.deriveFont(Font.PLAIN, SIZES._240));
        g2d.drawString("D", CarSize / 2 - (g2d.getFontMetrics().stringWidth("D") / 2), measureStringHeight(g2d, "D") / 2 + (CarSize / 3));
        return img;
    }

    private BufferedImage clearBuffer(BufferedImage b_img) {
        Graphics2D g2d = b_img.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, b_img.getWidth(), b_img.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
        return b_img;
    }

    private int measureStringHeight(Graphics2D g, String s) {
        return (int) g.getFont().getStringBounds(s, g.getFontRenderContext()).getHeight();
    }

    private void firstPart() {
        int ticks = 0;
        try {
            Thread.sleep(15);
        } catch (InterruptedException ex) {

        }
        do {
            jogo.bbg.setColor(new Color(10, 10, 10));
            jogo.bbg.fillRect(0, 0, jogo.janelaW, jogo.janelaH);
            jogo.bbg.drawImage(car, (int) CarX, (int) CarY, (int) atualSize, (int) atualSize, null);
            jogo.g.drawImage(jogo.backBuffer, 0, 0, jogo.getWidth(), jogo.getHeight(), null);
            if (atualSize < CarSize) {
                atualSize += incrementoSize;
            }
            if (CarX != FinalCX) {
                CarX += incrementoX;
            }
            if (CarY != FinalCY) {
                CarY += incrementoY;
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {

            }
            ticks++;
        } while (ticks < frames_p1);
    }

    private void animPause(double seconds) {
        int tick_waiting = (int) (seconds * 60);
        int ticks_passed = 0;
        do {
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
            }
            ticks_passed++;
        } while (ticks_passed < tick_waiting);
    }

    private void secondPart() {
        int ticks = 0;
        do {
            if (ticks == 2) {
                new Player(jogo.sfx.intro1, jogo.masterVolFloat).play();
            }
            jogo.bbg.setColor(new Color(10, 10, 10));
            jogo.bbg.fillRect(0, 0, jogo.janelaW, jogo.janelaH);
            jogo.bbg.setColor(Color.WHITE);
            for (int i = 0; i < LogoParts.length; i++) {
                if (LogoPartsPosition[i] < CarX + atualSize) {
                    jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, (int) LogoPartsSize[i]));
                    jogo.bbg.drawString(LogoParts[i], LogoPartsPosition[i], SIZES.MEIO_H + SIZES._45);
                    if (LogoPartsSize[i] < fontSize) {
                        LogoPartsSize[i] += incrementoFont;
                    }
                    if (LogoPartsSize[i] > fontSize) {
                        LogoPartsSize[i] = fontSize;
                    }
                }
            }
            jogo.bbg.drawImage(car, (int) CarX, (int) CarY, (int) atualSize, (int) atualSize, null);
            jogo.g.drawImage(jogo.backBuffer, 0, 0, jogo.getWidth(), jogo.getHeight(), null);
            if (atualSize < CarSize) {
                atualSize += incrementoSize;
            }
            if (CarX != FinalCX) {
                CarX += incrementoX;
            }
            if (CarY != FinalCY) {
                CarY += incrementoY;
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {

            }
            if (ticks == 30) {

            }
            ticks++;
        } while (ticks < 65);
    }

    private void lastPart() {
        int alpha = 255;
        int alpha2 = 0;
        boolean aumentando = true;
        int firstRajada = 0;
        for (int i = 0; i < 317; i++) {
            jogo.bbg.setColor(new Color(10, 10, 10));
            jogo.bbg.fillRect(0, 0, jogo.janelaW, jogo.janelaH);
            jogo.bbg.setColor(new Color(255, 255, 255, alpha2 / 5));
            jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, (int) (fontSize * 1.1)));
            jogo.bbg.drawString(LOGO, (jogo.janelaW / 2) - (jogo.bbg.getFontMetrics().stringWidth(LOGO) / 2), SIZES.MEIO_H + SIZES._49);
            jogo.bbg.setColor(new Color(255, 255, 255, alpha));
            jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, fontSize));
            jogo.bbg.drawString(LOGO, (jogo.janelaW / 2) - (jogo.bbg.getFontMetrics().stringWidth(LOGO) / 2), SIZES.MEIO_H + SIZES._45);
            if (i > 217) {
                jogo.bbg.setColor(Color.WHITE);
                jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, SIZES._13));
                jogo.bbg.drawString("Nonaginta Septem Softwares 2014-2020", jogo.janelaW - (jogo.bbg.getFontMetrics().stringWidth("Nonaginta Septem Softwares 2014-2020") + 10), jogo.janelaH - 13);
            }
            jogo.g.drawImage(jogo.backBuffer, 0, 0, jogo.getWidth(), jogo.getHeight(), null);
            if (alpha2 == alpha) {
                if (aumentando) {
                    alpha += 7.5;
                } else {
                    alpha -= 7.5;
                }
                if (alpha < 100) {
                    aumentando = true;
                    new Player(jogo.sfx.intro0, jogo.masterVolFloat).play();
                } else if (alpha > 255) {
                    aumentando = false;
                    alpha = 255;
                    new Player(jogo.sfx.intro0, jogo.masterVolFloat).play();
                    if (firstRajada == 1) {
                        new Player(jogo.sfx.rajada, jogo.masterVolFloat).play();
                        firstRajada++;
                    }
                }
                alpha2 = alpha;
            } else {
                alpha2 += 15;
                if (alpha2 > 255) {
                    alpha2 = alpha;
                }
            }
            if (i > 217 && firstRajada < 1) {
                firstRajada++;
            }
            if (i > 257) {
                aumentando = true;
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {

            }

        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
    }
}
