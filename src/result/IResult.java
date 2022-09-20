package result;

import common.FMapper;
import common.FFunctor;

public interface IResult<OkType, ErrorType>
{
	static <OkType, ErrorType> IResult<OkType, ErrorType> ok(OkType okValue)
	{
		return null;
	}

	static <OkType, ErrorType> IResult<OkType, ErrorType> error(ErrorType errorValue)
	{
		return null;
	}

	boolean getIsSuccess();

	OkType getOkValueOr(OkType defaultValue);

	ErrorType getErrorValueOr(ErrorType defaultValue);

	void matchOk(FFunctor<OkType> okFunctor);
	void matchError(FFunctor<ErrorType> errorFunctor);
	void match(FFunctor<OkType> okFunctor, FFunctor<ErrorType> errorFunctor);
	<OutOkType, OutErrorType> IResult<OutOkType, OutErrorType> map(FMapper<OkType, OutOkType> okMapper, FMapper<ErrorType, OutErrorType> errorMapper);
}