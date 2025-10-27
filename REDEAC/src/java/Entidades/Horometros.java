/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas2
 */
@Entity
@Table(name = "horometros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horometros.findAll", query = "SELECT h FROM Horometros h")
    , @NamedQuery(name = "Horometros.findByIdHorometro", query = "SELECT h FROM Horometros h WHERE h.idHorometro = :idHorometro")
    , @NamedQuery(name = "Horometros.findByIdEquipo", query = "SELECT h FROM Horometros h WHERE h.idEquipo = :idEquipo")
    , @NamedQuery(name = "Horometros.findByHorasActividad", query = "SELECT h FROM Horometros h WHERE h.horasActividad = :horasActividad")
    , @NamedQuery(name = "Horometros.findByMinutoActividad", query = "SELECT h FROM Horometros h WHERE h.minutoActividad = :minutoActividad")
    , @NamedQuery(name = "Horometros.findBySegundoActividad", query = "SELECT h FROM Horometros h WHERE h.segundoActividad = :segundoActividad")
    , @NamedQuery(name = "Horometros.findByDiaActividad", query = "SELECT h FROM Horometros h WHERE h.diaActividad = :diaActividad")})
public class Horometros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_Horometro")
    private Integer idHorometro;
    @Basic(optional = false)
    @Column(name = "Id_Equipo")
    private int idEquipo;
    @Basic(optional = false)
    @Column(name = "Horas_Actividad")
    private int horasActividad;
    @Column(name = "Minuto_Actividad")
    private Integer minutoActividad;
    @Column(name = "Segundo_Actividad")
    private Integer segundoActividad;
    @Basic(optional = false)
    @Column(name = "Dia_Actividad")
    private String diaActividad;

    public Horometros() {
    }

    public Horometros(Integer idHorometro) {
        this.idHorometro = idHorometro;
    }

    public Horometros(Integer idHorometro, int idEquipo, int horasActividad, String diaActividad) {
        this.idHorometro = idHorometro;
        this.idEquipo = idEquipo;
        this.horasActividad = horasActividad;
        this.diaActividad = diaActividad;
    }

    public Integer getIdHorometro() {
        return idHorometro;
    }

    public void setIdHorometro(Integer idHorometro) {
        this.idHorometro = idHorometro;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getHorasActividad() {
        return horasActividad;
    }

    public void setHorasActividad(int horasActividad) {
        this.horasActividad = horasActividad;
    }

    public Integer getMinutoActividad() {
        return minutoActividad;
    }

    public void setMinutoActividad(Integer minutoActividad) {
        this.minutoActividad = minutoActividad;
    }

    public Integer getSegundoActividad() {
        return segundoActividad;
    }

    public void setSegundoActividad(Integer segundoActividad) {
        this.segundoActividad = segundoActividad;
    }

    public String getDiaActividad() {
        return diaActividad;
    }

    public void setDiaActividad(String diaActividad) {
        this.diaActividad = diaActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHorometro != null ? idHorometro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horometros)) {
            return false;
        }
        Horometros other = (Horometros) object;
        if ((this.idHorometro == null && other.idHorometro != null) || (this.idHorometro != null && !this.idHorometro.equals(other.idHorometro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Horometros[ idHorometro=" + idHorometro + " ]";
    }
    
}
