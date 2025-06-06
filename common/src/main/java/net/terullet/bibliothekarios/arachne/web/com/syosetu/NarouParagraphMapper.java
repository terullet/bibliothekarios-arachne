package net.terullet.bibliothekarios.arachne.web.com.syosetu;

import java.util.List;

public interface NarouParagraphMapper {
	List<NarouParagraph> getParagraphsByEpisodeId(long id);
	int updateParagraph(NarouParagraph paragraph);
	int deleteParagraph(long id);
}
