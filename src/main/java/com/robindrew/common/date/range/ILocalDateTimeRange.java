package com.robindrew.common.date.range;

import java.time.LocalDateTime;

public interface ILocalDateTimeRange {

	LocalDateTime getFrom();

	LocalDateTime getTo();

	boolean contains(LocalDateTime date);

}
