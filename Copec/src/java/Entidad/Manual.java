package Entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "manual")
@NamedQueries({
    @NamedQuery(name = "Manual.findAll", query = "SELECT m FROM Manual m"),
    @NamedQuery(name = "Manual.findByIdManual", query = "SELECT m FROM Manual m WHERE m.idManual = :idManual"),
    @NamedQuery(name = "Manual.findByNombre", query = "SELECT m FROM Manual m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Manual.findByCantenido", query = "SELECT m FROM Manual m WHERE m.cantenido = :cantenido"),
    @NamedQuery(name = "Manual.findByCodigo", query = "SELECT m FROM Manual m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "Manual.findByVersion", query = "SELECT m FROM Manual m WHERE m.version = :version"),
    @NamedQuery(name = "Manual.findByTipo", query = "SELECT m FROM Manual m WHERE m.tipo = :tipo")})
public class Manual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_manual")
    private Integer idManual;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantenido")
    private String cantenido;
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "version")
    private String version;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "manual")
    private Collection<CargoManual> cargoManualCollection;

    public Manual() {
    }

    public Manual(Integer idManual) {
        this.idManual = idManual;
    }

    public Integer getIdManual() {
        return idManual;
    }

    public void setIdManual(Integer idManual) {
        this.idManual = idManual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantenido() {
        return cantenido;
    }

    public void setCantenido(String cantenido) {
        this.cantenido = cantenido;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Collection<CargoManual> getCargoManualCollection() {
        return cargoManualCollection;
    }

    public void setCargoManualCollection(Collection<CargoManual> cargoManualCollection) {
        this.cargoManualCollection = cargoManualCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idManual != null ? idManual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manual)) {
            return false;
        }
        Manual other = (Manual) object;
        if ((this.idManual == null && other.idManual != null) || (this.idManual != null && !this.idManual.equals(other.idManual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Manual[idManual=" + idManual + "]";
    }

}
