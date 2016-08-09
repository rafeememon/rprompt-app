package com.rbase.rprompt.backend;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.lexicalunit.nanodbc.Connection;

public class ExpiringRPromptBackend {

    private ExpiringRPromptBackend() {
        // utility class
    }

    public static RPromptBackend create(ScheduledExecutorService executor, long duration, TimeUnit unit) {
        Cache<String, Connection> cache = createConnectionCache(duration, unit);
        executor.scheduleAtFixedRate(new CacheCleaner<>(cache), duration, duration, unit);
        return new DefaultRPromptBackend(cache.asMap());
    }

    private static Cache<String, Connection> createConnectionCache(long duration, TimeUnit unit) {
        return CacheBuilder.newBuilder()
                .expireAfterAccess(duration, unit)
                .removalListener(new RemovalListener<String, Connection>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Connection> notification) {
                        if (notification.wasEvicted()) {
                            notification.getValue().close();
                        }
                    }
                })
                .build();
    }

    private static class CacheCleaner<K, V> implements Runnable {
        private final Cache<K, V> cache;

        private CacheCleaner(Cache<K, V> cache) {
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.cleanUp();
        }
    }

}
