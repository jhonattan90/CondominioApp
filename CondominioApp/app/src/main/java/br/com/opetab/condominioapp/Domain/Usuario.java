package br.com.opetab.condominioapp.Domain;

import java.io.Serializable;

import static android.R.attr.id;

public class Usuario implements Serializable {

    public  long id;
    public String nome;
    public  String email;
    public String urlFoto;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                '}';
    }
}
