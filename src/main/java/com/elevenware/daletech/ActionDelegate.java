package com.elevenware.daletech;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author George McIntosh <george@elevenware.com>
 * @version 1.0
 * @since 20th August 2014
 *
 * ActionDelegate - an annotation
 * <p>
 * An annotation to denote that the annotated method is a candidate for delegation
 * by the script being parsed.
 * </p>
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionDelegate {

}
