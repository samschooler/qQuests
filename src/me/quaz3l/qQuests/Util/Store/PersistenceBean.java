package me.quaz3l.qQuests.Util.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "quest_data" )
public class PersistenceBean {
	@Column (unique=true) 
	@Id private Long id;
	
	// Columns
	@Column (unique=true) private String key;
	@Column private String value;
	
	// Getters
	public Long getId() { return id; }
	public String getKey() { return key; }
	public String getValue() { return value; }
	
	// Setters
	public void setId(Long id) { this.id = id; }
	public void setKey(String key) { this.key = key; }
	public void setValue(String value) { this.value = value; }
}
