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

@Api(name = "votinguserendpoint", namespace = @ApiNamespace(ownerDomain = "masdar.com", ownerName = "masdar.com", packagePath = "entities"))
public class VotingUserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listVotingUser")
	public CollectionResponse<VotingUser> listVotingUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<VotingUser> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from VotingUser as VotingUser");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<VotingUser>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (VotingUser obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<VotingUser> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getVotesUsersByIdeaId")
	public CollectionResponse<VotingUser> getVotesUsersByIdeaId(@Named("ideaId") Long ideaId,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<VotingUser> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("SELECT x FROM VotingUser x WHERE x.vote_idea_id = "+ideaId);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<VotingUser>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (VotingUser obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<VotingUser> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getVotingUser")
	public VotingUser getVotingUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		VotingUser votinguser = null;
		try {
			votinguser = mgr.find(VotingUser.class, id);
		} finally {
			mgr.close();
		}
		return votinguser;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param votinguser the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertVotingUser")
	public VotingUser insertVotingUser(VotingUser votinguser) {
		EntityManager mgr = getEntityManager();
		try {
			//if (containsVotingUser(votinguser)) {
			//	throw new EntityExistsException("Object already exists");
			//}
			mgr.persist(votinguser);
		} finally {
			mgr.close();
		}
		return votinguser;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param votinguser the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateVotingUser")
	public VotingUser updateVotingUser(VotingUser votinguser) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsVotingUser(votinguser)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(votinguser);
		} finally {
			mgr.close();
		}
		return votinguser;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeVotingUser")
	public void removeVotingUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			VotingUser votinguser = mgr.find(VotingUser.class, id);
			mgr.remove(votinguser);
		} finally {
			mgr.close();
		}
	}

	private boolean containsVotingUser(VotingUser votinguser) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			VotingUser item = mgr.find(VotingUser.class,
					votinguser.getVote_id());
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
