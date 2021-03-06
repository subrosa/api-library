package com.subrosa.api.actions.list.annotation;

import java.lang.annotation.Target;

import com.subrosa.api.actions.list.Operator;

/**
 * Annotation that defines a grouping of fields that can be queried against together.
 *
 * This allows for an OR clause across filters to support things like searching for a term
 * that may appear in any of a number of fields.
 *
 * This will only occur within a {@link FilterGroups} complex annotation.
 *
 * @see {@link FilterGroups}
 * @see {@link Operator}
 */
@Target({ }) // SUPPRESS CHECKSTYLE AnnotationUseStyle evidently checkstyle doesn't grok complex annotation type declarations
public @interface FilterGroup {

    /**
     * Specify the name by which the group should be referred.
     */
    String value();

    /**
     * Specify the fields that are included in the filter group.
     */
    String[] fields();

    /**
     * Specify the filter operations that can be applied to this group in the query.
     */
    Operator[] operators() default { Operator.EQUAL, Operator.NOT_EQUAL };
}
