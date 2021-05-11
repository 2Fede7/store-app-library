package it.gruppopam.app_common.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = SubstituteArticle.SUBSTITUTE_ARTICLE_TABLE,
        indices = {@Index(value = {"article_id"}, unique = true)}, inheritSuperIndices = true)
public class SubstituteArticle extends BaseEntity {

    public static final String PRIMARY_KEY_GROUP = "substitute_primary_key";
    public static final String SUBSTITUTE_ARTICLE_TABLE = "substitute_article";

    @ColumnInfo(name = "article_id")
    private Long articleId;

    @ColumnInfo(name = "substitute_article_id")
    private Long substituteArticleId;

    @Ignore
    public SubstituteArticle(Long articleId, Long substituteArticleId) {
        this.articleId = articleId;
        this.substituteArticleId = substituteArticleId;
    }
}
