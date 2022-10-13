package option;

import result.GResult;

public class GMutableOption<SomeValueType> extends GOption<SomeValueType>
{
	public GMutableOption()
	{
		super();
	}

	public GMutableOption(final SomeValueType value)
	{
		super(value);
	}

	public final void reset()
	{
		setValue(null);
		setIsSet(false);
	}

	public final void set(final SomeValueType value)
	{
		setValue(value);
		setIsSet(value != null);
	}

	public <ErrorType> void  setIfSuccessful(GResult<SomeValueType, ErrorType> nextInteger)
	{
		if (nextInteger.wasSuccessful())
		{
			set(nextInteger.getOkValueUnsafe());
		}
	}
}
