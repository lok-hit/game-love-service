package org.lokhit.gamelove.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class PrometheusConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "game-love-service");
    }
}

