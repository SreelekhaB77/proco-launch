/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom.converter;

/**
 * @author redcrow
 */
public class StringTypeConverter implements TypeConverter<String> {

    @Override
    public String convert(Object value, String... pattern) {
        if (value instanceof String) {
            return ((String) value).trim();
        }
        
        return null;
    }

}
