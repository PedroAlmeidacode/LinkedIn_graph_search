package primeirafase;

public class Peso {

    private double custoTemporaldeCarro; //minutos
    private double custoTemporaldebicicleta; //minutos
    private double custoTemporalaPe; //minutos



    private double distancia;

    private double saltosSempreZero;

    public double getCustoTemporaldeCarro() {
        return custoTemporaldeCarro;
    }

    public void setCustoTemporaldeCarro(double custoTemporaldeCarro) {
        this.custoTemporaldeCarro = custoTemporaldeCarro;
    }

    public double getCustoTemporaldebicicleta() {
        return custoTemporaldebicicleta;
    }

    public void setCustoTemporaldebicicleta(double custoTemporaldebicicleta) {
        this.custoTemporaldebicicleta = custoTemporaldebicicleta;
    }

    public double getCustoTemporalaPe() {
        return custoTemporalaPe;
    }

    public void setCustoTemporalaPe(double custoTemporalaPe) {
        this.custoTemporalaPe = custoTemporalaPe;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getSaltosSempreZero() {
        return saltosSempreZero;
    }

    public void setSaltosSempreZero(double saltosSempreZero) {
        this.saltosSempreZero = saltosSempreZero;
    }

    public Peso(double custoTemporaldeCarro, double custoTemporaldebicicleta, double custoTemporalaPe, double distancia, double saltosSempreZero) {
        this.custoTemporaldeCarro = custoTemporaldeCarro;
        this.custoTemporaldebicicleta = custoTemporaldebicicleta;
        this.custoTemporalaPe = custoTemporalaPe;
        this.distancia = distancia;
        this.saltosSempreZero = saltosSempreZero;
    }

    @Override
    public String toString() {
        return "Peso{" +
                "custoTemporaldeCarro (min) = " + custoTemporaldeCarro +
                " , custoTemporaldebicicleta (min) = " + custoTemporaldebicicleta +
                " , custoTemporalaPe (min) = " + custoTemporalaPe +
                " , distancia (km) = " + distancia +
                '}';
    }
}
