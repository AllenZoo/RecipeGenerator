package model;

import exceptions.InvalidCookingUtensilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CookingUtensilTest {
    private CookingUtensil cookingUtensil;
    private CookingUtensil cookingUtensil2;
    private CookingUtensil cookingUtensil3;

    @BeforeEach
    public void runBefore() {
        cookingUtensil = new CookingUtensil("cutting board");
        cookingUtensil2 = new CookingUtensil("pan", true);
        cookingUtensil3 = new CookingUtensil("bacon");
    }

    @Test
    public void testConstructor() {
        assertEquals("cutting board", cookingUtensil.getName());
        assertFalse(cookingUtensil.getIsDamaged());
    }

    @Test
    public void testConstructorTwoParam() {
        assertEquals("pan", cookingUtensil2.getName());
        assertTrue(cookingUtensil2.getIsDamaged());
    }

    @Test
    public void testValidCookingUtensil() {
        try {
            cookingUtensil2.isValid();
        } catch (InvalidCookingUtensilException e) {
            fail("exception not expected");
        }
    }

    @Test
    public void testInvalidCookingUtensil() {
        try {
            cookingUtensil3.isValid();
            fail("exception expected");
        } catch (InvalidCookingUtensilException e) {
            // expected
        }
    }

}
