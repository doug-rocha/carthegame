/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame.IO;

import com.nonacept.carthegame.Entidades.Cars;
import com.nonacept.carthegame.Entidades.Itens;

/**
 *
 * @author Douglas Rocha
 */
public class SaveGame {

    public boolean TelaCheia;
    public int nivel, janelaw, janelah;
    public int GameTipo, LvlAtual;
    public double GameBuild;
    //int[] Wallx, Wally, oldwx, oldwy;
    public int[] itensJG2Tipo;
    public boolean[] itensJG2Ativo;
    public int MapX, MapY;
    public Cars carro;
    public double ModResol;
    public int[] botx, boty, oldbotx, oldboty, botveloc;
    public double PropCar;
    public boolean exibindoinfom, exibindoitem, ativado, invisivel;
    public int tempoinfom, tempospawn, loopitem, tempoativacao;
    public int tempoinvisible, frame;
    public int itTipo;
    public boolean itAtivo;
    public int quantidade, nitroexibido, exibindovida;
    public double cardamage = 0, nitro = 0;
    public int tempodano = 0;
    public int tempovida = 0;
    public int loopnivel = 1, loopnivelcar = 1, nivelcar = 0;

    //int[] portalx, portaly, destportalx, destportaly;
    public boolean[] portalativo;
    //int[] chegadax, chegaday, oldchegadax, oldchegaday;
    public boolean[] chegadaativa;
    //public int[] cxcX, cxcY, oldCxcX, oldCxcY;
    public double[] cxcDurab;
    public boolean[] cxcDestroy;

    //public int[] dcarX,dcarY,oldDcarX,oldDcarY;
    public double[] dcarDurab;
    public boolean[] dcarExplodido;

    //int[] cenaX, cenaY, cenaOldX, cenaOldY;
}
