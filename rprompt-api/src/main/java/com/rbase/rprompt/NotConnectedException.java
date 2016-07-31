package com.rbase.rprompt;

public class NotConnectedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String connectionId;

    public NotConnectedException(String connectionId) {
        super("Not connected: " + connectionId);
        this.connectionId = connectionId;
    }

    public String getConnectionId() {
        return connectionId;
    }

}
