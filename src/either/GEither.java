package either;

import common.FFunctor;
import common.FMapper;
import option.GOption;

public class GEither<LeftType, RightType>
{
	private GOption<LeftType> okValue;
	private GOption<RightType> errorValue;

	private boolean isLeft;

	private GEither() {}

	public static <LeftType, RightType> GEither<LeftType, RightType> ofLeftType(LeftType leftValue)
	{
		GEither<LeftType, RightType> either = new GEither<>();
		either.okValue = new GOption<>(leftValue);
		either.isLeft = true;
		return either;
	}

	public static <LeftType, RightType> GEither<LeftType, RightType> ofRightType(RightType errorValue)
	{
		GEither<LeftType, RightType> either = new GEither<>();
		either.errorValue = new GOption<>(errorValue);
		either.isLeft = false;
		return either;
	}

	public final boolean getIsLeft()
	{
		return isLeft;
	}

	public final LeftType getLeftValueOr(LeftType defaultValue)
	{
		return isLeft ? okValue.getValueOr(null) : defaultValue;
	}

	public final RightType getRightValueOr(RightType defaultValue)
	{
		return !isLeft ? errorValue.getValueOr(null) : defaultValue;
	}

	public final void matchLeft(FFunctor<LeftType> okFunctor)
	{
		if (isLeft)
		{
			okValue.matchSome(okFunctor);
		}
	}

	public final void matchRight(FFunctor<RightType> errorFunctor)
	{
		if (!isLeft)
		{
			errorValue.matchSome(errorFunctor);
		}
	}

	public final void match(FFunctor<LeftType> okFunctor, FFunctor<RightType> errorFunctor)
	{
		if (isLeft)
		{
			okValue.matchSome(okFunctor);
		}
		else
		{
			errorValue.matchSome(errorFunctor);
		}
	}

	public final <OutType> OutType mapExpression(FMapper<LeftType, OutType> okMapper, FMapper<RightType, OutType> errorMapper)
	{
		return isLeft ? okMapper.map(okValue.getValueOr(null))
		              : errorMapper.map(errorValue.getValueOr(null));
	}

	public final <OutOkType, OutErrorType> GEither<OutOkType, OutErrorType> map(FMapper<LeftType, OutOkType> okMapper, FMapper<RightType, OutErrorType> errorMapper)
	{
		return isLeft ? ofLeftType(okMapper.map(okValue.getValueOr(null)))
		              : ofRightType(errorMapper.map(errorValue.getValueOr(null)));
	}

	public final <OutOkType> GEither<OutOkType, RightType> mapLeftValue(FMapper<LeftType, OutOkType> okMapper)
	{
		return isLeft ? ofLeftType(okMapper.map(okValue.getValueOr(null)))
		              : ofRightType(errorValue.getValueOr(null));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(okValue, errorValue, isLeft);
	}
}
