package it.gruppopam.app_common.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import it.gruppopam.app_common.model.entity.Article;
import it.gruppopam.app_common.model.relations.ArticleWithRelations;
import it.gruppopam.app_common.model.relations.ArticleWithRelationsAndBarcode;

import static it.gruppopam.app_common.model.entity.Article.ARTICLES_TABLE;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;

@Dao
public interface ArticleDao extends IDao<Article> {
    String SELECT_ALL_QUERY = SELECT_FROM + ARTICLES_TABLE;
    String TRUNCATE_QUERY = DELETE_FROM + ARTICLES_TABLE;
    String COUNT_QUERY = COUNT_FROM + ARTICLES_TABLE;
    String SELECT_ALL_ARTICLES_BY_ARTICLE_IDS_QUERY = SELECT_FROM + ARTICLES_TABLE +
            " WHERE ARTICLE_ID IN (:arg0)";
    String SELECT_ARTICLE_BY_ARTICLE_ID_QUERY = SELECT_FROM + ARTICLES_TABLE +
            " WHERE ARTICLE_ID = :arg0";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<Article> articles);

    @Transaction
    @Delete
    void deleteArticle(Article article);

    @Transaction
    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @RawQuery
    ArticleWithRelations selectWrapper(SupportSQLiteQuery query);

    @Transaction
    @RawQuery
    List<ArticleWithRelations> selectWrapperList(SupportSQLiteQuery query);

    @Transaction
    @RawQuery
    LiveData<List<ArticleWithRelations>> selectLiveDataList(SupportSQLiteQuery query);

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<ArticleWithRelations> fetchAll();

    @Transaction
    @Query(SELECT_ARTICLE_BY_ARTICLE_ID_QUERY)
    ArticleWithRelations findArticleWithRelationsByArticleId(Long arg0);

    @Transaction
    @Query(SELECT_ARTICLE_BY_ARTICLE_ID_QUERY)
    ArticleWithRelationsAndBarcode findArticleWithRelationsAndBarcodeByArticleId(Long arg0);

    @Transaction
    @Query(SELECT_ARTICLE_BY_ARTICLE_ID_QUERY)
    Article findByArticleId(Long arg0);

    @Transaction
    @Query(SELECT_ALL_ARTICLES_BY_ARTICLE_IDS_QUERY)
    List<ArticleWithRelations> findManyByArticleIds(List<Long> arg0);
}
