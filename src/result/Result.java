package result;

import common.FFunctor;
import common.FMapper;
import either.Either;
import either.IEither;
import option.Option;

public final class Result<OkType, ErrorType>
{
	public static <OkType, ErrorType> Result<OkType, ErrorType> ok(OkType okValue)
	{
		Result<OkType, ErrorType> result = new Result<>();
		result.data = Either.ofLeftType(okValue);
		return result;
	}

	public static <OkType, ErrorType> Result<OkType, ErrorType> error(ErrorType errorValue)
	{
		Result<OkType, ErrorType> result = new Result<>();
		result.data = Either.ofRightType(errorValue);
		return result;
	}

	public final boolean wasSuccessful()
	{
		return data.getIsLeft();
	}

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

	public final ErrorType getErrorValueOr(ErrorType defaultValue)
	{
		return data.getRightValueOr(defaultValue);
	}

	public final void matchOk(FFunctor<OkType> okFunctor)
	{
		if (wasSuccessful())
		{
			data.matchLeft(okFunctor);
		}
	}

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

	public final <OutOkType, OutErrorType> Result<OutOkType, OutErrorType> map(FMapper<OkType, OutOkType> okMapper, FMapper<ErrorType, OutErrorType> errorMapper)
	{
		return wasSuccessful() ? ok(okMapper.map(data.getLeftValueOr(null)))
				         : error(errorMapper.map(data.getRightValueOr(null)));
	}

	public <OutErrorType> Result<OkType, OutErrorType> mapErrorValue(FMapper<ErrorType, OutErrorType> errorMapper)
	{
		return wasSuccessful() ? ok(data.getLeftValueOr(null))
							   : error(errorMapper.map(data.getRightValueOr(null)));
	}

	public final <OutOkType> Result<OutOkType, ErrorType> mapOkValue(FMapper<OkType, OutOkType> okMapper)
	{
		return wasSuccessful() ? ok(okMapper.map(data.getLeftValueOr(null)))
				               : error(data.getRightValueOr(null));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(data.getLeftValueOr(null), data.getRightValueOr(null), wasSuccessful());
	}

	public static <OkType, ErrorType> Result<OkType, ErrorType> flatMap(Result<Result<OkType, ErrorType>, ErrorType> result)
	{
		if (result.wasSuccessful())
		{
			Result<OkType, ErrorType> innerResult = result.getOkValueUnsafe();
			if (innerResult.wasSuccessful())
			{
				return Result.ok(innerResult.getOkValueUnsafe());
			}
			else
			{
				return Result.error(innerResult.getErrorValueUnsafe());
			}
		}
		else
		{
			return Result.error(result.getErrorValueUnsafe());
		}
	}

	public Option<OkType> getOk()
	{
		return data.getLeft();
	}

	public Option<ErrorType> getError()
	{
		return data.getRight();
	}

	private Result() {}

	private IEither<OkType, ErrorType> data;
}
