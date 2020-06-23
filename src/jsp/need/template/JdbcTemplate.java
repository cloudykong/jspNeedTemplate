package jsp.need.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcTemplate {
	private DataSource dataFactory;

	public JdbcTemplate() {
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public <T> T query(PreparedStatementCreator psc, RowMapper<T> rowMapper) {
		List<T> list = new ArrayList<T>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataFactory.getConnection();

			pstmt = psc.createPreparedStatement(conn);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rowMapper.mapRow(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clossAll(rs, pstmt, conn);
		}
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public void update(PreparedStatementCreator preparedStatementCreator) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataFactory.getConnection();

			pstmt = preparedStatementCreator.createPreparedStatement(conn);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clossAll(null, pstmt, conn);
		}
	}

	public void update(PreparedStatementCreator preparedStatementCreator, Object... parameters) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataFactory.getConnection();

			pstmt = preparedStatementCreator.createPreparedStatement(conn);
			int i = 1;
			for (Object object : parameters) {
				pstmt.setObject(i++, object);
			}

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clossAll(null, pstmt, conn);
		}
	}

	private void clossAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
