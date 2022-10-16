package result;

import common.FFunctor;
import common.FMapper;
import either.GEither;

public class GResult<OkType, ErrorType> implements IResult<OkType, ErrorType>
{
	private GEither<OkType, ErrorType> data;

	private GResult() {}

	public static <OkType, ErrorType> GResult<OkType, ErrorType> ok(OkType okValue)
	{
		GResult<OkType, ErrorType> result = new GResult<>();
		result.data = GEither.ofLeftType(okValue);
		return result;
	}

	public static <OkType, ErrorType> GResult<OkType, ErrorType> error(ErrorType errorValue)
	{
		GResult<OkType, ErrorType> result = new GResult<>();
		result.data = GEither.ofRightType(errorValue);
		return result;
	}

	@Override
	public final boolean wasSuccessful()
	{
		return data.getIsLeft();
	}

	@Override
	public final OkType getOkValueOr(OkType defaultValue)
	{
		return data.getLeftValueOr(defaultValue);
	}

	public final OkType getOkValueUnsafe() throws IllegalAccessError
	{
		if (!wasSuccessful())
		{
			throw new IllegalAccessError("Tried to access ok value when result was unsuccessful");
		}
		return data.getLeftValueOr(null);
	}

	public final ErrorType getErrorValueUnsafe() throws IllegalAccessError
	{
		if (wasSuccessful())
		{
			throw new IllegalAccessError("Tried to access error value when result was successful");
		}
		return data.getRightValueOr(null);
	}

	@Override
	public final ErrorType getErrorValueOr(ErrorType defaultValue)
	{
		return data.getRightValueOr(defaultValue);
	}

	@Override
	public final void matchOk(FFunctor<OkType> okFunctor)
	{
		if (wasSuccessful())
		{
			data.matchLeft(okFunctor);
		}
	}

	@Override
	public final void matchError(FFunctor<ErrorType> errorFunctor)
	{
		if (!wasSuccessful())
		{
			data.matchRight(errorFunctor);
		}
	}

	public final void match(FFunctor<OkType> okFunctor, FFunctor<ErrorType> errorFunctor)
	{
		if (wasSuccessful())
		{
			data.matchLeft(okFunctor);
		}
		else
		{
			data.matchRight(errorFunctor);
		}
	}

	public final <OutType> OutType mapExpression(FMapper<OkType, OutType> okMapper, FMapper<ErrorType, OutType> errorMapper)
	{
		return wasSuccessful() ? okMapper.map(data.getLeftValueOr(null))
						 : errorMapper.map(data.getRightValueOr(null));
	}

	public final <OutOkType, OutErrorType> GResult<OutOkType, OutErrorType> map(FMapper<OkType, OutOkType> okMapper, FMapper<ErrorType, OutErrorType> errorMapper)
	{
		return wasSuccessful() ? ok(okMapper.map(data.getLeftValueOr(null)))
				         : error(errorMapper.map(data.getRightValueOr(null)));
	}

	@Override
	public <OutErrorType> GResult<OkType, OutErrorType> mapErrorValue(FMapper<ErrorType, OutErrorType> errorMapper)
	{
		return wasSuccessful() ? ok(data.getLeftValueOr(null))
							   : error(errorMapper.map(data.getRightValueOr(null)));
	}

	public final <OutOkType> GResult<OutOkType, ErrorType> mapOkValue(FMapper<OkType, OutOkType> okMapper)
	{
		return wasSuccessful() ? ok(okMapper.map(data.getLeftValueOr(null)))
				               : error(data.getRightValueOr(null));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(data.getLeftValueOr(null), data.getRightValueOr(null), wasSuccessful());
	}
}
