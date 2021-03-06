/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2013-12-19 23:55:21 UTC)
 * on 2014-01-13 at 01:56:22 UTC 
 * Modify at your own risk.
 */

package com.masdar.entities.votinguserendpoint;

/**
 * Service definition for Votinguserendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link VotinguserendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Votinguserendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the votinguserendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://masdarengine.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "votinguserendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Votinguserendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Votinguserendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getVotesUsersByIdeaId".
   *
   * This request holds the parameters needed by the the votinguserendpoint server.  After setting any
   * optional parameters, call the {@link GetVotesUsersByIdeaId#execute()} method to invoke the remote
   * operation.
   *
   * @param ideaId
   * @return the request
   */
  public GetVotesUsersByIdeaId getVotesUsersByIdeaId(java.lang.Long ideaId) throws java.io.IOException {
    GetVotesUsersByIdeaId result = new GetVotesUsersByIdeaId(ideaId);
    initialize(result);
    return result;
  }

  public class GetVotesUsersByIdeaId extends VotinguserendpointRequest<com.masdar.entities.votinguserendpoint.model.CollectionResponseVotingUser> {

    private static final String REST_PATH = "collectionresponse_votinguser/{ideaId}";

    /**
     * Create a request for the method "getVotesUsersByIdeaId".
     *
     * This request holds the parameters needed by the the votinguserendpoint server.  After setting
     * any optional parameters, call the {@link GetVotesUsersByIdeaId#execute()} method to invoke the
     * remote operation. <p> {@link GetVotesUsersByIdeaId#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param ideaId
     * @since 1.13
     */
    protected GetVotesUsersByIdeaId(java.lang.Long ideaId) {
      super(Votinguserendpoint.this, "GET", REST_PATH, null, com.masdar.entities.votinguserendpoint.model.CollectionResponseVotingUser.class);
      this.ideaId = com.google.api.client.util.Preconditions.checkNotNull(ideaId, "Required parameter ideaId must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetVotesUsersByIdeaId setAlt(java.lang.String alt) {
      return (GetVotesUsersByIdeaId) super.setAlt(alt);
    }

    @Override
    public GetVotesUsersByIdeaId setFields(java.lang.String fields) {
      return (GetVotesUsersByIdeaId) super.setFields(fields);
    }

    @Override
    public GetVotesUsersByIdeaId setKey(java.lang.String key) {
      return (GetVotesUsersByIdeaId) super.setKey(key);
    }

    @Override
    public GetVotesUsersByIdeaId setOauthToken(java.lang.String oauthToken) {
      return (GetVotesUsersByIdeaId) super.setOauthToken(oauthToken);
    }

    @Override
    public GetVotesUsersByIdeaId setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetVotesUsersByIdeaId) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetVotesUsersByIdeaId setQuotaUser(java.lang.String quotaUser) {
      return (GetVotesUsersByIdeaId) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetVotesUsersByIdeaId setUserIp(java.lang.String userIp) {
      return (GetVotesUsersByIdeaId) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long ideaId;

    /**

     */
    public java.lang.Long getIdeaId() {
      return ideaId;
    }

    public GetVotesUsersByIdeaId setIdeaId(java.lang.Long ideaId) {
      this.ideaId = ideaId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public GetVotesUsersByIdeaId setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public GetVotesUsersByIdeaId setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public GetVotesUsersByIdeaId set(String parameterName, Object value) {
      return (GetVotesUsersByIdeaId) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getVotingUser".
   *
   * This request holds the parameters needed by the the votinguserendpoint server.  After setting any
   * optional parameters, call the {@link GetVotingUser#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetVotingUser getVotingUser(java.lang.Long id) throws java.io.IOException {
    GetVotingUser result = new GetVotingUser(id);
    initialize(result);
    return result;
  }

  public class GetVotingUser extends VotinguserendpointRequest<com.masdar.entities.votinguserendpoint.model.VotingUser> {

    private static final String REST_PATH = "votinguser/{id}";

    /**
     * Create a request for the method "getVotingUser".
     *
     * This request holds the parameters needed by the the votinguserendpoint server.  After setting
     * any optional parameters, call the {@link GetVotingUser#execute()} method to invoke the remote
     * operation. <p> {@link GetVotingUser#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetVotingUser(java.lang.Long id) {
      super(Votinguserendpoint.this, "GET", REST_PATH, null, com.masdar.entities.votinguserendpoint.model.VotingUser.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetVotingUser setAlt(java.lang.String alt) {
      return (GetVotingUser) super.setAlt(alt);
    }

    @Override
    public GetVotingUser setFields(java.lang.String fields) {
      return (GetVotingUser) super.setFields(fields);
    }

    @Override
    public GetVotingUser setKey(java.lang.String key) {
      return (GetVotingUser) super.setKey(key);
    }

    @Override
    public GetVotingUser setOauthToken(java.lang.String oauthToken) {
      return (GetVotingUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetVotingUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetVotingUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetVotingUser setQuotaUser(java.lang.String quotaUser) {
      return (GetVotingUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetVotingUser setUserIp(java.lang.String userIp) {
      return (GetVotingUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetVotingUser setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetVotingUser set(String parameterName, Object value) {
      return (GetVotingUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertVotingUser".
   *
   * This request holds the parameters needed by the the votinguserendpoint server.  After setting any
   * optional parameters, call the {@link InsertVotingUser#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.masdar.entities.votinguserendpoint.model.VotingUser}
   * @return the request
   */
  public InsertVotingUser insertVotingUser(com.masdar.entities.votinguserendpoint.model.VotingUser content) throws java.io.IOException {
    InsertVotingUser result = new InsertVotingUser(content);
    initialize(result);
    return result;
  }

  public class InsertVotingUser extends VotinguserendpointRequest<com.masdar.entities.votinguserendpoint.model.VotingUser> {

    private static final String REST_PATH = "votinguser";

    /**
     * Create a request for the method "insertVotingUser".
     *
     * This request holds the parameters needed by the the votinguserendpoint server.  After setting
     * any optional parameters, call the {@link InsertVotingUser#execute()} method to invoke the
     * remote operation. <p> {@link InsertVotingUser#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.masdar.entities.votinguserendpoint.model.VotingUser}
     * @since 1.13
     */
    protected InsertVotingUser(com.masdar.entities.votinguserendpoint.model.VotingUser content) {
      super(Votinguserendpoint.this, "POST", REST_PATH, content, com.masdar.entities.votinguserendpoint.model.VotingUser.class);
    }

    @Override
    public InsertVotingUser setAlt(java.lang.String alt) {
      return (InsertVotingUser) super.setAlt(alt);
    }

    @Override
    public InsertVotingUser setFields(java.lang.String fields) {
      return (InsertVotingUser) super.setFields(fields);
    }

    @Override
    public InsertVotingUser setKey(java.lang.String key) {
      return (InsertVotingUser) super.setKey(key);
    }

    @Override
    public InsertVotingUser setOauthToken(java.lang.String oauthToken) {
      return (InsertVotingUser) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertVotingUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertVotingUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertVotingUser setQuotaUser(java.lang.String quotaUser) {
      return (InsertVotingUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertVotingUser setUserIp(java.lang.String userIp) {
      return (InsertVotingUser) super.setUserIp(userIp);
    }

    @Override
    public InsertVotingUser set(String parameterName, Object value) {
      return (InsertVotingUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listVotingUser".
   *
   * This request holds the parameters needed by the the votinguserendpoint server.  After setting any
   * optional parameters, call the {@link ListVotingUser#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListVotingUser listVotingUser() throws java.io.IOException {
    ListVotingUser result = new ListVotingUser();
    initialize(result);
    return result;
  }

  public class ListVotingUser extends VotinguserendpointRequest<com.masdar.entities.votinguserendpoint.model.CollectionResponseVotingUser> {

    private static final String REST_PATH = "votinguser";

    /**
     * Create a request for the method "listVotingUser".
     *
     * This request holds the parameters needed by the the votinguserendpoint server.  After setting
     * any optional parameters, call the {@link ListVotingUser#execute()} method to invoke the remote
     * operation. <p> {@link ListVotingUser#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListVotingUser() {
      super(Votinguserendpoint.this, "GET", REST_PATH, null, com.masdar.entities.votinguserendpoint.model.CollectionResponseVotingUser.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListVotingUser setAlt(java.lang.String alt) {
      return (ListVotingUser) super.setAlt(alt);
    }

    @Override
    public ListVotingUser setFields(java.lang.String fields) {
      return (ListVotingUser) super.setFields(fields);
    }

    @Override
    public ListVotingUser setKey(java.lang.String key) {
      return (ListVotingUser) super.setKey(key);
    }

    @Override
    public ListVotingUser setOauthToken(java.lang.String oauthToken) {
      return (ListVotingUser) super.setOauthToken(oauthToken);
    }

    @Override
    public ListVotingUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListVotingUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListVotingUser setQuotaUser(java.lang.String quotaUser) {
      return (ListVotingUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListVotingUser setUserIp(java.lang.String userIp) {
      return (ListVotingUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListVotingUser setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListVotingUser setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListVotingUser set(String parameterName, Object value) {
      return (ListVotingUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeVotingUser".
   *
   * This request holds the parameters needed by the the votinguserendpoint server.  After setting any
   * optional parameters, call the {@link RemoveVotingUser#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveVotingUser removeVotingUser(java.lang.Long id) throws java.io.IOException {
    RemoveVotingUser result = new RemoveVotingUser(id);
    initialize(result);
    return result;
  }

  public class RemoveVotingUser extends VotinguserendpointRequest<Void> {

    private static final String REST_PATH = "votinguser/{id}";

    /**
     * Create a request for the method "removeVotingUser".
     *
     * This request holds the parameters needed by the the votinguserendpoint server.  After setting
     * any optional parameters, call the {@link RemoveVotingUser#execute()} method to invoke the
     * remote operation. <p> {@link RemoveVotingUser#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveVotingUser(java.lang.Long id) {
      super(Votinguserendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveVotingUser setAlt(java.lang.String alt) {
      return (RemoveVotingUser) super.setAlt(alt);
    }

    @Override
    public RemoveVotingUser setFields(java.lang.String fields) {
      return (RemoveVotingUser) super.setFields(fields);
    }

    @Override
    public RemoveVotingUser setKey(java.lang.String key) {
      return (RemoveVotingUser) super.setKey(key);
    }

    @Override
    public RemoveVotingUser setOauthToken(java.lang.String oauthToken) {
      return (RemoveVotingUser) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveVotingUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveVotingUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveVotingUser setQuotaUser(java.lang.String quotaUser) {
      return (RemoveVotingUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveVotingUser setUserIp(java.lang.String userIp) {
      return (RemoveVotingUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveVotingUser setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveVotingUser set(String parameterName, Object value) {
      return (RemoveVotingUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateVotingUser".
   *
   * This request holds the parameters needed by the the votinguserendpoint server.  After setting any
   * optional parameters, call the {@link UpdateVotingUser#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.masdar.entities.votinguserendpoint.model.VotingUser}
   * @return the request
   */
  public UpdateVotingUser updateVotingUser(com.masdar.entities.votinguserendpoint.model.VotingUser content) throws java.io.IOException {
    UpdateVotingUser result = new UpdateVotingUser(content);
    initialize(result);
    return result;
  }

  public class UpdateVotingUser extends VotinguserendpointRequest<com.masdar.entities.votinguserendpoint.model.VotingUser> {

    private static final String REST_PATH = "votinguser";

    /**
     * Create a request for the method "updateVotingUser".
     *
     * This request holds the parameters needed by the the votinguserendpoint server.  After setting
     * any optional parameters, call the {@link UpdateVotingUser#execute()} method to invoke the
     * remote operation. <p> {@link UpdateVotingUser#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.masdar.entities.votinguserendpoint.model.VotingUser}
     * @since 1.13
     */
    protected UpdateVotingUser(com.masdar.entities.votinguserendpoint.model.VotingUser content) {
      super(Votinguserendpoint.this, "PUT", REST_PATH, content, com.masdar.entities.votinguserendpoint.model.VotingUser.class);
    }

    @Override
    public UpdateVotingUser setAlt(java.lang.String alt) {
      return (UpdateVotingUser) super.setAlt(alt);
    }

    @Override
    public UpdateVotingUser setFields(java.lang.String fields) {
      return (UpdateVotingUser) super.setFields(fields);
    }

    @Override
    public UpdateVotingUser setKey(java.lang.String key) {
      return (UpdateVotingUser) super.setKey(key);
    }

    @Override
    public UpdateVotingUser setOauthToken(java.lang.String oauthToken) {
      return (UpdateVotingUser) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateVotingUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateVotingUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateVotingUser setQuotaUser(java.lang.String quotaUser) {
      return (UpdateVotingUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateVotingUser setUserIp(java.lang.String userIp) {
      return (UpdateVotingUser) super.setUserIp(userIp);
    }

    @Override
    public UpdateVotingUser set(String parameterName, Object value) {
      return (UpdateVotingUser) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Votinguserendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Votinguserendpoint}. */
    @Override
    public Votinguserendpoint build() {
      return new Votinguserendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link VotinguserendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setVotinguserendpointRequestInitializer(
        VotinguserendpointRequestInitializer votinguserendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(votinguserendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
