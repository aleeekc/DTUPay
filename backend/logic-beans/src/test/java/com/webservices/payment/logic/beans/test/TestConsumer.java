package com.webservices.payment.logic.beans.test;

import javax.jms.JMSConsumer;
import javax.jms.Message;
import javax.jms.MessageListener;
/**
 * @author Lasse
 */
public class TestConsumer implements MessageListener, JMSConsumer {
    private Message message;

    @Override
    public Message receive()
    {
        return message;
    }

    @Override
    public void onMessage(Message message) {
        this.message = message;
    }


    @Override
    public String getMessageSelector() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public MessageListener getMessageListener() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setMessageListener(MessageListener messageListener){
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Message receive(long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Message receiveNoWait() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void close() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public <T> T receiveBody(Class<T> aClass) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public <T> T receiveBody(Class<T> aClass, long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public <T> T receiveBodyNoWait(Class<T> aClass) {
        throw new RuntimeException("Not Implemented in test Context");
    }
}
