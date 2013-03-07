import java.util.List;
public class PotterDiscountCalculator {
	private static final int PRICE_FOR_SINGLE_BOOK = 8;
	private static final int NUMBER_OF_DIFFERENT_BOOKS_AVAILABLE = 5;
	double[] discountRates = { 1, 0.95, 0.90, 0.80, 0.75 };
	int[] discounts = new int[NUMBER_OF_DIFFERENT_BOOKS_AVAILABLE];
	public Double calculatePrice(List<Integer> order) {
		if (order == null || order.isEmpty()) return 0.0;
		int[] booksInOrder = calculateBooksInOrder(order);
		calculateDiscounts(booksInOrder);
		optimizeDiscounts();
		double price = 0.0;
		for (int i = 0; i < discounts.length; i++) {
			int number_of_books_in_current_discount = i + 1;
			int number_of_times_current_discounts_is_granted = discounts[i];
			double discount_rate_for_current_discount = discountRates[i];
			price += (PRICE_FOR_SINGLE_BOOK * number_of_books_in_current_discount * number_of_times_current_discounts_is_granted) * discount_rate_for_current_discount;
		}
		return price;
	}
	protected void optimizeDiscounts() {
		while (discounts[2] > 0 && discounts[4] > 0) {discounts[2]--;discounts[4]--;discounts[3] += 2;}
	}
	protected void calculateDiscounts(int[] booksInOrder) {
		if (booksInOrder == null) return;

		int differentFromZero = 0;
		for (int i = 0; i < NUMBER_OF_DIFFERENT_BOOKS_AVAILABLE; i++) {
			if (booksInOrder[i] > 0) {differentFromZero++; booksInOrder[i]--;}
		}
		if (differentFromZero > 0) {discounts[differentFromZero - 1] += 1;calculateDiscounts(booksInOrder);}
	}
	private int[] calculateBooksInOrder(List<Integer> order) {
		int[] result = new int[NUMBER_OF_DIFFERENT_BOOKS_AVAILABLE];
		for (int book : order) result[book - 1]++;
		return result;
	}
}