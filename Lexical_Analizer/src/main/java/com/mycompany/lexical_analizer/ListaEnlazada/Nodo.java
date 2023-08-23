/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.ListaEnlazada;

public class Nodo<T> {

    private T instancia;
    private Nodo<T> siguiente;

    public Nodo(T objeto) {
        this.instancia = objeto;
        this.siguiente = null;
    }

    public T getInstancia() {
        return instancia;
    }

    public void setInstancia(T objeto) {
        this.instancia = objeto;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

}
