package it.gruppopam.app_common.repository;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import it.gruppopam.app_common.dao.ArticleSupplierLineDao;
import it.gruppopam.app_common.dao.SupplierDao;
import it.gruppopam.app_common.dao.SupplierDistributionModeDao;
import it.gruppopam.app_common.model.OrderOption;
import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.model.entity.ArticleSupplierLine;
import it.gruppopam.app_common.model.entity.LineDetail;
import it.gruppopam.app_common.model.entity.Supplier;
import it.gruppopam.app_common.model.entity.SupplierDistributionMode;
import it.gruppopam.app_common.model.relations.SupplierAnagraphicWithDistributionMode;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.model.DistributionMode.X;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;


@SuppressWarnings("ALL")
public class SupplierRepository extends BaseRepository<Supplier> implements Persistable<SupplierAnagraphicWithDistributionMode> {
    private SupplierDao supplierDao;
    private SupplierDistributionModeDao supplierDistributionModeDao;
    private ArticleSupplierLineDao articleSupplierLineDao;

    @Inject
    public SupplierRepository(TransactionManager transactionManager, SupplierDao supplierDao, SupplierDistributionModeDao supplierDistributionModeDao,
                              ArticleSupplierLineDao articleSupplierLineDao) {
        super(transactionManager, supplierDao);
        this.supplierDao = supplierDao;
        this.supplierDistributionModeDao = supplierDistributionModeDao;
        this.articleSupplierLineDao = articleSupplierLineDao;
    }

    public List<SupplierAnagraphicWithDistributionMode> fetchAll() {
        return supplierDao.fetchAll();
    }

    public void save(List<SupplierAnagraphicWithDistributionMode> suppliers) {
        runInTransaction(() -> {
            supplierDao.save(Stream.of(suppliers).map(SupplierAnagraphicWithDistributionMode::getSupplier).collect(toList()));
            List<SupplierDistributionMode> collect = Stream.of(suppliers)
                    .filter(supplier -> !isEmpty(supplier.getDistributionModes()))
                    .map(supplier ->
                            Stream.of(supplier.getDistributionModes())
                                    .peek(dm -> dm.setSupplierId(supplier.getSupplier().getSupplierId())).collect(toList())
                    )
                    .flatMap(Stream::of)
                    .collect(toList());
            supplierDistributionModeDao.save(collect);
        });
    }

    public long save(SupplierAnagraphicWithDistributionMode suppliers) {
        return runInTransactionWithResult(() -> {
            long id = supplierDao.save(suppliers.getSupplier());
            if (!isEmpty(suppliers.getDistributionModes())) {
                supplierDistributionModeDao.save(suppliers.getDistributionModes());
            }
            return id;
        });
    }

    public void save(ArticleSupplierLine articleSupplierLine) {
        articleSupplierLineDao.save(articleSupplierLine);
    }

    public void truncateTable() {
        supplierDao.truncateTable();
    }

    public int countSuppliers() {
        return supplierDao.count();
    }

    public int countSupplierDistributionMode() {
        return supplierDistributionModeDao.count();
    }

    @Override
    public Long persist(SupplierAnagraphicWithDistributionMode supplierAnagraphic) {
        save(supplierAnagraphic);
        return 0L;
    }

    @Override
    public Long persist(List<SupplierAnagraphicWithDistributionMode> list) {
        save(list);
        return 0L;
    }

    public Map<SupplierAnagraphicWithDistributionMode, List<LineDetail>> retrieveDirectSupplierLineDetails(OrderOption orderOption, Long componentId) {

        String directSuppliersAndLinesQuery = "select sad.supplier_id supplier_id, lines_with_products.line_id line_id, sad.name supplier_name, line.description line_description from" +
                "(select distinct innAsl.line_id from article_supplier_lines innAsl" +
                " join articles innArt on innAsl.article_id = innArt.article_id and innArt.component_id = ? and innArt.active and innArt.order_option = ?" +
                " and innArt.replenishment_type = 'D') lines_with_products" +
                " join line_details line on lines_with_products.line_id = line.line_id and line.active" +
                " join supplier_anagraphic_details sad on sad.supplier_id = line.supplier_id and sad.active" +
                " order by sad.name, lines_with_products.line_id";

        return retrieveAndCollectSuppliersAndLines(directSuppliersAndLinesQuery, componentId, orderOption);
    }

