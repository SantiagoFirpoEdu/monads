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

	public Option(SomeValueType value)
	{
		this.value = value;
		isSet = value != null;
	}

	public final <ReturnType> Option<ReturnType> map(FMapper<SomeValueType, ReturnType> optionMapper)
	{
		return isSet ? new Option<>(optionMapper.map(value))
			         : new Option<>();
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
		return "option.Option{value=%s, getIsSet=%s}".formatted(value, isSet);
	}

	protected final SomeValueType getValue()
	{
		return value;
	}

	protected final void setValue(SomeValueType value)
	{
		this.value = value;
	}

	public final boolean getIsSet()
	{
		return isSet;
	}

	protected final void setIsSet(boolean set)
	{
		isSet = set;
	}

	public final SomeValueType getValueOr(SomeValueType defaultValue)
	{
		return isSet ? value : defaultValue;
	}
}
