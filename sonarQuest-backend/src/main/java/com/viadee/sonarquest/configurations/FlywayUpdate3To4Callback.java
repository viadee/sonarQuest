package com.viadee.sonarquest.configurations;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.flywaydb.core.api.configuration.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class FlywayUpdate3To4Callback implements Callback {
    private final Flyway flyway;

    public FlywayUpdate3To4Callback(@Lazy final Flyway flyway) {
        this.flyway = flyway;
    }

    private boolean checkColumnExists(final Configuration flywayConfiguration) throws MetaDataAccessException {
        return (boolean) JdbcUtils.extractDatabaseMetaData(flywayConfiguration.getDataSource(),
                callback -> callback
                        .getColumns(null, null, flywayConfiguration.getTable(), "version_rank")
                        .next());
    }

    @Override
    public boolean supports(final Event event, final Context context) {
        return event == Event.BEFORE_VALIDATE;
    }

    @Override
    public boolean canHandleInTransaction(final Event event, final Context context) {
        return false;
    }

    @Override
    public void handle(final Event event, final Context context) {
        boolean versionRankColumnExists = false;
        try {
            versionRankColumnExists = checkColumnExists(context.getConfiguration());
        } catch (final MetaDataAccessException e) {
            log.error("Cannot obtain flyway metadata");
            return;
        }
        if (versionRankColumnExists) {
            log.info("Upgrading metadata table the Flyway 4.0 format ...");
            final Resource resource = new ClassPathResource("db/migration/flyway_upgradeMetaDataTable_V3_to_V4.sql",
                    Thread.currentThread().getContextClassLoader());
            ScriptUtils.executeSqlScript(context.getConnection(), resource);
            log.info("Flyway metadata table updated successfully.");
            // recalculate checksums
            flyway.repair();
        }
    }
}
