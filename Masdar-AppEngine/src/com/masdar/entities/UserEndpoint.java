package com.masdar.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;
import com.masdar.EMF;

@Api(name = "userendpoint", namespace = @ApiNamespace(ownerDomain = "masdar.com", ownerName = "masdar.com", packagePath = "entities"))
public class UserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore. It uses HTTP
	 * GET method and paging support.
	 * 
	 * @return A CollectionResponse class containing the list of all entities
	 *         persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUser")
	public CollectionResponse<User> listUser(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from User as User");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<User>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (User obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	// Amr_Abdelkader - 2/1/2014 - getFollowingByUser
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "getFollowingByUser")
	public CollectionResponse<User> getFollowingByUser(
			@Named("userId") Long userId,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<User> execute = new ArrayList<User>();
		List<Long> usersIds = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("SELECT f.following_user_id FROM Followers f WHERE f.follower_user_id = "
							+ userId);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			usersIds = (List<Long>) query.getResultList();
			cursor = JPACursorHelper.getCursor(usersIds);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and
			// accomodate
			// for lazy fetch.
			for (Long id : usersIds) {
				User follower = getUser(id);
				if (follower != null) {
					execute.add(follower);
				}
			}

		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	@ApiMethod(name = "getSocialIdeaUsers" , path="getSocialIdeaUsers")
	public CollectionResponse<User> getSocialIdeaUsers(
			@Named("ideaId") Long ideaId) {
		EntityManager mgr = null;
		List<User> execute = new ArrayList<User>();
		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select s.user_id from SocialIdeaUser as s where s.idea_id = " + ideaId);

			@SuppressWarnings("unchecked")
			List<Long> tempExecute = (List<Long>) query.getResultList();
			if (tempExecute != null && tempExecute.size() > 0) {
				for (Long id : tempExecute) {
					User socialUser = getUser(id);
					if (socialUser != null) {
						execute.add(socialUser);
					}
				}
			}
		} finally {
			mgr.close();
		}
		return CollectionResponse.<User> builder().setItems(execute).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET
	 * method.
	 * 
	 * @param id
	 *            the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUser")
	public User getUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		User user = null;
		try {
			user = mgr.find(User.class, id);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity
	 * already exists in the datastore, an exception is thrown. It uses HTTP
	 * POST method.
	 * 
	 * @param user
	 *            the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUser")
	public User insertUser(User user) {
		EntityManager mgr = getEntityManager();
		try {
			// if (containsUser(user)) {
			// throw new EntityExistsException("Object already exists");
			// }
			mgr.persist(user);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does
	 * not exist in the datastore, an exception is thrown. It uses HTTP PUT
	 * method.
	 * 
	 * @param user
	 *            the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUser")
	public User updateUser(User user) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUser(user)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(user);
		} finally {
			mgr.close();
		}
		return user;
	}

	/**
	 * This method removes the entity with primary key id. It uses HTTP DELETE
	 * method.
	 * 
	 * @param id
	 *            the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUser")
	public void removeUser(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			User user = mgr.find(User.class, id);
			mgr.remove(user);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUser(User user) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			User item = mgr.find(User.class, user.getUser_id());
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
