/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TypeConverters {

    @SuppressWarnings("rawtypes")
	private static Map<Class, TypeConverter> converter;

    private static void registerConverter() {
        converter = new HashMap<>();
        converter.put(String.class, new StringTypeConverter());
        converter.put(Integer.class, new IntegerTypeConverter());
        converter.put(Double.class, new DoubleTypeConverter());
        converter.put(Boolean.class, new BooleanTypeConverter());
        converter.put(Date.class, new DateTypeConverter());
    }

    @SuppressWarnings("rawtypes")
	public static Map<Class, TypeConverter> getConverter() {
        if (converter == null) {
            registerConverter();
        }

        return converter;
    }
}
