package net.terullet.bibliothekarios.arachne.repository.typehandler.web.com.syosetu;

import net.terullet.bibliothekarios.arachne.domain.web.com.syosetu.NarouAllAgesGenre;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * MyBatis TypeHandler for NarouAllAgesGenre enum.
 * Converts between NarouAllAgesGenre enum and its integer ID in the database.
 *
 * <p>Usage in MyBatis configuration:
 * <pre>
 * &lt;typeHandlers&gt;
 *   &lt;typeHandler handler="net.terullet.bibliothekarios.arachne.repository.typehandler.web.com.syosetu.NarouAllAgesGenreTypeHandler"/&gt;
 * &lt;/typeHandlers&gt;
 * </pre>
 *
 * <p>Or register programmatically:
 * <pre>
 * typeHandlerRegistry.register(NarouAllAgesGenre.class, NarouAllAgesGenreTypeHandler.class);
 * </pre>
 *
 * <p>Example usage in MyBatis mapper:
 * <pre>
 * &lt;insert id="insertWork"&gt;
 *   INSERT INTO narou_works (work_id, genre_id) VALUES (#{workId}, #{genre})
 * &lt;/insert&gt;
 *
 * &lt;select id="selectWorkByGenre" resultType="Work"&gt;
 *   SELECT * FROM narou_works WHERE genre_id = #{genre}
 * &lt;/select&gt;
 * </pre>
 */
@MappedTypes(NarouAllAgesGenre.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class NarouAllAgesGenreTypeHandler extends AbstractNarouGenreTypeHandler<NarouAllAgesGenre> {

	/**
	 * NarouAllAgesGenreTypeHandlerのコンストラクタ。
	 * 親クラスにfromId関数とジャンル型名を渡します。
	 */
	public NarouAllAgesGenreTypeHandler() {
		super(NarouAllAgesGenre::fromId, "NarouAllAgesGenre");
	}
}
