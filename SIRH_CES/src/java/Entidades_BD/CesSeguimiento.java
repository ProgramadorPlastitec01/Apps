/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades_BD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prog.sistemas1
 */
@Entity
@Table(name = "ces_seguimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CesSeguimiento.findAll", query = "SELECT c FROM CesSeguimiento c"),
    @NamedQuery(name = "CesSeguimiento.findByIdCesSeguimiento", query = "SELECT c FROM CesSeguimiento c WHERE c.idCesSeguimiento = :idCesSeguimiento"),
    @NamedQuery(name = "CesSeguimiento.findByDocumento", query = "SELECT c FROM CesSeguimiento c WHERE c.documento = :documento"),
    @NamedQuery(name = "CesSeguimiento.findByIdCargo", query = "SELECT c FROM CesSeguimiento c WHERE c.idCargo = :idCargo"),
    @NamedQuery(name = "CesSeguimiento.findByMotivo", query = "SELECT c FROM CesSeguimiento c WHERE c.motivo = :motivo"),
    @NamedQuery(name = "CesSeguimiento.findByEstado", query = "SELECT c FROM CesSeguimiento c WHERE c.estado = :estado"),
    @NamedQuery(name = "CesSeguimiento.findByAnio", query = "SELECT c FROM CesSeguimiento c WHERE c.anio = :anio"),
    @NamedQuery(name = "CesSeguimiento.findByMes", query = "SELECT c FROM CesSeguimiento c WHERE c.mes = :mes")})
