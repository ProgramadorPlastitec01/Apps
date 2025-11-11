/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Programador.TI2
 */
@Entity
@Table(name = "segmentation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Segmentation.findAll", query = "SELECT s FROM Segmentation s")
    , @NamedQuery(name = "Segmentation.findByIdSegmentation", query = "SELECT s FROM Segmentation s WHERE s.idSegmentation = :idSegmentation")
    , @NamedQuery(name = "Segmentation.findByPlastitecCode", query = "SELECT s FROM Segmentation s WHERE s.plastitecCode = :plastitecCode")
    , @NamedQuery(name = "Segmentation.findByDateRegister", query = "SELECT s FROM Segmentation s WHERE s.dateRegister = :dateRegister")
    , @NamedQuery(name = "Segmentation.findByType", query = "SELECT s FROM Segmentation s WHERE s.type = :type")
    , @NamedQuery(name = "Segmentation.findByBusinessName", query = "SELECT s FROM Segmentation s WHERE s.businessName = :businessName")
    , @NamedQuery(name = "Segmentation.findByKindPerson", query = "SELECT s FROM Segmentation s WHERE s.kindPerson = :kindPerson")
    , @NamedQuery(name = "Segmentation.findByFinalBeneficiary", query = "SELECT s FROM Segmentation s WHERE s.finalBeneficiary = :finalBeneficiary")
    , @NamedQuery(name = "Segmentation.findById", query = "SELECT s FROM Segmentation s WHERE s.id = :id")
    , @NamedQuery(name = "Segmentation.findByAddressBeneficiary", query = "SELECT s FROM Segmentation s WHERE s.addressBeneficiary = :addressBeneficiary")
    , @NamedQuery(name = "Segmentation.findByRepresentativeName", query = "SELECT s FROM Segmentation s WHERE s.representativeName = :representativeName")
    , @NamedQuery(name = "Segmentation.findByContactName", query = "SELECT s FROM Segmentation s WHERE s.contactName = :contactName")
    , @NamedQuery(name = "Segmentation.findByContactPosition", query = "SELECT s FROM Segmentation s WHERE s.contactPosition = :contactPosition")
    , @NamedQuery(name = "Segmentation.findByOperationsFrecuency", query = "SELECT s FROM Segmentation s WHERE s.operationsFrecuency = :operationsFrecuency")
    , @NamedQuery(name = "Segmentation.findByShoppingValueYear", query = "SELECT s FROM Segmentation s WHERE s.shoppingValueYear = :shoppingValueYear")
    , @NamedQuery(name = "Segmentation.findByRelationship", query = "SELECT s FROM Segmentation s WHERE s.relationship = :relationship")
    , @NamedQuery(name = "Segmentation.findByDateLinking", query = "SELECT s FROM Segmentation s WHERE s.dateLinking = :dateLinking")
    , @NamedQuery(name = "Segmentation.findByAntiquity", query = "SELECT s FROM Segmentation s WHERE s.antiquity = :antiquity")
    , @NamedQuery(name = "Segmentation.findByDateMonitoring", query = "SELECT s FROM Segmentation s WHERE s.dateMonitoring = :dateMonitoring")
    , @NamedQuery(name = "Segmentation.findByTypeService", query = "SELECT s FROM Segmentation s WHERE s.typeService = :typeService")
    , @NamedQuery(name = "Segmentation.findByPep", query = "SELECT s FROM Segmentation s WHERE s.pep = :pep")
    , @NamedQuery(name = "Segmentation.findByCountry", query = "SELECT s FROM Segmentation s WHERE s.country = :country")
    , @NamedQuery(name = "Segmentation.findByCity", query = "SELECT s FROM Segmentation s WHERE s.city = :city")
    , @NamedQuery(name = "Segmentation.findByAssociateSupplyChain", query = "SELECT s FROM Segmentation s WHERE s.associateSupplyChain = :associateSupplyChain")
    , @NamedQuery(name = "Segmentation.findByTypeDocumentation", query = "SELECT s FROM Segmentation s WHERE s.typeDocumentation = :typeDocumentation")
    , @NamedQuery(name = "Segmentation.findByQualcustom", query = "SELECT s FROM Segmentation s WHERE s.qualcustom = :qualcustom")
    , @NamedQuery(name = "Segmentation.findByQualMarket", query = "SELECT s FROM Segmentation s WHERE s.qualMarket = :qualMarket")
    , @NamedQuery(name = "Segmentation.findByQualCommercial", query = "SELECT s FROM Segmentation s WHERE s.qualCommercial = :qualCommercial")
    , @NamedQuery(name = "Segmentation.findByQualShipment", query = "SELECT s FROM Segmentation s WHERE s.qualShipment = :qualShipment")})
