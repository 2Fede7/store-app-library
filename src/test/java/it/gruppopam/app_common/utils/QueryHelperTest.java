package it.gruppopam.app_common.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryHelperTest {

    @Before
    public void init() {
    }

    @Test
    public void checkSearchArticleWithApostrophe() {
        String articleName = "Sambuco bio l'Angelica";
        String returnedString = QueryHelper.getSafeString(articleName);
        assertEquals("Sambuco bio l''Angelica", returnedString);
    }

    @Test
    public void checkSearchArticleWithDoubleQuotes() {
        String articleName = "TRAPUNTA \"HILARY\" STAMPATA 1 P";
        String returnedString = QueryHelper.getSafeString(articleName);
        assertEquals("TRAPUNTA \"HILARY\" STAMPATA 1 P", returnedString);
    }

    @Test
    public void checkSearchArticleWithSpecialCharacter() {
        String articleName = "SERVIZIO C. BS DOG 3?";
        String returnedString = QueryHelper.getSafeString(articleName);
        assertEquals("SERVIZIO C. BS DOG 3?", returnedString);
    }

}