public class CesSeguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ces_seguimiento")
    private Integer idCesSeguimiento;
    @Column(name = "documento")
    private Integer documento;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Lob
    @Column(name = "datos")
    private String datos;
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "estado")
    private String estado;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "mes")
    private Integer mes;
    @Lob
    @Column(name = "seg_1")
    private String seg1;
    @Lob
    @Column(name = "obs_1")
    private String obs1;
    @Lob
    @Column(name = "seg_2")
    private String seg2;
    @Lob
    @Column(name = "obs_2")
    private String obs2;
    @Lob
    @Column(name = "seg_3")
    private String seg3;
    @Lob
    @Column(name = "obs_3")
    private String obs3;
    @Lob
    @Column(name = "seg_4")
    private String seg4;
    @Lob
    @Column(name = "obs_4")
    private String obs4;
    @Lob
    @Column(name = "seg_5")
    private String seg5;
    @Lob
    @Column(name = "obs_5")
    private String obs5;
    @Lob
    @Column(name = "seg_6")
    private String seg6;
    @Lob
    @Column(name = "obs_6")
    private String obs6;
    @Lob
    @Column(name = "seg_7")
    private String seg7;
    @Lob
    @Column(name = "obs_7")
    private String obs7;
    @Lob
    @Column(name = "seg_8")
    private String seg8;
    @Lob
    @Column(name = "obs_8")
    private String obs8;
    @Lob
    @Column(name = "seg_9")
    private String seg9;
    @Lob
    @Column(name = "obs_9")
    private String obs9;
    @Lob
    @Column(name = "seg_10")
    private String seg10;
    @Lob
    @Column(name = "obs_10")
    private String obs10;
    @Lob
    @Column(name = "seg_11")
    private String seg11;
    @Lob
    @Column(name = "obs_11")
    private String obs11;
    @Lob
    @Column(name = "seg_12")
    private String seg12;
    @Lob
    @Column(name = "obs_12")
    private String obs12;
    @Lob
    @Column(name = "seg_13")
    private String seg13;
    @Lob
    @Column(name = "obs_13")
    private String obs13;
    @Lob
    @Column(name = "seg_14")
    private String seg14;
    @Lob
    @Column(name = "obs_14")
    private String obs14;
    @Lob
    @Column(name = "seg_15")
    private String seg15;
    @Lob
    @Column(name = "obs_15")
    private String obs15;
    @Lob
    @Column(name = "seg_16")
    private String seg16;
    @Lob
    @Column(name = "obs_16")
    private String obs16;
    @Lob
    @Column(name = "seg_17")
    private String seg17;
    @Lob
    @Column(name = "obs_17")
    private String obs17;
    @Lob
    @Column(name = "seg_18")
    private String seg18;
    @Lob
    @Column(name = "obs_18")
    private String obs18;
    @Lob
    @Column(name = "seg_19")
    private String seg19;
    @Lob
    @Column(name = "obs_19")
    private String obs19;
    @Lob
    @Column(name = "seg_20")
    private String seg20;
    @Lob
    @Column(name = "obs_20")
    private String obs20;
    @Lob
    @Column(name = "seg_21")
    private String seg21;
    @Lob
    @Column(name = "obs_21")
    private String obs21;
    @Lob
    @Column(name = "seg_22")
    private String seg22;
    @Lob
    @Column(name = "obs_22")
    private String obs22;
    @Lob
    @Column(name = "seg_23")
    private String seg23;
    @Lob
    @Column(name = "obs_23")
    private String obs23;
    @Lob
    @Column(name = "seg_24")
    private String seg24;
    @Lob
    @Column(name = "obs_24")
    private String obs24;
    @Lob
    @Column(name = "seg_25")
    private String seg25;
    @Lob
    @Column(name = "obs_25")
    private String obs25;
    @Lob
    @Column(name = "seg_26")
    private String seg26;
    @Lob
    @Column(name = "obs_26")
    private String obs26;
    @Lob
    @Column(name = "seg_27")
    private String seg27;
    @Lob
    @Column(name = "obs_27")
    private String obs27;
    @Lob
    @Column(name = "seg_28")
    private String seg28;
    @Lob
    @Column(name = "obs_28")
    private String obs28;
    @Lob
    @Column(name = "seg_29")
    private String seg29;
    @Lob
    @Column(name = "obs_29")
    private String obs29;
    @Lob
    @Column(name = "seg_30")
    private String seg30;
    @Lob
    @Column(name = "obs_30")
    private String obs30;
    @Lob
    @Column(name = "seg_31")
    private String seg31;
    @Lob
    @Column(name = "obs_31")
    private String obs31;

    public CesSeguimiento() {
    }

    public CesSeguimiento(Integer idCesSeguimiento) {
        this.idCesSeguimiento = idCesSeguimiento;
    }

    public Integer getIdCesSeguimiento() {
        return idCesSeguimiento;
    }

    public void setIdCesSeguimiento(Integer idCesSeguimiento) {
        this.idCesSeguimiento = idCesSeguimiento;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getSeg1() {
        return seg1;
    }

    public void setSeg1(String seg1) {
        this.seg1 = seg1;
    }

    public String getObs1() {
        return obs1;
    }

    public void setObs1(String obs1) {
        this.obs1 = obs1;
    }

    public String getSeg2() {
        return seg2;
    }

    public void setSeg2(String seg2) {
        this.seg2 = seg2;
    }

    public String getObs2() {
        return obs2;
    }

    public void setObs2(String obs2) {
        this.obs2 = obs2;
    }

    public String getSeg3() {
        return seg3;
    }

    public void setSeg3(String seg3) {
        this.seg3 = seg3;
    }

    public String getObs3() {
        return obs3;
    }

    public void setObs3(String obs3) {
        this.obs3 = obs3;
    }

    public String getSeg4() {
        return seg4;
    }

    public void setSeg4(String seg4) {
        this.seg4 = seg4;
    }

    public String getObs4() {
        return obs4;
    }

    public void setObs4(String obs4) {
        this.obs4 = obs4;
    }

    public String getSeg5() {
        return seg5;
    }

    public void setSeg5(String seg5) {
        this.seg5 = seg5;
    }

    public String getObs5() {
        return obs5;
    }

    public void setObs5(String obs5) {
        this.obs5 = obs5;
    }

    public String getSeg6() {
        return seg6;
    }

    public void setSeg6(String seg6) {
        this.seg6 = seg6;
    }

    public String getObs6() {
        return obs6;
    }

    public void setObs6(String obs6) {
        this.obs6 = obs6;
    }

    public String getSeg7() {
        return seg7;
    }

    public void setSeg7(String seg7) {
        this.seg7 = seg7;
    }

    public String getObs7() {
        return obs7;
    }

    public void setObs7(String obs7) {
        this.obs7 = obs7;
    }

    public String getSeg8() {
        return seg8;
    }

    public void setSeg8(String seg8) {
        this.seg8 = seg8;
    }

    public String getObs8() {
        return obs8;
    }

    public void setObs8(String obs8) {
        this.obs8 = obs8;
    }

    public String getSeg9() {
        return seg9;
    }

    public void setSeg9(String seg9) {
        this.seg9 = seg9;
    }

    public String getObs9() {
        return obs9;
    }

    public void setObs9(String obs9) {
        this.obs9 = obs9;
    }

    public String getSeg10() {
        return seg10;
    }

    public void setSeg10(String seg10) {
        this.seg10 = seg10;
    }

    public String getObs10() {
        return obs10;
    }

    public void setObs10(String obs10) {
        this.obs10 = obs10;
    }

    public String getSeg11() {
        return seg11;
    }

    public void setSeg11(String seg11) {
        this.seg11 = seg11;
    }

    public String getObs11() {
        return obs11;
    }

    public void setObs11(String obs11) {
        this.obs11 = obs11;
    }

    public String getSeg12() {
        return seg12;
    }

    public void setSeg12(String seg12) {
        this.seg12 = seg12;
    }

    public String getObs12() {
        return obs12;
    }

    public void setObs12(String obs12) {
        this.obs12 = obs12;
    }

    public String getSeg13() {
        return seg13;
    }

    public void setSeg13(String seg13) {
        this.seg13 = seg13;
    }

    public String getObs13() {
        return obs13;
    }

    public void setObs13(String obs13) {
        this.obs13 = obs13;
    }

    public String getSeg14() {
        return seg14;
    }

    public void setSeg14(String seg14) {
        this.seg14 = seg14;
    }

    public String getObs14() {
        return obs14;
    }

    public void setObs14(String obs14) {
        this.obs14 = obs14;
    }

    public String getSeg15() {
        return seg15;
    }

    public void setSeg15(String seg15) {
        this.seg15 = seg15;
    }

    public String getObs15() {
        return obs15;
    }

    public void setObs15(String obs15) {
        this.obs15 = obs15;
    }

    public String getSeg16() {
        return seg16;
    }

    public void setSeg16(String seg16) {
        this.seg16 = seg16;
    }

    public String getObs16() {
        return obs16;
    }

    public void setObs16(String obs16) {
        this.obs16 = obs16;
    }

    public String getSeg17() {
        return seg17;
    }

    public void setSeg17(String seg17) {
        this.seg17 = seg17;
    }

    public String getObs17() {
        return obs17;
    }

    public void setObs17(String obs17) {
        this.obs17 = obs17;
    }

    public String getSeg18() {
        return seg18;
    }

    public void setSeg18(String seg18) {
        this.seg18 = seg18;
    }

    public String getObs18() {
        return obs18;
    }

    public void setObs18(String obs18) {
        this.obs18 = obs18;
    }

    public String getSeg19() {
        return seg19;
    }

    public void setSeg19(String seg19) {
        this.seg19 = seg19;
    }

    public String getObs19() {
        return obs19;
    }

    public void setObs19(String obs19) {
        this.obs19 = obs19;
    }

    public String getSeg20() {
        return seg20;
    }

    public void setSeg20(String seg20) {
        this.seg20 = seg20;
    }

    public String getObs20() {
        return obs20;
    }

    public void setObs20(String obs20) {
        this.obs20 = obs20;
    }

    public String getSeg21() {
        return seg21;
    }

    public void setSeg21(String seg21) {
        this.seg21 = seg21;
    }

    public String getObs21() {
        return obs21;
    }

    public void setObs21(String obs21) {
        this.obs21 = obs21;
    }

    public String getSeg22() {
        return seg22;
    }

    public void setSeg22(String seg22) {
        this.seg22 = seg22;
    }

    public String getObs22() {
        return obs22;
    }

    public void setObs22(String obs22) {
        this.obs22 = obs22;
    }

    public String getSeg23() {
        return seg23;
    }

    public void setSeg23(String seg23) {
        this.seg23 = seg23;
    }

    public String getObs23() {
        return obs23;
    }

    public void setObs23(String obs23) {
        this.obs23 = obs23;
    }

    public String getSeg24() {
        return seg24;
    }

    public void setSeg24(String seg24) {
        this.seg24 = seg24;
    }

    public String getObs24() {
        return obs24;
    }

    public void setObs24(String obs24) {
        this.obs24 = obs24;
    }

    public String getSeg25() {
        return seg25;
    }

    public void setSeg25(String seg25) {
        this.seg25 = seg25;
    }

    public String getObs25() {
        return obs25;
    }

    public void setObs25(String obs25) {
        this.obs25 = obs25;
    }

    public String getSeg26() {
        return seg26;
    }

    public void setSeg26(String seg26) {
        this.seg26 = seg26;
    }

    public String getObs26() {
        return obs26;
    }

    public void setObs26(String obs26) {
        this.obs26 = obs26;
    }

    public String getSeg27() {
        return seg27;
    }

    public void setSeg27(String seg27) {
        this.seg27 = seg27;
    }

    public String getObs27() {
        return obs27;
    }

    public void setObs27(String obs27) {
        this.obs27 = obs27;
    }

    public String getSeg28() {
        return seg28;
    }

    public void setSeg28(String seg28) {
        this.seg28 = seg28;
    }

    public String getObs28() {
        return obs28;
    }

    public void setObs28(String obs28) {
        this.obs28 = obs28;
    }

    public String getSeg29() {
        return seg29;
    }

    public void setSeg29(String seg29) {
        this.seg29 = seg29;
    }

    public String getObs29() {
        return obs29;
    }

    public void setObs29(String obs29) {
        this.obs29 = obs29;
    }

    public String getSeg30() {
        return seg30;
    }

    public void setSeg30(String seg30) {
        this.seg30 = seg30;
    }

    public String getObs30() {
        return obs30;
    }

    public void setObs30(String obs30) {
        this.obs30 = obs30;
    }

    public String getSeg31() {
        return seg31;
    }

    public void setSeg31(String seg31) {
        this.seg31 = seg31;
    }

    public String getObs31() {
        return obs31;
    }

    public void setObs31(String obs31) {
        this.obs31 = obs31;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCesSeguimiento != null ? idCesSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CesSeguimiento)) {
            return false;
        }
        CesSeguimiento other = (CesSeguimiento) object;
        if ((this.idCesSeguimiento == null && other.idCesSeguimiento != null) || (this.idCesSeguimiento != null && !this.idCesSeguimiento.equals(other.idCesSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades_BD.CesSeguimiento[ idCesSeguimiento=" + idCesSeguimiento + " ]";
    }
    
}
