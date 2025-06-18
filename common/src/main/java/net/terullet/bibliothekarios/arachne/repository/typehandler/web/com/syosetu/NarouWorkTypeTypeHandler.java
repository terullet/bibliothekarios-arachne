package net.terullet.bibliothekarios.arachne.repository.typehandler.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouWorkType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * NarouWorkType用のMyBatis TypeHandler
 * enumの明示的IDとDB INTEGER値との相互変換を行う
 */
@MappedTypes(NarouWorkType.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class NarouWorkTypeTypeHandler extends BaseTypeHandler<NarouWorkType> {

	/**
	 * PreparedStatementにNarouWorkTypeのIDを設定
	 *
	 * @param ps PreparedStatement
	 * @param i パラメータインデックス
	 * @param parameter NarouWorkType値
	 * @param jdbcType JDBC型
	 * @throws SQLException SQL例外
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, NarouWorkType parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getId());
	}

	/**
	 * ResultSetのカラム名からNarouWorkTypeを取得
	 *
	 * @param rs ResultSet
	 * @param columnName カラム名
	 * @return NarouWorkType値（nullの場合はnull）
	 * @throws SQLException SQL例外
	 */
	@Override
	public NarouWorkType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int id = rs.getInt(columnName);
		return rs.wasNull() ? null : convertToNarouWorkType(id);
	}

	/**
	 * ResultSetのカラムインデックスからNarouWorkTypeを取得
	 *
	 * @param rs ResultSet
	 * @param columnIndex カラムインデックス
	 * @return NarouWorkType値（nullの場合はnull）
	 * @throws SQLException SQL例外
	 */
	@Override
	public NarouWorkType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int id = rs.getInt(columnIndex);
		return rs.wasNull() ? null : convertToNarouWorkType(id);
	}

	/**
	 * CallableStatementからNarouWorkTypeを取得
	 *
	 * @param cs CallableStatement
	 * @param columnIndex カラムインデックス
	 * @return NarouWorkType値（nullの場合はnull）
	 * @throws SQLException SQL例外
	 */
	@Override
	public NarouWorkType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int id = cs.getInt(columnIndex);
		return cs.wasNull() ? null : convertToNarouWorkType(id);
	}

	/**
	 * IDからNarouWorkTypeへの変換（例外処理付き）
	 *
	 * @param id データベースから取得したID
	 * @return 対応するNarouWorkType
	 * @throws SQLException 不正なIDの場合
	 */
	private NarouWorkType convertToNarouWorkType(int id) throws SQLException {
		try {
			return NarouWorkType.fromId(id);
		} catch (IllegalArgumentException e) {
			throw new SQLException("Invalid NarouWorkType id: " + id + ". " + e.getMessage(), e);
		}
	}
}
