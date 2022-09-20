package option;

public class MutableOption<SomeValueType> extends Option<SomeValueType>
{
	public MutableOption()
	{
		super();
	}

	public MutableOption(SomeValueType value)
	{
		super(value);
	}

	public final void reset()
	{
		setValue(null);
		setSet(false);
	}

	public final void set(SomeValueType value)
	{
		setValue(value);
		setSet(value != null);
	}
}
