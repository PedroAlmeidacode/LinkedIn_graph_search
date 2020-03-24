package primeirafase;

import primeirafase.algs4.SeparateChainingHashST;

import java.util.ArrayList;

public class Profissional {

  private String primeiroNome;

  private String ultimoNome;

  private String email;

  private ArrayList<String> areasDeInteresse = new ArrayList<>();

  private int idProfissional;

  private Empresa empresaAtual;

  private Point localizacao;

  private SeparateChainingHashST<Integer, Competencia> competencias = new SeparateChainingHashST<>();

  private SeparateChainingHashST<Integer, Encontro> encontrosEmpresa = new SeparateChainingHashST<>();

  private SeparateChainingHashST<Integer, Empresa> empresasAntigas = new SeparateChainingHashST<>();

  private SeparateChainingHashST<Integer, Empresa> empresasASeguir = new SeparateChainingHashST<>(); // empresas que seguem o profissional

  private SeparateChainingHashST<Integer, Empresa> empresasSeguidaspeloProfissional = new SeparateChainingHashST<>(); // empresas que o profissional segue

  public Profissional(BD bd, int idProfissional, String primeiroNome, String ultimoNome, String email, Empresa empresaAtual, Point localizacao) {
    this.primeiroNome = primeiroNome;
    this.ultimoNome = ultimoNome;
    this.email = email;
    this.idProfissional = idProfissional;
    this.empresaAtual = empresaAtual;
    this.localizacao = localizacao;
    if (empresaAtual != null)
      empresaAtual.getTrabalhadores().put(this.getIdProfissional(), this);//add o trabalhador na empresaAtual
    bd.inserirProfissional(this);
  }

  public String getPrimeiroNome() {
    return primeiroNome;
  }

  public void setPrimeiroNome(String primeiroNome) {
    this.primeiroNome = primeiroNome;
  }

  public String getUltimoNome() {
    return ultimoNome;
  }

