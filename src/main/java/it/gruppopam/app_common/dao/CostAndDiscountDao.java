package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import it.gruppopam.app_common.model.entity.CostAndDiscount;

import static it.gruppopam.app_common.model.entity.CostAndDiscount.COST_AND_DISCOUNT_TABLE;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;

@Dao
public interface CostAndDiscountDao extends IDao<CostAndDiscount> {

    String SELECT_ALL_QUERY = SELECT_FROM + COST_AND_DISCOUNT_TABLE;
    String TRUNCATE_QUERY = DELETE_FROM + COST_AND_DISCOUNT_TABLE;
    String COUNT_QUERY = COUNT_FROM + COST_AND_DISCOUNT_TABLE;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(CostAndDiscount costAndDiscount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<CostAndDiscount> costAndDiscount);

    @Delete
    void deleteCompetitor(CostAndDiscount costAndDiscount);

    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<CostAndDiscount> fetchAll();

}
