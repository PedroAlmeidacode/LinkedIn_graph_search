package primeirafase;

import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
import primeirafase.algs4.In;
import primeirafase.algs4.RedBlackBST;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.System.out;

public class BD {
    private RedBlackBST<Integer, Profissional> profissionais = new RedBlackBST<>();
    private RedBlackBST<Integer, Empresa> empresas = new RedBlackBST<>();
    private RedBlackBST<Integer, Encontro> encontros = new RedBlackBST<>();
    private RedBlackBST<Integer, Competencia> competencias = new RedBlackBST<>();
    private RedBlackBST<Integer, PontosP> pontosDePassagem = new RedBlackBST<>();

    private RedBlackBST<Integer, DadosStFinal> stFinal = new RedBlackBST<>(); // st criada com a juncao da st encontros, empresas e pontos de passagem, de forma que nao haja repeticao de ids


    public RedBlackBST<Integer, DadosStFinal> getStFinal() {
        return stFinal;
    }

    public void setStFinal(RedBlackBST<Integer, DadosStFinal> stFinal) {
        this.stFinal = stFinal;
    }

    public RedBlackBST<Integer, PontosP> getPontosDePassagem() {
        return pontosDePassagem;
    }

    public void setPontosDePassagem(RedBlackBST<Integer, PontosP> pontosDePassagem) {
        this.pontosDePassagem = pontosDePassagem;
    }

    public RedBlackBST<Integer, Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(RedBlackBST<Integer, Profissional> profissionais) {
        this.profissionais = profissionais;
    }

    public RedBlackBST<Integer, Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(RedBlackBST<Integer, Empresa> empresas) {
        this.empresas = empresas;
    }

    public RedBlackBST<Integer, Encontro> getEncontros() {
        return encontros;
    }

    public void setEncontros(RedBlackBST<Integer, Encontro> encontros) {
        this.encontros = encontros;
    }

    public RedBlackBST<Integer, Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(RedBlackBST<Integer, Competencia> competencias) {
        this.competencias = competencias;
    }

    /**
     *
     * Cria a partir das sts encontros, empresas e pontos de passgem, com informaçao, uma st total, com novos ids na sua totalidade, onde é preservada toda a informação das sts
     * @param bd - contem toda a informação
     */
    public void createStForCaminhos(BD bd) {
        int counter = 0;
        for (Integer a : bd.getEmpresas().keys()) {
            DadosStFinal s = new DadosStFinal(bd.getEmpresas().get(a), counter,bd.getEmpresas().get(a).getLocalizacao(), a);
            bd.getStFinal().put(counter, s);
            counter++;

        }
        for (Integer a : bd.getEncontros().keys()) {
            DadosStFinal s = new DadosStFinal(bd.getEncontros().get(a), counter, bd.getEncontros().get(a).getLocalizacao(),a);
            bd.getStFinal().put(counter, s);
            counter++;
        }
        for (Integer a : bd.getPontosDePassagem().keys()) {
            DadosStFinal s = new DadosStFinal(bd.getPontosDePassagem().get(a), counter, bd.getPontosDePassagem().get(a).getLocalizacao(),a);
            bd.getStFinal().put(counter, s);
            counter++;

        }

    }




    /* FICHEIRO PROFISSIONAIS */

