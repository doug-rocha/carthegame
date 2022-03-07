/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhorgas.carthegame.Options;

/**
 *
 * @author Douglas
 */
public class FrPS {

    public static int _30 = 31, _45 = 46, _60 = 61, _75 = 79, _90 = 98;
    public static int _90t;

    public static void calcTicks(double mod) {
        _90 = (int) (90 * mod);
        _75 = (int) (76 * mod);
        _60 = (int) (61 * mod);
        _45 = (int) (46 * mod);
        _30 = (int) (31 * mod);
        if (mod > 1.01) {
            _90t = (int) (115 * mod * 1.10);
        } else {
            _90t = (int) (115 * mod);
        }
        if (_30 < 30) {
            _30 = 31;
        }
        if (_45 < 45) {
            _45 = 46;
        }
        if (_60 < 60) {
            _60 = 61;
        }
        if (_75 < 75) {
            _75 = 76;
        }
        if (_90 < 90) {
            _90 = 91;
        }
        if (_90t < 114) {
            _90t = 115;
        }
    }
}
