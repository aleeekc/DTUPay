package com.webservices.payment.logic.beans.test;

import com.webservices.payment.logic.beans.CreateClientMessageBean;
import com.webservices.payment.logic.beans.IssueTokenMessageBean;
import com.webservices.payment.logic.beans.TransactionMessageBean;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
/**
 * @author Lasse
 */
public class TestContext implements JMSContext, JMSProducer{


    private HashMap<TestQueue,MessageListener> queueMapping = new HashMap<>();

    public void addBean(CreateClientMessageBean messageBean, TestQueue queue) {
        queueMapping.put(queue,messageBean);
        messageBean.context = this;
    }

    public void addBean(IssueTokenMessageBean messageBean, TestQueue queue) {
        queueMapping.put(queue,messageBean);
        messageBean.context = this;
    }

    public void addBean(TransactionMessageBean messageBean, TestQueue queue) {
        queueMapping.put(queue,messageBean);
        messageBean.context = this;
    }

    @Override
    public TemporaryQueue createTemporaryQueue() {
        TestQueue temporaryQueue = new TestQueue("Temporary Test Queue: " + UUID.randomUUID().toString());
        queueMapping.put(temporaryQueue,null);
        return temporaryQueue;
    }

    @Override
    public TextMessage createTextMessage() {
        return new TestTextMessage();
    }

    @Override
    public Message createMessage()  {
        return new TestTextMessage();
    }

    @Override
    public JMSProducer createProducer() {
        return this;
    }

    @Override
    public JMSConsumer createConsumer(Destination destination) {
        if(!(destination instanceof TestQueue))
            throw new RuntimeException("destination is not TestQueue");

        TestQueue queue = (TestQueue)destination;

        TestConsumer consumer = new TestConsumer();
        queueMapping.replace(queue,consumer);

        return consumer;
    }

    @Override
    public JMSProducer send(Destination destination, Message message) {

        if(!(destination instanceof TestQueue))
            throw new RuntimeException("destination is not TestQueue");

        TestQueue queue = (TestQueue)destination;

        MessageListener bean = queueMapping.get(queue);
        bean.onMessage(message);

        return this;
    }













    @Override
    public JMSContext createContext(int i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public String getClientID() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setClientID(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public ConnectionMetaData getMetaData() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public ExceptionListener getExceptionListener() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setExceptionListener(ExceptionListener exceptionListener) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void start() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void stop() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void setAutoStart(boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean getAutoStart() {
        throw new RuntimeException("Not Implemented in test Context");
    }



    @Override
    public BytesMessage createBytesMessage() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public MapMessage createMapMessage() {
        throw new RuntimeException("Not Implemented in test Context");
    }



    @Override
    public ObjectMessage createObjectMessage() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public ObjectMessage createObjectMessage(Serializable serializable) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public StreamMessage createStreamMessage() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public TextMessage createTextMessage(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean getTransacted() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public int getSessionMode() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void commit() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void rollback() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void recover() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void close() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createConsumer(Destination destination, String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createConsumer(Destination destination, String s, boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Queue createQueue(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Topic createTopic(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createDurableConsumer(Topic topic, String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createDurableConsumer(Topic topic, String s, String s1, boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createSharedDurableConsumer(Topic topic, String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createSharedDurableConsumer(Topic topic, String s, String s1) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createSharedConsumer(Topic topic, String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSConsumer createSharedConsumer(Topic topic, String s, String s1) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public QueueBrowser createBrowser(Queue queue) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public QueueBrowser createBrowser(Queue queue, String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }



    @Override
    public TemporaryTopic createTemporaryTopic() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void unsubscribe(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public void acknowledge() {
        throw new RuntimeException("Not Implemented in test Context");
    }



    @Override
    public JMSProducer send(Destination destination, String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer send(Destination destination, Map<String, Object> map) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer send(Destination destination, byte[] bytes) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer send(Destination destination, Serializable serializable) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setDisableMessageID(boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean getDisableMessageID() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setDisableMessageTimestamp(boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean getDisableMessageTimestamp() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setDeliveryMode(int i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public int getDeliveryMode() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setPriority(int i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public int getPriority() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setTimeToLive(long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public long getTimeToLive() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setDeliveryDelay(long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public long getDeliveryDelay() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setAsync(CompletionListener completionListener) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public CompletionListener getAsync() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, boolean b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, byte b) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, short i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, int i) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, long l) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, float v) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, double v) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, String s1) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setProperty(String s, Object o) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer clearProperties() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean propertyExists(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public boolean getBooleanProperty(String s) {
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
    public String getStringProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Object getObjectProperty(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Set<String> getPropertyNames() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setJMSCorrelationIDAsBytes(byte[] bytes) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setJMSCorrelationID(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public String getJMSCorrelationID() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setJMSType(String s) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public String getJMSType() {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public JMSProducer setJMSReplyTo(Destination destination) {
        throw new RuntimeException("Not Implemented in test Context");
    }

    @Override
    public Destination getJMSReplyTo() {
        throw new RuntimeException("Not Implemented in test Context");
    }
}
