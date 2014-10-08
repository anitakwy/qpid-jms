/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.qpid.jms.provider.amqp.message;

import java.util.Map;

import org.apache.qpid.proton.amqp.Symbol;
import org.apache.qpid.proton.message.Message;

/**
 * Support class containing constant values and static methods that are
 * used to map to / from AMQP Message types being sent or received.
 */
public final class AmqpMessageSupport {

    /**
     * The Annotation name to store the destination name that the Message
     * will be sent to.  The Message should also be tagged with the appropriate
     * destination attribute to allow the receiver to determine the correct
     * destination type.
     */
    public static final String AMQP_TO_ANNOTATION = "x-opt-to-type";

    /**
     * The Annotation name to store the destination name that the sender wants
     * to receive replies on.  The Message should also be tagged with the
     * appropriate destination attribute to allow the receiver to determine the
     * correct destination type.
     */
    public static final String AMQP_REPLY_TO_ANNOTATION = "x-opt-reply-type";

    /**
     * Attribute used to mark a destination as temporary.
     */
    public static final String TEMPORARY_ATTRIBUTE = "temporary";

    /**
     * Attribute used to mark a destination as being a Queue type.
     */
    public static final String QUEUE_ATTRIBUTES = "queue";

    /**
     * Attribute used to mark a destination as being a Topic type.
     */
    public static final String TOPIC_ATTRIBUTES = "topic";

    /**
     * Convenience value used to mark a destination as a Temporary Queue.
     */
    public static final String TEMP_QUEUE_ATTRIBUTES = TEMPORARY_ATTRIBUTE + "," + QUEUE_ATTRIBUTES;

    /**
     * Convenience value used to mark a destination as a Temporary Topic.
     */
    public static final String TEMP_TOPIC_ATTRIBUTES = TEMPORARY_ATTRIBUTE + "," + TOPIC_ATTRIBUTES;

    /**
     * Attribute used to mark the Application defined correlation Id that has been
     * set for the message.
     */
    public static final String JMS_APP_CORRELATION_ID = "x-opt-app-correlation-id";

    /**
     * Attribute used to mark the JMSType header string value set on the message by
     * an application.
     */
    public static final String JMS_TYPE = "x-opt-jms-type";

    /**
     * Attribute used to mark the class type of JMS message that a particular message
     * instance represents, used internally by the client.
     */
    public static final String JMS_MSG_TYPE = "x-opt-jms-msg-type";

    /**
     * Value mapping for JMS_MSG_TYPE which indicates the message is a generic JMS Message
     * which has no body.
     */
    public static final byte JMS_MESSAGE = 0;

    /**
     * Value mapping for JMS_MSG_TYPE which indicates the message is a JMS ObjectMessage
     * which has an Object value serialized in its message body.
     */
    public static final byte JMS_OBJECT_MESSAGE = 1;

    /**
     * Value mapping for JMS_MSG_TYPE which indicates the message is a JMS MapMessage
     * which has an Map instance serialized in its message body.
     */
    public static final byte JMS_MAP_MESSAGE = 2;

    /**
     * Value mapping for JMS_MSG_TYPE which indicates the message is a JMS BytesMessage
     * which has a body that consists of raw bytes.
     */
    public static final byte JMS_BYTES_MESSAGE = 3;

    /**
     * Value mapping for JMS_MSG_TYPE which indicates the message is a JMS StreamMessage
     * which has a body that is a structured collection of primitives values.
     */
    public static final byte JMS_STREAM_MESSAGE = 4;

    /**
     * Value mapping for JMS_MSG_TYPE which indicates the message is a JMS TextMessage
     * which has a body that contains a UTF-8 encoded String.
     */
    public static final byte JMS_TEXT_MESSAGE = 5;

    public static final String JMS_AMQP_TTL = "JMS_AMQP_TTL";
    public static final String JMS_AMQP_REPLY_TO_GROUP_ID = "JMS_AMQP_REPLY_TO_GROUP_ID";
    public static final String JMS_AMQP_TYPED_ENCODING = "JMS_AMQP_TYPED_ENCODING";

    /**
     * Content type used to mark Data sections as containing text.
     */
    public static final String TEXT_PLAIN_CONTENT_TYPE = "text/plain";

    /**
     * Content type used to mark Data sections as containing a serialized java object.
     */
    public static final String SERIALIZED_JAVA_OBJECT_CONTENT_TYPE = "application/x-java-serialized-object";

    /**
     * Content type used to mark Data sections as containing arbitrary bytes.
     */
    public static final String OCTET_STREAM_CONTENT_TYPE = "application/octet-stream";

    /**
     * Lookup and return the correct Proton Symbol instance based on the given key.
     *
     * @param key
     *        the String value name of the Symbol to locate.
     *
     * @return the Symbol value that matches the given key.
     */
    public static Symbol getSymbol(String key) {
        return Symbol.valueOf(key);
    }

    /**
     * Safe way to access message annotations which will check internal structure and
     * either return the annotation if it exists or null if the annotation or any annotations
     * are present.
     *
     * @param key
     *        the String key to use to lookup an annotation.
     * @param message
     *        the AMQP message object that is being examined.
     *
     * @return the given annotation value or null if not present in the message.
     */
    public static Object getMessageAnnotation(String key, Message message) {
        if (message != null && message.getMessageAnnotations() != null) {
            Map<Symbol, Object> annotations = message.getMessageAnnotations().getValue();
            return annotations.get(AmqpMessageSupport.getSymbol(key));
        }

        return null;
    }

    /**
     * Check whether the content-type field of the properties section (if present) in
     * the given message matches the provided string (where null matches if there is
     * no content type present.
     *
     * @param contentType
     *        content type string to compare against, or null if none
     * @param message
     *        the AMQP message object that is being examined.
     *
     * @return true if content type matches
     */
    public static boolean isContentType(String contentType, Message message) {
        if (contentType == null) {
            return message.getContentType() == null;
        } else {
            return contentType.equals(message.getContentType());
        }
    }
}
