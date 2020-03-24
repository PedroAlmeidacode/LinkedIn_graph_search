package primeirafase;

import primeirafase.algs4.*;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class MenuJavaFXMLController {
    public BD bd=new BD();
    public Graphs graphs=new Graphs();
    /*
    Digraph pesado dos caminhos
    */
    public EdgeWeightedDigraph graphCaminhos;
    public TextField verticesNumberFieldCaminhos;
    public TextArea edgesNumberFieldCaminhos;
    public Group graphGroupCaminhos;
    public Label labelAviso;
    public TextField removeCaminhoNodeTextField;
    public TextField removeCaminhoEdgeTextField;
    public TextField addCaminhoEdgeTextField;
    /*
       Graph pesado das amizades
       */
    public EdgeWeightedGraph graphAmizades;
    public TextField verticesNumberFieldAmizades;
    public TextArea edgesNumberFieldAmizades;
    public Group graphGroupAmizades;
    public TextField removeAmizadeNodeTextField;
    public TextField removeAmizadeEdgeTextField;
    public TextField addAmizadeEdgeTextField;

    /*
        DiGraph ligEmpProf
        Ligação do maior para o menor -> Azul
        Ligação do menor para o maior -> Vermelho
         */
    public Digraph graphLigEmpProf;
    public TextField verticesNumberFieldLigProfEmp;
    public TextArea edgesNumberFieldLigProfEmp;
    public Group graphGroupLigProfEmp;
    public TextField removeLigEmpProfNodeTextField;
    public TextField removeLigProfEmpEdgeTextField;
    public TextField addLigProfEmpEdgeTextField;


    private String delimiter = ";";
    private double radius = 15.0;

    /**
     * Limpa todos os contituintes da GUI
     * @param actionEvent
     */
    public void handleClearButtonAction(ActionEvent actionEvent) {
        verticesNumberFieldCaminhos.clear();
        edgesNumberFieldCaminhos.clear();
        graphGroupCaminhos.getChildren().clear();

        verticesNumberFieldAmizades.clear();
        edgesNumberFieldAmizades.clear();
        graphGroupAmizades.getChildren().clear();

        verticesNumberFieldLigProfEmp.clear();
        edgesNumberFieldLigProfEmp.clear();
        graphGroupLigProfEmp.getChildren().clear();

        labelAviso.setText("Limpeza executada!");
    }

    /**
     * Carrega no ficheiro os 3 graphs obtidos a partir da informação da BD
     */
    public void handleLoadBDandGraphs(){
        /*
        add pontos de passagem manualmente
         */
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

        bd.loadfromfileprofissionais("C:\\Users\\asus\\Documents\\Projeto_FASE2\\Data\\profissionais.txt");
        bd.loadfromfilecompetencias("C:\\Users\\asus\\Documents\\Projeto_FASE2\\Data\\competencias.txt");
        bd.loadfromfileempresas("C:\\Users\\asus\\Documents\\Projeto_FASE2\\Data\\empresas.txt");
        bd.loadfromfilencontros("C:\\Users\\asus\\Documents\\Projeto_FASE2\\Data\\encontros.txt");

        bd.createStForCaminhos(bd);
        graphs.criarGrafoencontrosProfissionaisParticipantes(bd);
        graphs.criarGrafoEmpresasSeguidasporProfissisonais(bd);
        graphs.criarGrafoProfissionaisEmpresasQueSegue(bd);
        graphs.criarGrafoCompetenciasProfissionais(bd);
        graphs.criarGrafoProfissionais(bd);
        graphs.criarGrafoLigacaoEmpresaProfissional(bd);
        graphs.criarGrafoCaminhos(bd);
        graphAmizades=graphs.getProfissionais();
        graphCaminhos= graphs.getCaminhos();
        graphLigEmpProf=graphs.getLigacaoEmpresaProfissional();

        generateNodeGraph(graphAmizades.V(), graphGroupAmizades);//nº vertices
        for (int v = 0; v < graphAmizades.V(); v++) {

            for (Edge w : graphAmizades.adj(v)) {

                createGraphAmizades(v, w.other(v), w.weight());
            }
        }
        generateNodeGraph(graphLigEmpProf.V(), graphGroupLigProfEmp);//nº vertices

        for (int i = 0; i < graphLigEmpProf.V(); i++) {
            for (int j : graphLigEmpProf.adj(i)) {
                createGraphLigEmpProf(i, j);
            }
        }
        generateNodeGraph(graphCaminhos.V(), graphGroupCaminhos);//nº vertices
        for (int v = 0; v < graphCaminhos.V(); v++) {

            for (DirectedEdge w : graphCaminhos.adj(v)) {

                createGraphCaminhos(v, w.to(), w.weight());
            }
        }
    }


    /**
     * Criar os nodos no GraphGroup
     * @param vNumber - número de vértices
     * @param graphGroup - qual o graph group em que vai inserir os nodes
     */
    public void generateNodeGraph(int vNumber, Group graphGroup) {
        graphGroup.getChildren().clear();//limpa o group
        for (int i = 0; i < vNumber; i++) {
            Random r = new Random();
            double posX = r.nextDouble() * 760;
            double posY = r.nextDouble() * 430;
            Circle c = new Circle(posX, posY, radius);
            c.setFill(Color.ORANGERED);
            c.setId("" + i);
            Text text = new Text("" + i);
            StackPane sp = new StackPane();//para meter o numero em cima do circulo
            sp.setLayoutX(posX - radius);
            sp.setLayoutY(posY - radius);
            sp.getChildren().addAll(c, text);
            graphGroup.getChildren().add(sp);
        }
    }

    /**
     * Cria as ligações do graph caminhos na interface
     * @param v - node v
     * @param w - node w
     * @param p - weight
     */
    public void createGraphCaminhos(int v, int w, double p) {

        StackPane spv = (StackPane) graphGroupCaminhos.getChildren().get(v);
        Circle cv = (Circle) spv.getChildren().get(0);
        StackPane spw = (StackPane) graphGroupCaminhos.getChildren().get(w);
        Circle cw = (Circle) spw.getChildren().get(0);
        Line l;
        Text text;
        if (w < v) {//se a origem for maior que o destino
            l = new Line(cv.getCenterX() + 5, cv.getCenterY() + 5, cw.getCenterX() + 5, cw.getCenterY() + 5);
            l.setStroke(Color.DARKBLUE);
            text = new Text(((cv.getCenterX() + cw.getCenterX()) / 2) + 5, ((cv.getCenterY() + cw.getCenterY()) / 2) + 5, "");
            text.setFill(Color.BLUE);
        } else {
            l = new Line(cv.getCenterX() - 5, cv.getCenterY() - 5, cw.getCenterX() - 5, cw.getCenterY() - 5);
            l.setStroke(Color.DARKRED);
            text = new Text(((cv.getCenterX() + cw.getCenterX()) / 2) - 5, ((cv.getCenterY() + cw.getCenterY()) / 2) - 5, "");
            text.setFill(Color.RED);
        }
        String s = "" + p;
        text.setText(s);
        text.toFront();
        graphGroupCaminhos.getChildren().addAll(text, l);

    }

    /**
     * Cria as ligações do graph Amizades na interface
     * @param v - node v
     * @param w - node w
     * @param p - weight
     */
    public void createGraphAmizades(int v, int w, double p) {

        StackPane spv = (StackPane) graphGroupAmizades.getChildren().get(v);
        Circle cv = (Circle) spv.getChildren().get(0);
        StackPane spw = (StackPane) graphGroupAmizades.getChildren().get(w);
        Circle cw = (Circle) spw.getChildren().get(0);
        Line l = new Line(cv.getCenterX(), cv.getCenterY(), cw.getCenterX(), cw.getCenterY());
        double x = (cv.getCenterX() + cw.getCenterX()) / 2;
        double y = (cv.getCenterY() + cw.getCenterY()) / 2;
        String s = "" + p;
        Text text = new Text(x, y, s);
        graphGroupAmizades.getChildren().addAll(l, text);

    }
    /**
     * Cria as ligações do graph LigEmpProf na interface
     * @param v - node v
     * @param w - node w
     */
    public void createGraphLigEmpProf(int v, int w) {
        StackPane spv = (StackPane) graphGroupLigProfEmp.getChildren().get(v);
        Circle cv = (Circle) spv.getChildren().get(0);
        StackPane spw = (StackPane) graphGroupLigProfEmp.getChildren().get(w);
        Circle cw = (Circle) spw.getChildren().get(0);
        Line l;
        Text text;
        if (w < v) {//se a origem for maior que o destino
            l = new Line(cv.getCenterX() + 5, cv.getCenterY() + 5, cw.getCenterX() + 5, cw.getCenterY() + 5);
            l.setStroke(Color.DARKBLUE);
            text = new Text(((cv.getCenterX() + cw.getCenterX()) / 2) + 5, ((cv.getCenterY() + cw.getCenterY()) / 2) + 5, "");
            text.setFill(Color.BLUE);
        } else {
            l = new Line(cv.getCenterX() - 5, cv.getCenterY() - 5, cw.getCenterX() - 5, cw.getCenterY() - 5);
            l.setStroke(Color.DARKRED);
            text = new Text(((cv.getCenterX() + cw.getCenterX()) / 2) - 5, ((cv.getCenterY() + cw.getCenterY()) / 2) - 5, "");
            text.setFill(Color.RED);
        }
        graphGroupLigProfEmp.getChildren().add(l);
    }

    /**
     * Handler do botão para criar graph Caminhos manualmente
     * @param actionEvent
     */
    public void handleCreateButtonCaminhosAction(ActionEvent actionEvent) {
        int vNumber = Integer.parseInt(verticesNumberFieldCaminhos.getText());
        graphCaminhos = new EdgeWeightedDigraph(vNumber);

        generateNodeGraph(vNumber, graphGroupCaminhos);

        String[] lines = edgesNumberFieldCaminhos.getText().split("\n");
        for (String li : lines) {//guarda num array para cada edge
            String[] a = li.split(delimiter);//guarda num array todos os edges de cada edge
            if (a.length == 3) {
                int v = Integer.parseInt(a[0]);
                int w = Integer.parseInt(a[1]);
                double p = Double.parseDouble(a[2]);
                graphCaminhos.addEdge(new DirectedEdge(v, w, p));
                createGraphCaminhos(v, w, p);
            }
        }
        labelAviso.setText("Graph Caminhos Criado!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
    }

    /**
     * Handler do botão para criar graph Amizades manualmente
     * @param actionEvent
     */
    public void handleCreateButtonAmizadesAction(ActionEvent actionEvent) {
        int vNumber = Integer.parseInt(verticesNumberFieldAmizades.getText());
        graphAmizades = new EdgeWeightedGraph(vNumber);

        generateNodeGraph(vNumber, graphGroupAmizades);

        String[] lines = edgesNumberFieldAmizades.getText().split("\n");
        for (String li : lines) {//guarda num array para cada edge
            String[] a = li.split(delimiter);//guarda num array todos os edges de cada edge
            if (a.length == 3) {
                int v = Integer.parseInt(a[0]);
                int w = Integer.parseInt(a[1]);
                double p = Double.parseDouble(a[2]);
                graphAmizades.addEdge(new Edge(v, w, p));
                createGraphAmizades(v, w, p);

            }
        }
        labelAviso.setText("Graph Amizades Criado!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");

    }
    /**
     * Handler do botão para criar graph LigProfEmp manualmente
     * @param actionEvent
     */
    public void handleCreateLigProfEmpButtonAction(ActionEvent actionEvent) {
        int vNumber = Integer.parseInt(verticesNumberFieldLigProfEmp.getText());
        graphLigEmpProf = new Digraph(vNumber);

        generateNodeGraph(vNumber, graphGroupLigProfEmp);

        String[] lines = edgesNumberFieldLigProfEmp.getText().split("\n");
        for (String li : lines) {//guarda num array para cada edge
            String[] a = li.split(delimiter);//guarda num array todos os edges de cada edge
            if (a.length == 2) {
                int v = Integer.parseInt(a[0]);
                int w = Integer.parseInt(a[1]);
                if (v >= 0 && v < graphLigEmpProf.V() && w >= 0 && w < graphLigEmpProf.V()) {
                    graphLigEmpProf.addEdge(v, w);
                    createGraphLigEmpProf(v, w);
                }
            }
            labelAviso.setText("Graph LEP Criado!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");

        }
        labelAviso.setText("Graph ligProfEmp inserido manualmente foi criado!");
    }

    /**
     * Handler do botão de load from txt
     * @param actionEvent
     */
    public void handleLoadButtonAction(ActionEvent actionEvent) {
        loadCaminhosTxt("C:\\Users\\Aguia\\IdeaProjects\\Projeto_FASE2\\Data\\caminhos.txt");
        loadAmizadesFromTxt("C:\\Users\\Aguia\\IdeaProjects\\Projeto_FASE2\\Data\\amizades.txt");
        loadLigEmpProfTxt("C:\\Users\\Aguia\\IdeaProjects\\Projeto_FASE2\\Data\\lep.txt");

        labelAviso.setText("Load executado!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
    }

    /**
     * Ler o graph de amizades a partir do txt
     * @param path - path do ficheiro
     */
    public void loadAmizadesFromTxt(String path) {
        graphAmizades = new EdgeWeightedGraph(new In(path));
        generateNodeGraph(graphAmizades.V(), graphGroupAmizades);//nº vertices
        for (int v = 0; v < graphAmizades.V(); v++) {

            for (Edge w : graphAmizades.adj(v)) {

                createGraphAmizades(v, w.other(v), w.weight());
            }
        }
    }

    /**
     * Ler o graph de LigEmpProf a partir do txt
     * @param path - path do ficheiro
     */
    public void loadLigEmpProfTxt(String path) {
        graphLigEmpProf = new Digraph(new In(path));
        generateNodeGraph(graphLigEmpProf.V(), graphGroupLigProfEmp);//nº vertices
        for (int v = 0; v < graphLigEmpProf.V(); v++) {
            for (int w : graphLigEmpProf.adj(v)) {
                createGraphLigEmpProf(v, w);
            }
        }
    }
    /**
     * Ler o graph de Caminhos a partir do txt
     * @param path - path do ficheiro
     */
    public void loadCaminhosTxt(String path) {
        graphCaminhos = new EdgeWeightedDigraph(new In(path));
        generateNodeGraph(graphCaminhos.V(), graphGroupCaminhos);//nº vertices
        for (int v = 0; v < graphCaminhos.V(); v++) {
            for (DirectedEdge w : graphCaminhos.adj(v)) {
                createGraphCaminhos(v, w.to(), w.weight());
            }
        }
    }

    /**
     * Handler do botão remover node do graph Amizades
     * @param actionEvent
     */
    public void handleRemoveAmizadeAction(ActionEvent actionEvent) {
        System.out.println("AmizadeAntes\n" + graphAmizades);
        int r = Integer.parseInt(removeAmizadeNodeTextField.getText());
        if (r >= 0 && r < graphAmizades.V()) {
            removeNodeAmizades(r);
            labelAviso.setText("Nó " + r + " removido!\t(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
        } else
            labelAviso.setText("Nó inválido!");
        System.out.println("AmizadeDps\n" + graphAmizades);
    }
    /**
     * Handler do botão remover node do graph LigEmpProf
     * @param actionEvent
     */
    public void handleRemoveLigEmpProfAction(ActionEvent actionEvent) {
        System.out.println("LEPAntes\n" + graphLigEmpProf);
        int r = Integer.parseInt(removeLigEmpProfNodeTextField.getText());
        if (r >= 0 && r < graphLigEmpProf.V()) {
            removeNodeLigEmpProf(r);
            labelAviso.setText("Nó " + r + " removido!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");

        } else
            labelAviso.setText("Nó inválido!");
        System.out.println("LEPDps\n" + graphLigEmpProf);

    }

    /**
     * Handler do botão remover node do graph Caminho
     * @param actionEvent
     */
    public void handleRemoveCaminhoAction(ActionEvent actionEvent) {
        System.out.println("CaminhosAntes\n" + graphCaminhos);
        int r = Integer.parseInt(removeCaminhoNodeTextField.getText());
        if (r >= 0 && r < graphCaminhos.V()) {
            removeNodeCaminho(r);
            labelAviso.setText("Nó " + r + " removido!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
        } else
            labelAviso.setText("Nó inválido!");
        System.out.println("CaminhosDps\n" + graphCaminhos);
    }

    /**
     * Remover o node do graph Amizades
     * @param r - identificador de que node se pretende remover
     */
    public void removeNodeAmizades(int r) {
        graphGroupAmizades.getChildren().clear();
        EdgeWeightedGraph novo = new EdgeWeightedGraph(graphAmizades.V() - 1);//com menos 1 vertice
        for (int v = 0; v < graphAmizades.V(); v++) {
            if (v != r) {//para não passar o ele á nova graph
                for (Edge w : graphAmizades.adj(v)) {
                    if (w != null && v > w.other(v)) {//para não haver duplicações, porque, sendo graph,o addedge adiciona logo nas duas sockets
                        if (w.other(v) != r) {//para não passar o ele á nova graph
                            //é obrigatorio organizar, logo se forem > que o removido tem de descer um id
                            if (v > r && w.other(v) > r)
                                novo.addEdge(new Edge(v - 1, w.other(v) - 1, w.weight()));
                            else if (w.other(v) > r)
                                novo.addEdge(new Edge(v, w.other(v) - 1, w.weight()));
                            else if (v > r)
                                novo.addEdge(new Edge(v - 1, w.other(v), w.weight()));
                            else
                                novo.addEdge(new Edge(v, w.other(v), w.weight()));
                        }
                    }

                }
            }
        }
        graphAmizades = novo;
        generateNodeGraph(graphAmizades.V(), graphGroupAmizades);//nº vertices
        for (int v = 0; v < graphAmizades.V(); v++) {

            for (Edge w : graphAmizades.adj(v)) {

                createGraphAmizades(v, w.other(v), w.weight());
            }
        }
    }

    /**
     * Remover o node do graph LigEmpProf
     * @param r - identificador de que node se pretende remover
     */
    public void removeNodeLigEmpProf(int r) {
        graphGroupLigProfEmp.getChildren().clear();
        Digraph novo = new Digraph(graphLigEmpProf.V() - 1);//com menos 1 vertice
        for (int v = 0; v < graphLigEmpProf.V(); v++) {
            if (v != r) {//para não passar o ele á nova graph
                for (int w : graphLigEmpProf.adj(v)) {
                    if (w != r) {//para não passar o ele á nova graph
                        //é obrigatorio organizar, logo se forem > que o removido tem de descer um id
                        if (v > r && w > r)
                            novo.addEdge(v - 1, w - 1);
                        else if (w > r)
                            novo.addEdge(v, w - 1);
                        else if (v > r)
                            novo.addEdge(v - 1, w);
                        else
                            novo.addEdge(v, w);

                    }
                }
            }
        }
        graphLigEmpProf = novo;
        generateNodeGraph(graphLigEmpProf.V(), graphGroupLigProfEmp);//nº vertices
        for (int v = 0; v < graphLigEmpProf.V(); v++) {

            for (int w : graphLigEmpProf.adj(v)) {

                createGraphLigEmpProf(v, w);
            }
        }
    }

    /**
     * Remover o node do graph Caminhos
     * @param r - identificador de que node se pretende remover
     */
    public void removeNodeCaminho(int r) {
        graphGroupCaminhos.getChildren().clear();
        EdgeWeightedDigraph novo = new EdgeWeightedDigraph(graphCaminhos.V() - 1);//com menos 1 vertice
        for (int v = 0; v < graphCaminhos.V(); v++) {
            if (v != r) {//para não passar o ele á nova graph
                for (DirectedEdge w : graphCaminhos.adj(v)) {
                    if (w != null) {
                        if (w.to() != r) {//para não passar o ele á nova graph
                            //é obrigatorio organizar, logo se forem > que o removido tem de descer um id
                            if (v > r && w.to() > r)
                                novo.addEdge(new DirectedEdge(v - 1, w.to() - 1, w.weight()));
                            else if (w.to() > r)
                                novo.addEdge(new DirectedEdge(v, w.to() - 1, w.weight()));
                            else if (v > r)
                                novo.addEdge(new DirectedEdge(v - 1, w.to(), w.weight()));
                            else
                                novo.addEdge(new DirectedEdge(v, w.to(), w.weight()));

                        }
                    }
                }
            }
        }
        graphCaminhos = novo;
        generateNodeGraph(graphCaminhos.V(), graphGroupCaminhos);//nº vertices
        for (int v = 0; v < graphCaminhos.V(); v++) {

            for (DirectedEdge w : graphCaminhos.adj(v)) {

                createGraphCaminhos(v, w.to(), w.weight());
            }
        }
    }

    /**
     * Remover ligação entre dois nodes do graph Amizades
     * @param rv - identificador de um dos nodes
     * @param rw - identificador do outro node
     */
    public void removeEdgeAmizades(int rv, int rw) {
        graphGroupAmizades.getChildren().clear();
        EdgeWeightedGraph novo = new EdgeWeightedGraph(graphAmizades.V());
        for (int v = 0; v < graphAmizades.V(); v++) {
            for (Edge w : graphAmizades.adj(v)) {
                if (w != null && v > w.other(v)) {//para não haver duplicações, porque, sendo graph,o addedge adiciona logo nas duas sockets
                    //devido a ser um graph a lista de adjacencias ser "duplicada" tem de se testar o if a seguir
                    if (v != rv && v != rw && w.other(v) != rw && w.other(v) != rv) {//para não passar o edge á nova graph
                        novo.addEdge(new Edge(v, w.other(v), w.weight()));
                    }
                }

            }
        }
        graphAmizades = novo;
        generateNodeGraph(graphAmizades.V(), graphGroupAmizades);//nº vertices
        for (int v = 0; v < graphAmizades.V(); v++) {

            for (Edge w : graphAmizades.adj(v)) {

                createGraphAmizades(v, w.other(v), w.weight());
            }
        }

    }
    /**
     * Remover ligação entre dois nodes do graph LigEmpProf
     * @param rv - identificador de um dos nodes
     * @param rw - identificador do outro node
     */
    public void removeEdgeLigEmpProf(int rv, int rw) {
        graphGroupLigProfEmp.getChildren().clear();
        Digraph novo = new Digraph(graphLigEmpProf.V());
        for (int v = 0; v < graphLigEmpProf.V(); v++) {
            for (int w : graphLigEmpProf.adj(v)) {
                if (w != rw && v != rv) {//para não passar o edge á nova graph
                    novo.addEdge(v, w);
                }
            }
        }
        graphLigEmpProf = novo;
        generateNodeGraph(graphLigEmpProf.V(), graphGroupLigProfEmp);//nº vertices
        for (int v = 0; v < graphLigEmpProf.V(); v++) {

            for (int w : graphLigEmpProf.adj(v)) {

                createGraphLigEmpProf(v, w);
            }
        }

    }
    /**
     * Remover ligação entre dois nodes do graph Caminhos
     * @param rv - identificador de um dos nodes
     * @param rw - identificador do outro node
     */
    public void removeEdgeCaminhos(int rv, int rw) {
        graphGroupCaminhos.getChildren().clear();
        EdgeWeightedDigraph novo = new EdgeWeightedDigraph(graphCaminhos.V());
        for (int v = 0; v < graphCaminhos.V(); v++) {
            for (DirectedEdge w : graphCaminhos.adj(v)) {
                if (w != null) {
                    if (v != rv && w.to() != rw) {//para não passar o ele á nova graph
                        novo.addEdge(new DirectedEdge(v, w.to(), w.weight()));

                    }
                }
            }
        }
        graphCaminhos = novo;
        generateNodeGraph(graphCaminhos.V(), graphGroupCaminhos);//nº vertices
        for (int v = 0; v < graphCaminhos.V(); v++) {

            for (DirectedEdge w : graphCaminhos.adj(v)) {

                createGraphCaminhos(v, w.to(), w.weight());
            }
        }
    }

    /**
     * Handler do botão remover edge caminho
     * @param actionEvent
     */
    public void handleRemoveEdgeCaminhoAction(ActionEvent actionEvent) {
        String[] read = removeCaminhoEdgeTextField.getText().split(delimiter);
        if (read.length == 2) {
            int v = Integer.parseInt(read[0]);
            int w = Integer.parseInt(read[1]);
            if (v >= 0 && v < graphCaminhos.V() && w >= 0 && w < graphCaminhos.V()) {
                removeEdgeCaminhos(v, w);
                labelAviso.setText("Edge(" + v + ";" + w + ") removida!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
            }
        }
    }
    /**
     * Handler do botão remover edge amizade
     * @param actionEvent
     */
    public void handleRemoveEdgeAmizadeAction(ActionEvent actionEvent) {
        String[] read = removeAmizadeEdgeTextField.getText().split(delimiter);
        if (read.length == 2) {
            int v = Integer.parseInt(read[0]);
            int w = Integer.parseInt(read[1]);
            if (v >= 0 && v < graphAmizades.V() && w >= 0 && w < graphAmizades.V()) {
                removeEdgeAmizades(v, w);
                labelAviso.setText("Edge(" + v + ";" + w + ") removida!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
            }
        }
    }
    /**
     * Handler do botão add edge LigEmpProf
     * @param actionEvent
     */
    public void handleRemoveLigEmpEdgeProfAction(ActionEvent actionEvent) {
        String[] read = removeLigProfEmpEdgeTextField.getText().split(delimiter);
        if (read.length == 2) {
            int v = Integer.parseInt(read[0]);
            int w = Integer.parseInt(read[1]);
            if (v >= 0 && v < graphLigEmpProf.V() && w >= 0 && w < graphLigEmpProf.V()) {
                removeEdgeLigEmpProf(v, w);
                labelAviso.setText("Edge(" + v + ";" + w + ") removida!(Ligação do maior para o menor -> Azul\nLigação do menor para o maior -> Vermelho)");
            }
        }
    }
    /**
     * Handler do botão add edge caminho
     * @param actionEvent
     */
    public void handleAddEdgeCaminhoAction(ActionEvent actionEvent) {
        String[] read = addCaminhoEdgeTextField.getText().split(delimiter);
        if (read.length == 3) {
            int v = Integer.parseInt(read[0]);
            int w = Integer.parseInt(read[1]);
            double d = Double.parseDouble(read[2]);
            if (v >= 0 && v < graphCaminhos.V() && w >= 0 && w < graphCaminhos.V()) {//se for um nó presente
                graphCaminhos.addEdge(new DirectedEdge(v, w, d));
                graphGroupCaminhos.getChildren().clear();
                generateNodeGraph(graphCaminhos.V(), graphGroupCaminhos);//nº vertices
                for (int i = 0; i < graphCaminhos.V(); i++) {

                    for (DirectedEdge j : graphCaminhos.adj(i)) {

                        createGraphCaminhos(i, j.to(), j.weight());
                    }
                }
            }
        }
    }
    /**
     * Handler do botão add edge amizade
     * @param actionEvent
     */
    public void handleAddEdgeAmizadeAction(ActionEvent actionEvent) {
        String[] read = addAmizadeEdgeTextField.getText().split(delimiter);
        if (read.length == 3) {
            int v = Integer.parseInt(read[0]);
            int w = Integer.parseInt(read[1]);
            double d = Double.parseDouble(read[2]);
            if (v >= 0 && v < graphAmizades.V() && w >= 0 && w < graphAmizades.V()) {//se for um nó presente
                graphAmizades.addEdge(new Edge(v, w, d));
                graphGroupAmizades.getChildren().clear();
                generateNodeGraph(graphAmizades.V(), graphGroupAmizades);//nº vertices

                for (int i = 0; i < graphAmizades.V(); i++) {
                    for (Edge j : graphAmizades.adj(i)) {
                        createGraphAmizades(i, j.other(i), j.weight());
                    }
                }
            }
        }
    }
    /**
     * Handler do botão add edge LigEmpProf
     * @param actionEvent
     */
    public void handleAddLigEmpEdgeProfAction(ActionEvent actionEvent) {
        String[] read = addLigProfEmpEdgeTextField.getText().split(delimiter);
        if (read.length == 2) {
            int v = Integer.parseInt(read[0]);
            int w = Integer.parseInt(read[1]);
            if (v >= 0 && v < graphLigEmpProf.V() && w >= 0 && w < graphLigEmpProf.V()) {//se for um nó presente
                graphLigEmpProf.addEdge(v, w);
                graphGroupLigProfEmp.getChildren().clear();
                generateNodeGraph(graphLigEmpProf.V(), graphGroupLigProfEmp);//nº vertices

                for (int i = 0; i < graphLigEmpProf.V(); i++) {
                    for (int j : graphLigEmpProf.adj(i)) {
                        createGraphLigEmpProf(i, j);
                    }
                }
            }
        }
    }
    /**
     * Handler do botão add node caminho
     * @param actionEvent
     */
    public void handleAddCaminhoAction(ActionEvent actionEvent) {
        graphGroupCaminhos.getChildren().clear();
        EdgeWeightedDigraph novo = new EdgeWeightedDigraph(graphCaminhos.V() + 1);//com mais 1 vertice
        for (int v = 0; v < graphCaminhos.V(); v++) {
            for (DirectedEdge w : graphCaminhos.adj(v)) {
                if (w != null) {
                    novo.addEdge(new DirectedEdge(v, w.to(), w.weight()));

                }
            }
        }
        graphCaminhos = novo;
        generateNodeGraph(graphCaminhos.V(), graphGroupCaminhos);//nº vertices
        for (int v = 0; v < graphCaminhos.V(); v++) {

            for (DirectedEdge w : graphCaminhos.adj(v)) {

                createGraphCaminhos(v, w.to(), w.weight());
            }
        }
    }
    /**
     * Handler do botão add node amizade
     * @param actionEvent
     */
    public void handleAddAmizadeAction(ActionEvent actionEvent) {

        graphGroupAmizades.getChildren().clear();
        EdgeWeightedGraph novo = new EdgeWeightedGraph(graphAmizades.V() + 1);//com mais 1 vertice
        for (int v = 0; v < graphAmizades.V(); v++) {
            for (Edge w : graphAmizades.adj(v)) {
                if (v > w.other(v)) {//para não haver duplicações, porque, sendo graph,o addedge adiciona logo nas duas sockets

                    novo.addEdge(new Edge(v, w.other(v), w.weight()));
                }
            }
        }
        graphAmizades = novo;
        generateNodeGraph(graphAmizades.V(), graphGroupAmizades);//nº vertices
        for (int v = 0; v < graphAmizades.V(); v++) {

            for (Edge w : graphAmizades.adj(v)) {

                createGraphAmizades(v, w.other(v), w.weight());
            }
        }
    }

    /**
     * Handler do botão add node LigEmpProf
     * @param actionEvent
     */
    public void handleAddLigEmpProfAction(ActionEvent actionEvent) {
        graphGroupLigProfEmp.getChildren().clear();
        Digraph novo = new Digraph(graphLigEmpProf.V() + 1);//com mais 1 vertice
        for (int v = 0; v < graphLigEmpProf.V(); v++) {
            for (int w : graphLigEmpProf.adj(v)) {
                novo.addEdge(v, w);

            }
        }
        graphLigEmpProf = novo;
        generateNodeGraph(graphLigEmpProf.V(), graphGroupLigProfEmp);//nº vertices
        for (int v = 0; v < graphLigEmpProf.V(); v++) {

            for (int w : graphLigEmpProf.adj(v)) {

                createGraphLigEmpProf(v, w);
            }
        }
    }

    /**
     * handler do botão salvar graphs para txt
     * @param actionEvent
     */
    public void handleSaveButtonAction(ActionEvent actionEvent) {
        saveTxtCaminhos("C:\\Users\\Aguia\\IdeaProjects\\Projeto_FASE2\\Data\\savecaminhos.txt");
        saveTxtAmizades("C:\\Users\\Aguia\\IdeaProjects\\Projeto_FASE2\\Data\\saveamizades.txt");
        saveTxtLep("C:\\Users\\Aguia\\IdeaProjects\\Projeto_FASE2\\Data\\savelep.txt");

    }

    /**
     * guardar graph caminhos para txt
     * @param path - caminho para o ficheiro
     */
    public void saveTxtCaminhos(String path) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(path);
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pw != null) {
            pw.print(graphCaminhos.V() + "\n" + graphCaminhos.E() + "\n");
            for (int v = 0; v < graphCaminhos.V(); v++) {
                for (DirectedEdge w : graphCaminhos.adj(v))
                    pw.print(v + " " + w.to() + " " + w.weight() + "\n");
            }
            pw.close();
        }
    }
    /**
     * guardar graph amizades para txt
     * @param path - caminho para o ficheiro
     */
    public void saveTxtAmizades(String path) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(path);
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pw != null) {
            pw.print(graphAmizades.V() + "\n" + graphAmizades.E() + "\n");
            for (int v = 0; v < graphAmizades.V(); v++) {
                for (Edge w : graphAmizades.adj(v))
                    if (v < w.other(v))//para não duplicar
                        pw.print(v + " " + w.other(v) + " " + w.weight() + "\n");
            }
            pw.close();
        }
    }
    /**
     * guardar graph LigEmpProf para txt
     * @param path - caminho para o ficheiro
     */
    public void saveTxtLep(String path){
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter(path);
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pw != null) {
            pw.print(graphLigEmpProf.V() + "\n" + graphLigEmpProf.E() + "\n");
            for (int v = 0; v < graphLigEmpProf.V(); v++) {
                for (int w : graphLigEmpProf.adj(v))
                    pw.print(v + " " + w +"\n");
            }
            pw.close();
        }

    }

}