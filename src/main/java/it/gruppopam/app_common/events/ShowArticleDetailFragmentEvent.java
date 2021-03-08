package it.gruppopam.app_common.events;

import lombok.Getter;

@Getter
public class ShowArticleDetailFragmentEvent {

    Long articleId;
    Long departmentId;

    public ShowArticleDetailFragmentEvent(Long articleId, Long departmentId) {
        this.articleId = articleId;
        this.departmentId = departmentId;
    }

}
