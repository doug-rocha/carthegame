/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nonacept.carthegame;

import enigma.console.TextAttributes;
import java.awt.Color;

/**
 *
 * @author Douglas
 */
public class SIZES {

    public static void calcular(int janelaW, int janelaH) {

        double modificador = janelaH / 720.0;
        _1 = (int) Math.round(1 * modificador);
        _2 = (int) (2 * modificador);
        _3 = (int) (3 * modificador);
        _4 = (int) (4 * modificador);
        _5 = (int) (5 * modificador);
        _6 = (int) (6 * modificador);
        _7 = (int) (7 * modificador);
        _8 = (int) (8 * modificador);
        _10 = (int) (10 * modificador);
        _11 = (int) (11 * modificador);
        _12 = (int) (12 * modificador);
        _13 = (int) (13 * modificador);
        _14 = (int) (14 * modificador);
        _15 = (int) (15 * modificador);
        _16 = (int) (16 * modificador);
        _18 = (int) (18 * modificador);
        _20 = (int) (20 * modificador);
        _22 = (int) (22 * modificador);
        _23 = (int) (23 * modificador);
        _24 = (int) (24 * modificador);
        _25 = (int) (25 * modificador);
        _30 = (int) (30 * modificador);
        _32 = (int) (32 * modificador);
        _33 = (int) (33 * modificador);
        _36 = (int) (36 * modificador);
        _37 = (int) (37 * modificador);
        _38 = (int) (38 * modificador);
        _40 = (int) (40 * modificador);
        _45 = (int) (45 * modificador);
        _48 = (int) (48 * modificador);
        _49 = (int) (48 * modificador);
        _50 = (int) (50 * modificador);
        _60 = (int) (60 * modificador);
        _65 = (int) (65 * modificador);
        _68 = (int) (68 * modificador);
        _70 = (int) (70 * modificador);
        _75 = (int) (75 * modificador);
        _80 = (int) (80 * modificador);
        _88 = (int) (88 * modificador);
        _90 = (int) (90 * modificador);
        _95 = (int) (95 * modificador);

        _100 = (int) (100 * modificador);
        _107 = (int) (107 * modificador);
        _108 = (int) (108 * modificador);
        _110 = (int) (110 * modificador);
        _120 = (int) (120 * modificador);
        _124 = (int) (124 * modificador);
        _130 = (int) (130 * modificador);
        _137 = (int) (137 * modificador);
        _140 = (int) (140 * modificador);
        _142 = (int) (142 * modificador);
        _150 = (int) (150 * modificador);
        _151 = (int) (151 * modificador);
        _155 = (int) (155 * modificador);
        _160 = (int) (160 * modificador);
        _165 = (int) (165 * modificador);
        _170 = (int) (170 * modificador);
        _172 = (int) (172 * modificador);
        _174 = (int) (174 * modificador);
        _176 = (int) (176 * modificador);
        _182 = (int) (182 * modificador);

        _200 = (int) (200 * modificador);
        _202 = (int) (202 * modificador);
        _209 = (int) (209 * modificador);
        _210 = (int) (210 * modificador);
        _211 = (int) (211 * modificador);
        _215 = (int) (215 * modificador);
        _220 = (int) (220 * modificador);
        _223 = (int) (223 * modificador);
        _225 = (int) (225 * modificador);
        _232 = (int) (232 * modificador);
        _236 = (int) (236 * modificador);
        _240 = (int) (240 * modificador);
        _247 = (int) (247 * modificador);
        _275 = (int) (275 * modificador);
        _280 = (int) (280 * modificador);
        _285 = (int) (285 * modificador);

        _300 = (int) (300 * modificador);
        _304 = (int) (304 * modificador);
        _310 = (int) (310 * modificador);
        _319 = (int) (319 * modificador);
        _320 = (int) (320 * modificador);
        _334 = (int) (334 * modificador);
        _335 = (int) (335 * modificador);
        _349 = (int) (349 * modificador);
        _350 = (int) (350 * modificador);
        _387 = (int) (387 * modificador);
        _395 = (int) (395 * modificador);

        _404 = (int) (404 * modificador);
        _442 = (int) (442 * modificador);
        _459 = (int) (459 * modificador);
        _474 = (int) (474 * modificador);
        _480 = (int) (480 * modificador);
        _489 = (int) (489 * modificador);

        _520 = (int) (520 * modificador);
        _527 = (int) (527 * modificador);
        _544 = (int) (544 * modificador);
        _559 = (int) (559 * modificador);
        _565 = (int) (565 * modificador);
        _574 = (int) (574 * modificador);

        _605 = (int) (605 * modificador);
        _617 = (int) (617 * modificador);
        _650 = (int) (650 * modificador);

        MEIO_W = janelaW / 2;
        MEIO_H = janelaH / 2;

        _1_4 = (1.4 * modificador);
        _2_8 = (2.8 * modificador);

        if (Game.DEBUGMODE) {
            Game.TA = new TextAttributes(Color.GREEN);
            Game.DEBUGCONSOLE.setTextAttributes(Game.TA);
            System.out.println("INFO | SIZES | TAMANHOS FIXOS CALCULADOS");
        }

    }

    public static int _1, _2, _3, _4, _5, _6, _7, _8,
            _10, _11, _12, _13, _14, _15, _16, _18,
            _20, _22, _23, _24, _25,
            _30, _32, _33, _36, _37, _38, _40, _45, _48, _49,
            _50, _60, _65, _68, _70, _75, _80, _88, _90, _95,
            _100, _107, _108, _110, _120, _124, _130, _137, _140, _142,
            _150, _151, _155, _160, _165, _170, _172, _174, _176, _182,
            _200, _202, _209, _210, _211, _215, _220, _223, _225, _232, _236, _240,
            _247, _275, _280, _285,
            _300, _304, _310, _319, _320, _334, _335, _349,
            _350, _387, _395,
            _404, _442,
            _459, _474, _480, _489,
            _520, _527, _544,
            _559, _565, _574,
            _605, _617, _650,
            MEIO_W, MEIO_H;

    public static double _1_4, _2_8;
}
