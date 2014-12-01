package com.keitsen.demo.basic.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.keitsen.demo.module.user.entity.User;


@SuppressWarnings("serial")
@MappedSuperclass
public class UUIDEntity extends AbstractEntity {

	protected String id;
	
	protected User creator;
	
	protected User modifier;
	

	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	@ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creator")
	@Fetch(FetchMode.SELECT)
    @Basic(fetch = FetchType.LAZY)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "modifier")
	@Fetch(FetchMode.SELECT)
    @Basic(fetch = FetchType.LAZY)
	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UUIDEntity other = (UUIDEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	
	
	
}