    /***
     *
     * retira informaçao do ficheiro profissionais.txt e coloca nas sts da classe bd
     * @param path - path para o ficheiro profissionais.txt no caso deste metodo
     */
    public void loadfromfileprofissionais(String path) {

        In in = new In(path); // abertura do ficheiro/stream de entrada
        in.readLine(); // le a linha, uma de cada vez
        while (!in.isEmpty()) { //enquanto o ficheiro lido ainda tem conteudo
            //devido a escrita ficar com um ",;" em certos sitios onde deveria estar ";" usamos este metodo para simplificar
            String poss1 = ";";
            String poss2 = ",;";
            String texto1 = in.readLine().replace(poss2, poss1);
            String[] texto = texto1.split(";");

            //STRING AREAS DE INTERESSE
            String[] areasdeinteresse = texto[4].split(",");
            ArrayList<String> stringList = new ArrayList<>(Arrays.asList(areasdeinteresse));

            Empresa e = null;
            //EMPRESA ATUAL
            if (!texto[7].equals("null")) {
                Point localempresaatual = new Point(Double.parseDouble(texto[8]), Double.parseDouble(texto[9]));

                e = new Empresa(this, Integer.parseInt(texto[7]), texto[5], texto[6], localempresaatual);
                this.getEmpresas().put(Integer.parseInt(texto[7]), e); //inserir empresa na bst empresas


            }

            //LOCALIZACAO PROFISSIONAL
            Point p = new Point(Double.parseDouble(texto[10]), Double.parseDouble(texto[11]));
            Profissional pp = new Profissional(this, Integer.parseInt(texto[3]), texto[0], texto[1], texto[2], e, p);
            if (!texto[7].equals("null")) {
            this.getEmpresas().get(Integer.parseInt(texto[7])).addTrabalhadoresEmpresa(pp);}


            this.getProfissionais().get(Integer.parseInt(texto[3])).setAreasDeInteresse(stringList);

            if (texto[12] != null && !texto[12].isEmpty()) {
                if (!texto[12].equals("null")) {
                    String[] encontro = texto[12].split(",");

                    for (String s : encontro) { // itera o array criado com o id dos encontros que o profissional tem, introduz toda a informaçao do profissional na st participantes
                        String[] Encontroinf = s.split(":");
                        Data data = new Data(Integer.parseInt(Encontroinf[10]), Integer.parseInt(Encontroinf[11]), Integer.parseInt(Encontroinf[12]), Integer.parseInt(Encontroinf[13]), Integer.parseInt(Encontroinf[14]));
                        Point pointenc = new Point(Double.parseDouble(Encontroinf[3]), Double.parseDouble(Encontroinf[4]));
                        Point pointempenc = new Point(Double.parseDouble(Encontroinf[8]), Double.parseDouble(Encontroinf[9]));
                        Empresa empresaenc = new Empresa(this, Integer.parseInt(Encontroinf[7]), Encontroinf[5], Encontroinf[6], pointempenc);
                        Encontro encontro1 = new Encontro(this, Integer.parseInt(Encontroinf[2]), Encontroinf[1], pointenc, empresaenc, data);
                        this.getEncontros().put(Integer.parseInt(Encontroinf[2]), encontro1);

                        this.getEncontros().get(Integer.parseInt(Encontroinf[2])).addParticipanteEncontro(pp);
                    }
                }
            }
            if (texto[13] != null && !texto[13].isEmpty()) {
                if (!texto[13].equals("null")) {
                    String[] empresaseguidora = texto[13].split(",");

                    for (String f : empresaseguidora) { // itera o array criado com o id das empresas que o profissional é seguido, introduz toda a informaçao do profissional na st pProfissionaisASeguir
                        String[] EmpresaseguidoraInf = f.split(":");
                        Point pointes = new Point(Double.parseDouble(EmpresaseguidoraInf[3]), Double.parseDouble(EmpresaseguidoraInf[4]));
                        Empresa empresaseguidora1 = new Empresa(this, Integer.parseInt(EmpresaseguidoraInf[2]), EmpresaseguidoraInf[0], EmpresaseguidoraInf[1], pointes);
                        this.getEmpresas().put(Integer.parseInt(EmpresaseguidoraInf[2]), empresaseguidora1);

                        this.getEmpresas().get(Integer.parseInt(EmpresaseguidoraInf[2])).addProfissionalSeguidorEmpresa(pp);
                    }
                }
            }
            if (texto[14] != null && !texto[14].isEmpty()) {
                if (!texto[14].equals("null")) {
                    String[] empresaseguir = texto[14].split(",");

                    for (String d : empresaseguir) { // itera o array criado com o id das empresas que o profissional segue, introduz toda a informaçao do profissional na st profissionaisseguidores
                        String[] EmpresaaseguirInf = d.split(":");
                        Point pointeas = new Point(Double.parseDouble(EmpresaaseguirInf[3]), Double.parseDouble(EmpresaaseguirInf[4]));
                        Empresa empresaaseguir1 = new Empresa(this, Integer.parseInt(EmpresaaseguirInf[2]), EmpresaaseguirInf[0], EmpresaaseguirInf[1], pointeas);
                        this.getEmpresas().put(Integer.parseInt(EmpresaaseguirInf[2]), empresaaseguir1);

                        this.getEmpresas().get(Integer.parseInt(EmpresaaseguirInf[2])).addProfissionalASeguirEmpresa(pp);
                    }
                }
            }
            if (texto[15] != null && !texto[15].isEmpty()) {
                if (!texto[15].equals("null")) {
                    String[] empresaantiga = texto[15].split(",");
                    for (String h : empresaantiga) { // itera o array criado com o id das empresas que o profissional ja trabalhou , introduz toda a informaçao do profissional na st pProfissionaisASeguir
                        String[] EmpresaantigaInf = h.split(":");
                        Point pointeantiga = new Point(Double.parseDouble(EmpresaantigaInf[3]), Double.parseDouble(EmpresaantigaInf[4]));
                        Empresa empresaantiga1 = new Empresa(this, Integer.parseInt(EmpresaantigaInf[2]), EmpresaantigaInf[0], EmpresaantigaInf[1], pointeantiga);
                        this.getEmpresas().put(Integer.parseInt(EmpresaantigaInf[2]), empresaantiga1);

                        this.getEmpresas().get(Integer.parseInt(EmpresaantigaInf[2])).addExTrabalhadorEmpresa(pp);
                    }
                }
            }

            //CONSTRUTOR bst PROFISSIONAL from txt
            this.getProfissionais().put(Integer.parseInt(texto[3]), pp);

        }
    }

    /*Estrutura ficheiro profissionais:
    primeironome;ultimonome;email;idprofissional;areasdeinteresse1,,;
    nomemepresa;tipoempresa;idempresa;xempresa;yempresa
    xprofissional;yprofissional;
    nparticenc.encnome.idenco.locencx.locency.nomeempenc.tipoempenc.idempenc.locempencx.locempency.dia.mes.ano.hora.minuto,....,...; --> texto[12]
    nomempseguidora.tipoempseguidora.idempseguidora.locempseguidorax.locempseguidoray,,....,..; --> texto[13]
    nomempaseguir.tipoempaseguir.idempaseguir.locempaseguirx.locempaseguiry....,,.;  --> texto[14]
    nomempantiga.tipoempantiga.idempantiga.locempantigax.locempantigay,..,\n    --> texto[15] */

