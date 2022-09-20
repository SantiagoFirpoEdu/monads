package option;

import common.FMapper;

public class Option<SomeValueType>
{
	private SomeValueType value;

	private boolean isSet;

	public Option()
	{

	}

	public Option(SomeValueType value)
	{
		this.value = value;
		isSet = value != null;
	}

	public final <ReturnType> Option<ReturnType> map(FMapper<SomeValueType, ReturnType> optionMapper)
	{
		if (isSet)
		{
			return new Option<>(optionMapper.map(value));
		}
		else
		{
			return new Option<>();
		}
	}

	public final void match(FFunctor<SomeValueType> someFunctor)
	{
		if (isSet)
		{
			someFunctor.execute(value);
		}
	}

	public final void match(FNoneFunctor noneFunctor)
	{
		if (!isSet)
		{
			noneFunctor.execute();
		}
	}

	public final void match(FFunctor<SomeValueType> someFunctor, FNoneFunctor noneFunctor)
	{
		if (isSet)
		{
			someFunctor.execute(value);
		}
		else
		{
			noneFunctor.execute();
		}
	}

	@Override
	public final String toString()
	{
		return "option.Option{value=%s, isSet=%s}".formatted(value, isSet);
	}

	protected final SomeValueType getValue()
	{
		return value;
	}

	protected final void setValue(SomeValueType value)
	{
		this.value = value;
	}

	public boolean isSet()
	{
		return isSet;
	}

	protected void setSet(boolean set)
	{
		isSet = set;
	}

	public SomeValueType getValueOr(SomeValueType defaultValue)
	{
		return isSet ? value : defaultValue;
	}
}
