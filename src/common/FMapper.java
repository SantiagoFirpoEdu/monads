package common;

public interface FMapper<InType, OutType>
{
    OutType map(final InType value);
}
