package asins4.maconman.uv.es;

import java.io.Serializable;

public class Municipio implements Serializable {
    int id, codMunicipio, casosPCR, casosPCR14, defunciones;
    String municipi, incidenciaPCR, incidenciaPCR14, tasaDefuncion;

    public Municipio(int id, int codMunicipio, int casosPCR, int casosPCR14, int defunciones, String municipi, String incidenciaPCR, String incidenciaPCR14, String tasaDefuncion) {
        this.id = id;
        this.codMunicipio = codMunicipio;
        this.casosPCR = casosPCR;
        this.casosPCR14 = casosPCR14;
        this.defunciones = defunciones;
        this.municipi = municipi;
        this.incidenciaPCR = incidenciaPCR;
        this.incidenciaPCR14 = incidenciaPCR14;
        this.tasaDefuncion = tasaDefuncion;
    }

    public int getId() {
        return id;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public int getCasosPCR() {
        return casosPCR;
    }

    public int getCasosPCR14() {
        return casosPCR14;
    }

    public int getDefunciones() {
        return defunciones;
    }

    public String getMunicipi() {
        return municipi;
    }

    public String getIncidenciaPCR() {
        return incidenciaPCR;
    }

    public String getIncidenciaPCR14() {
        return incidenciaPCR14;
    }

    public String getTasaDefuncion() {
        return tasaDefuncion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public void setCasosPCR(int casosPCR) {
        this.casosPCR = casosPCR;
    }

    public void setCasosPCR14(int casosPCR14) {
        this.casosPCR14 = casosPCR14;
    }

    public void setDefunciones(int defunciones) {
        this.defunciones = defunciones;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public void setIncidenciaPCR(String incidenciaPCR) {
        this.incidenciaPCR = incidenciaPCR;
    }

    public void setIncidenciaPCR14(String incidenciaPCR14) {
        this.incidenciaPCR14 = incidenciaPCR14;
    }

    public void setTasaDefuncion(String tasaDefuncion) {
        this.tasaDefuncion = tasaDefuncion;
    }
}
