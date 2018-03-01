package cn.damei.utils.excel.export;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class QueryResult {
    public static void doQuery(DataSource dataSource, String sql, Object[] params, DataResult dataResult) {
        if (null == dataSource) {
            throw new IllegalStateException("QueryResult jdbc get dataSource is null ");
        }
        ResultSet rest = null;
        PreparedStatement pest = null;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            if (null == conn) {
                throw new IllegalStateException("QueryResult jdbc get conn is null ");
            }
            pest = conn.prepareStatement(sql);
            for (int j = 0; j < params.length; j++) {
                Object param = params[j];
                pest.setObject(j + 1, param);
            }
            rest = pest.executeQuery();
            int rowNum = 1;
            dataResult.before();
            while (rest.next()) {
                dataResult.doExport(rest, rowNum);
                ++rowNum;
            }
            dataResult.after(rowNum);
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new IllegalStateException("create file exception");
        } finally {
            if (null != rest) {
                try {
                    rest.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != pest) {
                try {
                    pest.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
