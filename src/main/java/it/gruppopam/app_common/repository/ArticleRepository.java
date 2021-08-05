package it.gruppopam.app_common.repository;

import com.annimon.stream.Stream;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import it.gruppopam.app_common.dao.ArticleDao;
import it.gruppopam.app_common.dao.ArticleOrderBlockDao;
import it.gruppopam.app_common.dao.ArticleSupplierLineDao;
import it.gruppopam.app_common.dao.CostAndDiscountDao;
import it.gruppopam.app_common.dao.PromotionDao;
import it.gruppopam.app_common.dao.SubstituteArticleDao;
import it.gruppopam.app_common.model.OrderOption;
import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.model.entity.Article;
import it.gruppopam.app_common.model.entity.ArticleOrderBlock;
import it.gruppopam.app_common.model.entity.ArticleSupplierLine;
import it.gruppopam.app_common.model.entity.Promotion;
import it.gruppopam.app_common.model.entity.SubstituteArticle;
import it.gruppopam.app_common.model.relations.ArticleWithRelations;
import it.gruppopam.app_common.model.relations.ArticleWithRelationsAndBarcode;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@SuppressWarnings("ALL")
public class ArticleRepository extends BaseRepository<Article> implements Persistable<ArticleWithRelations> {
    private ArticleDao articleDao;
    private PromotionDao promotionDao;
    private ArticleSupplierLineDao articleSupplierLineDao;
    private ArticleOrderBlockDao articleOrderBlockDao;
    private SubstituteArticleDao substituteArticleDao;
    private CostAndDiscountDao costAndDiscountDao;

    @Inject
    public ArticleRepository(TransactionManager transactionManager, ArticleDao articleDao, PromotionDao promotionDao,
                             ArticleSupplierLineDao articleSupplierLineDao, ArticleOrderBlockDao articleOrderBlockDao,
                             SubstituteArticleDao substituteArticleDao, CostAndDiscountDao costAndDiscountDao) {
        super(transactionManager, articleDao);
        this.articleDao = articleDao;
        this.promotionDao = promotionDao;
        this.articleSupplierLineDao = articleSupplierLineDao;
        this.articleOrderBlockDao = articleOrderBlockDao;
        this.substituteArticleDao = substituteArticleDao;
        this.costAndDiscountDao = costAndDiscountDao;
    }

    public void save(List<ArticleWithRelations> articleWrappers) {
        Stream.of(articleWrappers).forEach(this::save);
    }

    public long save(ArticleWithRelations articleWrapper) {
        return runInTransactionWithResult(() -> {
            purgeDependent(articleWrapper.getArticleId());
            long articleId = articleDao.save(articleWrapper.getArticle());
            if (!isEmpty(articleWrapper.getActiveAndFuturePromotions())) {
                promotionDao.save(articleWrapper.getActiveAndFuturePromotions());
            }
            if (!isEmpty(articleWrapper.getArticleSupplierLines())) {
                articleSupplierLineDao.save(Stream.of(articleWrapper.getArticleSupplierLines())
                        .peek(line -> line.setArticleId(articleId))
                        .collect(toList()));
            }
            if (!isEmpty(articleWrapper.getArticleOrderBlocks())) {
                articleOrderBlockDao.save(Stream.of(articleWrapper.getArticleOrderBlocks())
                        .peek(aob -> aob.setArticleId(articleId))
                        .collect(toList()));
            }
            if (articleWrapper.getSubstituteArticle() != null) {
                substituteArticleDao.save(articleWrapper.getSubstituteArticle());
            }
            if (!isEmpty(articleWrapper.getCostAndDiscount())) {
                costAndDiscountDao.save(Stream.of(articleWrapper.getCostAndDiscount())
                        .peek(aob -> aob.setArticleId(articleId))
                        .collect(toList()));
            }
            return articleId;
        });
    }

    public Article persistAndRetrieve(Article article) {
        Long id = articleDao.save(article);
        return find(id).getArticle();
    }

    private void purgeDependent(Long articleId) {
        purgeArticleOrderBlocks(articleId);
        purgeArticlePromotions(articleId);
        purgeArticleSupplierLines(articleId);
    }

    public void truncateTable() {
        articleDao.truncateTable();
    }

    public int countPromotion() {
        return promotionDao.count();
    }

    public int countLines() {
        return articleSupplierLineDao.count();
    }

    public long getMaxSequenceNumber() {
        return getMaxSequenceNumber(Article.ARTICLES_TABLE);
    }

    @Override
    public Long persist(ArticleWithRelations articleWrapper) {
        return save(articleWrapper);
    }

    @Override
    public Long persist(List<ArticleWithRelations> list) {
        save(list);
        return 0L;
    }

    public void deleteAll() {
        truncateTable();
    }

    public List<ArticleWithRelations> fetchAll() {
        return articleDao.fetchAll();
    }

    public ArticleWithRelations selectWrapperQuery(String rawQuery, Object... objects) {
        return articleDao.selectWrapper(buildQuery(rawQuery, objects));
    }

    public List<ArticleWithRelations> selectWrapperListQuery(String rawQuery, Object... objects) {
        return runInTransactionWithResult(() -> {
            return articleDao.selectWrapperList(buildQuery(rawQuery, objects));
        });
    }

    public ArticleWithRelations find(Long articleId) {
        return articleDao.findArticleWithRelationsByArticleId(articleId);
    }

    public Article findByArticleId(Long articleId) {
        return articleDao.findByArticleId(articleId);
    }

    public List<ArticleWithRelations> find(List<Long> articleIds) {
        return articleDao.findManyByArticleIds(articleIds);
    }

