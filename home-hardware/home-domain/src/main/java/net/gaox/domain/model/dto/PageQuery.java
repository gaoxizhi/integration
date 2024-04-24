package net.gaox.domain.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 分页查询参数 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-13 13:22
 */
@Data
@NoArgsConstructor
public class PageQuery {
    private Integer pageNum = 1;
    private Integer pageSize = 20;

    public PageQuery(Integer pageNum, Integer pageSize) {
        if (null == pageNum) {
            pageNum = 1;
        }
        if (1 > pageNum) {
            pageNum = 1;
        }
        if (null == pageSize) {
            pageSize = this.pageSize;
        }
        if (1 > pageSize) {
            pageSize = this.pageSize;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
