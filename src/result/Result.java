package result;

import common.FMapper;
import common.FFunctor;
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
	public final boolean getIsSuccess()
	{
		return isSuccess;
	}

	@Override
	public final OkType getOkValueOr(OkType defaultValue)
	{
		return isSuccess ? okValue.getValueOr(null) : defaultValue;
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
			okValue.match(okFunctor);
		}
	}

	@Override
	public final void matchError(FFunctor<ErrorType> errorFunctor)
	{
		if (!isSuccess)
		{
			errorValue.match(errorFunctor);
		}
	}

	public final void match(FFunctor<OkType> okFunctor, FFunctor<ErrorType> errorFunctor)
	{
		if (isSuccess)
		{
			okValue.match(okFunctor);
		}
		else
		{
			errorValue.match(errorFunctor);
		}
	}

	public final <OutOkType, OutErrorType> Result<OutOkType, OutErrorType> map(FMapper<OkType, OutOkType> okMapper, FMapper<ErrorType, OutErrorType> errorMapper)
	{
		return isSuccess ? ok(okMapper.map(okValue.getValueOr(null)))
				         : error(errorMapper.map(errorValue.getValueOr(null)));
	}

	@Override
	public final String toString()
	{
		return "Result{okValue=%s, errorValue=%s, getIsSuccess=%s}".formatted(okValue, errorValue, isSuccess);
	}
}
