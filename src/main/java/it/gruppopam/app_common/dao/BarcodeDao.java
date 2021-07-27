package it.gruppopam.app_common.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

import it.gruppopam.app_common.model.entity.Barcode;

import static it.gruppopam.app_common.model.entity.Barcode.BARCODES_TABLE;
import static it.gruppopam.app_common.utils.AppConstants.COUNT_FROM;
import static it.gruppopam.app_common.utils.AppConstants.DELETE_FROM;
import static it.gruppopam.app_common.utils.AppConstants.SELECT_FROM;

@Dao
public interface BarcodeDao extends IDao<Barcode> {
    String SELECT_ALL_QUERY = SELECT_FROM + BARCODES_TABLE;
    String TRUNCATE_QUERY = DELETE_FROM + BARCODES_TABLE;
    String COUNT_QUERY = COUNT_FROM + BARCODES_TABLE;
    String DELETE_QUERY = DELETE_FROM + BARCODES_TABLE + " where start_date > :arg0 or end_date < :arg1";

    @Query(COUNT_QUERY)
    int count();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<Barcode> collect);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(Barcode entity);

    @Transaction
    @Query(TRUNCATE_QUERY)
    void truncateTable();

    @Transaction
    @Query(SELECT_ALL_QUERY)
    List<Barcode> fetchAll();

    @Transaction
    @Query(DELETE_QUERY)
    void deleteInactiveBarcode(Date startDate, Date endDate);
}
