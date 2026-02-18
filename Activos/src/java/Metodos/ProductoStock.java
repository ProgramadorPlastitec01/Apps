package Metodos;


public class ProductoStock {

    private String estado;
    private String cod;
    private String nombre;
    private double minimo;
    private double exist;

    public ProductoStock(String estado, String cod, String nombre,
                         double minimo, double exist) {
        this.estado = estado;
        this.cod = cod;
        this.nombre = nombre;
        this.minimo = minimo;
        this.exist = exist;
    }

    public String getEstado() {
        return estado;
    }

    public String getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMinimo() {
        return minimo;
    }

    public double getExist() {
        return exist;
    }
}

