package net.terullet.bibliothekarios.arachne.repository.typehandler.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouR18Genre;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * MyBatis TypeHandler for NarouR18Genre enum.
 * Converts between NarouR18Genre enum and its integer ID in the database.
 *
 * <p>Usage in MyBatis configuration:
 * <pre>
 * &lt;typeHandlers&gt;
 *   &lt;typeHandler handler="net.terullet.bibliothekarios.arachne.repository.typehandler.web.com.syosetu.NarouR18GenreTypeHandler"/&gt;
 * &lt;/typeHandlers&gt;
 * </pre>
 *
 * <p>Or register programmatically:
 * <pre>
 * typeHandlerRegistry.register(NarouR18Genre.class, NarouR18GenreTypeHandler.class);
 * </pre>
 *
 * <p>Example usage in MyBatis mapper:
 * <pre>
 * &lt;insert id="insertR18Work"&gt;
 *   INSERT INTO narou_works (work_id, genre_id) VALUES (#{workId}, #{genre})
 * &lt;/insert&gt;
 *
 * &lt;select id="selectR18WorkByGenre" resultType="Work"&gt;
 *   SELECT * FROM narou_works WHERE genre_id = #{genre}
 * &lt;/select&gt;
 * </pre>
 */
@MappedTypes(NarouR18Genre.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class NarouR18GenreTypeHandler extends AbstractNarouGenreTypeHandler<NarouR18Genre> {

	/**
	 * NarouR18GenreTypeHandlerのコンストラクタ。
	 * 親クラスにfromId関数とジャンル型名を渡します。
	 */
	public NarouR18GenreTypeHandler() {
		super(NarouR18Genre::fromId, "NarouR18Genre");
	}
}
