package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import java.sql.PreparedStatement;
import java.math.BigDecimal;

public class V1_0_2__INSERT_BOOK extends BaseJavaMigration 
{
    @Override
    public void migrate(Context context) throws Exception 
    {
        final String sql = "INSERT INTO BOOK (title, price) VALUES (?, ?);";
        try (PreparedStatement pstmt = context.getConnection().prepareStatement(sql);) {
            pstmt.setString(1, "Monster");
            pstmt.setBigDecimal(2, BigDecimal.valueOf(23.99));
            pstmt.execute();
        }
    }
}
