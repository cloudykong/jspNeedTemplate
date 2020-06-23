package jsp.need.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementCreator{
	public PreparedStatement createPreparedStatement(Connection con) throws SQLException;
		
}