    /**
     * retira das bst a inforaçao e coloca no ficheiro sob o formato
     * Estrutura ficheiro profissionais:
     *     primeironome;ultimonome;email;idprofissional;areasdeinteresse1,,;
     *     nomemepresa;tipoempresa;idempresa;xempresa;yempresa
     *     xprofissional;yprofissional;
     *     nparticenc.encnome.idenco.locencx.locency.nomeempenc.tipoempenc.idempenc.locempencx.locempency.dia.mes.ano.hora.minuto,....,...; --> texto[12]
     *     nomempseguidora.tipoempseguidora.idempseguidora.locempseguidorax.locempseguidoray,,....,..; --> texto[13]
     *     nomempaseguir.tipoempaseguir.idempaseguir.locempaseguirx.locempaseguiry....,,.;  --> texto[14]
     *     nomempantiga.tipoempantiga.idempantiga.locempantigax.locempantigay,..,\n    --> texto[15]
     * @param bd   - classe bd que contem como atribuos 4 bsts - profissionais, empresas, competencias, encontros
     * @param path - path para o ficheiro profissionais.txt para onde será enviada informação
     * @throws IOException
     */
    public void loadfromprofissionaisBSTtofile(BD bd, String path) throws IOException {
        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);


        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos))) {
            out.write("primeironome;ultimonome;email;idprofissional;areasdeinteresse1,,;nomemepresa;tipoempresa;idempresa;xempresa;yempresa;xprofissional;yprofissional;" +
                    "nparticenc:encnome:idenco:locencx:locency:nomeempenc:tipoempenc:idempenc:locempencx:locempency:dia:mes:ano:hora:minuto,....,...;" +
                    "nomempseguidora:tipoempseguidora:idempseguidora:locempseguidorax:locempseguidoray,,....,..;" +
                    "nomempaseguir:tipoempaseguir:idempaseguir:locempaseguirx:locempaseguiry....,,.;" +
                    "nomempantiga:tipoempantiga:idempantiga:locempantigax:locempantigay,..,;");
            out.newLine();
            for (Integer s : bd.profissionais.keys()) {

                out.write(bd.profissionais.get(s).getPrimeiroNome() + ";"
                        + bd.profissionais.get(s).getUltimoNome() + ";"
                        + bd.profissionais.get(s).getEmail() + ";"
                        + bd.profissionais.get(s).getIdProfissional() + ";");
                for (String t : bd.profissionais.get(s).getAreasDeInteresse()) { // itera todas as areas de interesse e imprime as separadas por virgula
                    out.write(t + ",");
                }
                if(bd.profissionais.get(s).getEmpresaAtual() != null) {
                    out.write(";" + bd.profissionais.get(s).getEmpresaAtual().getNome() + ";"
                            + bd.profissionais.get(s).getEmpresaAtual().getTipo() + ";"
                            + bd.profissionais.get(s).getEmpresaAtual().getIdEmpresa() + ";"
                            + bd.profissionais.get(s).getEmpresaAtual().getLocalizacao().getX() + ";"
                            + bd.profissionais.get(s).getEmpresaAtual().getLocalizacao().getY() + ";");
                }else{out.write(";null;null;null;null;null;");}

                out.write( bd.profissionais.get(s).getLocalizacao().getX() + ";"
                + bd.getProfissionais().get(s).getLocalizacao().getY() + ";");

                if (bd.profissionais.get(s).getEncontrosEmpresa().isEmpty()) {
                    out.write("null");

                } else {
                    /*Encontros do profissional */
                    for (int a : bd.profissionais.get(s).getEncontrosEmpresa().keys()) { // itera todos os encontros que o profissional tem e imprime toda  a informaçao necxesaria para criar esse encontro
                        out.write(bd.profissionais.get(s).getEncontrosEmpresa().get(a).getnParticipantes() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getNome() + ":"  //Integer.parseint
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getIdEncontro() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getLocalizacao().getX() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getLocalizacao().getY() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getEmpresaCriadora().getNome() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getEmpresaCriadora().getTipo() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getEmpresaCriadora().getIdEmpresa() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getEmpresaCriadora().getLocalizacao().getX() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getEmpresaCriadora().getLocalizacao().getY() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getData().getDia() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getData().getMes() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getData().getAno() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getData().getHora() + ":"
                                + bd.profissionais.get(s).getEncontrosEmpresa().get(a).getData().getMinuto() + ",");
                    }
                }
                out.write(";");
                if (bd.profissionais.get(s).getempresasSeguidaspeloProfissional().isEmpty()) {
                    out.write("null");

                } else {
                    for (Integer e : bd.profissionais.get(s).getempresasSeguidaspeloProfissional().keys()) { // itera todas a empresas qeu o profissional é seguidp e imprime toda a informação necessaria para criar essa empresa
                        out.write(bd.profissionais.get(s).getempresasSeguidaspeloProfissional().get(e).getNome() + ":"
                                + bd.profissionais.get(s).getempresasSeguidaspeloProfissional().get(e).getTipo() + ":"
                                + bd.profissionais.get(s).getempresasSeguidaspeloProfissional().get(e).getIdEmpresa() + ":"
                                + bd.profissionais.get(s).getempresasSeguidaspeloProfissional().get(e).getLocalizacao().getX() + ":"
                                + bd.profissionais.get(s).getempresasSeguidaspeloProfissional().get(e).getLocalizacao().getY() + ":");
                    }
                }
                out.write(";");
                if (bd.profissionais.get(s).getEmpresasASeguir().isEmpty()) {
                    out.write("null");

                } else {
                    /* Empresa a seguir */
                    for (Integer f : bd.profissionais.get(s).getEmpresasASeguir().keys()) { // itera todas a empresas qeu o profissional segue e imprimee toda  informação necessaria para criar essa empresa
                        out.write(bd.profissionais.get(s).getEmpresasASeguir().get(f).getNome() + ":"
                                + bd.profissionais.get(s).getEmpresasASeguir().get(f).getTipo() + ":"
                                + bd.profissionais.get(s).getEmpresasASeguir().get(f).getIdEmpresa() + ":"
                                + bd.profissionais.get(s).getEmpresasASeguir().get(f).getLocalizacao().getX() + ":"
                                + bd.profissionais.get(s).getEmpresasASeguir().get(f).getLocalizacao().getY() + ",");

                    }
                }
                out.write(";");

                if (bd.profissionais.get(s).getEmpresasAntigas().isEmpty()) {
                    out.write("null");

                } else {
                    /* Empresa antiga */
                    for (Integer f : bd.profissionais.get(s).getEmpresasAntigas().keys()) { // itera todas a empresas qeu o profissional trabalhou e imrpime o seu id
                        out.write(bd.profissionais.get(s).getEmpresasAntigas().get(f).getNome() + ":"
                                + bd.profissionais.get(s).getEmpresasAntigas().get(f).getTipo() + ":"
                                + bd.profissionais.get(s).getEmpresasAntigas().get(f).getIdEmpresa() + ":"
                                + bd.profissionais.get(s).getEmpresasAntigas().get(f).getLocalizacao().getX() + ":"
                                + bd.profissionais.get(s).getEmpresasAntigas().get(f).getLocalizacao().getY() + ",");
                    }
                }
                out.write(";");
                out.newLine();
            }

        }
        out.close();


    }
    /* FICHEIRO EMPRESAS */

    /**
     * retira informaçao do ficheiro empresas.txt e coloca nas sts da classe bd
     * @param path - path para o ficheiro empresas.txt, de onde será retirada a informação
     */
    public void loadfromfileempresas(String path) {
        In in = new In(path); // abertura do ficheiro/stream de entrada
        in.readLine();


        while (!in.isEmpty()) {
            //devido a escrita ficar com um ",;" em certos sitios onde deveria estar ";" usamos este metodo para simplificar
            String poss1 = ";";
            String poss2 = ",;";
            String texto1 = in.readLine().replace(poss2, poss1);
            String[] texto = texto1.split(";");
            Empresa ee ;
            if(!this.getEmpresas().contains(Integer.parseInt(texto[2]))) {
                Point p = new Point(Double.parseDouble(texto[3]), Double.parseDouble(texto[4]));

                ee = new Empresa(this, Integer.parseInt(texto[2]), texto[0], texto[1], p);

            }else {

            ee = this.getEmpresas().get(Integer.parseInt(texto[2]));}

            if (texto[5] != null && !texto[5].isEmpty()) {
                if (!texto[5].equals("null")) {
                    String[] idprofissionalextrabalhador = texto[5].split(",");
                    for (String a : idprofissionalextrabalhador) {  // ciclo que itera a string que contem os ids de profissionais que ja trabalharam na respetiva empresa, e
                        this.getProfissionais().get(Integer.parseInt(a)).getEmpresasAntigas().put(Integer.parseInt(texto[2]), ee);
                    }

                }
            }

            if (texto[6] != null && !texto[6].isEmpty()) {
                if (!texto[6].equals("null")) {
                    String[] idprofissionalseguidor = texto[6].split(",");
                    for (String b : idprofissionalseguidor) {    // ciclo que itera a string que contem os ids de profissionais que seguem a respetiva empresa, e
                        this.getProfissionais().get(Integer.parseInt(b)).getEmpresasASeguir().put(Integer.parseInt(texto[2]), ee);
                    }
                }
            }


            if (texto[7] != null && !texto[7].isEmpty()) {
                if (!texto[7].equals("null")) {
                    String[] idprofissionalseguido = texto[7].split(",");
                    for (String c : idprofissionalseguido) {     // ciclo que itera a string que contem os ids de profissionais seguidos pela respetiva empresa,
                        this.getProfissionais().get(Integer.parseInt(c)).getempresasSeguidaspeloProfissional().put(Integer.parseInt(texto[2]), ee);
                    }
                }
            }


        }

    }

