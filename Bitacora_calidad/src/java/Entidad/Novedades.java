package Entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "novedades")
@NamedQueries({
    @NamedQuery(name = "Novedades.findAll", query = "SELECT n FROM Novedades n"),
    @NamedQuery(name = "Novedades.findByIdNovedad", query = "SELECT n FROM Novedades n WHERE n.idNovedad = :idNovedad"),
    @NamedQuery(name = "Novedades.findByFechaInsercion", query = "SELECT n FROM Novedades n WHERE n.fechaInsercion = :fechaInsercion")})
public class Novedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_novedad")
    private Integer idNovedad;
    @Basic(optional = false)
    @Column(name = "fecha_insercion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Lob
    @Column(name = "novedad")
    private String novedad;
    @Lob
    @Column(name = "producto")
    private String producto;
    @JoinColumn(name = "fk_id_maquina", referencedColumnName = "id_maquina")
    @ManyToOne
    private Maquinas maquinas;
    @JoinColumn(name = "fk_id_actividad", referencedColumnName = "id_actividad")
    @ManyToOne
    private Actividad actividad;

    public Novedades() {
    }

    public Novedades(Integer idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Novedades(Integer idNovedad, Date fechaInsercion) {
        this.idNovedad = idNovedad;
        this.fechaInsercion = fechaInsercion;
    }

    public Integer getIdNovedad() {
        return idNovedad;
    }

    public void setIdNovedad(Integer idNovedad) {
        this.idNovedad = idNovedad;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Maquinas getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(Maquinas maquinas) {
        this.maquinas = maquinas;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNovedad != null ? idNovedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Novedades)) {
            return false;
        }
        Novedades other = (Novedades) object;
        if ((this.idNovedad == null && other.idNovedad != null) || (this.idNovedad != null && !this.idNovedad.equals(other.idNovedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Novedades[idNovedad=" + idNovedad + "]";
    }

}
