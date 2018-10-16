package coop.tecso.examen.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Abstract base persistent object that provides object identification and basic
 * auditory fields.
 */
@MappedSuperclass
public abstract class AbstractPersistentObject implements Serializable {

	private static final long serialVersionUID = -975560023284258938L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private long versionNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date creationTimestamp;
    

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modificationTimestamp;



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public long getVersionNumber() {
        return versionNumber;
    }


    public void setVersionNumber(long versionNumber) {
        this.versionNumber = versionNumber;
    }


    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }


    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }


    public Date getCreationTimestamp() {
        return creationTimestamp;
    }


    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    
    @Override
    public boolean equals(Object other) {
        if (super.equals(other)) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        AbstractPersistentObject persistentObject = (AbstractPersistentObject) other;
        return getId() != null && getId().equals(persistentObject.getId());

    }


    @Override
    public int hashCode() {

        if (getId() != null) {
            return this.getClass().hashCode() + getId().hashCode();
        } else {
            return super.hashCode();
        }
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass());
        sb.append(" ID:");
        sb.append(id);
        return sb.toString();
    }

    @Transient
    public String getDescription() {
        return toString();
    }

}