public class Segmentation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdSegmentation")
    private Integer idSegmentation;
    @Column(name = "PlastitecCode")
    private Integer plastitecCode;
    @Column(name = "DateRegister")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;
    @Column(name = "Type")
    private Integer type;
    @Column(name = "BusinessName")
    private String businessName;
    @Column(name = "KindPerson")
    private Integer kindPerson;
    @Column(name = "FinalBeneficiary")
    private Integer finalBeneficiary;
    @Column(name = "Id")
    private Integer id;
    @Column(name = "AddressBeneficiary")
    private String addressBeneficiary;
    @Column(name = "RepresentativeName")
    private String representativeName;
    @Column(name = "ContactName")
    private String contactName;
    @Column(name = "ContactPosition")
    private String contactPosition;
    @Column(name = "OperationsFrecuency")
    private String operationsFrecuency;
    @Column(name = "ShoppingValueYear")
    private String shoppingValueYear;
    @Column(name = "Relationship")
    private Integer relationship;
    @Column(name = "DateLinking")
    @Temporal(TemporalType.DATE)
    private Date dateLinking;
    @Column(name = "Antiquity")
    private Integer antiquity;
    @Column(name = "DateMonitoring")
    @Temporal(TemporalType.DATE)
    private Date dateMonitoring;
    @Column(name = "TypeService")
    private Integer typeService;
    @Column(name = "Pep")
    private Integer pep;
    @Column(name = "Country")
    private String country;
    @Column(name = "City")
    private String city;
    @Column(name = "AssociateSupplyChain")
    private Integer associateSupplyChain;
    @Column(name = "TypeDocumentation")
    private Integer typeDocumentation;
    @Column(name = "Qualcustom")
    private Integer qualcustom;
    @Column(name = "QualMarket")
    private Integer qualMarket;
    @Column(name = "QualCommercial")
    private Integer qualCommercial;
    @Column(name = "QualShipment")
    private Integer qualShipment;
    @OneToMany(mappedBy = "segmentation")
    private Collection<Controls> controlsCollection;
    @JoinColumn(name = "IdCodeCiiu", referencedColumnName = "IdCiiu")
    @ManyToOne
    private Ciiu ciiu;
    @JoinColumn(name = "IdCodeCiiuSecond", referencedColumnName = "IdCiiu")
    @ManyToOne
    private Ciiu ciiu1;

    public Segmentation() {
    }

    public Segmentation(Integer idSegmentation) {
        this.idSegmentation = idSegmentation;
    }

    public Integer getIdSegmentation() {
        return idSegmentation;
    }

    public void setIdSegmentation(Integer idSegmentation) {
        this.idSegmentation = idSegmentation;
    }

    public Integer getPlastitecCode() {
        return plastitecCode;
    }

    public void setPlastitecCode(Integer plastitecCode) {
        this.plastitecCode = plastitecCode;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getKindPerson() {
        return kindPerson;
    }

    public void setKindPerson(Integer kindPerson) {
        this.kindPerson = kindPerson;
    }

    public Integer getFinalBeneficiary() {
        return finalBeneficiary;
    }

    public void setFinalBeneficiary(Integer finalBeneficiary) {
        this.finalBeneficiary = finalBeneficiary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressBeneficiary() {
        return addressBeneficiary;
    }

    public void setAddressBeneficiary(String addressBeneficiary) {
        this.addressBeneficiary = addressBeneficiary;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPosition() {
        return contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public String getOperationsFrecuency() {
        return operationsFrecuency;
    }

    public void setOperationsFrecuency(String operationsFrecuency) {
        this.operationsFrecuency = operationsFrecuency;
    }

    public String getShoppingValueYear() {
        return shoppingValueYear;
    }

    public void setShoppingValueYear(String shoppingValueYear) {
        this.shoppingValueYear = shoppingValueYear;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public Date getDateLinking() {
        return dateLinking;
    }

    public void setDateLinking(Date dateLinking) {
        this.dateLinking = dateLinking;
    }

    public Integer getAntiquity() {
        return antiquity;
    }

    public void setAntiquity(Integer antiquity) {
        this.antiquity = antiquity;
    }

    public Date getDateMonitoring() {
        return dateMonitoring;
    }

    public void setDateMonitoring(Date dateMonitoring) {
        this.dateMonitoring = dateMonitoring;
    }

    public Integer getTypeService() {
        return typeService;
    }

    public void setTypeService(Integer typeService) {
        this.typeService = typeService;
    }

    public Integer getPep() {
        return pep;
    }

    public void setPep(Integer pep) {
        this.pep = pep;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAssociateSupplyChain() {
        return associateSupplyChain;
    }

    public void setAssociateSupplyChain(Integer associateSupplyChain) {
        this.associateSupplyChain = associateSupplyChain;
    }

    public Integer getTypeDocumentation() {
        return typeDocumentation;
    }

    public void setTypeDocumentation(Integer typeDocumentation) {
        this.typeDocumentation = typeDocumentation;
    }

    public Integer getQualcustom() {
        return qualcustom;
    }

    public void setQualcustom(Integer qualcustom) {
        this.qualcustom = qualcustom;
    }

    public Integer getQualMarket() {
        return qualMarket;
    }

    public void setQualMarket(Integer qualMarket) {
        this.qualMarket = qualMarket;
    }

    public Integer getQualCommercial() {
        return qualCommercial;
    }

    public void setQualCommercial(Integer qualCommercial) {
        this.qualCommercial = qualCommercial;
    }

    public Integer getQualShipment() {
        return qualShipment;
    }

    public void setQualShipment(Integer qualShipment) {
        this.qualShipment = qualShipment;
    }

    @XmlTransient
    public Collection<Controls> getControlsCollection() {
        return controlsCollection;
    }

    public void setControlsCollection(Collection<Controls> controlsCollection) {
        this.controlsCollection = controlsCollection;
    }

    public Ciiu getCiiu() {
        return ciiu;
    }

    public void setCiiu(Ciiu ciiu) {
        this.ciiu = ciiu;
    }

    public Ciiu getCiiu1() {
        return ciiu1;
    }

    public void setCiiu1(Ciiu ciiu1) {
        this.ciiu1 = ciiu1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSegmentation != null ? idSegmentation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmentation)) {
            return false;
        }
        Segmentation other = (Segmentation) object;
        if ((this.idSegmentation == null && other.idSegmentation != null) || (this.idSegmentation != null && !this.idSegmentation.equals(other.idSegmentation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mail.Segmentation[ idSegmentation=" + idSegmentation + " ]";
    }
    
}
