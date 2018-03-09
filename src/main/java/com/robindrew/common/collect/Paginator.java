package com.robindrew.common.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Paginator<E> implements IPaginator<E> {

	private final List<E> list;

	public Paginator(Collection<? extends E> list) {
		if (list == null) {
			throw new NullPointerException("list");
		}
		this.list = new ArrayList<E>(list);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public List<E> getPage(int pageNumber, int pageSize) {
		if (pageNumber < 1) {
			throw new IllegalArgumentException("pageNumber=" + pageNumber);
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("pageSize=" + pageSize);
		}
		int fromIndex = (pageNumber - 1) * pageSize;
		if (fromIndex >= list.size()) {
			return Collections.emptyList();
		}
		int toIndex = fromIndex + pageSize;
		if (toIndex > list.size()) {
			toIndex = list.size();
		}
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public int getPageCount(int pageSize) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("pageSize=" + pageSize);
		}
		int count = list.size() / pageSize;
		if (list.size() % pageSize > 0) {
			count++;
		}
		return count;
	}

	public List<List<E>> getPages(int pageSize) {
		if (pageSize < 1) {
			throw new IllegalArgumentException("pageSize=" + pageSize);
		}
		List<List<E>> pages = new ArrayList<>();
		int count = getPageCount(pageSize);
		for (int i = 1; i <= count; i++) {
			pages.add(getPage(i, pageSize));
		}
		return pages;
	}

}
