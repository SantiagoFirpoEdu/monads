package tests;

import option.MutableOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MutableOptionTest
{

	MutableOptionTest() {}

	@Test
	public final void testResetSome()
	{
		MutableOption<Integer> option = new MutableOption<>(15);
		option.reset();
		Assertions.assertFalse(option.getIsSet());
		Assertions.assertEquals(4, option.getValueOr(4));
	}

	@Test
	public final void testResetNone()
	{
		MutableOption<Integer> option = new MutableOption<>();
		option.reset();
		Assertions.assertFalse(option.getIsSet());
		Assertions.assertEquals(5, option.getValueOr(5));
	}

	@Test
	public final void testSetNone()
	{
		MutableOption<Integer> option = new MutableOption<>();
		option.set(12);
		Assertions.assertTrue(option.getIsSet());
		Assertions.assertEquals(12, option.getValueOr(4));
	}

	@Test
	public final void testSetSome()
	{
		MutableOption<Integer> option = new MutableOption<>(23);
		option.set(14);
		Assertions.assertTrue(option.getIsSet());
		Assertions.assertEquals(14, option.getValueOr(5));
	}

	@Test
	public final void testSetNoneWithNull()
	{
		MutableOption<Integer> option = new MutableOption<>();
		option.set(null);
		Assertions.assertFalse(option.getIsSet());
		Assertions.assertEquals(4, option.getValueOr(4));
	}

	@Test
	public final void testSetSomeWithNull()
	{
		MutableOption<Integer> option = new MutableOption<>(23);
		option.set(null);
		Assertions.assertFalse(option.getIsSet());
		Assertions.assertEquals(5, option.getValueOr(5));
	}
}