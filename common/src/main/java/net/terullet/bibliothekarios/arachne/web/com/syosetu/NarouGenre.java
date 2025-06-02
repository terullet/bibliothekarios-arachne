package net.terullet.bibliothekarios.arachne.web.com.syosetu;

/**
 * なろうにおけるジャンルを表すインターフェースです．
 */
public interface NarouGenre {
	/**
	 * ジャンルに振り分けられた数値を取得します．
	 * @return ジャンルに振り分けられた数値．
	 */
	int getId();

	/**
	 * ジャンルの文字列表現を取得します．
	 * @return ジャンルの文字列表現．
	 */
	String getDescription();
}
