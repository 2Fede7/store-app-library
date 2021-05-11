package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import it.gruppopam.app_common.model.entity.Supplier;
import it.gruppopam.app_common.model.relations.SupplierAnagraphicWithDistributionMode;

import static it.gruppopam.app_common.model.entity.Supplier.SUPPLIER_TABLE;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;


@Dao
public interface SupplierDao extends IDao<Supplier> {
    String SELECT_ALL_QUERY = SELECT_FROM + SUPPLIER_TABLE;
    String SELECT_BY_SUPPLIER_IDS = SELECT_FROM + SUPPLIER_TABLE +
            " WHERE SUPPLIER_ID in (:arg0)";
    String TRUNCATE_QUERY = DELETE_FROM + SUPPLIER_TABLE;
    String COUNT_QUERY = COUNT_FROM + SUPPLIER_TABLE;

    @Query(COUNT_QUERY)
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<Supplier> collect);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(Supplier entity);

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<SupplierAnagraphicWithDistributionMode> fetchAll();

    @Transaction
    @Query(SELECT_BY_SUPPLIER_IDS)
    List<SupplierAnagraphicWithDistributionMode> findBySupplierIds(List<Long> arg0);

    @Transaction
    @RawQuery
    SupplierAnagraphicWithDistributionMode selectWrapper(SupportSQLiteQuery query);

    @Transaction
    @RawQuery
    List<SupplierAnagraphicWithDistributionMode> selectAllWrapper(SupportSQLiteQuery query);

    @Transaction
    @Query(TRUNCATE_QUERY)
    void truncateTable();

}
