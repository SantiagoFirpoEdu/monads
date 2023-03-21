import option.Option;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class SafeScanner
{
	public SafeScanner(@NotNull Readable source)
	{
		scanner = new Scanner(source);
	}

	public SafeScanner(@NotNull String source)
	{
		scanner = new Scanner(source);
	}

	public @NotNull Option<String> next()
	{
		try
		{
			return Option.some(scanner.next());
		}
		catch (Exception exception)
		{
			return Option.none();
		}
	}

	public @NotNull Option<String[]> nextTokens(final int amount)
	{
		try
		{
			String[] tokens = new String[amount];
			for (int i = 0; i < amount; i++)
			{
				tokens[i] = scanner.next();
			}
			return Option.some(tokens);
		}
		catch (Exception exception)
		{
			return Option.none();
		}
	}

	private final Scanner scanner;
}
