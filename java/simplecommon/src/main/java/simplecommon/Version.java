package simplecommon;

import java.io.Serializable;

public class Version implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -476621242614253935L;
	private String version;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
