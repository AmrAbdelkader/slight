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
 * on 2014-01-13 at 01:54:19 UTC 
 * Modify at your own risk.
 */

package com.masdar.entities.commentendpoint.model;

/**
 * Model definition for Comment.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the commentendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Comment extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("comment_date")
  private java.lang.String commentDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("comment_id") @com.google.api.client.json.JsonString
  private java.lang.Long commentId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("comment_text")
  private java.lang.String commentText;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("idea_id") @com.google.api.client.json.JsonString
  private java.lang.Long ideaId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("user_id") @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCommentDate() {
    return commentDate;
  }

  /**
   * @param commentDate commentDate or {@code null} for none
   */
  public Comment setCommentDate(java.lang.String commentDate) {
    this.commentDate = commentDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCommentId() {
    return commentId;
  }

  /**
   * @param commentId commentId or {@code null} for none
   */
  public Comment setCommentId(java.lang.Long commentId) {
    this.commentId = commentId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCommentText() {
    return commentText;
  }

  /**
   * @param commentText commentText or {@code null} for none
   */
  public Comment setCommentText(java.lang.String commentText) {
    this.commentText = commentText;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getIdeaId() {
    return ideaId;
  }

  /**
   * @param ideaId ideaId or {@code null} for none
   */
  public Comment setIdeaId(java.lang.Long ideaId) {
    this.ideaId = ideaId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public Comment setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public Comment set(String fieldName, Object value) {
    return (Comment) super.set(fieldName, value);
  }

  @Override
  public Comment clone() {
    return (Comment) super.clone();
  }

}
