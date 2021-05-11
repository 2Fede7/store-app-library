package it.gruppopam.app_common.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import it.gruppopam.app_common.dao.ArticleDao;
import it.gruppopam.app_common.dao.BarcodeDao;
import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.model.entity.Barcode;

@SuppressWarnings("ALL")
public class BarcodeRepository extends BaseRepository<Barcode> implements Persistable<Barcode> {
    private BarcodeDao barcodeDao;
    private ArticleDao articleDao;

    @Inject
    public BarcodeRepository(TransactionManager transactionManager, BarcodeDao barcodeDao, ArticleDao articleDao) {
        super(transactionManager, barcodeDao);
        this.barcodeDao = barcodeDao;
        this.articleDao = articleDao;
    }

    public List<Barcode> fetchAll() {
        return barcodeDao.fetchAll();
    }

    public void saves(List<Barcode> barcodes) {
        barcodeDao.save(barcodes);
    }

    public long save(Barcode barcode) {
        return barcodeDao.save(barcode);
    }

    public void truncateTable() {
        barcodeDao.truncateTable();
    }

    public void truncateAndSave(List<Barcode> barcodes) {
        barcodeDao.truncateTable();
        barcodeDao.save(barcodes);
    }

    public int countBarcodes() {
        return barcodeDao.count();
    }

    @Override
    public Long persist(Barcode barcode) {
        save(barcode);
        return 0L;
    }

    @Override
    public Long persist(List<Barcode> list) {
        saves(list);
        return 0L;
    }

    public Barcode persistAndRetrieve(Barcode barcode) {
        Long id = save(barcode);
        return find(id);
    }

    public Barcode findActiveByBarcode(String barcode) {
        Calendar now = GregorianCalendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return selectSingle("select * from barcode where barcode = ? and start_date <= ? and end_date >= ?",
                barcode, now.getTimeInMillis(), now.getTimeInMillis());
    }

    public void purgeInactiveBarcode() {
        Date now = new Date();
        barcodeDao.deleteInactiveBarcode(now, now);
    }

    public Barcode find(long id) {
        return selectSingle("select * from barcode where id = ?", id);
    }

    public Barcode findLatestByBarcode(String barcode) {
        Calendar now = GregorianCalendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        List<Barcode> barcodes = select("select * from barcode where barcode = ? and start_date <= ? and end_date >= ? " +
                "order by start_date desc", barcode, now.getTimeInMillis(), now.getTimeInMillis());
        return barcodes != null && barcodes.size() >= 1 ? barcodes.get(0) : null;
    }
}
