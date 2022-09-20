package option;

import common.FFunctor;
import common.FMapper;
import common.FNoneFunctor;

public interface IOption<SomeValueType>
{
	<ReturnType> IOption<ReturnType> map(FMapper<SomeValueType, ReturnType> optionMapper);
	void match(FFunctor<SomeValueType> someFunctor);
	void match(FNoneFunctor noneFunctor);
	void match(FFunctor<SomeValueType> someFunctor, FNoneFunctor noneFunctor);
	boolean getIsSet();
	SomeValueType getValueOr(SomeValueType defaultValue);
}
