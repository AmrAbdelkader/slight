package com.masdar.entities;

import com.masdar.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "socialideauserendpoint", namespace = @ApiNamespace(ownerDomain = "masdar.com", ownerName = "masdar.com", packagePath = "entities"))
public class SocialIdeaUserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listSocialIdeaUser")
	public CollectionResponse<SocialIdeaUser> listSocialIdeaUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<SocialIdeaUser> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from SocialIdeaUser as SocialIdeaUser");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<SocialIdeaUser>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (SocialIdeaUser obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<SocialIdeaUser> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getSocialIdeaUser")
	public SocialIdeaUser getSocialIdeaUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		SocialIdeaUser socialideauser = null;
		try {
			socialideauser = mgr.find(SocialIdeaUser.class, id);
		} finally {
			mgr.close();
		}
		return socialideauser;
	}
	
	@ApiMethod(name = "getUserRole")
	public SocialIdeaUser getUserRole(@Named("ideaId") Long ideaId,@Named("userId") Long userId) {
		EntityManager mgr = null;
		SocialIdeaUser execute = null;
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from SocialIdeaUser as s where s.idea_id = "+ideaId+" AND s.user_id = "+userId);
			
			@SuppressWarnings("unchecked")
			List<SocialIdeaUser> tempExecute = (List<SocialIdeaUser>) query.getResultList();
			if(tempExecute != null && tempExecute.size() > 0){
				execute = tempExecute.get(0);
			}else{
				execute = new SocialIdeaUser();
				execute.setRole_id(2);
			}
		} finally {
			mgr.close();
		}
		return execute;
	}
	
	@ApiMethod(name = "getIdeaStatus" , path="getIdeaStatus")
	public SocialIdeaUser getIdeaStatus(@Named("ideaId") Long ideaId) {
		EntityManager mgr = null;
		SocialIdeaUser execute = null;
		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from SocialIdeaUser as s where s.idea_id = "+ideaId+" AND s.isLocked = 1");
			
			@SuppressWarnings("unchecked")
			List<SocialIdeaUser> tempExecute = (List<SocialIdeaUser>) query.getResultList();
			if(tempExecute != null && tempExecute.size() > 0){
				execute = tempExecute.get(0);
			}
			else{
				execute = new SocialIdeaUser();
				execute.setIsLocked(0);
			}
		} finally {
			mgr.close();
		}
		return execute;
	}
	
	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param socialideauser the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertSocialIdeaUser")
	public SocialIdeaUser insertSocialIdeaUser(SocialIdeaUser socialideauser) {
		EntityManager mgr = getEntityManager();
		try {
//			if (containsSocialIdeaUser(socialideauser)) {
//				throw new EntityExistsException("Object already exists");
//			}
			mgr.persist(socialideauser);
		} finally {
			mgr.close();
		}
		return socialideauser;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param socialideauser the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateSocialIdeaUser")
	public SocialIdeaUser updateSocialIdeaUser(SocialIdeaUser socialideauser) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsSocialIdeaUser(socialideauser)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(socialideauser);
		} finally {
			mgr.close();
		}
		return socialideauser;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeSocialIdeaUser")
	public void removeSocialIdeaUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			SocialIdeaUser socialideauser = mgr.find(SocialIdeaUser.class, id);
			mgr.remove(socialideauser);
		} finally {
			mgr.close();
		}
	}

	private boolean containsSocialIdeaUser(SocialIdeaUser socialideauser) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			SocialIdeaUser item = mgr.find(SocialIdeaUser.class,
					socialideauser.getSocial_idea_user_id());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
