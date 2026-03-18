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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Programador.TI1
 */
@Entity
@Table(name = "calificar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificar.findAll", query = "SELECT c FROM Calificar c")
    , @NamedQuery(name = "Calificar.findByIdCalificado", query = "SELECT c FROM Calificar c WHERE c.idCalificado = :idCalificado")
    , @NamedQuery(name = "Calificar.findByNombrePlano", query = "SELECT c FROM Calificar c WHERE c.nombrePlano = :nombrePlano")
    , @NamedQuery(name = "Calificar.findByDescripcion", query = "SELECT c FROM Calificar c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Calificar.findByMedidaEstandar", query = "SELECT c FROM Calificar c WHERE c.medidaEstandar = :medidaEstandar")
    , @NamedQuery(name = "Calificar.findByCumple", query = "SELECT c FROM Calificar c WHERE c.cumple = :cumple")
    , @NamedQuery(name = "Calificar.findByAplica", query = "SELECT c FROM Calificar c WHERE c.aplica = :aplica")})
public class Calificar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_calificado")
    private Integer idCalificado;
    @Basic(optional = false)
    @Column(name = "nombre_plano")
    private String nombrePlano;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "medida_estandar")
    private String medidaEstandar;
    @Basic(optional = false)
    @Column(name = "cumple")
    private String cumple;
    @Basic(optional = false)
    @Column(name = "aplica")
    private String aplica;
    @JoinColumn(name = "id_plano", referencedColumnName = "id_plano")
    @ManyToOne(optional = false)
    private Plano idPlano;
    @JoinColumn(name = "id_verificar", referencedColumnName = "idVerificar_etd")
    @ManyToOne(optional = false)
    private VerificarEtd idVerificar;

    public Calificar() {
    }

    public Calificar(Integer idCalificado) {
        this.idCalificado = idCalificado;
    }

    public Calificar(Integer idCalificado, String nombrePlano, String descripcion, String medidaEstandar, String cumple, String aplica) {
        this.idCalificado = idCalificado;
        this.nombrePlano = nombrePlano;
        this.descripcion = descripcion;
        this.medidaEstandar = medidaEstandar;
        this.cumple = cumple;
        this.aplica = aplica;
    }

    public Integer getIdCalificado() {
        return idCalificado;
    }

    public void setIdCalificado(Integer idCalificado) {
        this.idCalificado = idCalificado;
    }

    public String getNombrePlano() {
        return nombrePlano;
    }

    public void setNombrePlano(String nombrePlano) {
        this.nombrePlano = nombrePlano;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedidaEstandar() {
        return medidaEstandar;
    }

    public void setMedidaEstandar(String medidaEstandar) {
        this.medidaEstandar = medidaEstandar;
    }

    public String getCumple() {
        return cumple;
    }

    public void setCumple(String cumple) {
        this.cumple = cumple;
    }

    public String getAplica() {
        return aplica;
    }

    public void setAplica(String aplica) {
        this.aplica = aplica;
    }

    public Plano getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(Plano idPlano) {
        this.idPlano = idPlano;
    }

    public VerificarEtd getIdVerificar() {
        return idVerificar;
    }

    public void setIdVerificar(VerificarEtd idVerificar) {
        this.idVerificar = idVerificar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCalificado != null ? idCalificado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificar)) {
            return false;
        }
        Calificar other = (Calificar) object;
        if ((this.idCalificado == null && other.idCalificado != null) || (this.idCalificado != null && !this.idCalificado.equals(other.idCalificado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Calificar[ idCalificado=" + idCalificado + " ]";
    }
    
}