//formato do ficheiro empresas.txt- nomeempre;tipoempres;idem;xemp;yemp;idprofissionalextrabalhador,...;idprofessionalseguidor,...,...;professionaisseguidos,..,..\n

    /**
     * retira das bst a inforaçao e coloca no ficheiro sob o formato
     * nomeempre;tipoempres;idem;xemp;yemp;idprofissionalextrabalhador,...;idprofessionalseguidor,...,...;professionaisseguidos,..,..\n
     * @param bd   - classe bd que contem como atribuos 4 bsts - profissionais, empresas, competencias, encontros
     * @param path - path para o file emoresas.txt para onde sera enviada a infromação retirada da bst empresas
     * @throws IOException exeçao
     */
    public void loadfromempresasBSTtofile(BD bd, String path) throws IOException {

        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos))) {
            out.write("nomeempre;tipoempres;idem;xemp;yemp;idprofissionalextrabalhador,...;idprofessionalseguidor,...,...;professionaisseguidos,..,..;");
            out.newLine();
            for (Integer s : bd.empresas.keys()) {
                out.write(bd.empresas.get(s).getNome() + ";"
                        + bd.empresas.get(s).getTipo() + ";"
                        + bd.empresas.get(s).getIdEmpresa() + ";"
                        + bd.empresas.get(s).getLocalizacao().getX() + ";"
                        + bd.empresas.get(s).getLocalizacao().getY() + ";");

                if (bd.empresas.get(s).getExTrabalhadores().isEmpty()) {
                    out.write("null");

                } else {
                    for (Integer b : bd.empresas.get(s).getExTrabalhadores().keys()) { // itera todos os profssionais e imprime o id daqueles que foram trabalhadores antigos da empresa
                        out.write(bd.empresas.get(s).getExTrabalhadores().get(b).getIdProfissional() + ",");

                    }
                }
                out.write(";");
                if (bd.empresas.get(s).getProfissionaisSeguidores().isEmpty()) {
                    out.write("null");

                } else {
                    for (Integer b : bd.empresas.get(s).getProfissionaisSeguidores().keys()) { // itera todos os profissionais e imprime o id daqueles que segueem a empresa
                        out.write(bd.empresas.get(s).getProfissionaisSeguidores().get(b).getIdProfissional() + ",");

                    }
                }
                out.write(";");
                if (bd.empresas.get(s).getProfissionaisASeguir().isEmpty()) {
                    out.write("null");

                } else {
                    for (Integer a : bd.empresas.get(s).getProfissionaisASeguir().keys()) { //itera todos os profissionais e imprime o id daqueles que sao seguidos pela empresa em questao
                        out.write(bd.empresas.get(s).getProfissionaisASeguir().get(a).getIdProfissional() + ",");

                    }
                }
                out.write(";");
                out.newLine();
            }


        }
        out.close();


    }

    /* FICHEIRO ENCONTROS */


    /**
     * retira informaçao do ficheiro encontros.txt e coloca nas sts da classe bd
     * @param path - path para o file encontros.txt de onde será retirada a informação de encontros
     */
    public void loadfromfilencontros(String path) {

        In in = new In(path); // abertura do ficheiro/stream de entrada
        in.readLine();
        while (!in.isEmpty()) {
            //devido a escrita ficar com um ",;" em certos sitios onde deveria estar ";" usamos este metodo para simplificar
            String poss1 = ";";
            String poss2 = ",;";
            String texto1 = in.readLine().replace(poss2, poss1);
            String[] texto = texto1.split(";");

            Point p = new Point(Double.parseDouble(texto[3]), Double.parseDouble(texto[4]));

            //EMPRESA CRIADORA
            Point localempresac = new Point(Double.parseDouble(texto[8]), Double.parseDouble(texto[9]));
            Empresa eee;
            if (!this.getEmpresas().contains(Integer.parseInt(texto[7]))){
                eee = new Empresa(this, Integer.parseInt(texto[7]), texto[5], texto[6], localempresac);
                this.getEmpresas().put(Integer.parseInt(texto[7]), eee); //inserir empresa na bst empresas


            }else{

                eee= this.getEmpresas().get(Integer.parseInt(texto[7]));
            }



            String[] d = texto[10].split("/");
            Data data = new Data(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(d[3]), Integer.parseInt(d[4]));


            Encontro enc;
            if (!this.getEncontros().contains(Integer.parseInt(texto[2]))){
                enc= new Encontro(this, Integer.parseInt(texto[2]), texto[1], p, eee, data);
                this.getEncontros().put(Integer.parseInt(texto[2]), enc);
                this.getEmpresas().get(Integer.parseInt(texto[7])).getEncontros().put(Integer.parseInt(texto[2]), enc);
                if (texto[11] != null && !texto[11].isEmpty()) {
                    if (!texto[11].equals("null")) {
                        String[] idprosfissional = texto[11].split(",");
                        for (String b : idprosfissional) {  // itera a string que contem os ids de profissionais que estao incluidos no respetivo encontro
                            this.getProfissionais().get(Integer.parseInt(b)).getEncontrosEmpresa().put(Integer.parseInt(texto[2]), enc);

                        }
                    }
                }

            }
        }
    }
