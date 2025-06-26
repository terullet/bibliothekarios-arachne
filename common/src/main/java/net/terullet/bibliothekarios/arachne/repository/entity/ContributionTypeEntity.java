package net.terullet.bibliothekarios.arachne.repository.entity;

public final class ContributionTypeEntity {
	private final int typeId;
	private final String typeName;

	public ContributionTypeEntity(int typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public int getTypeId() {
		return this.typeId;
	}
	public String getTypeName() {
		return this.typeName;
	}
}
