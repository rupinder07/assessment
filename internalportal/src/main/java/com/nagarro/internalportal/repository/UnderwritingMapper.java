package com.nagarro.internalportal.repository;

import com.nagarro.internalportal.domain.Underwriting;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UnderwritingMapper implements ResultSetMapper<Underwriting> {
    @Override
    public Underwriting map(final int index,
                            final ResultSet resultSet,
                            final StatementContext ctx) throws SQLException {
        return new Underwriting(resultSet.getString("id"),
                resultSet.getString("version"),
                resultSet.getTimestamp("creation_time").toLocalDateTime(),
                Objects.isNull(resultSet.getTimestamp("updation_time"))
                        ? null
                        : resultSet.getTimestamp("updation_time").toLocalDateTime(),
                resultSet.getString("application_id"),
                resultSet.getString("data"),
                resultSet.getString("status"),
                resultSet.getString("updated_by"));
    }
}
