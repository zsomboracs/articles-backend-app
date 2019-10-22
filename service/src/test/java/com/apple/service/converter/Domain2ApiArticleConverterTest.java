package com.apple.service.converter;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.apple.api.response.Article;
import com.apple.api.response.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class Domain2ApiArticleConverterTest {

    private static final Long AUTHOR_ID = 122L;

    private static final Long ARTICLE_ID = 14L;
    private static final String TITLE = "The Most Important Question of Your Life";
    private static final String SUMMARY = "Best post to start with to understand the underlying philosophy of my work.";
    private static final String TEXT = "TBD";
    private static final LocalDateTime CREATION_DATE = LocalDateTime.of(2009, 1, 4, 11, 23);
    private static final LocalDateTime MODIFY_DATE = LocalDateTime.of(2009, 1, 4, 11, 54);

    @Mock
    private Domain2ApiAuthorConverter authorConverter;

    private Domain2ApiArticleConverter victim;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        victim = new Domain2ApiArticleConverter(authorConverter);
    }

    @Test
    public void shouldConvertAuthor() {
        com.apple.domain.Author domainAuthor = new com.apple.domain.Author(AUTHOR_ID, null, null);
        com.apple.domain.Article domainArticle = new com.apple.domain.Article(ARTICLE_ID, TITLE, SUMMARY, TEXT, CREATION_DATE, MODIFY_DATE, domainAuthor);
        Author apiAuthor = new Author(AUTHOR_ID, null, null);

        when(authorConverter.convert(eq(domainAuthor))).thenReturn(apiAuthor);

        Article target = victim.convert(domainArticle);

        assertEquals(target.getId(), ARTICLE_ID);
        assertEquals(target.getTitle(), TITLE);
        assertEquals(target.getSummary(), SUMMARY);
        assertEquals(target.getText(), TEXT);
        assertEquals(target.getCreateDate(), CREATION_DATE);
        assertEquals(target.getModifyDate(), MODIFY_DATE);
        assertEquals(target.getAuthor(), apiAuthor);
    }

}