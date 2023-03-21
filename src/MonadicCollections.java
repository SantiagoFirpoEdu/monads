import option.Option;

import java.util.Collection;
import java.util.stream.Stream;

public class MonadicCollections
{
    public static <ElementType> boolean areAllSet(Collection<Option<ElementType>> collection)
    {
        return collection.stream().allMatch(Option::isSet);
    }
    private MonadicCollections() {}
}
