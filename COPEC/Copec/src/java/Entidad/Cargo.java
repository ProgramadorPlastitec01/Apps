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
@Table(name = "cargo")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
    @NamedQuery(name = "Cargo.findByIdCargo", query = "SELECT c FROM Cargo c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "Cargo.findByCargo", query = "SELECT c FROM Cargo c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "Cargo.findBySiglatura", query = "SELECT c FROM Cargo c WHERE c.siglatura = :siglatura")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "siglatura")
    private String siglatura;
    @OneToMany(mappedBy = "cargo")
    private Collection<Empleado> empleadoCollection;
    @OneToMany(mappedBy = "cargo")
    private Collection<CargoManual> cargoManualCollection;
    @OneToMany(mappedBy = "cargo")
    private Collection<Capacitador> capacitadorCollection;
    @JoinColumn(name = "fk_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area area;

    public Cargo() {
    }

    public Cargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSiglatura() {
        return siglatura;
    }

    public void setSiglatura(String siglatura) {
        this.siglatura = siglatura;
    }

    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    public Collection<CargoManual> getCargoManualCollection() {
        return cargoManualCollection;
    }

    public void setCargoManualCollection(Collection<CargoManual> cargoManualCollection) {
        this.cargoManualCollection = cargoManualCollection;
    }

    public Collection<Capacitador> getCapacitadorCollection() {
        return capacitadorCollection;
    }

    public void setCapacitadorCollection(Collection<Capacitador> capacitadorCollection) {
        this.capacitadorCollection = capacitadorCollection;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Cargo[idCargo=" + idCargo + "]";
    }

}
