package net.terullet.net;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class URLEncoderTest {

    @Test
    void encode_shouldHandleBasicString() {
        // given
        String input = "hello world";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("hello%20world");
    }

    @Test
    void encode_shouldReplaceSpecialCharacters() {
        // given
        String input = "test.file-name*with_plus+end";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("test%2Efile%2Dname%2Awith%5Fplus%2Bend");
    }

    @Test
    void encode_shouldHandleDot() {
        // given
        String input = "file.txt";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("file%2Etxt");
    }

    @Test
    void encode_shouldHandleHyphen() {
        // given
        String input = "file-name";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("file%2Dname");
    }

    @Test
    void encode_shouldHandleAsterisk() {
        // given
        String input = "search*term";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("search%2Aterm");
    }

    @Test
    void encode_shouldHandleUnderscore() {
        // given
        String input = "var_name";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("var%5Fname");
    }

    @Test
    void encode_shouldHandlePlusAsSpace() {
        // given
        String input = "hello+world";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        // The standard URLEncoder encodes + as %2B, so postProcess has no + to replace
        assertThat(result).isEqualTo("hello%2Bworld");
    }

    @Test
    void encode_shouldHandleEmptyString() {
        // given
        String input = "";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void encode_shouldHandleJapaneseCharacters() {
        // given
        String input = "テスト.ファイル";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).contains("%E3%83%86%E3%82%B9%E3%83%88%2E%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB");
    }

    @Test
    void encode_shouldHandleAllSpecialCharactersTogether() {
        // given
        String input = ".-*_+";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("%2E%2D%2A%5F%2B");
    }

    @Test
    void encode_shouldHandleRepeatedSpecialCharacters() {
        // given
        String input = "...---***___+++";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).isEqualTo("%2E%2E%2E%2D%2D%2D%2A%2A%2A%5F%5F%5F%2B%2B%2B");
    }

    @Test
    void encode_shouldHandleCharactersNotInMapper() {
        // Test that characters not in the mapper are handled by standard URLEncoder
        // given
        String input = "test@example.com#fragment?query=value";

        // when
        String result = URLEncoder.encode(input, StandardCharsets.UTF_8);

        // then
        assertThat(result).contains("%40"); // @ -> %40
        assertThat(result).contains("%23"); // # -> %23  
        assertThat(result).contains("%3F"); // ? -> %3F
        assertThat(result).contains("%3D"); // = -> %3D
        assertThat(result).contains("%2E"); // . -> %2E (from our mapper)
    }
}