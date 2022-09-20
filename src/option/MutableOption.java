package option;

public class MutableOption<SomeValueType> extends Option<SomeValueType>
{
	public MutableOption()
	{
		super();
	}

	public MutableOption(final SomeValueType value)
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
}
