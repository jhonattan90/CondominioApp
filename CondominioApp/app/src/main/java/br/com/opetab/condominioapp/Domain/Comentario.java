package br.com.opetab.condominioapp.Domain;

import java.io.Serializable;

public class Comentario implements Serializable {

    public Usuario usuario;
    public String comentario;

    @Override
    public String toString() {
        return "Comentario{" +
                "usuario=" + usuario +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
