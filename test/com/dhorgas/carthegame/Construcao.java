/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame;

/**
 *
 * @author Douglas Rocha
 */
import dhorgas.midi.tocarMusica;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//A CLASSE Construcao HERDA AS FUNCIONALIDADES DE JFRAME
public class Construcao extends JFrame {

    BufferedImage backBuffer; // <-BUFFER
    char FPS = 60; //<- TAXA DE ATUALIZAÇÃO DA TELA
    short janelaW = 888; //<-LARGURA DA TELA
    short janelaH = 500; //<-ALTURA DA TELA

    short x = 10, y = 20;
    boolean direita = true;
    boolean desce = true;
    short velx = 5, vely = 5;

    short txtx = 100, txty = 100;
    boolean txtdireita = false;
    boolean txtdesce = true;
    short txtvelx = 5, txtvely = 5;

    /*NO METODO ATUALIZAR CHAMAR OS METODOS
     * QUE SERÃO EXECUTADOS O TEMPO TODO*/
    public void atualizar() {

    }
    tocarMusica tm = new tocarMusica();

    /*NESSE MÉTODO SERÁ DESENHADO FORMAS GEOMÉTRICAS,
     * IMAGENS E TEXTOS NA TELA*/
    public void desenharGraficos() {
        Graphics g = getGraphics();//<-COM g SERÁ DESENHADO NA TELA O QUE ESTÁ NO BUFFER
        Graphics2D bbg = (Graphics2D) backBuffer.getGraphics();//<- COM bbg DESENHARÁ NO BUFFER
        bbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bbg.setColor(Color.BLACK);
        bbg.fillRect(0, 0, 888, 500);
        bbg.setColor(Color.blue);
        bbg.setFont(new Font("rufa", Font.PLAIN, 48));
        bbg.drawString("CAR", 384, 250);
        bbg.setColor(Color.red);
        bbg.setFont(new Font("times new romam", Font.BOLD, 12));
        bbg.drawString("THE GAME", 409, 274);
        bbg.drawString("TM", 499, 220);
        bbg.setColor(Color.yellow);
        bbg.setFont(new Font("times new roman", Font.BOLD, 30));
        bbg.drawString("EM CONSTRUÇÃO...", txtx, txty);
        bbg.setColor(Color.red);
        bbg.drawRoundRect(x, y, 225, 19, 12, 12);
        bbg.setColor(Color.white);
        bbg.setFont(new Font("times new roman", Font.BOLD, 20));
        bbg.drawString("DHORGAS SOFTWARE", x + 5, y + 17);
        /*AQUI DESENHARÁ O BUFFER NA TELA,
         NAS CORDENADA X:0 e Y:0*/
        g.drawImage(backBuffer, 0, 0, this);
    }

    public void inicializar() {
        setTitle("DHORGAS' GAME");//TITULO DA JANELA
        setSize(janelaW, janelaH);//DIMENSÕES DA JANELA
        setResizable(false);//IMPEDINDO DE REDIMENSIONAR A JANELA
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        backBuffer = new BufferedImage(janelaW, janelaH, BufferedImage.TYPE_INT_RGB);//CRIANDO O BUFFER DE IMAGEM
        tm.Reproduzir("arquivos/Sons/zoot.mid", 999);
        sortearVelocidades();
    }

    //MÉTODO RUN() NELE TEM O GAME LOOP (UM LOOP INFINITO)
    public void run() {
        inicializar();
        while (true) {
            atualizar();
            desenharGraficos();
            alterString();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro crítico durane a execução dos frames", "ERRO CRÍTICO", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        Construcao game = new Construcao(); //CRIAÇÃO DE UM OBJETO À PARTIR DESSA PRÓPRIA CLASSE
        game.run();
    }

    public void alterString() {
        if (x >= 888 - 225) {
            direita = false;
        } else if (x <= 0) {
            direita = true;
        }
        if (y >= 500 - 19) {
            desce = false;
        } else if (y <= 0 + 27) {
            desce = true;
        }
        if (desce == false) {
            y -= vely;
        } else {
            y += vely;
        }
        if (direita == false) {
            x -= velx;
        } else {
            x += velx;
        }

        if (txtx >= 888 - 292) {
            txtdireita = false;
        } else if (txtx <= 0) {
            txtdireita = true;
        }
        if (txty >= 500) {
            txtdesce = false;
        } else if (txty <= 0 + 47) {
            txtdesce = true;
        }
        if (txtdesce == false) {
            txty -= txtvely;
        } else {
            txty += txtvely;
        }
        if (txtdireita == false) {
            txtx -= txtvelx;
        } else {
            txtx += txtvelx;
        }
    }

    private void sortearVelocidades() {
        Random rdm=new Random((long)Math.random());
        velx=(short)(1+rdm.nextInt(4));
        vely=(short)(1+rdm.nextInt(5));
        txtvelx=(short)(1+rdm.nextInt(5));
        txtvely=(short)(1+rdm.nextInt(4));
    }
}
