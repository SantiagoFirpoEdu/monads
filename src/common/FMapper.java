package common;

public interface FMapper<InType, OutType>
{
	OutType map(InType value);
}
