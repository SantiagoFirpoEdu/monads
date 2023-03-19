package either;

import common.FFunctor;
import common.FMapper;

public interface IEither<LeftType, RightType>
{
    boolean getIsLeft();

    LeftType getLeftValueOr(LeftType defaultValue);

    RightType getRightValueOr(RightType defaultValue);

    void matchLeft(FFunctor<LeftType> okFunctor);

    void matchRight(FFunctor<RightType> errorFunctor);

    void match(FFunctor<LeftType> okFunctor, FFunctor<RightType> errorFunctor);

    <OutType> OutType mapExpression(FMapper<LeftType, OutType> okMapper, FMapper<RightType, OutType> errorMapper);

    <OutOkType, OutErrorType> IEither<OutOkType, OutErrorType> map(FMapper<LeftType, OutOkType> okMapper, FMapper<RightType, OutErrorType> errorMapper);

    <OutOkType> IEither<OutOkType, RightType> mapLeftValue(FMapper<LeftType, OutOkType> okMapper);

	Option<LeftType> getLeft();

    Option<RightType> getRight();
}
