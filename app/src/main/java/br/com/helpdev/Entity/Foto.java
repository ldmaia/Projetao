package br.com.helpdev.Entity;

import android.graphics.Bitmap;

/**
 * Created by Lucas on 11/03/2016.
 */
public class Foto {
    private int id;
    private String nome;
    private Bitmap imagem;

    public Foto(){

    }

    public Foto(String nome, Bitmap imagem){
        this.nome = nome;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}
