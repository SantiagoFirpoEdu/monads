import option.Option;

import java.util.Scanner;

public class SafeScanner
{
	public SafeScanner(Readable source)
	{
		scanner = new Scanner(source);
	}

	public SafeScanner(String source)
	{
		scanner = new Scanner(source);
	}

	public Option<String> next()
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

	public Option<String[]> nextTokens(final int amount)
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
