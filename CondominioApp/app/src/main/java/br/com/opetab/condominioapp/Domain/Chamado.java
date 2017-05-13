package br.com.opetab.condominioapp.Domain;

import java.io.Serializable;


public class Chamado implements Serializable {

    public static final String KEY = "chamado";
    public long id;
    public String titulo;
    public String descricao;
    public String situacao;
    public Usuario usuario;

    @Override
    public String toString() {
        return "Chamado{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", situacao='" + situacao + '\'' +
                ", usuario=" + usuario.toString() +
                '}';
    }
}
