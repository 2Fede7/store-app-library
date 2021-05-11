package it.gruppopam.app_common.repository;

import java.util.List;

import javax.inject.Inject;

import it.gruppopam.app_common.dao.PromotionDao;
import it.gruppopam.app_common.model.Persistable;
import it.gruppopam.app_common.model.entity.Promotion;

import static it.gruppopam.app_common.model.entity.Promotion.PROMOTIONS_TABLE;

public class PromotionRepository extends BaseRepository<Promotion> implements Persistable<Promotion> {
    private PromotionDao promotionDao;

    @Inject
    public PromotionRepository(TransactionManager transactionManager, PromotionDao promotionDao) {
        super(transactionManager, promotionDao);
        this.promotionDao = promotionDao;
    }

    public void save(List<Promotion> lineDetails) {
        promotionDao.save(lineDetails);
    }

    public long save(Promotion Promotion) {
        return promotionDao.save(Promotion);
    }

    public int count() {
        return promotionDao.count();
    }

    @Override
    public Long persist(Promotion Promotion) {
        save(Promotion);
        return 0L;
    }

    @Override
    public Long persist(List<Promotion> list) {
        save(list);
        return 0L;
    }

    public Promotion find(long id) {
        return selectSingle("select * from " + PROMOTIONS_TABLE + " where article_id=" + id);
    }

    public List<Promotion> fetchAll() {
        return promotionDao.fetchAll();
    }

    public List<Promotion> findByArticleId(long articleId) {
        return select("select * from promotions where article_id = ?", articleId);
    }
}
