package primeirafase;

import primeirafase.algs4.SeparateChainingHashST;

public class Encontro {

    private int nParticipantes;

    private String nome;

    private int idEncontro;

    private Point localizacao;

    private SeparateChainingHashST<Integer, Profissional> participantes = new SeparateChainingHashST<>();

    private Empresa empresaCriadora;

    private Data data;

    private SeparateChainingHashST<Integer, Data> horaChegadaProfissional = new SeparateChainingHashST<>(); // sendo integer o id do profissional



    public SeparateChainingHashST<Integer, Data> getHoraChegadaProfissional() {
        return horaChegadaProfissional;
    }

    public void setHoraChegadaProfissional(SeparateChainingHashST<Integer, Data> horaChegadaProfissional) {
        this.horaChegadaProfissional = horaChegadaProfissional;
    }



    public Encontro(BD bd, int idEncontro, String nome, Point localizacao, Empresa empresaCriadora, Data data) {
        this.nParticipantes = 0; //O encontro é criado com 0 participantes, estes só sao adicionados após a sua criação
        this.nome = nome;
        this.idEncontro = idEncontro;
        this.localizacao = localizacao;
        this.empresaCriadora = empresaCriadora;
        if (empresaCriadora != null)
            empresaCriadora.getEncontros().put(this.getIdEncontro(), this);//add do encontro na empresaCriadora
        this.data = data;
        bd.inserirEncontro(this);
    }

    public int getnParticipantes() {
        return nParticipantes;
    }

    public void setnParticipantes(int nParticipantes) {
        this.nParticipantes = nParticipantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(int idEncontro) {
        this.idEncontro = idEncontro;
    }

    public Point getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Point localizacao) {
        this.localizacao = localizacao;
    }

    public Empresa getEmpresaCriadora() {
        return empresaCriadora;
    }

    public void setEmpresaCriadora(Empresa empresaCriadora) {
        this.empresaCriadora = empresaCriadora;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public SeparateChainingHashST<Integer, Profissional> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(SeparateChainingHashST<Integer, Profissional> participantes) {
        this.participantes = participantes;
    }

    @Override
    public String toString() {
        return "{" +
                "nParticipantes=" + nParticipantes +
                ", nome='" + nome + '\'' +
                ", idEncontro=" + idEncontro +
                ", localizacao=" + localizacao +
                ", empresaCriadora=" + empresaCriadora +
                ", data=" + data +
                '}';
    }

    public void editarNParticipantesEncontro(int change) {//Função sem uso lógico
        this.setnParticipantes(change);
    }

    public void editarNomeEncontro(String change) {
        this.setNome(change);
    }

    public void editarLocalizacaoEncontro(double x, double y) {
        this.getLocalizacao().setX(x);
        this.getLocalizacao().setY(y);
    }

    public void addParticipanteEncontro(Profissional p) {
        if (!this.getParticipantes().contains(p.getIdProfissional())) {
            this.getParticipantes().put(p.getIdProfissional(), p);
            p.getEncontrosEmpresa().put(this.getIdEncontro(), this);
            this.setnParticipantes(getnParticipantes()+1);
        }
    }

    public void removeParticipanteEncontro(Profissional p) {
        if (this.getParticipantes().contains(p.getIdProfissional())) {
            this.getParticipantes().delete(p.getIdProfissional());
            p.getEncontrosEmpresa().delete(this.getIdEncontro());
            this.setnParticipantes(getnParticipantes()-1);
        }
    }

    public void editarEmpresaCriadoraEncontro(Empresa e) {
        this.getEmpresaCriadora().getEncontros().delete(this.getIdEncontro());//Apaga o encontro da empresaCriadora antiga
        this.setEmpresaCriadora(e);
        e.getEncontros().put(this.getIdEncontro(), this);
    }

    public void editarDataEncontro(int dia, int mes, int ano, int hora, int minuto) {
        Data d = new Data(dia, mes, ano, hora, minuto);
        this.setData(d);
    }
}