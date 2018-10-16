package coop.tecso.examen.model;

import javax.persistence.Entity;

@Entity
public class Country extends AbstractPersistentObject {

	private static final long serialVersionUID = -8901155893511467206L;
	
	private String isoCode;
	private String name;
	
	public String getIsoCode() {
		return isoCode;
	}
	
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
