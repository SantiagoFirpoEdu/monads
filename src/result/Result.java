package result;

import common.FMapper;
import option.FFunctor;
import option.FNoneFunctor;
import option.Option;

public class Result<OkType, ErrorType>
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

	public void match(FFunctor<OkType> okFunctor, FNoneFunctor noneFunctor)
	{
		if (isSuccess)
		{
			okValue.match(okFunctor);
		}
		else
		{
			errorValue.match(noneFunctor);
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
		return "Result{okValue=%s, errorValue=%s, isSuccess=%s}".formatted(okValue, errorValue, isSuccess);
	}
}
