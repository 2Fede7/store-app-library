package it.gruppopam.app_common.mapper;

import static com.annimon.stream.Collectors.toList;
import static it.gruppopam.app_common.utils.CollectionUtils.isEmpty;

import com.annimon.stream.Stream;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import it.gruppopam.app_common.dto.ArticleDto;
import it.gruppopam.app_common.dto.ArticleOrderBlockDto;
import it.gruppopam.app_common.dto.ArticleSupplierLineDto;
import it.gruppopam.app_common.dto.PromotionDto;
import it.gruppopam.app_common.model.entity.Article;
import it.gruppopam.app_common.model.entity.ArticleOrderBlock;
import it.gruppopam.app_common.model.entity.ArticleSupplierLine;
import it.gruppopam.app_common.model.entity.Promotion;
import it.gruppopam.app_common.model.entity.SubstituteArticle;
import it.gruppopam.app_common.model.relations.ArticleWithRelations;

public class ArticleMapper implements BaseMapper<ArticleDto, ArticleWithRelations> {

    @Inject
    public ArticleMapper() {
    }

    @Override
    public ArticleDto mapToDto(ArticleWithRelations articleWrapper) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setDepartmentId(articleWrapper.getDepartmentId());
        articleDto.setArticleId(articleWrapper.getArticleId());
        articleDto.setArticleName(articleWrapper.getArticleName());
        articleDto.setRotationClass(articleWrapper.getRotationClass());
        articleDto.setPiecesPerPackage(articleWrapper.getPiecesPerPackage());
        articleDto.setWeight(articleWrapper.getWeight());
        articleDto.setMeasurementUnitCode(articleWrapper.getMeasurementUnitCode());
        articleDto.setSequenceNumber(articleWrapper.getSequenceNumber());
        articleDto.setMinOrderableQuantity(articleWrapper.getMinOrderableQuantity());
        articleDto.setMaxOrderableQuantity(articleWrapper.getMaxOrderableQuantity());
        articleDto.setMerchandiseAreaId(articleWrapper.getMerchandiseAreaId());
        articleDto.setConsumerDepartments(articleWrapper.getConsumerDepartments());
        articleDto.setReorderClass(articleWrapper.getReorderClass());
        articleDto.setStandardPackageWeight(articleWrapper.getStandardPackageWeight());
        articleDto.setSaleType(articleWrapper.getSaleType());
        articleDto.setAssortmentType(articleWrapper.getAssortmentType());
        articleDto.setXdxvSupplierId(articleWrapper.getXdxvSupplierId());
        articleDto.setDistributionMode(articleWrapper.getDistributionMode());
        articleDto.setReplenishmentType(articleWrapper.getReplenishmentType());
        articleDto.setDirectSupplierId(articleWrapper.getDirectSupplierId());
        articleDto.setActive(articleWrapper.getActive());
        articleDto.setAssortmentStartDate(articleWrapper.getAssortmentStartDate());
        articleDto.setReplenishmentStatus(articleWrapper.getReplenishmentStatus());
        articleDto.setOrderOption(articleWrapper.getOrderOption());
        articleDto.setComponentId(articleWrapper.getComponentId());
        articleDto.setWastagePercentage(articleWrapper.getWastagePercentage());
        articleDto.setMeasurementUnitType(articleWrapper.getMeasurementUnitType());
        articleDto.setPrice(articleWrapper.getPrice());
        articleDto.setSectorId(articleWrapper.getSectorId());
        articleDto.setCategoryId(articleWrapper.getCategoryId());
        articleDto.setChangeType(articleWrapper.getChangeType());
        articleDto.setBlocks(articleWrapper.getBlocks());
        articleDto.setTiers(articleWrapper.getTiers());
        return articleDto;
    }

    @Override
    public List<ArticleDto> mapToDto(List<ArticleWithRelations> list) {
        if (isEmpty(list)) {
            return null;
        }
        return Stream.of(list).map(this::mapToDto).collect(toList());
    }

    @Override
    public ArticleWithRelations mapToModel(ArticleDto articleDto) {
        ArticleWithRelations articleWrapper = new ArticleWithRelations();
        articleWrapper.setArticle(mapArticleDtoToArticle(articleDto));
        articleWrapper.setActiveAndFuturePromotions(mapArticlePromotionToModel(articleDto.getActiveAndFuturePromotions(), articleDto.getArticleId()));
        articleWrapper.setArticleOrderBlocks(mapArticleOrderBlockDtoToArticleOrderBlock(articleDto.getArticleOrderBlocks(), articleDto.getArticleId()));
        articleWrapper.setArticleSupplierLines(mapArticleSupplierLineToModel(articleDto.getArticleSupplierLines(), articleDto.getArticleId()));
        if (articleDto.getSubstituteArticleId() != null) {
            articleWrapper.setSubstituteArticle(new SubstituteArticle(articleDto.getArticleId(), articleDto.getSubstituteArticleId()));
        }
        return articleWrapper;
    }

    @Override
    public List<ArticleWithRelations> mapToModel(List<ArticleDto> articleDtos) {
        if (isEmpty(articleDtos)) {
            return null;
        }
        return Stream.of(articleDtos).map(this::mapToModel).collect(toList());
    }

    public ArticleSupplierLine mapToModel(ArticleSupplierLineDto articleSupplierLineDto, Long articleId) {
        ArticleSupplierLine articleSupplierLine = new ArticleSupplierLine();
        articleSupplierLine.setSupplierId(articleSupplierLineDto.getSupplierId());
        articleSupplierLine.setArticleId(articleId);
        articleSupplierLine.setLineId(articleSupplierLineDto.getLineId());
        return articleSupplierLine;
    }

    public Promotion mapToModel(PromotionDto promotionDto, Long articleId) {
        Promotion promotion = new Promotion();
        promotion.setArticleId(articleId);
        promotion.setEndDate(promotionDto.getEndDate());
        promotion.setEventCode(promotionDto.getEventCode());
        promotion.setFlyerCode(promotionDto.getFlyerCode());
        promotion.setIsInFlyer(promotionDto.getIsInFlyer());
        promotion.setPromoYear(promotionDto.getPromoYear());
        promotion.setPromoType(promotionDto.getPromoType());
        promotion.setStartDate(promotionDto.getStartDate());
        promotion.setPrice(safeGetDouble(promotionDto.getPrice()));
        promotion.setDiscountPercentage(safeGetDouble(promotionDto.getDiscountPercentage()));
        promotion.setSoldPieces(promotionDto.getSoldPieces());
        promotion.setPaidPieces(promotionDto.getPaidPieces());
        promotion.setIsFidelity(promotionDto.getIsFidelity());
        promotion.setFidelityDiscountPercentage(safeGetDouble(promotionDto.getFidelityDiscountPercentage()));
        promotion.setFidelityPrice(safeGetDouble(promotionDto.getFidelityPrice()));
        return promotion;
    }

    private Double safeGetDouble(BigDecimal price) {
        if (price == null) {
            return null;
        }
        return price.doubleValue();
    }

    private ArticleOrderBlock mapToModel(ArticleOrderBlockDto articleOrderBlockDto, Long articleId) {
        ArticleOrderBlock articleOrderBlock = new ArticleOrderBlock();
        articleOrderBlock.setArticleId(articleId);
        articleOrderBlock.setBlockReason(articleOrderBlockDto.getBlockReason());
        articleOrderBlock.setStartDateBlock(articleOrderBlockDto.getStartDateBlock());
        articleOrderBlock.setEndDateBlock(articleOrderBlockDto.getEndDateBlock());
        return articleOrderBlock;
    }

    public Article mapArticleDtoToArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setDepartmentId(articleDto.getDepartmentId());
        article.setArticleId(articleDto.getArticleId());
        article.setArticleName(articleDto.getArticleName());
        article.setRotationClass(articleDto.getRotationClass());
        article.setPiecesPerPackage(articleDto.getPiecesPerPackage());
        article.setWeight(articleDto.getWeight());
        article.setMeasurementUnitCode(articleDto.getMeasurementUnitCode());
        article.setSequenceNumber(articleDto.getSequenceNumber());
        article.setMinOrderableQuantity(articleDto.getMinOrderableQuantity());
        article.setMaxOrderableQuantity(articleDto.getMaxOrderableQuantity());
        article.setMerchandiseAreaId(articleDto.getMerchandiseAreaId());
        article.setConsumerDepartments(articleDto.getConsumerDepartments());
        article.setReorderClass(articleDto.getReorderClass());
        article.setStandardPackageWeight(articleDto.getStandardPackageWeight());
        article.setSaleType(articleDto.getSaleType());
        article.setAssortmentType(articleDto.getAssortmentType());
        article.setXdxvSupplierId(articleDto.getXdxvSupplierId());
        article.setDistributionMode(articleDto.getDistributionMode());
        article.setReplenishmentType(articleDto.getReplenishmentType());
        article.setDirectSupplierId(articleDto.getDirectSupplierId());
        article.setActive(articleDto.getActive());
        article.setAssortmentStartDate(articleDto.getAssortmentStartDate());
        article.setReplenishmentStatus(articleDto.getReplenishmentStatus());
        article.setOrderOption(articleDto.getOrderOption());
        article.setComponentId(articleDto.getComponentId());
        article.setWastagePercentage(articleDto.getWastagePercentage());
        article.setMeasurementUnitType(articleDto.getMeasurementUnitType());
        article.setPrice(articleDto.getPrice());
        article.setSectorId(articleDto.getSectorId());
        article.setCategoryId(articleDto.getCategoryId());
        article.setChangeType(articleDto.getChangeType());
        article.setBlocks(articleDto.getBlocks());
        article.setTiers(articleDto.getTiers());
        article.setDeliveryDateUnknown(articleDto.getDeliveryDateUnknown());
        article.setUnknownDeliveryReason(articleDto.getUnknownDeliveryReason());
        return article;
    }

    private List<ArticleOrderBlock> mapArticleOrderBlockDtoToArticleOrderBlock(List<ArticleOrderBlockDto> articleOrderBlocks, Long articleId) {
        if (isEmpty(articleOrderBlocks)) {
            return null;
        }
        return Stream.of(articleOrderBlocks)
                .map(aob -> mapToModel(aob, articleId))
                .collect(toList());
    }

    private List<ArticleSupplierLine> mapArticleSupplierLineToModel(List<ArticleSupplierLineDto> articleSupplierLineDtos, Long articleId) {
        if (isEmpty(articleSupplierLineDtos)) {
            return null;
        }
        return Stream.of(articleSupplierLineDtos)
                .map(asl -> mapToModel(asl, articleId))
                .collect(toList());
    }

    private List<Promotion> mapArticlePromotionToModel(List<PromotionDto> promotionDtos, Long articleId) {
        if (isEmpty(promotionDtos)) {
            return null;
        }
        return Stream.of(promotionDtos)
                .map(p -> mapToModel(p, articleId))
                .collect(toList());
    }
}
