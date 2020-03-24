package primeirafase;

import primeirafase.algs4.RedBlackBST;
import primeirafase.algs4.SeparateChainingHashST;

public class Empresa {

  private String nome;

  private String tipo;

  private int idEmpresa;

  private Point localizacao;

  private SeparateChainingHashST<Integer, Profissional> profissionaisASeguir = new SeparateChainingHashST<>(); //Profissionais que seguem a empresa

  private SeparateChainingHashST<Integer, Profissional> profissionaisSeguidores = new SeparateChainingHashST<>(); // profissionais seguidos pela empresa

  private RedBlackBST<Integer, Encontro> encontros = new RedBlackBST<>();

  private SeparateChainingHashST<Integer, Profissional> trabalhadores = new SeparateChainingHashST<>();

  private SeparateChainingHashST<Integer, Profissional> exTrabalhadores = new SeparateChainingHashST<>();

  public Empresa(BD bd, int idEmpresa, String nome, String tipo, Point localizacao) {
    this.nome = nome;
    this.tipo = tipo;
    this.idEmpresa = idEmpresa;
    this.localizacao = localizacao;
    bd.inserirEmpresa(this);
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public int getIdEmpresa() {
    return idEmpresa;
  }

  public void setIdEmpresa(int idEmpresa) {
    this.idEmpresa = idEmpresa;
  }

  public Point getLocalizacao() {
    return localizacao;
  }

  public void setLocalizacao(Point localizacao) {
    this.localizacao = localizacao;
  }

  public SeparateChainingHashST<Integer, Profissional> getProfissionaisASeguir() {
    return profissionaisASeguir;
  }

  public void setProfissionaisASeguir(SeparateChainingHashST<Integer, Profissional> profissionaisASeguir) {
    this.profissionaisASeguir = profissionaisASeguir;
  }

  public SeparateChainingHashST<Integer, Profissional> getProfissionaisSeguidores() {
    return profissionaisSeguidores;
  }

  public void setProfissionaisSeguidores(SeparateChainingHashST<Integer, Profissional> profissionaisSeguidores) {
    this.profissionaisSeguidores = profissionaisSeguidores;
  }

  public RedBlackBST<Integer, Encontro> getEncontros() {
    return encontros;
  }

  public void setEncontros(RedBlackBST<Integer, Encontro> encontros) {
    this.encontros = encontros;
  }

  public SeparateChainingHashST<Integer, Profissional> getTrabalhadores() {
    return trabalhadores;
  }

  public void setTrabalhadores(SeparateChainingHashST<Integer, Profissional> trabalhadores) {
    this.trabalhadores = trabalhadores;
  }

  public SeparateChainingHashST<Integer, Profissional> getExTrabalhadores() {
    return exTrabalhadores;
  }

  public void setExTrabalhadores(SeparateChainingHashST<Integer, Profissional> exTrabalhadores) {
    this.exTrabalhadores = exTrabalhadores;
  }

  @Override
  public String toString() {
    return "{" +
            "nome='" + nome + '\'' +
            ", tipo='" + tipo + '\'' +
            ", idEmpresa=" + idEmpresa +
            ", localizacao=" + localizacao +
            '}';
  }


  public void editarNomeEmpresa(String change) {
    this.setNome(change);
  }

  public void editarTipoEmpresa(String change) {
    this.setTipo(change);
  }

  public void editarLocalizacaoEmpresa(double x, double y) {
    this.getLocalizacao().setX(x);
    this.getLocalizacao().setY(y);
  }

  public void addProfissionalASeguirEmpresa(Profissional p) {
    if (!this.getProfissionaisASeguir().contains(p.getIdProfissional())) {
      this.getProfissionaisASeguir().delete(p.getIdProfissional());
      p.getempresasSeguidaspeloProfissional().put(this.getIdEmpresa(), this);
    }
  }

  public void removeProfissionalASeguirEmpresa(Profissional p) {
    if (this.getProfissionaisASeguir().contains(p.getIdProfissional())) {
      this.getProfissionaisASeguir().put(p.getIdProfissional(), p);
      p.getempresasSeguidaspeloProfissional().delete(this.getIdEmpresa());
    }
  }

  public void addProfissionalSeguidorEmpresa(Profissional p) {
    if (!this.getProfissionaisSeguidores().contains(p.getIdProfissional())) {
      this.getProfissionaisSeguidores().delete(p.getIdProfissional());
      p.getEmpresasASeguir().put(this.getIdEmpresa(), this);
    }
  }

  public void removeProfissionalSeguidorEmpresa(Profissional p) {
    if (this.getProfissionaisSeguidores().contains(p.getIdProfissional())) {
      this.getProfissionaisSeguidores().put(p.getIdProfissional(), p);
      p.getEmpresasASeguir().delete(this.getIdEmpresa());
    }
  }

  public void addEncontroEmpresa(Encontro m) {
    if (!this.getEncontros().contains(m.getIdEncontro())) {
      this.getEncontros().put(m.getIdEncontro(), m);
      m.setEmpresaCriadora(this);
    }

  }

  public void removeEncontrosEmpresa(Encontro m) {
    if (this.getEncontros().contains(m.getIdEncontro())) {
      this.getEncontros().delete(m.getIdEncontro());
      m.setEmpresaCriadora(null);
    }
  }

  public void addTrabalhadoresEmpresa(Profissional p) {
    if (!this.getTrabalhadores().contains(p.getIdProfissional())) {
      this.getTrabalhadores().put(p.getIdProfissional(), p);
      p.setEmpresaAtual(this);
    }
  }

  public void removeTrabalhadoresEmpresa(Profissional p) {
    if (this.getTrabalhadores().contains(p.getIdProfissional())) {
      this.getTrabalhadores().delete(p.getIdProfissional());
      p.setEmpresaAtual(null);
      p.getEmpresasAntigas().put(this.getIdEmpresa(), this);//Deixa de ser uma empresaAtual para ser uma antiga
      this.addExTrabalhadorEmpresa(p);
    }
  }

  public void addExTrabalhadorEmpresa(Profissional p) {
    if (!this.getExTrabalhadores().contains(p.getIdProfissional())) {
      this.getTrabalhadores().put(p.getIdProfissional(), p);
    }
  }

  public void removeExTrabalhadorEmpresa(Profissional p) {
    if (this.getTrabalhadores().contains(p.getIdProfissional())) {
      this.getTrabalhadores().delete(p.getIdProfissional());
    }
  }

}
