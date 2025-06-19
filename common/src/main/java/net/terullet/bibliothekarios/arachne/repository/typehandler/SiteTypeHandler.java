package net.terullet.bibliothekarios.arachne.repository.typehandler;

import net.terullet.bibliothekarios.arachne.repository.Site;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatis TypeHandler for Site enum.
 * Converts between Site enum and its integer ID in the database.
 *
 * <p>Usage in MyBatis configuration:
 * <pre>
 * &lt;typeHandlers&gt;
 *   &lt;typeHandler handler="net.terullet.bibliothekarios.arachne.repository.typehandler.SiteTypeHandler"/&gt;
 * &lt;/typeHandlers&gt;
 * </pre>
 *
 * <p>Or register programmatically:
 * <pre>
 * typeHandlerRegistry.register(Site.class, SiteTypeHandler.class);
 * </pre>
 */
@MappedTypes(Site.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class SiteTypeHandler extends BaseTypeHandler<Site> {

	private static final Logger logger = LogManager.getLogger(SiteTypeHandler.class);

	/**
	 * Sets the Site enum value as an integer ID in PreparedStatement.
	 *
	 * @param ps PreparedStatement
	 * @param i parameter index
	 * @param parameter Site enum value (must not be null)
	 * @param jdbcType JDBC type
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Site parameter, JdbcType jdbcType)
			throws SQLException {
		if (logger.isTraceEnabled()) {
			logger.trace("Setting Site parameter at index {}: {} (ID: {})", i, parameter, parameter.getId());
		}
		ps.setInt(i, parameter.getId());
	}

	/**
	 * Gets Site enum from ResultSet by column name.
	 *
	 * @param rs ResultSet
	 * @param columnName column name
	 * @return Site enum value, or null if the column value is null
	 * @throws SQLException if a database access error occurs or invalid Site ID is found
	 */
	@Override
	public Site getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int id = rs.getInt(columnName);
		if (rs.wasNull()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Column '{}' is null, returning null Site", columnName);
			}
			return null;
		}
		return convertToSite(id, "column '" + columnName + "'");
	}

	/**
	 * Gets Site enum from ResultSet by column index.
	 *
	 * @param rs ResultSet
	 * @param columnIndex column index
	 * @return Site enum value, or null if the column value is null
	 * @throws SQLException if a database access error occurs or invalid Site ID is found
	 */
	@Override
	public Site getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int id = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Column at index {} is null, returning null Site", columnIndex);
			}
			return null;
		}
		return convertToSite(id, "column index " + columnIndex);
	}

	/**
	 * Gets Site enum from CallableStatement by column index.
	 *
	 * @param cs CallableStatement
	 * @param columnIndex column index
	 * @return Site enum value, or null if the column value is null
	 * @throws SQLException if a database access error occurs or invalid Site ID is found
	 */
	@Override
	public Site getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int id = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			if (logger.isTraceEnabled()) {
				logger.trace("CallableStatement column at index {} is null, returning null Site", columnIndex);
			}
			return null;
		}
		return convertToSite(id, "CallableStatement column index " + columnIndex);
	}

	/**
	 * Converts integer ID to Site enum with error handling and logging.
	 *
	 * @param id Site ID
	 * @param context context information for error messages
	 * @return Site enum value
	 * @throws SQLException if the ID is not valid
	 */
	private Site convertToSite(int id, String context) throws SQLException {
		try {
			Site site = Site.fromId(id);
			if (logger.isTraceEnabled()) {
				logger.trace("Converted ID {} from {} to Site: {}", id, context, site);
			}
			return site;
		} catch (IllegalArgumentException e) {
			String errorMsg = String.format("Invalid Site ID %d found in %s: %s", id, context, e.getMessage());
			logger.error(errorMsg);
			throw new SQLException(errorMsg, e);
		}
	}
}
