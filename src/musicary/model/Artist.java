package musicary.model;

import java.io.Serializable;

public class Artist implements Serializable {

    private int id;
    private String nome;
    private String fotoProfilo;
    private String fotoCopertina;

    public String getNome(){return nome;}
    public String getFotoProfilo(){return fotoProfilo;}
    public String getFotoCopertina(){return fotoCopertina;}
    public void setId(int id) { this.id = id; }

    public void setNome(String nome){this.nome = nome;}
    public void setFotoprofilo(String fotoProfilo){this.fotoProfilo = fotoProfilo;}
    public void setFotoCopertina(String fotoCopertina){this.fotoCopertina = fotoCopertina;}
    public int getId() { return id; }
}
