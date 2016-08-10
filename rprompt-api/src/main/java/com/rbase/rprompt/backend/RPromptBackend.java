package com.rbase.rprompt.backend;

public interface RPromptBackend {

    String connect(String dsn);

    void disconnect(String connectionId);

    boolean connected(String connectionId);

    RPromptResult execute(String connectionId, String query);

}
