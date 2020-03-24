package primeirafase;

import primeirafase.algs4.SeparateChainingHashST;

public class Competencia {

    private String tipo;

    private int experiencia;

    private int idCompetencia;

    private Data dataDeAquisicao;

    private SeparateChainingHashST<Integer, Profissional> profissionaisAtribuidos = new SeparateChainingHashST<>();

    public Competencia(BD bd, int idCompetencia, String tipo, int experiencia, Data dataDeAquisicao) {
        this.idCompetencia = idCompetencia;
        this.tipo = tipo;
        this.experiencia = experiencia;
        this.dataDeAquisicao = dataDeAquisicao;
        bd.inserirCompetencia(this);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Data getDataDeAquisicao() {
        return dataDeAquisicao;
    }

    public void setDataDeAquisicao(Data dataDeAquisicao) {
        this.dataDeAquisicao = dataDeAquisicao;
    }

    public int getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(int idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public SeparateChainingHashST<Integer, Profissional> getProfissionaisAtribuidos() {
        return profissionaisAtribuidos;
    }

    public void setProfissionaisAtribuidos(SeparateChainingHashST<Integer, Profissional> profissionaisAtribuidos) {
        this.profissionaisAtribuidos = profissionaisAtribuidos;
    }

    @Override
    public String toString() {
        return "{" +
                "tipo='" + tipo + '\'' +
                ", experiencia=" + experiencia +
                ", idCompetencia=" + idCompetencia +
                ", dataDeAquisicao=" + dataDeAquisicao +
                '}';
    }

    public void editarTipoCompetencia(String change) {
        this.setTipo(change);
    }

    public void editarExperienciaCompetencia(int change) {
        this.setExperiencia(change);
    }

    public void editarDataAquisicaoCompetencia(int dia, int mes, int ano, int hora, int minuto) {
        Data d = new Data(dia, mes, ano, hora, minuto);
        this.setDataDeAquisicao(d);
    }

    public void addProfissionalAtribuidoCompetencias(Profissional p) {
        if (!this.getProfissionaisAtribuidos().contains(p.getIdProfissional())) {
            this.getProfissionaisAtribuidos().put(p.getIdProfissional(), p);
            p.getCompetencias().put(this.getIdCompetencia(), this);
        }
    }

    public void removeProfissionalAtribuidoCompetencias(Profissional p) {
        if (this.getProfissionaisAtribuidos().contains(p.getIdProfissional())) {
            this.getProfissionaisAtribuidos().delete(p.getIdProfissional());
            p.getCompetencias().delete(this.getIdCompetencia());
        }
    }
}