package primeirafase;

public class PontosP {

    private String nomePontoDePassagem;

    private Point localizacao;

    private int id;

    public PontosP(String nomePontoDePassagem, Point localizacao, int id) {
        this.nomePontoDePassagem = nomePontoDePassagem;
        this.localizacao = localizacao;
        this.id = id;
    }

    public String getNomePontoDePassagem() {
        return nomePontoDePassagem;
    }

    public void setNomePontoDePassagem(String nomePontoDePassagem) {
        this.nomePontoDePassagem = nomePontoDePassagem;
    }

    public Point getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Point localizacao) {
        this.localizacao = localizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "PontosP{" +
                "nomePontoDePassagem='" + nomePontoDePassagem + '\'' +
                ", localizacao=" + localizacao +
                ", id=" + id +
                '}';
    }
}
