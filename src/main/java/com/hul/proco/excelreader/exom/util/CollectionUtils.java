/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom.util;

import java.util.Collection;

/**
 * @author redcrow
 */
public class CollectionUtils {

    @SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] object) {
        return object == null || object.length < 1;
    }
}
