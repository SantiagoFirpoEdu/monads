package either;

import common.FFunctor;
import common.FMapper;
import option.GOption;

public class GEither<LeftType, RightType>
{
	public static <LeftType, RightType> GEither<LeftType, RightType> ofLeftType(LeftType leftValue)
	{
		GEither<LeftType, RightType> either = new GEither<>();
		either.leftValue = new GOption<>(leftValue);
		either.isLeft = true;
		return either;
	}

	public static <LeftType, RightType> GEither<LeftType, RightType> ofRightType(RightType errorValue)
	{
		GEither<LeftType, RightType> either = new GEither<>();
		either.rightValue = new GOption<>(errorValue);
		either.isLeft = false;
		return either;
	}

	public final boolean getIsLeft()
	{
		return isLeft;
	}

	public final LeftType getLeftValueOr(LeftType defaultValue)
	{
		return isLeft ? leftValue.getValueOr(null) : defaultValue;
	}

	public final RightType getRightValueOr(RightType defaultValue)
	{
		return !isLeft ? rightValue.getValueOr(null) : defaultValue;
	}

	public final void matchLeft(FFunctor<LeftType> okFunctor)
	{
		if (isLeft)
		{
			leftValue.matchSome(okFunctor);
		}
	}

	public final void matchRight(FFunctor<RightType> errorFunctor)
	{
		if (!isLeft)
		{
			rightValue.matchSome(errorFunctor);
		}
	}

	public final void match(FFunctor<LeftType> okFunctor, FFunctor<RightType> errorFunctor)
	{
		if (isLeft)
		{
			leftValue.matchSome(okFunctor);
		}
		else
		{
			rightValue.matchSome(errorFunctor);
		}
	}

	public final <OutType> OutType mapExpression(FMapper<LeftType, OutType> okMapper, FMapper<RightType, OutType> errorMapper)
	{
		return isLeft ? okMapper.map(leftValue.getValueOr(null))
		              : errorMapper.map(rightValue.getValueOr(null));
	}

	public final <OutOkType, OutErrorType> GEither<OutOkType, OutErrorType> map(FMapper<LeftType, OutOkType> okMapper, FMapper<RightType, OutErrorType> errorMapper)
	{
		return isLeft ? ofLeftType(okMapper.map(leftValue.getValueOr(null)))
		              : ofRightType(errorMapper.map(rightValue.getValueOr(null)));
	}

	public final <OutOkType> GEither<OutOkType, RightType> mapLeftValue(FMapper<LeftType, OutOkType> okMapper)
	{
		return isLeft ? ofLeftType(okMapper.map(leftValue.getValueOr(null)))
		              : ofRightType(rightValue.getValueOr(null));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(leftValue, rightValue, isLeft);
	}

	private GOption<LeftType> leftValue;
	private GOption<RightType> rightValue;
	private boolean isLeft;

	private GEither() {}
}
