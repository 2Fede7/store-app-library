package it.gruppopam.app_common.utils;

import org.junit.Before;
import org.junit.Test;
import static it.gruppopam.app_common.utils.AppConstants.*;

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
        long articleId = articleIdUtils.extractArticleId("22218040");
        assertThat(articleId, is(2218049L));
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
