/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom.converter;

/**
 * @author redcrow
 */
public interface TypeConverter<T> {

    T convert(Object value, String... pattern);
}
