import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {
    private StringCalculator sc;

    @BeforeEach
    public void setup() {
        sc = new StringCalculator();
    }
    @Test
    void addShouldBeOne() {
        assertEquals(1, sc.add("1"));
    }

    @Test
    void shouldBe3() {
        assertEquals(3, sc.add("1,2"));
    }

    @Test
    void shouldBeNone() {
        assertEquals(0, sc.add(""));
    }

    @Test
    void shouldBe24() {
        assertEquals(24, sc.add("1,13,10"));
    }

    @Test
    void shouldBe10() {
        assertEquals(10, sc.add("5\n5"));
    }

    @Test
    void shouldBe11() {assertEquals(11, sc.add("5\n5,1"));}

    @Test
    void shouldBe1999() {
        assertEquals(1999, sc.add("1000,999,1001"));
    }

    @Test
    void shouldBe3WithDelims() {assertEquals(3, sc.add("//[;]\n1;2"));}

    @Test
    void shouldBeExCauseNegNums() {assertThrows(RuntimeException.class, () -> sc.add("//[;]\n-1;-2"));}

    @Test
    void shouldBeExCauseIncorrectInput() {assertThrows(RuntimeException.class, () -> sc.add("1,\n2"));}

    @Test
    void shouldBe6WithRepeatedDelims() {assertEquals(6, sc.add("//[;]\n1;2;;3"));}

    @Test
    void shouldBe3WithMultipleDelims() {assertEquals(3, sc.add("//[;][.]\n1;1.1"));}

    @Test
    void shouldBe6WithMultipleRepeatedDelims() {assertEquals(6, sc.add("//[;][*]\n1;2**3"));}

    @Test
    void shouldBe6With3Delims() {assertEquals(6, sc.add("//[;][*][!]\n1;1*3!1"));}

    @Test
    void shouldBe9With3RepeatedDelims() {assertEquals(9, sc.add("//[;][*][!]\n3;3**2!!1"));}
}