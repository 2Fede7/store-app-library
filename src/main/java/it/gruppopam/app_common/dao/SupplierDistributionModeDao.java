package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import it.gruppopam.app_common.model.entity.SupplierDistributionMode;

import static it.gruppopam.app_common.model.entity.SupplierDistributionMode.SUPPLIER_DISTRIBUTION_MODE_TABLE;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;

@Dao
public interface SupplierDistributionModeDao extends IDao<SupplierDistributionMode> {
    String SELECT_ALL_QUERY = SELECT_FROM + SUPPLIER_DISTRIBUTION_MODE_TABLE;
    String TRUNCATE_QUERY = DELETE_FROM + SUPPLIER_DISTRIBUTION_MODE_TABLE;
    String COUNT_QUERY = COUNT_FROM + SUPPLIER_DISTRIBUTION_MODE_TABLE;

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<SupplierDistributionMode> collect);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(SupplierDistributionMode entity);

    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Query(SELECT_ALL_QUERY)
    List<SupplierDistributionMode> fetchAll();
}
