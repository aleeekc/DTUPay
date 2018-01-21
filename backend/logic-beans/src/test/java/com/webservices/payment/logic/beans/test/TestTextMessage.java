package com.webservices.payment.logic.beans.test;

import javax.jms.Destination;
import javax.jms.TextMessage;
import java.util.Enumeration;
import java.util.HashMap;
/**
 * @author Lasse
 */
public class TestTextMessage implements TextMessage {

    private String text;

    private HashMap<String,String> stringProperties = new HashMap<>();
    private HashMap<String,Boolean> booleanProperties = new HashMap<>();
    private Destination replyTo;

    @Override
    public void setText(String text){
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getStringProperty(String property) {
        return stringProperties.get(property);
    }

    @Override
    public Destination getJMSReplyTo() {
        return replyTo;
    }

    @Override
    public void setJMSReplyTo(Destination destination){
        this.replyTo = destination;
    }

    @Override
    public void setStringProperty(String property, String value) {
        stringProperties.put(property,value);
    }

    @Override
    public void setBooleanProperty(String property, boolean value) {
        booleanProperties.put(property,value);
    }

    @Override
    public boolean getBooleanProperty(String property) {
        return booleanProperties.get(property);
    }























    @Override
    public String getJMSMessageID() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSMessageID(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public long getJMSTimestamp() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSTimestamp(long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSCorrelationIDAsBytes(byte[] bytes) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSCorrelationID(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public String getJMSCorrelationID() {
        throw new RuntimeException("Not Implemented in test Context");
    }





    @Override
    public Destination getJMSDestination() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSDestination(Destination destination) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public int getJMSDeliveryMode() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSDeliveryMode(int i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean getJMSRedelivered() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSRedelivered(boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public String getJMSType() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSType(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public long getJMSExpiration() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSExpiration(long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public long getJMSDeliveryTime() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSDeliveryTime(long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public int getJMSPriority() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setJMSPriority(int i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void clearProperties() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean propertyExists(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }



    @Override
    public byte getByteProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public short getShortProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public int getIntProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public long getLongProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public float getFloatProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public double getDoubleProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }



    @Override
    public Object getObjectProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public Enumeration getPropertyNames() {
        throw new RuntimeException("Not Implemented in test Context");

    }



    @Override
    public void setByteProperty(String s, byte b) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void setShortProperty(String s, short i) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void setIntProperty(String s, int i) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void setLongProperty(String s, long l) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void setFloatProperty(String s, float v) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void setDoubleProperty(String s, double v) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void setObjectProperty(String s, Object o) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void acknowledge() {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public void clearBody() {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public <T> T getBody(Class<T> aClass) {
        throw new RuntimeException("Not Implemented in test Context");

    }

    @Override
    public boolean isBodyAssignableTo(Class aClass) {
        throw new RuntimeException("Not Implemented in test Context");

    }
}
