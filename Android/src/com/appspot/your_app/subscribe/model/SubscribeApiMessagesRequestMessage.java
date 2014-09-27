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
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-09-26 at 18:17:07 UTC 
 * Modify at your own risk.
 */

package com.appspot.your_app.subscribe.model;

/**
 * ProtoRPC message definition to provide inputs.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the Subscribe Service. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SubscribeApiMessagesRequestMessage extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean async;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String body;

  /**
   * ProtoRPC message definition to represents a email address object.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("email_addresses")
  private java.util.List<SubscribeApiMessagesEmailAddressMessage> emailAddresses;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("private_key")
  private java.lang.String privateKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("reply_to")
  private java.lang.String replyTo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sender;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String subject;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAsync() {
    return async;
  }

  /**
   * @param async async or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setAsync(java.lang.Boolean async) {
    this.async = async;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBody() {
    return body;
  }

  /**
   * @param body body or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setBody(java.lang.String body) {
    this.body = body;
    return this;
  }

  /**
   * ProtoRPC message definition to represents a email address object.
   * @return value or {@code null} for none
   */
  public java.util.List<SubscribeApiMessagesEmailAddressMessage> getEmailAddresses() {
    return emailAddresses;
  }

  /**
   * ProtoRPC message definition to represents a email address object.
   * @param emailAddresses emailAddresses or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setEmailAddresses(java.util.List<SubscribeApiMessagesEmailAddressMessage> emailAddresses) {
    this.emailAddresses = emailAddresses;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPrivateKey() {
    return privateKey;
  }

  /**
   * @param privateKey privateKey or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setPrivateKey(java.lang.String privateKey) {
    this.privateKey = privateKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getReplyTo() {
    return replyTo;
  }

  /**
   * @param replyTo replyTo or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setReplyTo(java.lang.String replyTo) {
    this.replyTo = replyTo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSender() {
    return sender;
  }

  /**
   * @param sender sender or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setSender(java.lang.String sender) {
    this.sender = sender;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSubject() {
    return subject;
  }

  /**
   * @param subject subject or {@code null} for none
   */
  public SubscribeApiMessagesRequestMessage setSubject(java.lang.String subject) {
    this.subject = subject;
    return this;
  }

  @Override
  public SubscribeApiMessagesRequestMessage set(String fieldName, Object value) {
    return (SubscribeApiMessagesRequestMessage) super.set(fieldName, value);
  }

  @Override
  public SubscribeApiMessagesRequestMessage clone() {
    return (SubscribeApiMessagesRequestMessage) super.clone();
  }

}