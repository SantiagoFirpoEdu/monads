package result;

import common.FFunctor;
import common.FMapper;
import option.Option;

public class Result<OkType, ErrorType> implements IResult<OkType, ErrorType>
{
	private Option<OkType> okValue;
	private Option<ErrorType> errorValue;

	private boolean isSuccess;

	private Result() {}

	public static <OkType, ErrorType> Result<OkType, ErrorType> ok(OkType okValue)
	{
		Result<OkType, ErrorType> result = new Result<>();
		result.okValue = new Option<>(okValue);
		result.isSuccess = true;
		return result;
	}

	public static <OkType, ErrorType> Result<OkType, ErrorType> error(ErrorType errorValue)
	{
		Result<OkType, ErrorType> result = new Result<>();
		result.errorValue = new Option<>(errorValue);
		result.isSuccess = false;
		return result;
	}

	@Override
	public final boolean wasSuccessful()
	{
		return isSuccess;
	}

	@Override
	public final OkType getOkValueOr(OkType defaultValue)
	{
		return isSuccess ? okValue.getValueOr(null) : defaultValue;
	}

	public final OkType getOkValueUnsafe() throws IllegalAccessError
	{
		if (!isSuccess)
		{
			throw new IllegalAccessError("Tried to access ok value when result was unsuccessful");
		}
		return okValue.getValueOr(null);
	}

	public final ErrorType getErrorValueUnsafe() throws IllegalAccessError
	{
		if (!isSuccess)
		{
			throw new IllegalAccessError("Tried to access error value when result was successful");
		}
		return errorValue.getValueOr(null);
	}

	@Override
	public final ErrorType getErrorValueOr(ErrorType defaultValue)
	{
		return !isSuccess ? errorValue.getValueOr(null) : defaultValue;
	}

	@Override
	public final void matchOk(FFunctor<OkType> okFunctor)
	{
		if (isSuccess)
		{
			okValue.matchSome(okFunctor);
		}
	}

	@Override
	public final void matchError(FFunctor<ErrorType> errorFunctor)
	{
		if (!isSuccess)
		{
			errorValue.matchSome(errorFunctor);
		}
	}

	public final void match(FFunctor<OkType> okFunctor, FFunctor<ErrorType> errorFunctor)
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

	public final <OutType> OutType mapExpression(FMapper<OkType, OutType> okMapper, FMapper<ErrorType, OutType> errorMapper)
	{
		return isSuccess ? okMapper.map(okValue.getValueOr(null))
						 : errorMapper.map(errorValue.getValueOr(null));
	}

	public final <OutOkType, OutErrorType> Result<OutOkType, OutErrorType> map(FMapper<OkType, OutOkType> okMapper, FMapper<ErrorType, OutErrorType> errorMapper)
	{
		return isSuccess ? ok(okMapper.map(okValue.getValueOr(null)))
				         : error(errorMapper.map(errorValue.getValueOr(null)));
	}

	public final <OutOkType> Result<OutOkType, ErrorType> mapOkValue(FMapper<OkType, OutOkType> okMapper)
	{
		return isSuccess ? ok(okMapper.map(okValue.getValueOr(null)))
				: error(errorValue.getValueOr(null));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(okValue, errorValue, isSuccess);
	}
}
