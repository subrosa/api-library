package com.subrosa.api.actions.list;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import com.subrosa.api.actions.list.annotation.FilterGroup;
import com.subrosa.api.actions.list.annotation.FilterGroups;
import com.subrosa.api.actions.list.annotation.Filterable;

/**
 * Test {@link QueryCriteria}.
 */
public class QueryCriteriaTest {

    // CHECKSTYLE-OFF: JavadocMethod

    @Test
    public void testGetValidFilterKeys() {
        QueryCriteria<QueriableModelObject> criteria = new QueryCriteria<QueriableModelObject>(QueriableModelObject.class);
        List<String> filterKeys = criteria.getValidFilterKeys();

        List<String> expectedKeys = Arrays.asList(
                "name", "nameNot",
                "age", "ageLessThan", "ageGreaterThan", "ageBefore", "ageAfter",
                "createDateBefore", "createDateAfter", "createDateLessThan", "createDateGreaterThan",
                "oneGroup", "oneGroupNot"
        );
        Assert.assertTrue(filterKeys.containsAll(expectedKeys));
        Assert.assertFalse(filterKeys.contains("nameLessThan"));
        Assert.assertFalse(filterKeys.contains("nameAfter"));
        Assert.assertFalse(filterKeys.contains("ageNot"));
        Assert.assertFalse(filterKeys.contains("createDate"));
        Assert.assertFalse(filterKeys.contains("createDateNot"));
        Assert.assertFalse(filterKeys.contains("notAnnotated"));
        Assert.assertFalse(filterKeys.contains("twoGroupEqual"));
        Assert.assertFalse(filterKeys.contains("twoGroupLessThan"));
    }

    /**
     * Provides an example object for querying to test filtering and sorting functionality.
     */
    @FilterGroups({
            @FilterGroup(value = "oneGroup", fields = { "name", "age" }),
            @FilterGroup(value = "twoGroup", fields = { "createDate" }, operators = Operator.GREATER_THAN)
    })
    public class QueriableModelObject {

        @Filterable
        private String name;

        @Filterable(operators = { Operator.EQUAL, Operator.LESS_THAN, Operator.GREATER_THAN })
        private Integer age;

        @Filterable(operators = { Operator.GREATER_THAN, Operator.LESS_THAN })
        private Date createDate;

        private String notAnnotated;
    }

    // CHECKSTYLE-ON: JavadocMethod
}
