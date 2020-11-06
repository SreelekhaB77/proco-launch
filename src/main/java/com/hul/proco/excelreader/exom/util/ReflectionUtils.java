/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom.util;

import static com.hul.proco.excelreader.exom.util.CollectionUtils.isEmpty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import com.hul.proco.excelreader.exom.annotation.Column;
import com.hul.proco.excelreader.exom.converter.TypeConverter;
import com.hul.proco.excelreader.exom.converter.TypeConverters;

/**
 * @author redcrow
 */
public class ReflectionUtils {
    

    private static String toUpperCaseFirstCharacter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setValueOnField(Object instance, Field field, Object value) throws Exception {
        Class clazz = instance.getClass();
        String setMethodName = "set" + toUpperCaseFirstCharacter(field.getName());

        for (Map.Entry<Class, TypeConverter> entry : TypeConverters.getConverter().entrySet()) {
            if (field.getType().equals(entry.getKey())) {
                Method method = clazz.getDeclaredMethod(setMethodName, entry.getKey());
                Column column = field.getAnnotation(Column.class);
                        
                method.invoke(
                        instance,
                        entry.getValue().convert(
                                value,
                                column == null ? null : column.pattern()
                        )
                );
            }
        }
    }

    @SuppressWarnings("rawtypes")
	public static void eachFields(Class clazz, EachFieldCallback callback) throws Throwable {
        Field[] fields = clazz.getDeclaredFields();
        if (!isEmpty(fields)) {
            for (Field field : fields) {
                callback.each(
                        field,
                        field.isAnnotationPresent(Column.class)
                        ? field.getAnnotation(Column.class).name()
                        : field.getName()
                );
            }
        }
    }
}
