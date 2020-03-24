package primeirafase;


public class DadosStFinal {


    private Object obj; // podera ser uma empresa ou encontro ou ponto de passagem
    private int idSt;
    private Point localizacao;
    private int idInicial;


    public int getIdInicial() {
        return idInicial;
    }

    public void setIdInicial(int idInicial) {
        this.idInicial = idInicial;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getIdSt() {
        return idSt;
    }

    public void setIdSt(int idSt) {
        this.idSt = idSt;
    }

    public Point getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Point localizacao) {
        this.localizacao = localizacao;
    }



    public DadosStFinal(Object obj, int idSt, Point localizacao, int idInicial) {
        this.obj = obj;
        this.idSt = idSt;
        this.localizacao = localizacao;
        this.idInicial = idInicial;
    }

    @Override
    public String toString() {
        return "DadosStFinal{" +
                "obj=" + obj +
                ", idSt=" + idSt +
                ", localizacao=" + localizacao +
                ", idInicial=" + idInicial +
                '}';
    }
}
