package com.cloudapp.lib.xml;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XmlProperty {
	public static enum XmlPropertyType {
		INTEGER, DOUBLE, DECIMAL, STRING, TEXT, CDATA, OBJECT, LIST
	}

	XmlPropertyType value();
}
