import option.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

class OptionTest
{
	OptionTest() {}

	@Test
	final void testMapNone()
	{
		Option<Integer> option = new Option<>(42);
		Option<Integer> newOption = option.map((Integer value) -> 2 * value);
		Assertions.assertTrue(newOption.getIsSet());
		Assertions.assertEquals(84, newOption.getValueOr(0));
	}

	@Test
	final void testMapSome()
	{
		Option<Integer> option = new Option<>();
		Option<Integer> newOption = option.map((Integer value) -> 5 * value);
		Assertions.assertFalse(newOption.getIsSet());
		Assertions.assertEquals(128, newOption.getValueOr(128));
	}


	@Test
	public final void testMatchNone()
	{
		Option<Integer> option = new Option<>();
		AtomicReference<Integer> someValue = new AtomicReference<>(134);
		option.match(someValue::set);
		Assertions.assertEquals(134, someValue.get());
	}

	@Test
	public final void testMatchSome()
	{
		Option<Integer> option = new Option<>(85);
		AtomicReference<Integer> someValue = new AtomicReference<>(0);
		option.match(someValue::set);
		Assertions.assertEquals(85, someValue.get());
	}

	@Test
	public final void testMatchBothNone()
	{
		Option<Integer> option = new Option<>();
		AtomicReference<Integer> someValue = new AtomicReference<>(999);
		option.match(someValue::set, () -> someValue.set(1234) );
		Assertions.assertEquals(1234, someValue.get());
	}

	@Test
	public final void testMatchBothSome()
	{
		Option<Integer> option = new Option<>(42);
		AtomicReference<Integer> someValue = new AtomicReference<>(999);
		option.match(someValue::set, () -> someValue.set(1234) );
		Assertions.assertEquals(42, someValue.get());
	}

	@Test
	public final void testGetValueSome()
	{
		Option<Integer> option = new Option<>(42);
		Assertions.assertEquals(42, option.getValueOr(12));
	}

	@Test
	public final void testGetValueNone()
	{
		Option<Integer> option = new Option<>();
		Assertions.assertEquals(12, option.getValueOr(12));
	}

	@Test
	public final void testIsSetSome()
	{
		Option<Integer> option = new Option<>(99);
		Assertions.assertTrue(option.getIsSet());
	}

	@Test
	public final void testIsSetNone()
	{
		Option<Integer> option = new Option<>();
		Assertions.assertFalse(option.getIsSet());
	}
}