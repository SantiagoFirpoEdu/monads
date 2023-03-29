import option.Option;

import java.nio.file.Path;

public class SafePath
{
    public static Option<Path> of(String path)
    {
        try
        {
            return Option.some(Path.of(path));
        }
        catch (Exception exception)
        {
            return Option.none();
        }
    }
    private SafePath() {}
}
