package option;

import common.FFunctor;
import common.FMapper;
import common.FNoneFunctor;

public class Option<SomeValueType> implements IOption<SomeValueType>
{
	private SomeValueType value;

	private boolean isSet;

	public Option()
	{
		value = null;
		isSet = false;
	}

	public Option(final SomeValueType value)
	{
		this.value = value;
		isSet = value != null;
	}

	public final <ReturnType> Option<ReturnType> map(final FMapper<SomeValueType, ReturnType> optionMapper)
	{
		return isSet ? new Option<>(optionMapper.map(value))
			         : new Option<>();
	}

	public final void match(final FFunctor<SomeValueType> someFunctor)
	{
		if (isSet)
		{
			someFunctor.execute(value);
		}
	}

	public final void match(final FNoneFunctor noneFunctor)
	{
		if (!isSet)
		{
			noneFunctor.execute();
		}
	}

	public final void match(final FFunctor<SomeValueType> someFunctor, final FNoneFunctor noneFunctor)
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
		return "option.Option{value=%s, getIsSet=%s}".formatted(value, isSet);
	}

	protected final SomeValueType getValue()
	{
		return value;
	}

	protected final void setValue(final SomeValueType value)
	{
		this.value = value;
	}

	public final boolean getIsSet()
	{
		return isSet;
	}

	protected final void setIsSet(final boolean set)
	{
		isSet = set;
	}

	public final SomeValueType getValueOr(final SomeValueType defaultValue)
	{
		return isSet ? value : defaultValue;
	}
}
