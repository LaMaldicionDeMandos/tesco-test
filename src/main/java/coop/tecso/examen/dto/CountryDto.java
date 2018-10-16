package coop.tecso.examen.dto;

import java.io.Serializable;

public class CountryDto implements Serializable {
	

	private static final long serialVersionUID = -1854383574061855612L;

	private Long id;
	private String isoCode;
	private String name;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
