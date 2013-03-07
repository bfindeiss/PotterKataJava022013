import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PotterDiscountCalculatorTest {

	@SuppressWarnings("serial")
	@DataPoint
	public List<Integer> getOrder() {
		return new ArrayList<Integer>() {
			{
				add(5);
			}
		};
	}

	@Test
	public void testNullBasket() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		Double price = calc.calculatePrice(null);

		assertThat(price, is(0.0));
	}

	@Test
	public void testEmptyBasket() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>();

		Double price = calc.calculatePrice(order);

		assertThat(price, is(0.0));
	}

	@SuppressWarnings("serial")
	@Test
	public void testSingleBook() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>() {
			{
				add(5);
			}
		};

		Double price = calc.calculatePrice(order);

		assertThat(price, is(8.0));
	}

	@Theory
	public void purchaseSingleBook(List<Integer> order) {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		Double price = calc.calculatePrice(order);
		assertThat(price, is(8.0));
	}

	@SuppressWarnings("serial")
	@Theory
	public void purchaseTwoSameBooks() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>() {
			{
				add(5);
				add(5);
			}
		};
		Double price = calc.calculatePrice(order);
		assertThat(price, is(16.0));
	}

	@Test
	public void purchaseTwoDifferentBooks() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>() {
			{
				add(4);
				add(5);
			}
		};
		Double price = calc.calculatePrice(order);
		assertThat(price, is(16.0 * 0.95));
	}

	@Test
	public void purchaseThreeBookWithTwoDifferentBooks() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>() {
			{
				add(4);
				add(4);
				add(5);
			}
		};
		Double price = calc.calculatePrice(order);
		assertThat(price, is(8 + 16 * 0.95));
	}

	@Test
	public void severalDiscounts() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>() {
			{
				add(1);
				add(1);
				add(2);
				add(2);
			}
		};
		Double price = calc.calculatePrice(order);
		assertThat(price, is(2 * (8 * 2 * 0.95)));
	}

	@Test
	public void severalDiscounts2() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = new ArrayList<Integer>() {
			{
				add(1);
				add(1);
				add(2);
				add(3);
				add(3);
				add(4);
			}
		};
		Double price = calc.calculatePrice(order);
		assertThat(price, is((8 * 4 * 0.8) + (8 * 2 * 0.95)));
	}

	@Test
	public void edgeCases() {
		PotterDiscountCalculator calc = new PotterDiscountCalculator();
		List<Integer> order = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 5);

		Double price = calc.calculatePrice(order);
		assertThat(price, is(2 * (8 * 4 * 0.8)));
	}
}
