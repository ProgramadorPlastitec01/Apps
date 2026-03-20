package Entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "capacitador")
@NamedQueries({
    @NamedQuery(name = "Capacitador.findAll", query = "SELECT c FROM Capacitador c"),
    @NamedQuery(name = "Capacitador.findByIdCapacitador", query = "SELECT c FROM Capacitador c WHERE c.idCapacitador = :idCapacitador"),
    @NamedQuery(name = "Capacitador.findByNombre", query = "SELECT c FROM Capacitador c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Capacitador.findByApellido", query = "SELECT c FROM Capacitador c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Capacitador.findByDocumento", query = "SELECT c FROM Capacitador c WHERE c.documento = :documento"),
    @NamedQuery(name = "Capacitador.findByCodigo", query = "SELECT c FROM Capacitador c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Capacitador.findByUsuario", query = "SELECT c FROM Capacitador c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Capacitador.findByContrasena", query = "SELECT c FROM Capacitador c WHERE c.contrasena = :contrasena")})
public class Capacitador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_capacitador")
    private Integer idCapacitador;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "documento")
    private String documento;
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrasena")
    private String contrasena;
    @OneToMany(mappedBy = "capacitador")
    private Collection<Capacitacion> capacitacionCollection;
    @JoinColumn(name = "fk_cargo", referencedColumnName = "id_cargo")
    @ManyToOne
    private Cargo cargo;

    public Capacitador() {
    }

    public Capacitador(Integer idCapacitador) {
        this.idCapacitador = idCapacitador;
    }

    public Integer getIdCapacitador() {
        return idCapacitador;
    }

    public void setIdCapacitador(Integer idCapacitador) {
        this.idCapacitador = idCapacitador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Collection<Capacitacion> getCapacitacionCollection() {
        return capacitacionCollection;
    }

    public void setCapacitacionCollection(Collection<Capacitacion> capacitacionCollection) {
        this.capacitacionCollection = capacitacionCollection;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCapacitador != null ? idCapacitador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capacitador)) {
            return false;
        }
        Capacitador other = (Capacitador) object;
        if ((this.idCapacitador == null && other.idCapacitador != null) || (this.idCapacitador != null && !this.idCapacitador.equals(other.idCapacitador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Capacitador[idCapacitador=" + idCapacitador + "]";
    }

}
