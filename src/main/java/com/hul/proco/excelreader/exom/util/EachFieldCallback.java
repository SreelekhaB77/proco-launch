/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package com.hul.proco.excelreader.exom.util;

import java.lang.reflect.Field;

/**
 * @author redcrow
 */
public interface EachFieldCallback {

    void each(Field field, String name) throws Throwable;
}