    public List<Article> findAsList(Long articleId, int resultLimit) {
        return articleDao.selectMany(buildQuery("select * from articles a join barcode b on a.article_id = b.article_id " +
                "where a.article_id=? limit ?", articleId, resultLimit));
    }

    public int count() {
        return articleDao.count();
    }

    public List<Article> findAsList(Long articleId) {
        Article article = findByArticleId(articleId);
        return article == null ? emptyList() : singletonList(article);
    }

    public List<Article> getArticlesMatchingName(String nameSubString, int resultLimit) {
        return select("select * from articles where name like ? order by name limit ?", "%" + nameSubString + "%", resultLimit);
    }

    public List<Article> findByBarcode(String barcode, int resultLimit) {
        return select("select * from articles art " +
                "join barcode b on art.article_id = b.article_id where barcode = ? order by name limit ?", barcode, resultLimit);
    }

    public ArticleWithRelationsAndBarcode findWithBarcode(Long articleId) {
        return articleDao.findArticleWithRelationsAndBarcodeByArticleId(articleId);
    }

    public List<Promotion> getActiveAndFuturePromotions(Long articleId) {
        return selectPromotion("select * from promotions where article_id=?", articleId);
    }

    public List<ArticleOrderBlock> getActiveArticleOrderBlocks(Long articleId) {
        long today = new Date().getTime();
        return selectArticleOrderBlock(
                "select * from article_order_block where article_id=? and start_date_block <= ? " +
                        "and end_date_block >= ?", articleId, today, today);
    }

    public List<ArticleSupplierLine> getArticleSupplierLines(Long articleId) {
        return selectArticleSupplierLine("select * from article_supplier_lines where article_id=?", articleId);
    }

    public List<ArticleWithRelations> getXdSupplierLineArticles(long supplierId, long lineId, OrderOption orderOption, Long componentId) {
        return selectWrapperListQuery("select art.* from articles art " +
                        "join article_supplier_lines asl on art.article_id = asl.article_id and art.xdxv_supplier_id = asl.supplier_id and asl.line_id = ? " +
                        "where art.xdxv_supplier_id = ? and art.component_id = ? and art.active and art.order_option = ? " +
                        "order by art.name",
                lineId, supplierId, componentId, orderOption.toString());
    }

    public List<ArticleWithRelations> getDirectSupplierLineArticles(long supplierId, long lineId, OrderOption orderOption, Long componentId) {
        return selectWrapperListQuery("select art.* from articles art " +
                        "join article_supplier_lines asl on art.article_id = asl.article_id and art.direct_supplier_id = asl.supplier_id and asl.line_id = ? " +
                        "where art.replenishment_type = 'D' and art.direct_supplier_id = ? and art.component_id = ? and art.active and art.order_option = ? " +
                        "order by art.name",
                lineId, supplierId, componentId, orderOption.toString());
    }

    public Promotion selectSinglePromotion(String rawQuery, Object... objects) {
        return promotionDao.select(buildQuery(rawQuery, objects));
    }

    public List<Promotion> selectPromotion(String rawQuery, Object... objects) {
        return promotionDao.selectMany(buildQuery(rawQuery, objects));
    }

    public ArticleSupplierLine selectSingleArticleSupplierLine(String rawQuery, Object... objects) {
        return articleSupplierLineDao.select(buildQuery(rawQuery, objects));
    }

    public List<ArticleSupplierLine> selectArticleSupplierLine(String rawQuery, Object... objects) {
        return articleSupplierLineDao.selectMany(buildQuery(rawQuery, objects));
    }

    public ArticleOrderBlock selectSingleArticleOrderBlock(String rawQuery, Object... objects) {
        return articleOrderBlockDao.select(buildQuery(rawQuery, objects));
    }

    public List<ArticleOrderBlock> selectArticleOrderBlock(String rawQuery, Object... objects) {
        return articleOrderBlockDao.selectMany(buildQuery(rawQuery, objects));
    }

    public List<ArticleWithRelations> getArticles(String rawQuery, Object... objects) {
        return articleDao.selectWrapperList(buildQuery(rawQuery, objects));
    }

    public Long getCountOfArticles(String query) {
        return articleDao.selectLong(buildQuery(query));
    }

    public void purgeArticleSupplierLines(Long articleId) {
        deleteQuery("delete from article_supplier_lines where article_id = ?", articleId);
    }

    public void purgeArticleOrderBlocks(Long articleId) {
        deleteQuery("delete from article_order_block where article_id = ?", articleId);
    }

    public void purgeArticlePromotions(Long articleId) {
        deleteQuery("delete from promotions where article_id = ?", articleId);
    }

    public SubstituteArticle getSubstituteBy(Long substituteArticleId) {
        return substituteArticleDao.select(buildQuery("select * from substitute_article where substitute_article_id=?", substituteArticleId));
    }

    public SubstituteArticle getLastSubstituteBy(SubstituteArticle substituteByArticle) {
        SubstituteArticle substituteArticle = getSubstituteBy(substituteByArticle.getArticleId());
        if (substituteArticle == null) {
            return substituteByArticle;
        }
        return getLastSubstituteBy(substituteArticle);
    }

    public SubstituteArticle getSubstitutedArticle(Long articleId) {
        return substituteArticleDao.select(buildQuery("select * from substitute_article where article_id=?", articleId));
    }

    public SubstituteArticle isReplacedOrHaveSubstituteArticle(Long articleId) {
        return substituteArticleDao.select(buildQuery("select * from substitute_article where article_id=? or substitute_article_id=?", articleId, articleId));
    }

}

