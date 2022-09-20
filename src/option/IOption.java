package option;

import common.FFunctor;
import common.FMapper;
import common.FNoneFunctor;

public interface IOption<SomeValueType>
{
	<ReturnType> IOption<ReturnType> map(final FMapper<SomeValueType, ReturnType> optionMapper);
	void match(final FFunctor<SomeValueType> someFunctor);
	void match(final FNoneFunctor noneFunctor);
	void match(final FFunctor<SomeValueType> someFunctor, final FNoneFunctor noneFunctor);
	boolean getIsSet();
	SomeValueType getValueOr(final SomeValueType defaultValue);
}
