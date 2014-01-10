package sk.seges.corpis.server.domain.product.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sk.seges.corpis.server.domain.DBNamespace;
import sk.seges.corpis.server.domain.product.server.model.base.TagBase;
import sk.seges.corpis.server.domain.product.server.model.data.TagData;
import sk.seges.corpis.server.domain.product.server.model.data.TagNameData;
import sk.seges.corpis.shared.domain.product.EAssignmentTagsType;
import sk.seges.corpis.shared.domain.product.ESystemTagsType;

@Entity
@Table(name = DBNamespace.TABLE_PREFIX + "tag")
@SequenceGenerator(name = JpaTag.SEQ_TAG, sequenceName = DBNamespace.TABLE_PREFIX + "seg_tag", initialValue = 1)
public class JpaTag extends TagBase {

	private static final long serialVersionUID = -8053971923634270766L;

	protected static final String SEQ_TAG = "seqOleaTags";
	
	@Override
	@Id
	@GeneratedValue(generator = SEQ_TAG)
	public Long getId() {
		return super.getId();
	}

	@Override
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, targetEntity = JpaTagName.class)
	public List<TagNameData> getTagNames() {
		return super.getTagNames();
	}
	
	@Override
	@ManyToOne(targetEntity = JpaTag.class)
	public TagData getParent() {
		return super.getParent();
	}
	
	@Override
	@Column
	public Long getPriority() {
		return super.getPriority();
	}

	@Override
	@Column
	public Integer getIndex() {
		return super.getIndex();
	}

	@Column
	@Enumerated(EnumType.STRING)
	public ESystemTagsType getType() {
		return super.getType();
	}
	
	@Column 
	@Enumerated(EnumType.STRING)
	public EAssignmentTagsType getAssignmentType() {
		return super.getAssignmentType();
	}

	@Override
	@Column(nullable = false)
	public String getWebId() {
		return super.getWebId();
	}

	@Override
	public TagData clone() {
		TagData newTag = new JpaTag();

		TagData newParent = null;
		if (getParent() != null) {
			newParent = getParent().clone();
		}

		List<TagNameData> newTagNames = null;
		if (getTagNames() != null) {
			newTagNames = new ArrayList<TagNameData>();
			for (TagNameData tagName : getTagNames()) {
				newTagNames.add(tagName.clone());
			}
		}

		newTag.setId(getId());
		newTag.setParent(newParent);
		newTag.setPriority(getPriority());
		newTag.setTagNames(newTagNames);
		newTag.setWebId(getWebId());
		newTag.setIndex(getIndex());
		newTag.setType(getType());
		newTag.setAssignmentType(getAssignmentType());

		return newTag;
	}
}