package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import it.gruppopam.app_common.model.entity.ArticleOrderBlock;

import static it.gruppopam.app_common.model.entity.ArticleOrderBlock.ARTICLE_ORDER_BLOCK;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;

@Dao
public interface ArticleOrderBlockDao extends IDao<ArticleOrderBlock> {
    String SELECT_ALL_QUERY = SELECT_FROM + ARTICLE_ORDER_BLOCK;
    String TRUNCATE_QUERY = DELETE_FROM + ARTICLE_ORDER_BLOCK;
    String COUNT_QUERY = COUNT_FROM + ARTICLE_ORDER_BLOCK;
    String DELETE_QUERY = DELETE_FROM + ARTICLE_ORDER_BLOCK + " WHERE job_name=?";

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] save(List<ArticleOrderBlock> collect);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(ArticleOrderBlock entity);

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<ArticleOrderBlock> fetchAll();

    @Transaction
    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Transaction
    @Delete
    void delete(ArticleOrderBlock entity);
}
