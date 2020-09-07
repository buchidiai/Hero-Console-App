/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.exceptions;

/**
 *
 * @author louie
 */
public class SuperHeroDuplicateKeyException extends Exception {

    public SuperHeroDuplicateKeyException(String message) {
        super(message);
    }

    public SuperHeroDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}
