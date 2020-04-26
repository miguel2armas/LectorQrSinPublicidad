package com.miguel.armas.developer.lectorqrsinpublicidad.Entidades;

public class HistorySqlLite {


    private String id_history;
    private String save_string;
    private int date;
    public HistorySqlLite(){
    }
    public HistorySqlLite(String id_history, String save_string, int date) {
        this.id_history = id_history;
        this.save_string=save_string;
        this.date=date;
    }


    public String getid_history() {
        return id_history;
    }
    public void setid_history(String id_history) {
        this.id_history = id_history;
    }
    public String getsave_string() {
        return save_string;
    }
    public void setsave_string(String save_string) {
        this.save_string = save_string;
    }
    public int getdate() {
        return date;
    }
    public void setdate(int date) {
        this.date = date;
    }

}
