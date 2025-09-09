package org.lokhit.gamelove.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class HazelcastConfig {

    @Bean(name = "customHazelcastConfig")
    public Config hazelcastConfig() {
        EvictionConfig evictionConfig = new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                .setSize(100);

        MapConfig mapConfig = new MapConfig()
                .setName("games")
                .setTimeToLiveSeconds(3600)
                .setEvictionConfig(evictionConfig);

        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(mapConfig);
    }
    @Bean
    public HazelcastInstance hazelcastInstance(Config config) {
        return Hazelcast.newHazelcastInstance(config);
    }
}


