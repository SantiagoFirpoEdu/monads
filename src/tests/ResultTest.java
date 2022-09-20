import option.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import result.Result;

import java.util.concurrent.atomic.AtomicReference;

class ResultTest
{
	ResultTest() {}

	@Test
	public final void testMapOk()
	{
		Result<Boolean, Character> result = Result.ok(true);
		Result<Integer, String> newResult = result.map((Boolean value) -> Boolean.TRUE.equals(value) ? 86 : -1, Object::toString);
		Assertions.assertTrue(newResult.getIsSuccess());
		Assertions.assertEquals(86, newResult.getOkValueOr(0));
	}

	@Test
	public final void testMapError()
	{
		Result<Boolean, Character> result = Result.error('a');
		Result<Integer, String> newResult = result.map((Boolean value) -> Boolean.TRUE.equals(value) ? 32 : -1, (Character character) -> character.toString());
		Assertions.assertFalse(newResult.getIsSuccess());
		Assertions.assertEquals("a", newResult.getErrorValueOr("hg"));
	}


	@Test
	public final void testMatchOk()
	{
		Result<Integer, String> result = Result.ok(123);
		AtomicReference<Integer> someValue = new AtomicReference<>(134);
		result.matchOk(someValue::set);
		Assertions.assertEquals(123, someValue.get());
	}

	@Test
	public final void testMatchError()
	{
		Result<String, Boolean> result = Result.error(true);
		AtomicReference<Boolean> someValue = new AtomicReference<>(false);
		result.matchError(someValue::set);
		Assertions.assertEquals(true, someValue.get());
	}

	@Test
	public final void testMatchBothOk()
	{
		Result<Integer, String> result = Result.ok(12);
		AtomicReference<Integer> someValue = new AtomicReference<>(999);
		result.match(someValue::set, (String error) -> someValue.set(1234) );
		Assertions.assertEquals(12, someValue.get());
	}

	@Test
	public final void testMatchBothError()
	{
		Result<Character, Boolean> result = Result.error(true);
		AtomicReference<Integer> someValue = new AtomicReference<>(999);
		result.match((Character okValue) -> someValue.set(87), (Boolean errorValue) -> someValue.set(errorValue ? 42 : 90));
		Assertions.assertEquals(42, someValue.get());
	}

	@Test
	public final void testGetOkValueOr()
	{
		Result<Integer, Character> result = Result.ok(42);
		Assertions.assertEquals(42, result.getOkValueOr(12));
	}

	@Test
	public final void testGetOkValueOrWithError()
	{
		Result<Integer, String> result = Result.error("AA");
		Assertions.assertEquals(12, result.getOkValueOr(12));
	}

	@Test
	public final void testIsSuccessOk()
	{
		Result<Integer, Boolean> result = Result.ok(1);
		Assertions.assertTrue(result.getIsSuccess());
	}

	@Test
	public final void testIsSuccessError()
	{
		Result<Integer, Character> result = Result.error('k');
		Assertions.assertFalse(result.getIsSuccess());
	}
}