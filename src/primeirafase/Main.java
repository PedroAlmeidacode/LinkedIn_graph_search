/* Criado por Pedro Almeida e Luis Aguiar
no ambito das disciplinas de AEDII/LPII */



package primeirafase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import primeirafase.algs4.In;
import primeirafase.algs4.StdOut;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        /*Iniciar A GUI*/
        launch(args);
        // de forma a que envie o output para o ficheiro dump mas tambem para a consola como outout
        for (int i=0 ; i< 2 ; i++) {



            BD bd = new BD();
            Graphs graphs = new Graphs();
            Data dataclass = new Data();


            if (i==0){
                // Store current System.out before assigning a new value
                PrintStream console = System.out;
                // Use stored value for output stream
                System.setOut(console);}


            if(i==1){
            // Creating a File object that represents the disk file.
            PrintStream o = new PrintStream(new File(".//Data//dump.txt"));
                System.setOut(o);}




        Point pp = new Point(2, 12);
        Point pp1 = new Point(2, 3);
        Point pp2 = new Point(12, 3);
        Point pp3 = new Point(23, 2);

        Data data = new Data(19, 4, 2019, 14, 58);
        Data data1 = new Data(19, 5, 2016, 2, 58);
        Data data2 = new Data(22, 4, 2014, 5, 3);
        Data data3 = new Data(11, 12, 2016, 12, 34);
        Data data4 = new Data(23, 3, 2013, 23, 12);
        Data horaChegada = new Data(11, 12, 2016, 12, 45);
        Data horaChegada1 =new Data(11, 12, 2016, 13, 20);
        Data data5 = new Data(11, 10, 2016, 12, 32);
        Data data6 =new Data(11, 12, 2016, 14, 30);

        Empresa d = new Empresa(bd, 0, "google", "software", pp);
        Empresa e = new Empresa(bd, 1, "Microsoft", "disto", pp1);
        Empresa e1 = new Empresa(bd, 2, "blip", "tecnologia", pp2);
        Empresa e2 = new Empresa(bd, 3, "Yahoo", "coisas", pp3);


        Profissional p = new Profissional(bd, 0, "Luis", "Aguiar", "ola", null, pp);
        Profissional l = new Profissional(bd, 1, "Zezinho", "Couves", "fds@gmail.com", e, pp2);
        Profissional q = new Profissional(bd, 2, "Pedro", "Alemeida", "123@gmail.com", d, pp1);
        Profissional p2 = new Profissional(bd, 3, "Antonio", "Fernando", "123@gmail.com", e1, pp1);
        Profissional p3 = new Profissional(bd, 4, "Joe", "Berardo", "wikileaks@gmail.com", e2, pp1);

        Encontro encontro = new Encontro(bd, 0, "evento", pp, d, data1);
        Encontro encontro1 = new Encontro(bd, 2, "reuniao", pp2, e, data3);
        Encontro encontro2 = new Encontro(bd, 1, "convencao", pp3, d, data2);

        Competencia competencia = new Competencia(bd, 1, "tecnologias", 3, data);
        Competencia competencia2 = new Competencia(bd, 0, "economia", 1, data4);
        Competencia competencia3 = new Competencia(bd, 2, "tecnologias", 10, data1);
        Competencia competencia4 = new Competencia(bd, 3, "informatica", 5, data3);

        Point p5 = new Point(13, 17);
        PontosP pontosP = new PontosP("Arca dAgua", p5, 0);
        bd.getPontosDePassagem().put(0, pontosP);
        Point p6 = new Point(2, 13);
        PontosP pontosP1 = new PontosP("Polo Universitario", p6, 1);
        bd.getPontosDePassagem().put(1, pontosP1);
        Point p7 = new Point(13, 16);
        PontosP pontosP2 = new PontosP("Fernando Pessoa", p7, 2);
        bd.getPontosDePassagem().put(2, pontosP2);
        Point p8 = new Point(13, 4);
        PontosP pontosP3 = new PontosP("ISEP", p8, 3);
        bd.getPontosDePassagem().put(3, pontosP3);


        //test adicao de reas de interesse
        ArrayList<String> aid = new ArrayList<>();
        aid.add("Informatica");
        aid.add("SQL");
        aid.add("java");
        p.setAreasDeInteresse(aid);

        ArrayList<String> aid2 = new ArrayList<>();
        aid2.add("Informatica");
        aid2.add("background");
        aid2.add("C++");
        l.setAreasDeInteresse(aid2);

        competencia.addProfissionalAtribuidoCompetencias(q);
        p.addCompetenciaProfissional(competencia2);
        l.addCompetenciaProfissional(competencia2);
        p2.addCompetenciaProfissional(competencia3);
        p3.addCompetenciaProfissional(competencia4);

        //test adicao de trabalahador de empresa
        d.addTrabalhadoresEmpresa(p3);
        //test adicao de extrabalahador de empresa
        e.addExTrabalhadorEmpresa(p);
        //test adicao de
        p.addEncontroEmpresaProfissional(encontro);
        l.addEncontroEmpresaProfissional(encontro1);
        p.addEncontroEmpresaProfissional(encontro2);
        q.addEncontroEmpresaProfissional(encontro1);
        //test adicao de profissional como participante de encontro
        encontro.addParticipanteEncontro(p); // estas duas declaraçoes fazm a a mesma coisa ? Nao esta  afuncionar no txt
        //d.addProfissionalSeguidorEmpresa(p);
        p.addempresasSeguidaspeloProfissional(d);


        p.addEmpresaAntigaProfissional(e);
        //e.addProfissionalSeguidorEmpresa(l);
        //e.addProfissionalSeguidorEmpresa(p);
        l.addempresasSeguidaspeloProfissional(e);
        p.addempresasSeguidaspeloProfissional(e);

        // hora de chegada dos participantes do encontro 1
        encontro1.getHoraChegadaProfissional().put(q.getIdProfissional(),horaChegada);
        encontro1.getHoraChegadaProfissional().put(l.getIdProfissional(),horaChegada1);


        e.addProfissionalASeguirEmpresa(p);
        e.addProfissionalSeguidorEmpresa(l);
        //e.addTrabalhadoresEmpresa(l);


        l.addEmpresaAntigaProfissional(d);

        bd.listarProfissionais();
        bd.listarCompetencias();
        bd.listarEncontros();
        bd.listarEmpresas();



/*
        bd.loadfromcompetenciasBSTtofile(bd,".//Data//competencias.txt");
        bd.loadfromempresasBSTtofile(bd,".//Data//empresas.txt");
        bd.loadfromprofissionaisBSTtofile(bd,".//Data//profissionais.txt");
        bd.loadfromencontrosBSTtofile(bd,".//Data//encontros.txt");




            bd.loadfromfileprofissionais(".//Data//profissionais.txt");
            bd.loadfromfilecompetencias(".//Data//competencias.txt");
            bd.loadfromfileempresas(".//Data//empresas.txt");
            bd.loadfromfilencontros(".//Data//encontros.txt");

*/



            bd.createStForCaminhos(bd);
            graphs.criarGrafoEmpresasSeguidasporProfissisonais(bd);
            graphs.criarGrafoCaminhos(bd);
            graphs.criarGrafoencontrosProfissionaisParticipantes(bd);
            graphs.criarGrafoCompetenciasProfissionais(bd);
            graphs.criarGrafoLigacaoEmpresaProfissional(bd);
            graphs.criarGrafoProfissionais(bd);
            graphs.criarGrafoProfissionaisEmpresasQueSegue(bd);
            graphs.criarGrafoCompetenciasProfissionais(bd);

            //teste da criacao da st que agrega a informacao precisa para criar o grafo caminhos


        int j = 0, k = 0, m = 0;
        for (Integer r : bd.getStFinal().keys()) {

            if (j == 0) {
                if (bd.getStFinal().get(r).getObj() instanceof Empresa) {
                    System.out.println("Empresas:");
                    j++;
                }
            }
            if (k == 0) {
                if (bd.getStFinal().get(r).getObj() instanceof Encontro) {
                    System.out.println("Encontro:");
                    k++;
                }
            }
            if (m == 0)
                if (bd.getStFinal().get(r).getObj() instanceof PontosP) {
                    System.out.println("Pontos de Passagem:");
                    m++;
                }


            System.out.println(bd.getStFinal().get(r));
        }

            // TESTE R10 a)

        System.out.println("\n");
        graphs.shortestPathBetweenPontoPEmpresaOrEncontroBasedInDistancia(pontosP,null,encontro1,bd,graphs.getCaminhos(),graphs);
        graphs.fastestPathBetweenPontoPEmpresaOrEncontroBasedInEdgeCustoTemporal(pontosP,null,encontro1,bd,graphs.getCaminhos(),graphs,"pe");
        graphs.shortestePathBetweenPontoPEmpresaOrEncontroBasedInNSaltos(pontosP,null,encontro1,bd,graphs.getCaminhos(),graphs);


            //TESTE R10 b)
        if(graphs.isConnected(graphs.getProfissionais())){
            System.out.println("É CONEXO");
        }


            //TESTE R10 c)


            //TESTE R11 a)
            graphs.listarProfissionaisSeguemThisEmpresa(bd,e,graphs.getEmpresasSeguidasporProfissisonais());

            //TESTE R11 b)
            //
            graphs.listarProfissionaisParticipantesThisEncontro(bd,encontro1,graphs.getEncontrosProfissionaisParticipantes(),dataclass);

            //TESTE R11 c)
            graphs.listarProfissionaisParticipantesThisEncontroInThisInterval(bd,encontro1,data5,data6);

            //TESTE R11 d)


        ArrayList<Profissional> profissionalArrayListC = graphs.procurarElistarProfissionaisThisCompetenciaSemEmpresaAssociada(bd, "economia", 1, graphs.getCompetenciasProfissionais());
        System.out.println("Competencias procuradas : Tipo: " + "Economia" + " - Experiência: " + "1"
                + "\n Profissionais com as competências procuradas:");
        for (Profissional profissional0 : profissionalArrayListC) {
            System.out.println(profissional0);
        }


            //TESTE R11 e)

        ArrayList<Profissional> profissionalArrayList = graphs.sugerirProfissionaisToEmpresasBasedInCompetencias(bd,graphs.getLigacaoEmpresaProfissional(),e1);
        System.out.println("Profissionais sugeridos para empresa:\n" + e1);
        for (Profissional PPPP: profissionalArrayList){
            System.out.println(PPPP);
        }


            //TESTE R11 f)

        /*
        ArrayList<Integer> idsprofissionaisempresasugestao =graphs.sugerirProfissionalRelacionarWithThisEmpresaBasedInThisProfissional(bd,p,graphs.getProfissionais(),graphs.getProfissionaisEmpresasQueSegue(),graphs.getEmpresasSeguidasporProfissisonais());
        for (Integer i : idsprofissionaisempresasugestao){
            System.out.println(bd.getEmpresas().get(i));
            System.out.println("Profissional sugeridos à empresa:");
            if(i==1){
                System.out.println(bd.getProfissionais().get(i));

            }


        }
        */
            // parte 2 (funcional)

        graphs.pathOfThisProfissionalToThisEmpresa(bd,p,e2,graphs.getCaminhos(),graphs);



            //TESTE R11 g)


        System.out.println(graphs.getCompetenciasProfissionais());
        ArrayList<Profissional> profissionalArrayListG = graphs.searchProfissionaisMultiplosCriterios(graphs,bd,"tecnologias",true,graphs.getCompetenciasProfissionais(),3);
        System.out.println("Profissionais com os criterios requisitados\n");
        for (Profissional PPPP: profissionalArrayListG){
            System.out.println(PPPP);
        }






       //TESTE R11 h)
        ArrayList<Encontro> encontroTime = bd.encontroNumIntervaloDeTempo(data2,data);
        for (Encontro enc: encontroTime){
            System.out.println(enc);
        }


        }
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menujava.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);//desativa o redimensionamento da pagina
        primaryStage.setTitle("ProjetoSO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
