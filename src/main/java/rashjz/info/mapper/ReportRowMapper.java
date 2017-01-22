package rashjz.info.mapper;

import org.springframework.jdbc.core.RowMapper;
import rashjz.info.model.Report;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRowMapper implements RowMapper<Report> {

    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {

        Report report = new Report();

        report.setId(rs.getInt("id"));
        report.setName(rs.getString("name"));
        report.setSurname(rs.getString("surname"));
        report.setTransaction(rs.getBigDecimal("transaction"));
        report.setDate(rs.getDate("date"));
        System.out.println("mapRow = = = = "+report.toString());
        return report;
    }

}