    public Map<SupplierAnagraphicWithDistributionMode, List<LineDetail>> retrieveXDSupplierLineDetails(OrderOption orderOption, Long componentId) {

        String xdSuppliersAndLinesQuery = "select sad.supplier_id supplier_id, lines_with_products.line_id line_id, sad.name supplier_name, line.description line_description from" +
                "(select distinct innAsl.line_id from article_supplier_lines innAsl" +
                " join articles innArt on innAsl.article_id = innArt.article_id and innArt.component_id = ? and innArt.active and innArt.order_option = ?) lines_with_products" +
                " join line_details line on lines_with_products.line_id = line.line_id and line.active" +
                " join supplier_anagraphic_details sad on sad.supplier_id = line.supplier_id and sad.active" +
                " join supplier_distribution_modes sdm on sdm.supplier_id = line.supplier_id and sdm.distribution_mode = 'X'" +
                " order by sad.name, lines_with_products.line_id";

        return retrieveAndCollectSuppliersAndLines(xdSuppliersAndLinesQuery, componentId, orderOption);
    }

    private Map<SupplierAnagraphicWithDistributionMode, List<LineDetail>> retrieveAndCollectSuppliersAndLines(String selectQuery, Long componentId, OrderOption orderOption) {
        Cursor cursor = null;
        try {

            cursor = selectCursor(selectQuery, componentId, orderOption.toString());

            return collectSuppliersAndLinesIntoMap(cursor);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private Map<SupplierAnagraphicWithDistributionMode, List<LineDetail>> collectSuppliersAndLinesIntoMap(Cursor cursor) {
        Map<SupplierAnagraphicWithDistributionMode, List<LineDetail>> map = new LinkedHashMap<>();
        while (cursor.moveToNext()) {
            addToSupplierLineToMap(map, buildSupplierAnagraphic(cursor), getLineDetail(cursor));
        }
        return map;
    }

    private void addToSupplierLineToMap(Map<SupplierAnagraphicWithDistributionMode, List<LineDetail>> map,
                                        SupplierAnagraphicWithDistributionMode supplier, LineDetail lineDetail) {
        Map.Entry<SupplierAnagraphicWithDistributionMode, List<LineDetail>> entry = Stream.of(map.entrySet())
                .filter(s -> s.getKey().getSupplierId().equals(supplier.getSupplierId())).findSingle()
                .orElse(null);
        if (entry == null) {
            map.put(supplier, new ArrayList<LineDetail>() {{
                add(lineDetail);
            }});
        } else {
            entry.getValue().add(lineDetail);
        }
    }

    private LineDetail getLineDetail(Cursor cursor) {
        return new LineDetail(cursor.getLong(cursor.getColumnIndex("line_id")),
                cursor.getLong(cursor.getColumnIndex("supplier_id")),
                cursor.getString(cursor.getColumnIndex("line_description")), true);
    }

    public SupplierAnagraphicWithDistributionMode getSupplierDetail(long supplierId) {
        String query = "select * from supplier_anagraphic_details where supplier_id  = ?";
        return supplierDao.selectWrapper(buildQuery(query, supplierId));
    }

    public Map<Long, String> getSupplierDetails(List<Long> supplierIds) {
        List<SupplierAnagraphicWithDistributionMode> suppliers = supplierDao.findBySupplierIds(supplierIds);
        return Stream.of(suppliers).collect(Collectors.toMap(SupplierAnagraphicWithDistributionMode::getSupplierId, SupplierAnagraphicWithDistributionMode::getSupplierName));
    }

    private SupplierAnagraphicWithDistributionMode buildSupplierAnagraphic(Cursor cursor) {
        return SupplierAnagraphicWithDistributionMode.create(cursor.getLong(cursor.getColumnIndex("supplier_id")),
                cursor.getString(cursor.getColumnIndex("supplier_name")), true);
    }

    public SupplierAnagraphicWithDistributionMode find(long id) {
        return supplierDao.selectWrapper(buildQuery("select * from supplier_anagraphic_details where id = ?", id));
    }

    public Map<Long, SupplierAnagraphicWithDistributionMode> getXDSuppliers() {
        String query = "select sad.* from supplier_anagraphic_details sad " +
                "join supplier_distribution_modes sdm " +
                "on sad.supplier_id = sdm.supplier_id " +
                "where sdm.distribution_mode=? " +
                "and sad.active=?";
        List<SupplierAnagraphicWithDistributionMode> suppliers = selectWrapperListQuery(query, X.name(), true);
        return Stream.of(suppliers).collect(Collectors.toMap(SupplierAnagraphicWithDistributionMode::getSupplierId, s -> s));
    }

    public SupplierAnagraphicWithDistributionMode selectWrapperQuery(String rawQuery, Object... objects) {
        return supplierDao.selectWrapper(buildQuery(rawQuery, objects));
    }

    public List<SupplierAnagraphicWithDistributionMode> selectWrapperListQuery(String rawQuery, Object... objects) {
        return supplierDao.selectAllWrapper(buildQuery(rawQuery, objects));
    }
}
