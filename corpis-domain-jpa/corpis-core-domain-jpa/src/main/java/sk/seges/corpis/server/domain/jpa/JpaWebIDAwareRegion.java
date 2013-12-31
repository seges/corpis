package sk.seges.corpis.server.domain.jpa;

import javax.persistence.*;

import sk.seges.corpis.server.domain.server.model.data.RegionCoreData;
import sk.seges.corpis.server.domain.server.model.data.WebIDAwareRegionData;

@Entity
@Table(name = "webid_aware_region", uniqueConstraints = @UniqueConstraint(columnNames = {WebIDAwareRegionData.WEB_ID, RegionCoreData.NAME}))
@SequenceGenerator(name = JpaWebIDAwareRegion.SEQ_REGIONS, sequenceName = "seq_web_id_aware_regions", initialValue = 1)
public class JpaWebIDAwareRegion extends JpaRegionCore {

	private static final long serialVersionUID = 5176052095692907550L;

	protected static final String SEQ_REGIONS = "seqWebIDAwareRegions";

	private String webId;

	@Column
	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	@Id
	@Override
	@GeneratedValue(generator = SEQ_REGIONS)
	public Long getId() {
		return super.getId();
	}
}