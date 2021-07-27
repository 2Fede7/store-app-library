package it.gruppopam.app_common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ArticleSupplierLineDto {

    @Setter
    private Long articleId;

    private Long supplierId;

    private Long lineId;


}
