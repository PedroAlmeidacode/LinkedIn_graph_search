package primeirafase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data implements Comparable<Data> {

  private int dia;
  private int mes;
  private int ano;
  private int hora;
  private int minuto;

  public Data() {
    Calendar gregCalendar = new GregorianCalendar();
    this.dia = gregCalendar.get(Calendar.DAY_OF_MONTH);
    this.mes = gregCalendar.get(Calendar.MONTH) + 1;
    this.ano = gregCalendar.get(Calendar.YEAR);
    this.hora = gregCalendar.get(Calendar.HOUR_OF_DAY);
    this.minuto = gregCalendar.get(Calendar.MINUTE);
  }

  public Data(int dia, int mes, int ano, int hora, int minuto) {
    this.dia = dia;
    this.mes = mes;
    this.ano = ano;
    this.hora = hora;
    this.minuto = minuto;
  }




  public boolean beforeDate(Data d) {
    if (this.ano < d.ano){
      return true;}
    if (this.ano == d.ano) {
      if (this.mes < d.mes) {
        return true;
      }
      if (this.mes == d.mes) {
        if (this.dia < d.dia)
          return true;
        if (this.dia == d.dia) {
          if (this.hora < d.hora)
            return true;
          if (this.hora == d.hora)
            return this.minuto < d.minuto;
        }
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return this.getDia() + "/" + this.getMes() + "/" + this.getAno();
  }



  public boolean isLeapYear() {
    if ((this.ano % 4 == 0) && (this.ano % 100 != 0) || this.ano % 400 == 0)
      return true;
    return false;
  }

  public boolean afterDate(Data d) {
    if (this.dia == d.dia && this.mes == d.mes && this.ano == d.ano)
      return false;
    return !beforeDate(d);
  }

  public int differenceYears(Data d) {
    return Math.abs(this.ano - d.ano);//Retorna o valor absoluto da diferença
  }


  /**
   * @param d a given date
   * @return returns 0 if this date is equal to d date, -1 if this date is
   * before d date or 1 if this date is after d date
   */
  public int compareTo(Data d) {
    if (this.dia == d.dia && this.mes == d.mes && this.ano == d.ano)
      return 0;
    return this.beforeDate(d) ? -1 : 1;
  }

  public int getDia() {
    return dia;
  }

  public void setDia(int dia) {
    this.dia = dia;
  }

  public int getMes() {
    return mes;
  }

  public void setMes(int mes) {
    this.mes = mes;
  }

  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public int getHora() {
    return hora;
  }

  public void setHora(int hora) {
    this.hora = hora;
  }

  public int getMinuto() {
    return minuto;
  }

  public void setMinuto(int minuto) {
    this.minuto = minuto;
  }
}