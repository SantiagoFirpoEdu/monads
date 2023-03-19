package either;

import common.FFunctor;
import common.FMapper;
import option.Option;

public class Either<LeftType, RightType> implements IEither<LeftType, RightType>
{
    public static <LeftType, RightType> Either<LeftType, RightType> ofRightType(RightType errorValue)
    {
        Either<LeftType, RightType> either = new Either<>();
        either.rightValue = Option.some(errorValue);
        either.isLeft = false;
        return either;
    }

	public static <LeftType, RightType> Either<LeftType, RightType> ofLeftType(LeftType leftValue)
	{
		Either<LeftType, RightType> either = new Either<>();
		either.leftValue = Option.some(leftValue);
		either.isLeft = true;
		return either;
	}

	@Override
    public final boolean getIsLeft()
    {
        return isLeft;
    }

    @Override
    public final LeftType getLeftValueOr(LeftType defaultValue)
    {
        return isLeft ? leftValue.getValueOr(null) : defaultValue;
    }

    @Override
    public final RightType getRightValueOr(RightType defaultValue)
    {
        return !isLeft ? rightValue.getValueOr(null) : defaultValue;
    }

    @Override
    public final void matchLeft(FFunctor<LeftType> okFunctor)
    {
        if (isLeft)
        {
            leftValue.matchSome(okFunctor);
        }
    }

    @Override
    public final void matchRight(FFunctor<RightType> errorFunctor)
    {
        if (!isLeft)
        {
            rightValue.matchSome(errorFunctor);
        }
    }

    @Override
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

    @Override
    public final <OutType> OutType mapExpression(FMapper<LeftType, OutType> okMapper, FMapper<RightType, OutType> errorMapper)
    {
        return isLeft ? okMapper.map(leftValue.getValueOr(null))
                : errorMapper.map(rightValue.getValueOr(null));
    }

    @Override
    public final <OutOkType, OutErrorType> Either<OutOkType, OutErrorType> map(FMapper<LeftType, OutOkType> okMapper, FMapper<RightType, OutErrorType> errorMapper)
    {
        return isLeft ? Either.ofLeftType(okMapper.map(leftValue.getValueOr(null)))
                : Either.ofRightType(errorMapper.map(rightValue.getValueOr(null)));
    }

    @Override
    public final <OutOkType> Either<OutOkType, RightType> mapLeftValue(FMapper<LeftType, OutOkType> okMapper)
    {
        return isLeft ? Either.ofLeftType(okMapper.map(leftValue.getValueOr(null)))
                : Either.ofRightType(rightValue.getValueOr(null));
    }

    @Override
    public Option<LeftType> getLeft()
    {
        return leftValue;
    }

    @Override
    public Option<RightType> getRight()
    {
        return rightValue;
    }

    @Override
    public final String toString()
    {
        return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(leftValue, rightValue, isLeft);
    }

    private Either()
    {
    }

    private Option<LeftType> leftValue;
    private Option<RightType> rightValue;
    private boolean isLeft;
}
