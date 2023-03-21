import option.Option;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class MonadicCollections
{
    public static <ElementType> boolean areAllSet(@NotNull Collection<Option<ElementType>> collection)
    {
        return collection.stream().allMatch(Option::isSet);
    }
    private MonadicCollections() {}
}
