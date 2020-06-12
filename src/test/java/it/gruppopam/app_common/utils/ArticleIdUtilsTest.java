package it.gruppopam.app_common.utils;

import org.junit.Before;
import org.junit.Test;

import static it.gruppopam.app_common.utils.AppConstants.INVALID_ARTICLE_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArticleIdUtilsTest {
    private ArticleIdUtils articleIdUtils;
    private static final int ARTICLE_ID_BARCODE_LENGTH = 8;
    private static final int ARTICLE_ID_MAX_LENGTH = 7;

    @Before
    public void init() {
        articleIdUtils = new ArticleIdUtils(ARTICLE_ID_BARCODE_LENGTH, ARTICLE_ID_MAX_LENGTH);
    }

    @Test
    public void shouldExtractArticleId() {
        long articleId = articleIdUtils.extractArticleId("22218049");
        assertThat(articleId, is(2218049L));

        long articleId2 = articleIdUtils.extractArticleId("21180491");
        assertThat(articleId2, is(1180491L));

        long articleId3 = articleIdUtils.extractArticleId("20318048");
        assertThat(articleId3, is(318048L));

        long articleId4 = articleIdUtils.extractArticleId("20018047");
        assertThat(articleId4, is(18047L));
    }

    @Test
    public void shouldExtractArticleIdNormalize() {
        long articleId = articleIdUtils.extractArticleId("0000022218049");
        assertThat(articleId, is(2218049L));

        long articleId2 = articleIdUtils.extractArticleId("0000021180491");
        assertThat(articleId2, is(1180491L));

        long articleId3 = articleIdUtils.extractArticleId("0000020318048");
        assertThat(articleId3, is(318048L));

        long articleId4 = articleIdUtils.extractArticleId("0000020018047");
        assertThat(articleId4, is(18047L));
    }

    @Test
    public void shouldReturnInvalidArticleId() {
        Long articleId = articleIdUtils.extractArticleId("222180");
        assertThat(articleId, is(INVALID_ARTICLE_ID));
    }

    @Test
    public void shouldReturnInvalidArticleIdForArticleIdNotBeginningWith2() throws Exception {
        Long articleId = articleIdUtils.extractArticleId("12335677");
        assertThat(articleId, is(INVALID_ARTICLE_ID));
    }

    @Test
    public void shouldReturnTrueWhenStringIsNullOrEmpty() throws Exception {
        assertFalse(articleIdUtils.isValidArticleId("123"));
        assertTrue(articleIdUtils.isValidArticleId("123456"));
        assertFalse(articleIdUtils.isValidArticleId("10000000000000"));
        assertFalse(articleIdUtils.isValidArticleId("123456A"));
    }
}
