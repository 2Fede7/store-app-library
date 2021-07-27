package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import it.gruppopam.app_common.model.entity.SubstituteArticle;

import static it.gruppopam.app_common.model.entity.SubstituteArticle.SUBSTITUTE_ARTICLE_TABLE;

@Dao
public interface SubstituteArticleDao extends IDao<SubstituteArticle> {
    String SELECT_ALL_QUERY = "SELECT * FROM " + SUBSTITUTE_ARTICLE_TABLE;
    String TRUNCATE_QUERY = "DELETE FROM " + SUBSTITUTE_ARTICLE_TABLE;
    String COUNT_QUERY = "SELECT COUNT(*) FROM " + SUBSTITUTE_ARTICLE_TABLE;

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] save(List<SubstituteArticle> collect);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(SubstituteArticle entity);

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<SubstituteArticle> fetchAll();

    @Transaction
    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Transaction
    @Delete
    void delete(SubstituteArticle entity);
}
