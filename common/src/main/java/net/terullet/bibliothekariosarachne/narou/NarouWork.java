package net.terullet.bibliothekariosarachne.narou;

public class NarouWork {
	private final String ncode;
	public String getNcode() {
		return this.ncode;
	}

	private final String numericalId;
	public String getNumericalId() {
		return this.numericalId;
	}

	public NarouWork(String ncode, String numericalId) {
		this.ncode = ncode;
		this.numericalId = numericalId;
	}
}
