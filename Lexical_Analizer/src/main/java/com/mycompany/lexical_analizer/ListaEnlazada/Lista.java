/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.ListaEnlazada;

public class Lista<T> {

    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int tamano;

    public Lista() {
        this.inicio = null;
        this.fin = null;
        this.tamano = 0;
    }

    public void agregarDeUltimo(T objeto) {
        Nodo<T> nuevoNodo = new Nodo<>(objeto);
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
        tamano++;
    }

    public void eliminarUltimo() {
        if (tamano == 1) {
            inicio = null;
            fin = null;
        } else {
            Nodo<T> penultimo = inicio;
            while (penultimo.getSiguiente() != fin) {
                penultimo = penultimo.getSiguiente();
            }
            fin = penultimo;
            fin.setSiguiente(null);
        }
        tamano--;
    }

    public void eliminarEnIndice(int indice) {
        if (indice >= 0 && indice < tamano) {
            if (indice == 0) {
                inicio = inicio.getSiguiente();
                if (inicio == null) {
                    fin = null;
                }
            } else {
                Nodo<T> anterior = obtenerNodoEnIndice(indice - 1);
                anterior.setSiguiente(anterior.getSiguiente().getSiguiente());
                if (anterior.getSiguiente() == null) {
                    fin = anterior;
                }
            }
            tamano--;
        }
    }

    public T getInstancia(int indice) {
        if (indice >= 0 && indice < tamano) {
            Nodo<T> nodoEnIndice = obtenerNodoEnIndice(indice);
            return nodoEnIndice.getInstancia();
        } else {
            return null;
        }
    }

    public void setInstancia(int indice, T objeto) {
        if (indice >= 0 && indice < tamano) {
            Nodo<T> nodoEnIndice = obtenerNodoEnIndice(indice);
            nodoEnIndice.setInstancia(objeto);
        }
    }

    private Nodo<T> obtenerNodoEnIndice(int indice) {
        Nodo<T> nodoActual = inicio;
        int indiceActual = 0;
        while (indiceActual < indice) {
            nodoActual = nodoActual.getSiguiente();
            indiceActual++;
        }
        return nodoActual;
    }

    public int getTamano() {
        return tamano;
    }

}
