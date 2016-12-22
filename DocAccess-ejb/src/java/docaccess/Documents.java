/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docaccess;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NoneDark
 */
@Entity
@Table(name = "DOCUMENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documents.findAll", query = "SELECT d FROM Documents d"),
    @NamedQuery(name = "Documents.findById", query = "SELECT d FROM Documents d WHERE d.id = :id"),
    @NamedQuery(name = "Documents.findByName", query = "SELECT d FROM Documents d WHERE d.name = :name"),
    @NamedQuery(name = "Documents.findByLockState", query = "SELECT d FROM Documents d WHERE d.lockState = :lockState"),
    @NamedQuery(name = "Documents.findByChangeTime", query = "SELECT d FROM Documents d WHERE d.changeTime = :changeTime")})
public class Documents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOCK_STATE")
    private short lockState;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "DOC_DATA")
    private String docData;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANGE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;
    @JoinColumn(name = "LAST_CHANGER", referencedColumnName = "ID")
    @ManyToOne
    private Users lastChanger;
    @JoinColumn(name = "LOCK_OWNER", referencedColumnName = "ID")
    @ManyToOne
    private Users lockOwner;

    public Documents() {
    }


    public Documents(String name, short lockState, String docData, Date changeTime) {
        this.name = name;
        this.lockState = lockState;
        this.docData = docData;
        this.changeTime = changeTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLockState() {
        return lockState;
    }

    public void setLockState(short lockState) {
        this.lockState = lockState;
    }

    public String getDocData() {
        return docData;
    }

    public void setDocData(String docData) {
        this.docData = docData;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Users getLastChanger() {
        return lastChanger;
    }

    public void setLastChanger(Users lastChanger) {
        this.lastChanger = lastChanger;
    }

    public Users getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(Users lockOwner) {
        this.lockOwner = lockOwner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documents)) {
            return false;
        }
        Documents other = (Documents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "docaccess.Documents[ id=" + id + " ]";
    }
    
}
