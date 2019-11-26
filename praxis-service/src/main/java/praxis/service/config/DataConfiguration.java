/**
 * Copyright 2019 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package praxis.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import praxis.service.config.settings.DatabaseSettings;

import javax.sql.DataSource;

/**
 * Database configuration
 */
@Configuration
@EnableConfigurationProperties({
        DatabaseSettings.class
})
public class DataConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DataConfiguration.class);

    @Bean
    public DataSource dataSource(DatabaseSettings databaseSettings) {
        if (StringUtils.isEmpty(databaseSettings.getJdbcurl())) {
            throw new IllegalStateException("Database configuration not found! Please verify that you have specified a database configuration for the current environment.");
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseSettings.getJdbcurl());
        hikariConfig.setUsername(databaseSettings.getUsername());

        if (databaseSettings.getPassword() != null) {
            hikariConfig.setPassword(databaseSettings.getPassword());
        }

        if (databaseSettings.getMaxpoolsize() != null) {
            hikariConfig.setMaximumPoolSize(databaseSettings.getMaxpoolsize());
        }

        LOG.info("Configuring Praxis Database Connection: {}", hikariConfig.getJdbcUrl());

        return new HikariDataSource(hikariConfig);
    }
}
