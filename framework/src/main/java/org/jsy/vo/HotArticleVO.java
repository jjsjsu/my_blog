package org.jsy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVO {
    private Long id;
    private String title;
    private Long viewCount;
    //特定的view object类，用于避免一些特殊信息的返回
}
