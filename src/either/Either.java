package either;

import common.FFunctor;
import common.FMapper;
import option.Option;

public class Either<LeftType, RightType>
{
	private Option<LeftType> okValue;
	private Option<RightType> errorValue;

	private boolean isSuccess;

	private Either() {}

	public static <LeftType, RightType> Either<LeftType, RightType> ofLeftType(LeftType leftValue)
	{
		Either<LeftType, RightType> either = new Either<>();
		either.okValue = new Option<>(leftValue);
		either.isSuccess = true;
		return either;
	}

	public static <LeftType, RightType> Either<LeftType, RightType> ofRightType(RightType errorValue)
	{
		Either<LeftType, RightType> either = new Either<>();
		either.errorValue = new Option<>(errorValue);
		either.isSuccess = false;
		return either;
	}

	public final boolean getIsLeft()
	{
		return isSuccess;
	}

	public final LeftType getLeftValueOr(LeftType defaultValue)
	{
		return isSuccess ? okValue.getValueOr(null) : defaultValue;
	}

	public final RightType getRightValueOr(RightType defaultValue)
	{
		return !isSuccess ? errorValue.getValueOr(null) : defaultValue;
	}

	public final void matchLeft(FFunctor<LeftType> okFunctor)
	{
		if (isSuccess)
		{
			okValue.matchSome(okFunctor);
		}
	}

	public final void matchRight(FFunctor<RightType> errorFunctor)
	{
		if (!isSuccess)
		{
			errorValue.matchSome(errorFunctor);
		}
	}

	public final void match(FFunctor<LeftType> okFunctor, FFunctor<RightType> errorFunctor)
	{
		if (isSuccess)
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
		return isSuccess ? okMapper.map(okValue.getValueOr(null))
						 : errorMapper.map(errorValue.getValueOr(null));
	}

	public final <OutOkType, OutErrorType> Either<OutOkType, OutErrorType> map(FMapper<LeftType, OutOkType> okMapper, FMapper<RightType, OutErrorType> errorMapper)
	{
		return isSuccess ? ofLeftType(okMapper.map(okValue.getValueOr(null)))
				         : ofRightType(errorMapper.map(errorValue.getValueOr(null)));
	}

	public final <OutOkType> Either<OutOkType, RightType> mapLeftValue(FMapper<LeftType, OutOkType> okMapper)
	{
		return isSuccess ? ofLeftType(okMapper.map(okValue.getValueOr(null)))
				: ofRightType(errorValue.getValueOr(null));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(okValue, errorValue, isSuccess);
	}
}
