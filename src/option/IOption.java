package option;

import common.FFunctor;
import common.FMapper;
import common.FNoneFunctor;

public interface IOption<SomeValueType>
{
	<ReturnType> IOption<ReturnType> map(FMapper<SomeValueType, ReturnType> optionMapper);
	void matchSome(FFunctor<SomeValueType> someFunctor);
	void matchNone(FNoneFunctor noneFunctor);
	void match(FFunctor<SomeValueType> someFunctor, FNoneFunctor noneFunctor);
	boolean isSet();
	SomeValueType getValueOr(final SomeValueType defaultValue);
}
