package option;

import common.FFunctor;
import common.FMapper;
import common.FStatefulMapper;
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

	public final <OutType> OutType mapExpression(final FStatefulMapper<OutType> someMapper, final FStatefulMapper<OutType> noneMapper)
	{
		return isSet ? someMapper.map()
					 : noneMapper.map();
	}

	public final <OutType> OutType mapExpression(final FMapper<SomeValueType, OutType> someMapper, final FStatefulMapper<OutType> noneMapper)
	{
		return isSet ? someMapper.map(value)
				: noneMapper.map();
	}

	public final void matchSome(FFunctor<SomeValueType> someFunctor)
	{
		if (isSet)
		{
			someFunctor.execute(value);
		}
	}

	public final void matchNone(FNoneFunctor noneFunctor)
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

	public final boolean isSet()
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
