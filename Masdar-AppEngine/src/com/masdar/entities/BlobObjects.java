package com.masdar.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlobObjects {
	@Id
	private Long blob_object_id;
	private String parent_blob_key;
	private String blob_object_key;
	/**
	 * @return the parent_blob_key
	 */
	public String getParent_blob_key() {
		return parent_blob_key;
	}
	/**
	 * @param parent_blob_key the parent_blob_key to set
	 */
	public void setParent_blob_key(String parent_blob_key) {
		this.parent_blob_key = parent_blob_key;
	}
	/**
	 * @return the blob_object_key
	 */
	public String getBlob_object_key() {
		return blob_object_key;
	}
	/**
	 * @param blob_object_key the blob_object_key to set
	 */
	public void setBlob_object_key(String blob_object_key) {
		this.blob_object_key = blob_object_key;
	}
	/**
	 * @return the blob_object_id
	 */
	public Long getBlob_object_id() {
		return blob_object_id;
	}
	
	
}
