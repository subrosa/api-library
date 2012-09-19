package com.subrosa.api.notification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing general notification codes.
 */
public class GeneralCodeTest {

    /**
     * Test to determine if all notification codes are unique.
     */
    @Test
    public void testUniqueCodes() {
        List<GeneralCode> codes = Arrays.asList(GeneralCode.values());
        Set<Integer> integerCodes = new HashSet<Integer>();
        for (GeneralCode code : codes) {
            int integerCode = code.getCode();
            assertFalse("Notification code " + integerCode + " is duplicated", integerCodes.contains(integerCode));
            integerCodes.add(integerCode);
        }

    }
    
    /**
     * Test to determine if all general notification codes have the proper numeric category.
     */
    @Test
    public void testCodeCategories() {
        List<GeneralCode> codes = Arrays.asList(GeneralCode.values());
        for (GeneralCode code : codes) {
            int integerCode = code.getCode();
            assertTrue("Notification code " + integerCode + " is not in general code range 10XXXXXXXX", integerCode >= 1000000000 && integerCode <= 1099999999);
        }
    }
}
