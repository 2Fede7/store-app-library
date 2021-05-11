package it.gruppopam.app_common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import it.gruppopam.app_common.model.entity.ArticleOrderBlock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
public class ArticleOrderBlockDto {

    @Setter
    private Long articleId;

    @JsonProperty("replenishment_block_reason")
    private String blockReason;

    private Date startDateBlock;

    private Date endDateBlock;

    public ArticleOrderBlock mapToModel() {
        return null;
    }
}
