package net.terullet.bibliothekarios.arachne.repository.typehandler.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouGenre;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * NarouGenre実装enum用の共通TypeHandlerベースクラス。
 * 具体的なenum型に対してはこのクラスを継承して実装します。
 *
 * @param <T> NarouGenreを実装するenum型
 */
public abstract class AbstractNarouGenreTypeHandler<T extends Enum<T> & NarouGenre> extends BaseTypeHandler<T> {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	private final Function<Integer, T> fromIdFunction;
	private final String genreTypeName;

	/**
	 * AbstractNarouGenreTypeHandlerのコンストラクタ。
	 *
	 * @param fromIdFunction IDからenum値を取得する関数
	 * @param genreTypeName ジャンル型名（エラーメッセージ用）
	 */
	protected AbstractNarouGenreTypeHandler(Function<Integer, T> fromIdFunction, String genreTypeName) {
		this.fromIdFunction = fromIdFunction;
		this.genreTypeName = genreTypeName;
	}

	/**
	 * NarouGenre enum値をinteger IDとしてPreparedStatementに設定します。
	 *
	 * @param ps PreparedStatement
	 * @param i parameter index
	 * @param parameter NarouGenre enum value (must not be null)
	 * @param jdbcType JDBC type
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
			throws SQLException {
		if (logger.isTraceEnabled()) {
			logger.trace("Setting {} parameter at index {}: {} (ID: {})",
					genreTypeName, i, parameter, parameter.getId());
		}
		ps.setInt(i, parameter.getId());
	}

	/**
	 * ResultSetからカラム名でNarouGenre enumを取得します。
	 *
	 * @param rs ResultSet
	 * @param columnName column name
	 * @return NarouGenre enum value, or null if the column value is null
	 * @throws SQLException if a database access error occurs or invalid genre ID is found
	 */
	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int id = rs.getInt(columnName);
		if (rs.wasNull()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Column '{}' is null, returning null {}", columnName, genreTypeName);
			}
			return null;
		}
		return convertToGenre(id, "column '" + columnName + "'");
	}

	/**
	 * ResultSetからカラムインデックスでNarouGenre enumを取得します。
	 *
	 * @param rs ResultSet
	 * @param columnIndex column index
	 * @return NarouGenre enum value, or null if the column value is null
	 * @throws SQLException if a database access error occurs or invalid genre ID is found
	 */
	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int id = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			if (logger.isTraceEnabled()) {
				logger.trace("Column at index {} is null, returning null {}", columnIndex, genreTypeName);
			}
			return null;
		}
		return convertToGenre(id, "column index " + columnIndex);
	}

	/**
	 * CallableStatementからカラムインデックスでNarouGenre enumを取得します。
	 *
	 * @param cs CallableStatement
	 * @param columnIndex column index
	 * @return NarouGenre enum value, or null if the column value is null
	 * @throws SQLException if a database access error occurs or invalid genre ID is found
	 */
	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int id = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			if (logger.isTraceEnabled()) {
				logger.trace("CallableStatement column at index {} is null, returning null {}",
						columnIndex, genreTypeName);
			}
			return null;
		}
		return convertToGenre(id, "CallableStatement column index " + columnIndex);
	}

	/**
	 * integer IDをNarouGenre enumに変換します（エラーハンドリング・ログ出力付き）。
	 *
	 * @param id ジャンルID
	 * @param context エラーメッセージ用のコンテキスト情報
	 * @return NarouGenre enum value
	 * @throws SQLException IDが無効な場合
	 */
	private T convertToGenre(int id, String context) throws SQLException {
		try {
			T genre = fromIdFunction.apply(id);
			if (logger.isTraceEnabled()) {
				logger.trace("Converted ID {} from {} to {}: {}", id, context, genreTypeName, genre);
			}
			return genre;
		} catch (IllegalArgumentException e) {
			String errorMsg = String.format("Invalid %s ID %d found in %s: %s",
					genreTypeName, id, context, e.getMessage());
			logger.error(errorMsg);
			throw new SQLException(errorMsg, e);
		}
	}
}
