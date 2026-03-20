package Entidad;

import java.io.Serializable;
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
import javax.persistence.Table;

@Entity
@Table(name = "cargo_manual")
@NamedQueries({
    @NamedQuery(name = "CargoManual.findAll", query = "SELECT c FROM CargoManual c"),
    @NamedQuery(name = "CargoManual.findByIdCargoManual", query = "SELECT c FROM CargoManual c WHERE c.idCargoManual = :idCargoManual")})
public class CargoManual implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cargo_manual")
    private Integer idCargoManual;
    @JoinColumn(name = "fk_manual", referencedColumnName = "id_manual")
    @ManyToOne
    private Manual manual;
    @JoinColumn(name = "fk_cargo", referencedColumnName = "id_cargo")
    @ManyToOne
    private Cargo cargo;

    public CargoManual() {
    }

    public CargoManual(Integer idCargoManual) {
        this.idCargoManual = idCargoManual;
    }

    public Integer getIdCargoManual() {
        return idCargoManual;
    }

    public void setIdCargoManual(Integer idCargoManual) {
        this.idCargoManual = idCargoManual;
    }

    public Manual getManual() {
        return manual;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
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
        hash += (idCargoManual != null ? idCargoManual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargoManual)) {
            return false;
        }
        CargoManual other = (CargoManual) object;
        if ((this.idCargoManual == null && other.idCargoManual != null) || (this.idCargoManual != null && !this.idCargoManual.equals(other.idCargoManual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.CargoManual[idCargoManual=" + idCargoManual + "]";
    }

}
