package sk.seges.corpis.server.domain.jpa;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import sk.seges.corpis.server.domain.server.model.base.RegionCoreBase;

@MappedSuperclass
public abstract class JpaRegionCore extends RegionCoreBase {

	private static final long serialVersionUID = 5925648057837515258L;
	
	@Override
	@Column
	public String getDescription() {
		return super.getDescription();
	}

	@Override
	@Column
	public String getName() {
		return super.getName();
	}
}