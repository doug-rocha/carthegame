/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.Options;

import com.dhorgas.carthegame.Idiomas.TutoLang;
import com.dhorgas.carthegame.Idiomas.LangText;
import com.dhorgas.carthegame.IO.Fontes;
import com.dhorgas.carthegame.Game;
import com.dhorgas.carthegame.SIZES;
import com.thoughtworks.xstream.XStream;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas
 */
public class OpcoesEmbut implements Runnable {

    private Game jogo;
    public int idResol = 0, idFullscr = 0, idFPS = 0, idAA = 0, idLang = 0;
    public int menuX = 0, menuY = 0;
    public int[] FPDdisp;
    public String[] nomesLang;
    private Integer[] idArqLangs;
    public OpcoesVar ops;
    public boolean nSalvo = false;
    private int yep = 0;
    private int fadeinicio;
    private int fadedesejado;
    boolean reloaded = false;

    public OpcoesEmbut(Game g) {
        FPDdisp = new int[]{30, 45, 60, 75, 90};
        jogo = g;
    }

    public OpcoesEmbut(Game g, int mx, int my) {
        FPDdisp = new int[]{30, 45, 60, 75, 90};
        jogo = g;
        menuX = mx;
        menuY = my;
    }

    @Override
    public void run() {
        langs();
        loadOps();
        fadeinicio = (int) ((jogo.janelaH / Game.MODRESOL));
        fadedesejado = fadeinicio;
        telaOp();
    }

    public void saveNload() {
        yep = 1;
        jogo.janelaW = jogo.resolsDisp[idResol].X;
        jogo.janelaH = jogo.resolsDisp[idResol].Y;
        if (idFullscr == 1) {
            jogo.telacheia = true;
        } else {
            jogo.telacheia = false;
        }
        jogo.FPS = FPDdisp[idFPS];
        if (idAA == 1) {
            jogo.antiAliasing = true;
        } else {
            jogo.antiAliasing = false;
        }
        jogo.iMsg.id = Short.parseShort(idArqLangs[idLang].toString());
        jogo.SalvarOpcoes();
        reload();
        nSalvo = false;
        reloaded = true;
    }