  public void setUltimoNome(String ultimoNome) {
    this.ultimoNome = ultimoNome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ArrayList<String> getAreasDeInteresse() {
    return areasDeInteresse;
  }

  public void setAreasDeInteresse(ArrayList<String> areasDeInteresse) {
    this.areasDeInteresse = areasDeInteresse;
  }

  public int getIdProfissional() {
    return idProfissional;
  }

  public void setIdProfissional(int idProfissional) {
    this.idProfissional = idProfissional;
  }

  public Empresa getEmpresaAtual() {
    return empresaAtual;
  }

  public void setEmpresaAtual(Empresa empresaAtual) {
    this.empresaAtual = empresaAtual;
  }

  public Point getLocalizacao() {
    return localizacao;
  }

  public void setLocalizacao(Point localizacao) {
    this.localizacao = localizacao;
  }

  public SeparateChainingHashST<Integer, Competencia> getCompetencias() {
    return competencias;
  }

  public void setCompetencias(SeparateChainingHashST<Integer, Competencia> competencias) {
    this.competencias = competencias;
  }

  public SeparateChainingHashST<Integer, Encontro> getEncontrosEmpresa() {
    return encontrosEmpresa;
  }

  public void setEncontrosEmpresa(SeparateChainingHashST<Integer, Encontro> encontrosEmpresa) {
    this.encontrosEmpresa = encontrosEmpresa;
  }

  public SeparateChainingHashST<Integer, Empresa> getEmpresasAntigas() {
    return empresasAntigas;
  }

  public void setEmpresasAntigas(SeparateChainingHashST<Integer, Empresa> empresasAntigas) {
    this.empresasAntigas = empresasAntigas;
  }

  public SeparateChainingHashST<Integer, Empresa> getEmpresasASeguir() {
    return empresasASeguir;
  }

  public void setEmpresasASeguir(SeparateChainingHashST<Integer, Empresa> empresasASeguir) {
    this.empresasASeguir = empresasASeguir;
  }

  public SeparateChainingHashST<Integer, Empresa> getempresasSeguidaspeloProfissional() {
    return empresasSeguidaspeloProfissional;
  }

  public void setEmpresasSeguidoras(SeparateChainingHashST<Integer, Empresa> empresasSeguidoras) {
    this.empresasSeguidaspeloProfissional = empresasSeguidoras;
  }

  @Override
  public String toString() {
    return "{" +
            "primeiroNome='" + primeiroNome + '\'' +
            ", ultimoNome='" + ultimoNome + '\'' +
            ", email='" + email + '\'' +
            ", areasDeInteresse=" + areasDeInteresse +
            ", idProfissional=" + idProfissional +
            ", empresaAtual=" + empresaAtual +
            ", localizacao=" + localizacao + '}';
  }

  public void editarPrimeiroNomeProfissional(String change) {
    this.setPrimeiroNome(change);
  }

  public void editarUltimoNomeProfissional(String change) {
    this.setUltimoNome(change);
  }

  public void editarEmailProfissional(String change) {
    this.setEmail(change);
  }

  public void addAreaDeInteresseProfissional(String s) {
    if (!this.getAreasDeInteresse().contains(s)) {//só add se não existir, evita replicas
      this.getAreasDeInteresse().add(s);
    }
  }

  public void removeAreaDeInteresseProfissional(String s) {
    this.getAreasDeInteresse().remove(s);
  }

  public void editarEmpresaAtualProfissional(Empresa e) {
    this.setEmpresaAtual(e);
    e.getTrabalhadores().put(this.getIdProfissional(), this);
  }

  public void editarLocalizacaoProfissional(double x, double y) {
    this.getLocalizacao().setX(x);
    this.getLocalizacao().setY(y);
  }

  public void addCompetenciaProfissional(Competencia c) {
    if (!this.getCompetencias().contains(c.getIdCompetencia())) {
      this.getCompetencias().put(c.getIdCompetencia(), c);
      c.getProfissionaisAtribuidos().put(this.getIdProfissional(), this);
    }
  }

  public void removeCompetenciaProfissional(Competencia c) {
    if (this.getCompetencias().contains(c.getIdCompetencia())) {
      this.getCompetencias().delete(c.getIdCompetencia());
      c.getProfissionaisAtribuidos().delete(this.getIdProfissional());
    }
  }

  public void addEncontroEmpresaProfissional(Encontro e) {
    if (!this.getEncontrosEmpresa().contains(e.getIdEncontro())) {
      this.getEncontrosEmpresa().put(e.getIdEncontro(), e);
      e.getParticipantes().put(this.getIdProfissional(), this);
      e.setnParticipantes(e.getnParticipantes() + 1);
    }
  }

  public void removeEncontroEmpresaProfissional(Encontro e) {
    if (this.getEncontrosEmpresa().contains(e.getIdEncontro())) {
      this.getEncontrosEmpresa().delete(e.getIdEncontro());
      e.getParticipantes().delete(this.getIdProfissional());
      e.setnParticipantes(e.getnParticipantes() - 1);
    }
  }

  public void addEmpresaAntigaProfissional(Empresa e) {
    if (!this.getEmpresasAntigas().contains(e.getIdEmpresa()))
      this.getEmpresasAntigas().put(e.getIdEmpresa(), e);
  }

  public void removeEmpresaAntigaProfissional(Empresa e) {
    if (this.getEmpresasAntigas().contains(e.getIdEmpresa()))
      this.getEmpresasAntigas().delete(e.getIdEmpresa());
  }

  public void addEmpresaASeguirProfissional(Empresa e) {
    if (!this.getEmpresasASeguir().contains(e.getIdEmpresa())) {
      this.getEmpresasASeguir().put(e.getIdEmpresa(), e);
      e.getProfissionaisSeguidores().put(this.getIdProfissional(), this);
    }
  }

  public void removeEmpresaASeguirProfissional(Empresa e) {
    if (this.getEmpresasASeguir().contains(e.getIdEmpresa())) {
      this.getEmpresasASeguir().delete(e.getIdEmpresa());
      e.getProfissionaisSeguidores().delete(this.getIdProfissional());
    }
  }

  public void addempresasSeguidaspeloProfissional(Empresa e) {
    if (!this.getempresasSeguidaspeloProfissional().contains(e.getIdEmpresa())) {
      this.getempresasSeguidaspeloProfissional().put(e.getIdEmpresa(), e);
      e.getProfissionaisASeguir().put(this.getIdProfissional(), this);
    }
  }

  public void removeEmpresaSeguidoraProfissional(Empresa e) {
    if (this.getempresasSeguidaspeloProfissional().contains(e.getIdEmpresa())) {
      this.getempresasSeguidaspeloProfissional().delete(e.getIdEmpresa());
      e.getProfissionaisASeguir().delete(this.getIdProfissional());
    }
  }
}