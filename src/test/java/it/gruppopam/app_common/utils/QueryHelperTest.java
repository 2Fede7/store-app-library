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
    public void checkSearchArticleWithApostropheAndSQLInjection() {
        String articleName = "Sambuco bio 'DROP table articles;' l'Angelica";
        String returnedString = QueryHelper.getSafeString(articleName);
        assertEquals("Sambuco bio ''DROP table articles;'' l''Angelica", returnedString);
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

    @Test
    public void checkBoolean() {
        Object paramValue = true;
        String returnedString = QueryHelper.getSafeBoolean(paramValue);
        assertEquals(Boolean.TRUE.toString(), returnedString);
    }

    @Test
    public void checkBooleanFail() {
        Object paramValue = "DROP table articles;";
        String returnedString = QueryHelper.getSafeBoolean(paramValue);
        assertEquals(Boolean.FALSE.toString(), returnedString);
    }

}
