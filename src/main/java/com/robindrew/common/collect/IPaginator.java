package com.robindrew.common.collect;

import java.util.List;

public interface IPaginator<E> {

	int size();

	List<E> getPage(int pageNumber, int pageSize);

	int getPageCount(int pageSize);

}
