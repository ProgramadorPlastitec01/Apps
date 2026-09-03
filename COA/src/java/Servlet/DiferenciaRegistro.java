package Servlet;

public class DiferenciaRegistro {

    private String campo;
    private String valorCorrecto;
    private String valorDiferente;

    private int cantidadCorrecto;
    private int cantidadDiferente;

    public DiferenciaRegistro() {
    }

    public DiferenciaRegistro(
            String campo,
            String valorCorrecto,
            String valorDiferente,
            int cantidadCorrecto,
            int cantidadDiferente) {

        this.campo = campo;
        this.valorCorrecto = valorCorrecto;
        this.valorDiferente = valorDiferente;
        this.cantidadCorrecto = cantidadCorrecto;
        this.cantidadDiferente = cantidadDiferente;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorCorrecto() {
        return valorCorrecto;
    }

    public void setValorCorrecto(String valorCorrecto) {
        this.valorCorrecto = valorCorrecto;
    }

    public String getValorDiferente() {
        return valorDiferente;
    }

    public void setValorDiferente(String valorDiferente) {
        this.valorDiferente = valorDiferente;
    }

    public int getCantidadCorrecto() {
        return cantidadCorrecto;
    }

    public void setCantidadCorrecto(int cantidadCorrecto) {
        this.cantidadCorrecto = cantidadCorrecto;
    }

    public int getCantidadDiferente() {
        return cantidadDiferente;
    }

    public void setCantidadDiferente(int cantidadDiferente) {
        this.cantidadDiferente = cantidadDiferente;
    }
}