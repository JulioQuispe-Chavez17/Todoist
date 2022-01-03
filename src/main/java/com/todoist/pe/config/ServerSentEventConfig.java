package com.todoist.pe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.todoist.pe.sse")
public final class ServerSentEventConfig {
    private int heartBeatDelayMs;
    private int reconnectionDelayMs = 2_000;
}