    private void reload() {
        loadIdioma();
        jogo.g.dispose();
        jogo.bbg.dispose();
        if (jogo.telacheia == true) {
            //dispose();
            //setUndecorated(true);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screen = ge.getDefaultScreenDevice();
            if (screen.isFullScreenSupported()) {
                jogo.dispose();
                jogo.setUndecorated(true);
                screen.setFullScreenWindow(jogo);
                jogo.setVisible(true);
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.GREEN);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println("INFO| CFG | O SISTEMA SUPORTA TELA CHEIA NATIVA");
                }
            } else {
                jogo.setSize(jogo.tela.getSize());
                jogo.setAlwaysOnTop(true);
                if (Game.DEBUGMODE) {
                    Game.TA = new TextAttributes(Color.YELLOW);
                    Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                    System.out.println("ALRT | CFG | O SISTEMA NÃO SUPORTA TELA CHEIA NATIVA");
                    System.out.println("ALRT | CFG | USANDO MÉTODO DE TELA CHEIA ALTERNATIVO");
                }
            }
            //setVisible(true);
        } else {
            jogo.dispose();
            jogo.setUndecorated(false);
            jogo.setSize(jogo.janelaW, jogo.janelaH);
            jogo.setLocationRelativeTo(null);
            jogo.setVisible(true);
            jogo.setAlwaysOnTop(false);
        }
        jogo.g = jogo.getGraphics();
        jogo.backBuffer = new BufferedImage(jogo.janelaW, jogo.janelaH, BufferedImage.TYPE_INT_RGB);
        jogo.bbg = (Graphics2D) jogo.backBuffer.getGraphics();
        jogo.pintura = new BufferedImage(jogo.janelaW, jogo.janelaH, BufferedImage.TYPE_INT_RGB);
        jogo.bbg2 = (Graphics2D) jogo.pintura.getGraphics();
        Game.MODRESOL = jogo.janelaH / 720.0;
        SIZES.calcular(jogo.janelaW, jogo.janelaH);

        if (jogo.antiAliasing) {
            jogo.bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            jogo.bbg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.ORANGE);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println("INFO | CFG | ANTIALIASING ATIVADO");
            }
        }
        FrPS.calcTicks(Game.MODRESOL);
        switch (jogo.FPS) {
            case 90:
                jogo.FPS = FrPS._90;
                break;
            case 75:
                jogo.FPS = FrPS._75;
                break;
            case 60:
                jogo.FPS = FrPS._60;
                break;
            case 45:
                jogo.FPS = FrPS._45;
                break;
            case 30:
                jogo.FPS = FrPS._30;
                break;
        }
        reloaded = true;

    }

    private void loadIdioma() {
        String arq = "";
        try {
            File leitorf = new File("arquivos/lang/langs.ctgi");
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println( String.format("INFO | ARQUIVO DE LINGUAS: %s", leitorf.getAbsolutePath()));
            }
            InputStream opa = new FileInputStream(leitorf);
            Charset cs = Charset.forName("UTF-8");
            InputStreamReader entra = new InputStreamReader(opa, cs);
            BufferedReader entrada = new BufferedReader(entra);
            String linha;
            while ((linha = entrada.readLine()) != null) {
                StringTokenizer entrar = new StringTokenizer(linha, "%");
                if (jogo.iMsg.id == Integer.parseInt(entrar.nextToken())) {
                    arq = entrar.nextToken();
                }
            }
            File xLangArq = new File("arquivos/lang/" + arq + ".xlang");
            XStream xstreamIdioma = new XStream();
            xstreamIdioma.alias("idiomaArq", LangText.class);
            xstreamIdioma.alias("tutoIdiomaArq", TutoLang.class);
            File xTutorialLangArq = new File("arquivos/lang/" + arq + "0.xlang");
            jogo.iMsg = (LangText) xstreamIdioma.fromXML(xLangArq);
            jogo.tutoMsg = (TutoLang) xstreamIdioma.fromXML(xTutorialLangArq);
            if (Game.DEBUGMODE) {
                Game.TA = new TextAttributes(Color.GREEN);
                Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
                System.out.println(String.format("INFO | IDIOMA %s", arq));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar configurações" + e.getMessage());
        }

    }

    private void langs() {
        try {
            File leitorf = new File("arquivos/lang/langs.ctgi");
            InputStream opa = new FileInputStream(leitorf);
            Charset cs = Charset.forName("UTF-8");
            InputStreamReader entra = new InputStreamReader(opa, cs);
            BufferedReader entrada = new BufferedReader(entra);
            String linha;
            ArrayList<String> nomes = new ArrayList<>();
            ArrayList<Integer> ids = new ArrayList<>();
            while ((linha = entrada.readLine()) != null) {
                StringTokenizer entrar = new StringTokenizer(linha, "%");
                ids.add(Integer.parseInt(entrar.nextToken()));
                entrar.nextToken();
                nomes.add(entrar.nextToken());
            }
            nomesLang = nomes.toArray(new String[nomes.size()]);
            idArqLangs = ids.toArray(new Integer[ids.size()]);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar configurações" + e.getMessage());
        }
    }

    private void telaOp() {
        reloaded = false;
        do {
            jogo.bbg.drawImage(jogo.fundoIm, 0, 0, jogo.janelaW, jogo.janelaH, jogo);
            jogo.bbg.setColor(new Color(10, 10, 10, 150));
            jogo.bbg.fillRect(0, 0, jogo.janelaW, jogo.janelaH);
            jogo.bbg.setColor(new Color(10, 10, 10, 150));
            jogo.bbg.fillRect(SIZES._140, 0, (int) (jogo.janelaW - SIZES._280), jogo.janelaH);
            jogo.bbg.setColor(new Color(200, 200, 200, 50));
            jogo.bbg.fillRect(SIZES._140, SIZES._38, jogo.janelaW - SIZES._280, SIZES._70);
            jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.BOLD, SIZES._22));
            jogo.bbg.setColor(Color.WHITE);
            jogo.bbg.drawString(jogo.iMsg.opTela[5].texto, SIZES._150, SIZES._65);
            jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, SIZES._18));
            int alpha = 0;
            for (int i = (int) ((jogo.janelaH / 2) - (SIZES._100)); i < jogo.janelaH; i += 2) {
                alpha++;
                if (alpha > 200 && i < (jogo.janelaH - (SIZES._100))) {
                    alpha = 200;
                } else if (alpha > 250) {
                    alpha = 250;
                }
                jogo.bbg.setColor(new Color(0, 0, 0, alpha));
                jogo.bbg.fillRect(0, i, jogo.janelaW, 2);
            }

            switch (menuX) {
                case 0: //MENUS TELA
                    fadedesejado = 265;
                    if (menuY != 0) {
                        jogo.bbg.setColor(new Color(200, 200, 200, 90));
                        jogo.bbg.fillRect(SIZES._150, (int) (SIZES._137 + ((27 * (menuY - 1)) * Game.MODRESOL)), SIZES._275, SIZES._22);
                    }

                    switch (menuY) {
                        case 0:

                            jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            jogo.bbg.fillRoundRect(SIZES._140, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[1].texto, SIZES._165, SIZES._155);
                            jogo.bbg.drawString(jogo.resolsDisp[idResol].Nome, SIZES._350, SIZES._155);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[2].texto, SIZES._165, SIZES._182);
                            if (idFullscr == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._182);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._182);
                            }

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[3].texto, SIZES._165, SIZES._209);
                            jogo.bbg.drawString(String.valueOf(FPDdisp[idFPS]), SIZES._350, SIZES._209);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[4].texto, SIZES._165, SIZES._236);
                            if (idAA == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._236);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._236);
                            }

                            break;

                        case 1:

                            jogo.bbg.setColor(new Color(100, 100, 100, 60));
                            jogo.bbg.fillRoundRect(SIZES._140, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(200, 200, 200));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(0, 0, 0, 45));
                            jogo.bbg.fillRect(SIZES._140, SIZES._108, jogo.janelaW - SIZES._280, jogo.janelaH - SIZES._110);

                            //jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            //jogo.bbg.fillRect(SIZES._150, (int) SIZES._137, (int) (275 * Main.modResol), SIZES._22);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.opTela[1].texto, SIZES._165, SIZES._155);
                            jogo.bbg.drawString(jogo.resolsDisp[idResol].Nome, SIZES._350, SIZES._155);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[2].texto, SIZES._165, SIZES._182);
                            if (idFullscr == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._182);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._182);
                            }

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[3].texto, SIZES._165, SIZES._209);
                            jogo.bbg.drawString(String.valueOf(FPDdisp[idFPS]), SIZES._350, SIZES._209);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[4].texto, SIZES._165, SIZES._236);
                            if (idAA == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._236);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._236);
                            }
                            break;
                        case 2:
                            jogo.bbg.setColor(new Color(100, 100, 100, 60));
                            jogo.bbg.fillRoundRect(SIZES._140, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(200, 200, 200));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(0, 0, 0, 45));
                            jogo.bbg.fillRect(SIZES._140, SIZES._108, (int) (jogo.janelaW - (SIZES._280)), (int) (jogo.janelaH - SIZES._110));

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[1].texto, SIZES._165, SIZES._155);
                            jogo.bbg.drawString(jogo.resolsDisp[idResol].Nome, SIZES._350, SIZES._155);

                            //jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            //jogo.bbg.fillRect(SIZES._150, (int) (164 * Main.modResol), (int) (275 * Main.modResol), SIZES._22);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.opTela[2].texto, SIZES._165, SIZES._182);
                            if (idFullscr == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._182);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._182);
                            }

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[3].texto, SIZES._165, SIZES._209);
                            jogo.bbg.drawString(String.valueOf(FPDdisp[idFPS]), SIZES._350, SIZES._209);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[4].texto, SIZES._165, SIZES._236);
                            if (idAA == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._236);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._236);
                            }
                            break;
                        case 3:

                            jogo.bbg.setColor(new Color(100, 100, 100, 60));
                            jogo.bbg.fillRoundRect(SIZES._140, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(200, 200, 200));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(0, 0, 0, 45));
                            jogo.bbg.fillRect(SIZES._140, SIZES._108, (int) (jogo.janelaW - (SIZES._280)), (int) (jogo.janelaH - SIZES._110));

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[1].texto, SIZES._165, SIZES._155);
                            jogo.bbg.drawString(jogo.resolsDisp[idResol].Nome, SIZES._350, SIZES._155);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[2].texto, SIZES._165, SIZES._182);
                            if (idFullscr == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._182);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._182);
                            }

                            //jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            //jogo.bbg.fillRect(SIZES._150, (int) (191 * Main.modResol), (int) (275 * Main.modResol), SIZES._22);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.opTela[3].texto, SIZES._165, SIZES._209);
                            jogo.bbg.drawString(String.valueOf(FPDdisp[idFPS]), SIZES._350, SIZES._209);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[4].texto, SIZES._165, SIZES._236);
                            if (idAA == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._236);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._236);
                            }
                            break;
                        case 4:
                            jogo.bbg.setColor(new Color(100, 100, 100, 60));
                            jogo.bbg.fillRoundRect(SIZES._140, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(200, 200, 200));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(0, 0, 0, 45));
                            jogo.bbg.fillRect(SIZES._140, SIZES._108, (int) (jogo.janelaW - (SIZES._280)), (int) (jogo.janelaH - SIZES._110));

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[1].texto, SIZES._165, SIZES._155);
                            jogo.bbg.drawString(jogo.resolsDisp[idResol].Nome, SIZES._350, SIZES._155);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[2].texto, SIZES._165, SIZES._182);
                            if (idFullscr == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._182);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._182);
                            }

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[3].texto, SIZES._165, SIZES._209);
                            jogo.bbg.drawString(String.valueOf(FPDdisp[idFPS]), SIZES._350, SIZES._209);

                            //jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            //jogo.bbg.fillRect(SIZES._150, (int) (218 * Main.modResol), (int) (275 * Main.modResol), SIZES._22);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.opTela[4].texto, SIZES._165, SIZES._236);
                            if (idAA == 0) {
                                jogo.bbg.drawString(jogo.iMsg.ativado[0].texto, SIZES._350, SIZES._236);
                            } else {
                                jogo.bbg.drawString(jogo.iMsg.ativado[1].texto, SIZES._350, SIZES._236);
                            }
                            break;

                    }

                    break;
                case 1: //MENUS IDIOMA
                    fadedesejado = 184;
                    if (menuY != 0) {
                        jogo.bbg.setColor(new Color(200, 200, 200, 90));
                        jogo.bbg.fillRect(SIZES._320, (int) (SIZES._137 + ((27 * (menuY - 1)) * Game.MODRESOL)), SIZES._300, SIZES._22);
                    }

                    switch (menuY) {
                        case 0:

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            jogo.bbg.fillRoundRect(SIZES._310, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, SIZES._335, SIZES._155);
                            jogo.bbg.drawString(nomesLang[idLang], SIZES._520, SIZES._155);

                            break;
                        case 1:

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(100, 100, 100, 60));
                            jogo.bbg.fillRoundRect(SIZES._310, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(200, 200, 200));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 100, 100, 200));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            jogo.bbg.setColor(new Color(0, 0, 0, 45));
                            jogo.bbg.fillRect(SIZES._140, SIZES._108, (int) (jogo.janelaW - (SIZES._280)), (int) (jogo.janelaH - SIZES._110));

                            //jogo.bbg.setColor(new Color(200, 200, 200, 90));
                            //jogo.bbg.fillRect(SIZES._320, (int) SIZES._137, SIZES._300, SIZES._22);
                            jogo.bbg.setColor(new Color(250, 250, 250));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, SIZES._335, SIZES._155);
                            jogo.bbg.drawString(nomesLang[idLang], SIZES._520, SIZES._155);

                            break;

                    }

                    break;
                case 2: //MENUS TRILHA
                    fadedesejado = 130;

                    switch (menuY) {
                        case 0:

                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.opTela[0].texto, (int) (SIZES._225 - (jogo.iMsg.opTela[0].posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(150, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.idioma.texto, (int) (SIZES._395 - (jogo.iMsg.idioma.posX * Game.MODRESOL)), SIZES._100);
                            jogo.bbg.setColor(new Color(125, 100, 100, 75));
                            jogo.bbg.fillRoundRect(SIZES._480, SIZES._75, SIZES._170, SIZES._33, SIZES._5, SIZES._5);
                            jogo.bbg.setColor(new Color(200, 150, 150));
                            jogo.bbg.drawString(jogo.iMsg.trilhaSom.texto, (int) (SIZES._565 - (jogo.iMsg.trilhaSom.posX * Game.MODRESOL)), SIZES._100);

                            break;
                    }
            }
            jogo.bbg.setColor(new Color(200, 200, 200, 20));
            jogo.bbg.drawLine(SIZES._150, SIZES._75, jogo.janelaW - SIZES._151, SIZES._75);
            jogo.bbg.drawLine(SIZES._150, SIZES._107, jogo.janelaW - SIZES._151, SIZES._107);
            alpha = 0;
            if (menuY != 0) {
                fadedesejado += 25;
            }
            for (int j = 0; j < (25 * Game.MODRESOL); j++) {
                if (fadeinicio > fadedesejado) {
                    fadeinicio--;
                } else if (fadeinicio < fadedesejado) {
                    fadeinicio++;
                }
            }
            for (int i = (int) (fadeinicio * Game.MODRESOL); i < jogo.janelaH; i++) {
                alpha++;
                if (alpha > 150 && i < (jogo.janelaH - SIZES._150)) {
                    alpha = 150;
                } else if (alpha > 240) {
                    alpha = 240;
                }
                jogo.bbg.setColor(new Color(0, 0, 0, alpha));
                jogo.bbg.fillRect(SIZES._140, i, (int) (jogo.janelaW - (SIZES._280)), 1);
            }

            if (nSalvo) {
                jogo.bbg.setColor(Color.WHITE);
                jogo.bbg.setFont(Fontes.LIBEL.deriveFont(Font.PLAIN, SIZES._16));
                jogo.bbg.drawString(jogo.iMsg.salvarOpSpc.texto, SIZES._142, jogo.janelaH - SIZES._7);
                jogo.bbg.setColor(Color.RED);
                jogo.bbg.drawRect(SIZES._140, 0, jogo.janelaW - SIZES._280, jogo.janelaH - 1);
            }

            jogo.bbg.setColor(Color.WHITE);

            jogo.bbg2.drawImage(jogo.backBuffer, 0, 0, jogo);

            try {
                Thread.sleep(1000 / 90);
            } catch (InterruptedException ex) {
            }

        } while (jogo.yep == -5 && yep == 0);

        if (yep == 1) {
            do {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            } while ((!jogo.isShowing()) || (!reloaded));
            yep = 0;
            telaOp();

        }

    }

    private void loadOps() {
        XStream xstream = new XStream();
        xstream.alias("opcoesCTG", OpcoesVar.class);
        File arqOps = null;
        if (jogo.OSystem.contains("linux")) {
            arqOps = new File(System.getProperty("user.home") + "/.config/CTG/ops.ops");
        } else if (jogo.OSystem.contains("windows")) {
            arqOps = new File(System.getProperty("user.home") + "/Documents/CTG/ops.ops");
        }
        OpcoesVar ops = (OpcoesVar) xstream.fromXML(arqOps);
        for (int i = 0; i < jogo.resolsDisp.length; i++) {
            if (ops.resolucaoX == jogo.resolsDisp[i].X && ops.resolucaoY == jogo.resolsDisp[i].Y) {
                idResol = i;
            }
        }
        if (ops.antiAliasing) {
            idAA = 1;
        } else {
            idAA = 0;
        }
        if (ops.telaCheia) {
            idFullscr = 1;
        } else {
            idFullscr = 0;
        }
        for (int i = 0; i < FPDdisp.length; i++) {
            if (FPDdisp[i] == ops.FPS) {
                idFPS = i;
            }
        }
        for (int i = 0; i < idArqLangs.length; i++) {
            if (idArqLangs[i] == ops.idIdioma) {
                idLang = i;
            }
        }
    }

}
