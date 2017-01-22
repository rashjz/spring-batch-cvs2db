package rashjz.info.job;

/**
 * Created by Mobby on 1/19/2017.
 */

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import rashjz.info.model.Report;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ReportItemPreparedStatementSetter implements ItemPreparedStatementSetter<Report> {

    public void setValues(Report result, PreparedStatement ps) throws SQLException {
        System.out.println("------------ ReportItemPreparedStatementSetter ------------- "+result.toString());
        ps.setInt(1, result.getId());
        ps.setString(2, result.getName());
        ps.setString(3,  result.getSurname());
        ps.setBigDecimal(4,  result.getTransaction());
        ps.setDate(5, new java.sql.Date(result.getDate().getTime()));

    }

}