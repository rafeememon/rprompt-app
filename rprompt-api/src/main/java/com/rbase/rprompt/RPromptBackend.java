package com.rbase.rprompt;

public interface RPromptBackend {

    String connect(String dsn);

    void disconnect(String connectionId);

    RPromptResult execute(String connectionId, String query);

}
