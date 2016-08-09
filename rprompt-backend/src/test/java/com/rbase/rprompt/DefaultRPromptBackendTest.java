package com.rbase.rprompt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.lexicalunit.nanodbc.Connection;
import com.lexicalunit.nanodbc.Nanodbc;

public class DefaultRPromptBackendTest {

    private static final long CONNECT_TIMEOUT_SECONDS = 10;
    private static final String CONNECTION_ID = "CONNECTION_ID";

    private static final String DSN = "RRBYW18";
    private static final String QUERY = "SELECT * FROM Titles WHERE EmpTID = 1";
    private static final RPromptResult EXPECTED_RESULT = new RPromptResult(
            Lists.newArrayList("EmpTID", "EmpTitle"),
            Collections.singletonList(Lists.newArrayList("1", "Office Manager")));

    private Map<String, Connection> connections;

    @Before
    public void setup() {
        connections = new HashMap<>();
    }

    @After
    public void teardown() {
        for (Connection connection : connections.values()) {
            connection.close();
        }
    }

    @Test
    public void testConnect() {
        RPromptBackend service = new DefaultRPromptBackend(connections);
        String connectionId = service.connect(DSN);
        Assert.assertTrue("connection exists", connections.containsKey(connectionId));
        Assert.assertTrue("connection is connected", connections.get(connectionId).connected());
    }

    @Test
    public void testDisconnect() {
        Connection connection = getNewConnection();
        connections.put(CONNECTION_ID, connection);
        RPromptBackend service = new DefaultRPromptBackend(connections);
        service.disconnect(CONNECTION_ID);
        Assert.assertFalse("connection is removed", connections.containsKey(CONNECTION_ID));
        Assert.assertFalse("connection is disconnected", connection.connected());
    }

    @Test
    public void testExecute() {
        connections.put(CONNECTION_ID, getNewConnection());
        RPromptBackend service = new DefaultRPromptBackend(connections);
        RPromptResult result = service.execute(CONNECTION_ID, QUERY);
        Assert.assertEquals("result is correct", EXPECTED_RESULT, result);
    }

    @Test(expected = NotConnectedException.class)
    public void testExecuteInvalidId() {
        new DefaultRPromptBackend(connections).execute(CONNECTION_ID, QUERY);
    }

    @Test(expected = NotConnectedException.class)
    public void testExecuteDisconnected() {
        Connection connection = getNewConnection();
        connection.disconnect();
        connections.put(CONNECTION_ID, connection);
        new DefaultRPromptBackend(connections).execute(CONNECTION_ID, QUERY);
    }

    private static Connection getNewConnection() {
        return Nanodbc.newConnection("DSN=" + DSN, CONNECT_TIMEOUT_SECONDS);
    }

}
