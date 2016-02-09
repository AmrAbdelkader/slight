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
 * on 2014-01-13 at 01:57:04 UTC 
 * Modify at your own risk.
 */

package com.masdar.entities.followersendpoint;

/**
 * Service definition for Followersendpoint (v1).
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
 * This service uses {@link FollowersendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Followersendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the followersendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "followersendpoint/v1/";

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
  public Followersendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Followersendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getFollowers".
   *
   * This request holds the parameters needed by the the followersendpoint server.  After setting any
   * optional parameters, call the {@link GetFollowers#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetFollowers getFollowers(java.lang.Long id) throws java.io.IOException {
    GetFollowers result = new GetFollowers(id);
    initialize(result);
    return result;
  }

  public class GetFollowers extends FollowersendpointRequest<com.masdar.entities.followersendpoint.model.Followers> {

    private static final String REST_PATH = "followers/{id}";

    /**
     * Create a request for the method "getFollowers".
     *
     * This request holds the parameters needed by the the followersendpoint server.  After setting
     * any optional parameters, call the {@link GetFollowers#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetFollowers#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetFollowers(java.lang.Long id) {
      super(Followersendpoint.this, "GET", REST_PATH, null, com.masdar.entities.followersendpoint.model.Followers.class);
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
    public GetFollowers setAlt(java.lang.String alt) {
      return (GetFollowers) super.setAlt(alt);
    }

    @Override
    public GetFollowers setFields(java.lang.String fields) {
      return (GetFollowers) super.setFields(fields);
    }

    @Override
    public GetFollowers setKey(java.lang.String key) {
      return (GetFollowers) super.setKey(key);
    }

    @Override
    public GetFollowers setOauthToken(java.lang.String oauthToken) {
      return (GetFollowers) super.setOauthToken(oauthToken);
    }

    @Override
    public GetFollowers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetFollowers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetFollowers setQuotaUser(java.lang.String quotaUser) {
      return (GetFollowers) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetFollowers setUserIp(java.lang.String userIp) {
      return (GetFollowers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetFollowers setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetFollowers set(String parameterName, Object value) {
      return (GetFollowers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertFollowers".
   *
   * This request holds the parameters needed by the the followersendpoint server.  After setting any
   * optional parameters, call the {@link InsertFollowers#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.masdar.entities.followersendpoint.model.Followers}
   * @return the request
   */
  public InsertFollowers insertFollowers(com.masdar.entities.followersendpoint.model.Followers content) throws java.io.IOException {
    InsertFollowers result = new InsertFollowers(content);
    initialize(result);
    return result;
  }

  public class InsertFollowers extends FollowersendpointRequest<com.masdar.entities.followersendpoint.model.Followers> {

    private static final String REST_PATH = "followers";

    /**
     * Create a request for the method "insertFollowers".
     *
     * This request holds the parameters needed by the the followersendpoint server.  After setting
     * any optional parameters, call the {@link InsertFollowers#execute()} method to invoke the remote
     * operation. <p> {@link InsertFollowers#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.masdar.entities.followersendpoint.model.Followers}
     * @since 1.13
     */
    protected InsertFollowers(com.masdar.entities.followersendpoint.model.Followers content) {
      super(Followersendpoint.this, "POST", REST_PATH, content, com.masdar.entities.followersendpoint.model.Followers.class);
    }

    @Override
    public InsertFollowers setAlt(java.lang.String alt) {
      return (InsertFollowers) super.setAlt(alt);
    }

    @Override
    public InsertFollowers setFields(java.lang.String fields) {
      return (InsertFollowers) super.setFields(fields);
    }

    @Override
    public InsertFollowers setKey(java.lang.String key) {
      return (InsertFollowers) super.setKey(key);
    }

    @Override
    public InsertFollowers setOauthToken(java.lang.String oauthToken) {
      return (InsertFollowers) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertFollowers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertFollowers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertFollowers setQuotaUser(java.lang.String quotaUser) {
      return (InsertFollowers) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertFollowers setUserIp(java.lang.String userIp) {
      return (InsertFollowers) super.setUserIp(userIp);
    }

    @Override
    public InsertFollowers set(String parameterName, Object value) {
      return (InsertFollowers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listFollowers".
   *
   * This request holds the parameters needed by the the followersendpoint server.  After setting any
   * optional parameters, call the {@link ListFollowers#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListFollowers listFollowers() throws java.io.IOException {
    ListFollowers result = new ListFollowers();
    initialize(result);
    return result;
  }

  public class ListFollowers extends FollowersendpointRequest<com.masdar.entities.followersendpoint.model.CollectionResponseFollowers> {

    private static final String REST_PATH = "followers";

    /**
     * Create a request for the method "listFollowers".
     *
     * This request holds the parameters needed by the the followersendpoint server.  After setting
     * any optional parameters, call the {@link ListFollowers#execute()} method to invoke the remote
     * operation. <p> {@link ListFollowers#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListFollowers() {
      super(Followersendpoint.this, "GET", REST_PATH, null, com.masdar.entities.followersendpoint.model.CollectionResponseFollowers.class);
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
    public ListFollowers setAlt(java.lang.String alt) {
      return (ListFollowers) super.setAlt(alt);
    }

    @Override
    public ListFollowers setFields(java.lang.String fields) {
      return (ListFollowers) super.setFields(fields);
    }

    @Override
    public ListFollowers setKey(java.lang.String key) {
      return (ListFollowers) super.setKey(key);
    }

    @Override
    public ListFollowers setOauthToken(java.lang.String oauthToken) {
      return (ListFollowers) super.setOauthToken(oauthToken);
    }

    @Override
    public ListFollowers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListFollowers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListFollowers setQuotaUser(java.lang.String quotaUser) {
      return (ListFollowers) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListFollowers setUserIp(java.lang.String userIp) {
      return (ListFollowers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListFollowers setCursor(java.lang.String cursor) {
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

    public ListFollowers setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListFollowers set(String parameterName, Object value) {
      return (ListFollowers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeFollowers".
   *
   * This request holds the parameters needed by the the followersendpoint server.  After setting any
   * optional parameters, call the {@link RemoveFollowers#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveFollowers removeFollowers(java.lang.Long id) throws java.io.IOException {
    RemoveFollowers result = new RemoveFollowers(id);
    initialize(result);
    return result;
  }

  public class RemoveFollowers extends FollowersendpointRequest<Void> {

    private static final String REST_PATH = "followers/{id}";

    /**
     * Create a request for the method "removeFollowers".
     *
     * This request holds the parameters needed by the the followersendpoint server.  After setting
     * any optional parameters, call the {@link RemoveFollowers#execute()} method to invoke the remote
     * operation. <p> {@link RemoveFollowers#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveFollowers(java.lang.Long id) {
      super(Followersendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveFollowers setAlt(java.lang.String alt) {
      return (RemoveFollowers) super.setAlt(alt);
    }

    @Override
    public RemoveFollowers setFields(java.lang.String fields) {
      return (RemoveFollowers) super.setFields(fields);
    }

    @Override
    public RemoveFollowers setKey(java.lang.String key) {
      return (RemoveFollowers) super.setKey(key);
    }

    @Override
    public RemoveFollowers setOauthToken(java.lang.String oauthToken) {
      return (RemoveFollowers) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveFollowers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveFollowers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveFollowers setQuotaUser(java.lang.String quotaUser) {
      return (RemoveFollowers) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveFollowers setUserIp(java.lang.String userIp) {
      return (RemoveFollowers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveFollowers setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveFollowers set(String parameterName, Object value) {
      return (RemoveFollowers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateFollowers".
   *
   * This request holds the parameters needed by the the followersendpoint server.  After setting any
   * optional parameters, call the {@link UpdateFollowers#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.masdar.entities.followersendpoint.model.Followers}
   * @return the request
   */
  public UpdateFollowers updateFollowers(com.masdar.entities.followersendpoint.model.Followers content) throws java.io.IOException {
    UpdateFollowers result = new UpdateFollowers(content);
    initialize(result);
    return result;
  }

  public class UpdateFollowers extends FollowersendpointRequest<com.masdar.entities.followersendpoint.model.Followers> {

    private static final String REST_PATH = "followers";

    /**
     * Create a request for the method "updateFollowers".
     *
     * This request holds the parameters needed by the the followersendpoint server.  After setting
     * any optional parameters, call the {@link UpdateFollowers#execute()} method to invoke the remote
     * operation. <p> {@link UpdateFollowers#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.masdar.entities.followersendpoint.model.Followers}
     * @since 1.13
     */
    protected UpdateFollowers(com.masdar.entities.followersendpoint.model.Followers content) {
      super(Followersendpoint.this, "PUT", REST_PATH, content, com.masdar.entities.followersendpoint.model.Followers.class);
    }

    @Override
    public UpdateFollowers setAlt(java.lang.String alt) {
      return (UpdateFollowers) super.setAlt(alt);
    }

    @Override
    public UpdateFollowers setFields(java.lang.String fields) {
      return (UpdateFollowers) super.setFields(fields);
    }

    @Override
    public UpdateFollowers setKey(java.lang.String key) {
      return (UpdateFollowers) super.setKey(key);
    }

    @Override
    public UpdateFollowers setOauthToken(java.lang.String oauthToken) {
      return (UpdateFollowers) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateFollowers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateFollowers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateFollowers setQuotaUser(java.lang.String quotaUser) {
      return (UpdateFollowers) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateFollowers setUserIp(java.lang.String userIp) {
      return (UpdateFollowers) super.setUserIp(userIp);
    }

    @Override
    public UpdateFollowers set(String parameterName, Object value) {
      return (UpdateFollowers) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Followersendpoint}.
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

    /** Builds a new instance of {@link Followersendpoint}. */
    @Override
    public Followersendpoint build() {
      return new Followersendpoint(this);
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
     * Set the {@link FollowersendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setFollowersendpointRequestInitializer(
        FollowersendpointRequestInitializer followersendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(followersendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}