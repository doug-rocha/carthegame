/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.IO.Levels;

/**
 *
 * @author Douglas Rocha de Oliveira (@dhorgas1) - dhorgas_software@hotmail.com
 */
public class Map {

    public int gameTipo;
    public String levelName;
    public String levelTexto;
    public Parede[] paredes;
    public ParedeArea[] areaParedes;
    public Teleport[] portals;
    public TeleportArea[] areaPortals;
    public Chegada[] chegadas;
    public ChegadaArea[] areaChegadas;
    public String spawnX;
    public String spawnY;
    public String nitroInicial;
    public String invTempo;
    public Requisitos requisitos;
    public CaixoteLvl[] caixotes;
    public CaixoteArea[] areaCaixotes;
    public CarDestruido[] carrosDestruidos;
    public CarDestruidoArea[] areaCarrosDestruidos;
    public Decoracoes[] decoracoes;
    public DecoracoesArea[] areaDecoracoes;
    public Item[] itens;
}
