import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ruben.arana on 21/4/16.
 */
public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void initialize() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void shouldReturn0_whenEmptyInput() {
        assertThat(stringCalculator.add(""), is(0));
    }

    @Test
    public void shouldReturnSameNumber_whenInputIsOneNumber() {
        assertThat(stringCalculator.add("5"), is(5));
    }

    @Test
    public void shouldReturnSum_when2SeparatedByCommaNumbers() {
        assertThat(stringCalculator.add("1,2"), is(3));
    }

    @Test
    public void shouldReturnSum_when2SeparatedByNewLineNumbers() {
        assertThat(stringCalculator.add("1\n2"), is(3));
    }

    @Test
    public void shouldReturnSum_whenNNumbers() {
        assertThat(stringCalculator.add("1\n2,3"), is(6));
    }

    @Test
    public void shouldReturnSum_whenCustomDelimiter() {
        assertThat(stringCalculator.add("//;\n2;3"), is(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnException_whenNegativeNumbers() {
        stringCalculator.add("-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnExceptionWithNumbers_whenNegativeNumbers() {
        stringCalculator.add("//;\n-3;2;3;-5;6");
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Negatives not allowed: -3,-5");
    }

    @Test
    public void shouldAvoidBiggerThan1000Numbers() {
        assertThat(stringCalculator.add("//;\n2;1007"), is(2));
    }

    @Test
    public void shouldSumForAnyDelimitorLength() {
        assertThat(stringCalculator.add("//[;;;]\n2;;;1007"), is(2));
    }

    @Test
    public void shouldSumForSeveralDelimitors() {
        assertThat(stringCalculator.add("//[;][%]\n2;3%4"), is(9));
    }

    @Test
    public void shouldSumForSeveralDelimitorsForAnyDelimitorLength() {
        assertThat(stringCalculator.add("//[;;][%%%]\n2;;3%%%4"), is(9));
    }

    @Test
    public void shouldSum_whenDelimitorIsProhibitedCharacter() {
        assertThat(stringCalculator.add("//[.]\n2.3"), is(5));
    }

    @Test
    public void shouldSumForSeveralDelimitorsForAnyDelimitorLengthAndProhibitedCharacters() {
        assertThat(stringCalculator.add("//[.][++]\n2.3++4"), is(9));
    }
}