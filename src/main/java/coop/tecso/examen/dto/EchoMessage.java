package coop.tecso.examen.dto;

import java.io.Serializable;

public class EchoMessage implements Serializable {

	private static final long serialVersionUID = -1737700610469175651L;

	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
