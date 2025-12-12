package Tag;

public class Product {

    private String codigo;
    private String producto;
    private String lote;

    public Product() {
    }

    public Product(String codigo, String producto, String lote) {
        this.codigo = codigo;
        this.producto = producto;
        this.lote = lote;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
}
