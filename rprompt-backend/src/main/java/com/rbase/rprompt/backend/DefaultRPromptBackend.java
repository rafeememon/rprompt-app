package com.rbase.rprompt.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.lexicalunit.nanodbc.Connection;
import com.lexicalunit.nanodbc.Nanodbc;
import com.lexicalunit.nanodbc.Result;

public class DefaultRPromptBackend implements RPromptBackend {

    private static final long CONNECT_TIMEOUT_SECONDS = 10;
    private static final long EXECUTE_TIMEOUT_SECONDS = 30;
    private static final long EXECUTE_BATCH_OPERATIONS = 1;

    private final Map<String, Connection> connections;

    public DefaultRPromptBackend(Map<String, Connection> connections) {
        this.connections = connections;
    }

    @Override
    public String connect(String dsn) {
        String connectionId = UUID.randomUUID().toString();
        Connection connection = Nanodbc.newConnection("DSN=" + dsn, CONNECT_TIMEOUT_SECONDS);
        connections.put(connectionId, connection);
        return connectionId;
    }

    @Override
    public void disconnect(String connectionId) {
        Connection connection = connections.remove(connectionId);
        if (connection != null && connection.connected()) {
            connection.disconnect();
        }
    }

    @Override
    public RPromptResult execute(String connectionId, String query) {
        Connection connection = connections.get(connectionId);
        if (connection == null) {
            throw new NotConnectedException(connectionId);
        } else if (!connection.connected()) {
            connections.remove(connectionId);
            throw new NotConnectedException(connectionId);
        }
        try (Result result = connection.execute(query, EXECUTE_BATCH_OPERATIONS, EXECUTE_TIMEOUT_SECONDS)) {
            return new RPromptResult(getColumns(result), getValues(result));
        }
    }

    private static List<String> getColumns(Result result) {
        List<String> columns = new ArrayList<>();
        short numColumns = result.getNumColumns();
        for (short column = 0; column < numColumns; column++) {
            columns.add(result.getColumnName(column));
        }
        return columns;
    }

    private static List<List<String>> getValues(Result result) {
        List<List<String>> values = new ArrayList<>();
        short numColumns = result.getNumColumns();
        while (result.next()) {
            values.add(getValuesForRow(result, numColumns));
        }
        return values;
    }

    private static List<String> getValuesForRow(Result result, short numColumns) {
        List<String> rowValues = new ArrayList<>();
        for (short column = 0; column < numColumns; column++) {
            rowValues.add(result.getString(column));
        }
        return rowValues;
    }

}
