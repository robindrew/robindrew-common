package com.robindrew.common.date.range;

import java.time.LocalDate;

public interface ILocalDateRange {

	LocalDate getFrom();

	LocalDate getTo();

	boolean contains(LocalDate date);

}
