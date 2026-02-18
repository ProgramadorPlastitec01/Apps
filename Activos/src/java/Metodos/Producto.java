package Metodos;

public class Producto {
    private String cod;
    private String nombre;
    private String exist;
    private String unidad;

    public Producto(String cod, String nombre, String exist, String unidad) {
        this.cod = cod;
        this.nombre = nombre;
        this.exist = exist;
        this.unidad = unidad;
    }

    public String getCod() { return cod; }
    public String getNombre() { return nombre; }
    public String getExist() { return exist; }
    public String getUnidad() { return unidad; }
}
