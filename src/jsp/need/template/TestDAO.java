package jsp.need.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TestDAO {
	private JdbcTemplate jdbcTemplate;

	public TestDAO() {
		this.jdbcTemplate = new JdbcTemplate();
	}
	public TestTable selectAll() {
//		return jdbcTemplate.query(con->{
//			PreparedStatement ps = con.prepareStatement("select * from testtable");
//			return ps;
//			}, new RowMapper<TestTable>() {
//				@Override
//				public TestTable mapRow(ResultSet rs) throws SQLException {
//					return new TestTable(rs.getInt(1), rs.getString(2));
//				}
//			});
		return jdbcTemplate.query(con->{
			PreparedStatement ps = con.prepareStatement("select * from testtable");
			return ps;
			}, rs-> {
			return new TestTable(rs.getInt(1), rs.getString(2));
			});
	}
	public void update(TestTable table) {
		jdbcTemplate.update(con->{
			PreparedStatement ps = con.prepareStatement("update testtable set id=?, name=?");
			ps.setInt(1, table.getId());
			ps.setString(2, table.getName());
			return ps;
			});
	}
	public void insert(TestTable table) {
		jdbcTemplate.update(
		 new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement("insert into testtable values (?,?)");
				ps.setInt(1, table.getId());
				ps.setString(2, table.getName());
				return ps;
			}
		});
		jdbcTemplate.update(con->{
			PreparedStatement ps = con.prepareStatement("insert into testtable values (?,?)");
			ps.setInt(1, table.getId());
			ps.setString(2, table.getName());
			return ps;
			});
	}
	public void deleteAll() {
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			@Override
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				return con.prepareStatement("delete from testtable");
//			}
//		});
		jdbcTemplate.update(con->con.prepareStatement("delete from testtable"));
	}
//	public void update() {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		String sql = "update board set writer=?,title=?,content=? where num=?";
//		try {
//			conn = dataFactory.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, writer);
//			pstmt.setString(2, title);
//			pstmt.setString(3, content);
//			pstmt.setInt(4, num);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
