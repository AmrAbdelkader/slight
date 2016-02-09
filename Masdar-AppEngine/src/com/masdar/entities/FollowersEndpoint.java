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

@Api(name = "followersendpoint", namespace = @ApiNamespace(ownerDomain = "masdar.com", ownerName = "masdar.com", packagePath = "entities"))
public class FollowersEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listFollowers")
	public CollectionResponse<Followers> listFollowers(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Followers> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Followers as Followers");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Followers>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Followers obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Followers> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	
	
	
	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getFollowers")
	public Followers getFollowers(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Followers followers = null;
		try {
			followers = mgr.find(Followers.class, id);
		} finally {
			mgr.close();
		}
		return followers;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param followers the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertFollowers")
	public Followers insertFollowers(Followers followers) {
		EntityManager mgr = getEntityManager();
		try {
//			if (containsFollowers(followers)) {
//				throw new EntityExistsException("Object already exists");
//			}
			mgr.persist(followers);
		} finally {
			mgr.close();
		}
		return followers;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param followers the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateFollowers")
	public Followers updateFollowers(Followers followers) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsFollowers(followers)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(followers);
		} finally {
			mgr.close();
		}
		return followers;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeFollowers")
	public void removeFollowers(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Followers followers = mgr.find(Followers.class, id);
			mgr.remove(followers);
		} finally {
			mgr.close();
		}
	}

	private boolean containsFollowers(Followers followers) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Followers item = mgr.find(Followers.class,
					followers.getFollower_id());
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
