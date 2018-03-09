package com.robindrew.common.io.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.UnitTime;

public class UnitTimeSerializer implements IDataSerializer<UnitTime> {

	@Override
	public UnitTime readObject(IDataReader reader) throws IOException {
		TimeUnit unit = reader.readEnum(TimeUnit.class, false);
		long time = reader.readPositiveLong();
		return new UnitTime(time, unit);
	}

	@Override
	public void writeObject(IDataWriter writer, UnitTime instance) throws IOException {
		writer.writeEnum(instance.getUnit(), false);
		writer.writePositiveLong(instance.getTime());
	}
}