//formato do ficheiro encontros.txt - nparticipantes;nome;idencontro;xenc;yenc;nomemepcriadora;tipoec;idec;xec;yec;dia/mes/ano/hora/minuto
// ;idprofissionais,...,\n

    /**
     * retira das bst a inforaçao e coloca no ficheiro sob o formato
     * nparticipantes;nome;idencontro;xenc;yenc;nomemepcriadora;tipoec;idec;xec;yec;dia/mes/ano/hora/minuto;idprofissionais,...,\n
     * @param bd   - classe bd que contem como atribuos 4 bsts - profissionais, empresas, competencias, encontros
     * @param path - path para o file encontros.txt para onde será enviada a informação retirada da bst encontros
     * @throws IOException
     */
    public void loadfromencontrosBSTtofile(BD bd, String path) throws IOException {
        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos))) {
            out.write("nparticipantes;nome;idencontro;xenc;yenc;nomemepcriadora;tipoec;idec;xec;yec;dia/mes/ano/hora/minuto;idprofissionais,...,;");
            out.newLine();

            for (Integer s : bd.encontros.keys()) {

                out.write(bd.encontros.get(s).getnParticipantes() + ";"
                        + bd.encontros.get(s).getNome() + ";"
                        + bd.encontros.get(s).getIdEncontro() + ";"
                        + bd.encontros.get(s).getLocalizacao().getX() + ";"
                        + bd.encontros.get(s).getLocalizacao().getY() + ";"
                        + bd.encontros.get(s).getEmpresaCriadora().getNome() + ";"
                        + bd.encontros.get(s).getEmpresaCriadora().getTipo() + ";"
                        + bd.encontros.get(s).getEmpresaCriadora().getIdEmpresa() + ";"
                        + bd.encontros.get(s).getEmpresaCriadora().getLocalizacao().getX() + ";"
                        + bd.encontros.get(s).getEmpresaCriadora().getLocalizacao().getY() + ";"
                        + bd.encontros.get(s).getData().getAno() + "/"
                        + bd.encontros.get(s).getData().getMes() + "/"
                        + bd.encontros.get(s).getData().getDia() + "/"
                        + bd.encontros.get(s).getData().getHora() + "/"
                        + bd.encontros.get(s).getData().getMinuto() + ";");

                if (bd.encontros.get(s).getParticipantes().isEmpty()) {
                    out.write("null");

                } else {
                    for (Integer c : bd.encontros.get(s).getParticipantes().keys()) {  // iteração para imprimir para o file os ids dos profissionais que participam no respetivo encontro
                        out.write(bd.encontros.get(s).getParticipantes().get(c).getIdProfissional() + ",");
                    }
                }
                out.write(";");
                out.newLine();
            }
        }
        out.close();
    }

    /*   FICHEIRO COMPETENCIA */

    /**
     * retira informaçao do ficheiro competencias.txt e coloca nas sts da classe bd
     * @param path - path para o file competencias.txt de onde será retirada a informação para a bst competencias
     */
    public void loadfromfilecompetencias(String path) {
        In in = new In(path); // abertura do ficheiro/stream de entrada
        in.readLine();
        while (!in.isEmpty()) {
            //devido a escrita ficar com um ",;" em certos sitios onde deveria estar ";" usamos este metodo para simplificar
            String poss1 = ";";
            String poss2 = ",;";
            String texto1 = in.readLine().replace(poss2, poss1);
            String[] texto = texto1.split(";");

            String[] d = texto[3].split("/");
            Data data = new Data(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(d[3]), Integer.parseInt(d[4]));

            Competencia competencia ;
            if(!this.getCompetencias().contains(Integer.parseInt(texto[2]))) {


                competencia = new Competencia(this, Integer.parseInt(texto[2]), texto[0], Integer.parseInt(texto[1]), data);
            }else {

                competencia = this.getCompetencias().get(Integer.parseInt(texto[2]));
            }


            if (texto[4] != null && !texto[4].isEmpty()) {
                if (!texto[4].equals("null")) {
                    String[] idprofissional = texto[4].split(",");
                    for (String a : idprofissional) { // percorre a string que contem o id dos profissionais que contem a respetiva competencia
                        this.getProfissionais().get(Integer.parseInt(a)).getCompetencias().put(Integer.parseInt(texto[2]), competencia);
                    }
                }
            }
            this.getCompetencias().put(Integer.parseInt(texto[2]), competencia);
        }
    }
    //estrutura do ficheiro competencias.txt- tipo;experiencia;idcompetencias;dia/mes/ano/hora/minuto;idprofissional,..,...;\n

    /**
     * retira das bst a inforaçao e coloca no ficheiro sob o formato
     * tipo;experiencia;idcompetencias;dia/mes/ano/hora/minuto;idprofissional,..,...;\n
     * @param bd   - classe bd que contem como atribuos 4 bsts - profissionais, empresas, competencias, encontros
     * @param path -path para o file competencias.txt para onde sera enviada a infromação retirada da bst competencias
     * @throws IOException
     */
    public void loadfromcompetenciasBSTtofile(BD bd, String path) throws IOException {
        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos))) {
            out.write("tipo;experiencia;idcompetencias;dia/mes/ano/hora/minuto;idprofissional,..,...;");
            out.newLine();

            for (Integer s : bd.competencias.keys()) {
                out.write(bd.competencias.get(s).getTipo() + ";"
                        + bd.competencias.get(s).getExperiencia() + ";"
                        + bd.competencias.get(s).getIdCompetencia() + ";"
                        + bd.competencias.get(s).getDataDeAquisicao().getAno() + "/"
                        + bd.competencias.get(s).getDataDeAquisicao().getMes() + "/"
                        + bd.competencias.get(s).getDataDeAquisicao().getDia() + "/"
                        + bd.competencias.get(s).getDataDeAquisicao().getHora() + "/"
                        + bd.competencias.get(s).getDataDeAquisicao().getMinuto() + ";");
                if (bd.competencias.get(s).getProfissionaisAtribuidos().isEmpty()) {
                    out.write("null");

                } else {
                    for (Integer c : bd.competencias.get(s).getProfissionaisAtribuidos().keys()) { // iteracao para imprimir para o file os ids dos profissionais que contem esta competencia
                        out.write(bd.competencias.get(s).getProfissionaisAtribuidos().get(c).getIdProfissional() + ",");//Integer.parseint
                    }
                    out.write(";");
                    out.newLine();
                }
            }
        }
        out.close();

    }

 /*
    PROFISSIONAIS
     */


    public void inserirProfissional(Profissional p) {
        this.profissionais.put(p.getIdProfissional(), p);
    }

    public void removerProfissional(Profissional p) {
        //class profissionais
        this.profissionais.delete(p.getIdProfissional());
        //class empresas
        for (int j : this.empresas.keys()) {
            //trabalhadores
            if (this.empresas.get(j).getTrabalhadores().contains(p.getIdProfissional()))
                this.empresas.get(j).getTrabalhadores().delete(p.getIdProfissional());
            //ProfissionaisASeguir
            if (this.empresas.get(j).getProfissionaisASeguir().contains(p.getIdProfissional()))
                this.empresas.get(j).getProfissionaisASeguir().delete(p.getIdProfissional());
            //ProfissionaisSeguidores
            if (this.empresas.get(j).getProfissionaisSeguidores().contains(p.getIdProfissional()))
                this.empresas.get(j).getProfissionaisSeguidores().delete(p.getIdProfissional());
            //ExTrabalhadores
            if (this.empresas.get(j).getExTrabalhadores().contains(p.getIdProfissional()))
                this.empresas.get(j).getExTrabalhadores().delete(p.getIdProfissional());

        }
        //class encontros
        for (int j : this.encontros.keys()) {
            if (this.encontros.get(j).getParticipantes().contains(p.getIdProfissional())) {
                this.encontros.get(j).getParticipantes().delete(p.getIdProfissional());
                this.encontros.get(j).setnParticipantes(this.encontros.get(j).getnParticipantes() - 1);
            }
        }
        //class competencias
        for (int j : this.competencias.keys()) {
            if (this.competencias.get(j).getProfissionaisAtribuidos().contains(p.getIdProfissional()))
                this.competencias.get(j).getProfissionaisAtribuidos().delete(p.getIdProfissional());
        }
    }


    public void listarProfissionais() {
        out.println("PROFISSIONAIS(Quantidade:" + this.getProfissionais().size() + ")");
        for (int i : this.getProfissionais().keys()) {
            out.println(this.getProfissionais().get(i));
            out.println("Competencias");
            for (int j : this.getProfissionais().get(i).getCompetencias().keys())
                out.println(this.getProfissionais().get(i).getCompetencias().get(j));

            out.println("EncontrosEmpresa");
            for (int j : this.getProfissionais().get(i).getEncontrosEmpresa().keys())
                out.println(this.getProfissionais().get(i).getEncontrosEmpresa().get(j));

            out.println("EmpresasAntigas");
            for (int j : this.getProfissionais().get(i).getEmpresasAntigas().keys())
                out.println(this.getProfissionais().get(i).getEmpresasAntigas().get(j));

            out.println("EmpresasASeguir");
            for (int j : this.getProfissionais().get(i).getempresasSeguidaspeloProfissional().keys())
                out.println(this.getProfissionais().get(i).getempresasSeguidaspeloProfissional().get(j));

            out.println("EmpresasSeguidoras");
            for (int j : this.getProfissionais().get(i).getEmpresasASeguir().keys())
                out.println(this.getProfissionais().get(i).getEmpresasASeguir().get(j));

            out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        }
        out.println();
    }

    /*
    EMPRESAS
     */


    public void inserirEmpresa(Empresa e) {
        this.empresas.put(e.getIdEmpresa(), e);
    }

    public void removerEmpresa(Empresa e) {
        //class empresa
        this.empresas.delete(e.getIdEmpresa());
        //class profissionais
        for (int i : this.profissionais.keys()) {
            //empresa atual
            if (this.profissionais.get(i).getEmpresaAtual().equals(e))
                this.profissionais.get(i).setEmpresaAtual(null);
            //empresasAntigas - Dúvida na consistencia da remoção
            if (this.profissionais.get(i).getEmpresasAntigas().contains(e.getIdEmpresa()))
                this.profissionais.get(i).getEmpresasAntigas().delete(e.getIdEmpresa());
            //empresasASeguir
            if (this.profissionais.get(i).getEmpresasASeguir().contains(e.getIdEmpresa()))
                this.profissionais.get(i).getEmpresasASeguir().delete(e.getIdEmpresa());
            //empresasSeguidoras
            if (this.profissionais.get(i).getempresasSeguidaspeloProfissional().contains(e.getIdEmpresa()))
                this.profissionais.get(i).getempresasSeguidaspeloProfissional().delete(e.getIdEmpresa());
        }
        //class encontros - Ao eliminar a empresa criadora, o encontro é completamenta removido, feito anteriormente
        for (int j : this.encontros.keys())
            if (this.encontros.get(j).getEmpresaCriadora().equals(e))
                removerEncontro(encontros.get(j));

    }


    public void listarEmpresas() {
        out.println("EMPRESAS(Quantidade:" + this.getEmpresas().size() + ")");
        for (int i : this.getEmpresas().keys()) {
            out.println(this.getEmpresas().get(i));
            out.println("ProfissionaisASeguir");
            for (int j : this.getEmpresas().get(i).getProfissionaisASeguir().keys())
                out.println(this.getEmpresas().get(i).getProfissionaisASeguir().get(j));

            out.println("ProfissionaisSeguidores");
            for (int j : this.getEmpresas().get(i).getProfissionaisSeguidores().keys())
                out.println(this.getEmpresas().get(i).getProfissionaisSeguidores().get(j));

            out.println("Encontros");
            for (int j : this.getEmpresas().get(i).getEncontros().keys())
                out.println(this.getEmpresas().get(i).getEncontros().get(j));

            out.println("Trabalhadores");
            for (int j : this.getEmpresas().get(i).getTrabalhadores().keys())
                out.println(this.getEmpresas().get(i).getTrabalhadores().get(j));

            out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        }
        out.println();
    }

    /*
    Encontros
    */


    public void inserirEncontro(Encontro e) {
        this.encontros.put(e.getIdEncontro(), e);
    }


    public void removerEncontro(Encontro e) {
        //class encontro
        this.encontros.delete(e.getIdEncontro());
        //class profissional
        for (int i : this.profissionais.keys())
            if (this.profissionais.get(i).getEncontrosEmpresa().contains(e.getIdEncontro()))
                this.profissionais.get(i).getEncontrosEmpresa().delete(e.getIdEncontro());
        //class empresa
        for (int i : this.empresas.keys())
            if (this.empresas.get(i).getEncontros().contains(e.getIdEncontro()))
                this.empresas.get(i).getEncontros().delete(e.getIdEncontro());
    }

    public void listarEncontros() {
        out.println("ENCONTROS(Quantidade:" + this.getEncontros().size() + ")");
        for (int i : this.getEncontros().keys()) {
            out.println(this.getEncontros().get(i));

            out.println("Participantes");
            for (int j : this.getEncontros().get(i).getParticipantes().keys())
                out.println(this.getEncontros().get(i).getParticipantes().get(j));

            out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        out.println();
    }

    /*
    Competencias
     */


    public void inserirCompetencia(Competencia c) {
        this.competencias.put(c.getIdCompetencia(), c);
    }


    public void removerCompetencia(Competencia c) {
        this.competencias.delete(c.getIdCompetencia());
        //class profissional
        for (int i : this.profissionais.keys())
            if (this.profissionais.get(i).getCompetencias().contains(c.getIdCompetencia()))
                this.profissionais.get(i).getCompetencias().delete(c.getIdCompetencia());
    }

    public void listarCompetencias() {
        out.println("COMPETENCIAS(Quantidade:" + this.getCompetencias().size() + ")");
        for (int i : this.getCompetencias().keys()) {
            out.println(this.getCompetencias().get(i));
            out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        out.println();
    }

    /*
    R8
     */
    public ArrayList<Profissional> profissionaisComCompetencia(Competencia c) {
        ArrayList<Profissional> p = new ArrayList<>();
        for (int i : this.profissionais.keys()) {
            for (int j : this.profissionais.get(i).getCompetencias().keys())
                if (this.profissionais.get(i).getCompetencias().get(j).equals(c))
                    p.add(this.profissionais.get(i));
        }
        return p;
    }

    public ArrayList<Profissional> profissionaisSemEmpresa() {
        ArrayList<Profissional> p = new ArrayList<>();
        for (int i : this.profissionais.keys()) {
            if (this.profissionais.get(i).getEmpresaAtual() == null)
                p.add(this.profissionais.get(i));
        }
        return p;
    }

    public ArrayList<Encontro> encontrosComMaisDeNParticipantes(int n) {
        ArrayList<Encontro> e = new ArrayList<>();
        for (int i : this.encontros.keys()) {
            if (this.encontros.get(i).getnParticipantes() > n)
                e.add(this.encontros.get(i));
        }
        return e;
    }

    public ArrayList<Encontro> encontrosComMenosDeNParticipantes(int n) {
        ArrayList<Encontro> e = new ArrayList<>();
        for (int i : this.encontros.keys()) {
            if (this.encontros.get(i).getnParticipantes() < n)
                e.add(this.encontros.get(i));
        }
        return e;
    }

    public ArrayList<Encontro> encontroNumIntervaloDeTempo(Data menor, Data maior) {
        ArrayList<Encontro> e = new ArrayList<>();
        for (int i : this.encontros.keys()) {
            if (this.encontros.get(i).getData().afterDate(menor) && this.encontros.get(i).getData().beforeDate(maior))
                e.add(this.encontros.get(i));
        }
        return e;
    }

    public ArrayList<Profissional> profissionaisComAreaDeInteresse(String a) {
        ArrayList<Profissional> p = new ArrayList<>();
        for (int i : this.profissionais.keys()) {
            for (int j = 0; j < this.profissionais.get(i).getAreasDeInteresse().size(); j++)
                if (this.profissionais.get(i).getAreasDeInteresse().get(j).compareTo(a) == 0)
                    p.add(this.profissionais.get(i));
        }
        return p;
    }


}
