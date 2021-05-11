package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import it.gruppopam.app_common.model.entity.ArticleSupplierLine;

import static androidx.room.OnConflictStrategy.REPLACE;
import static it.gruppopam.app_common.model.entity.ArticleSupplierLine.ARTICLE_SUPPLIER_LINES_TABLE;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;

@Dao
public interface ArticleSupplierLineDao extends IDao<ArticleSupplierLine> {
    String SELECT_ALL_QUERY = SELECT_FROM + ARTICLE_SUPPLIER_LINES_TABLE;
    String TRUNCATE_QUERY = DELETE_FROM + ARTICLE_SUPPLIER_LINES_TABLE;
    String COUNT_QUERY = COUNT_FROM + ARTICLE_SUPPLIER_LINES_TABLE;

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @Insert
    void save(List<ArticleSupplierLine> collect);

    @Transaction
    @Insert(onConflict = REPLACE)
    long save(ArticleSupplierLine entity);

    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<ArticleSupplierLine> fetchAll();

}
