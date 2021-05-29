package com.whu.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 用来返回分页Grid的数据格式
 * @author Created By MrXi on 2019/12/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedGridResult {

	/**当前页数*/
	private int page;
	/**总页数*/
	private int total;
	/**总记录数*/
	private long records;
	/**每行显示的内容*/
	private List<?> rows;

}
