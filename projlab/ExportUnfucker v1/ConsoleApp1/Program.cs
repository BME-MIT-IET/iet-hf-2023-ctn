// See https://aka.ms/new-console-template for more information

//This single line of code removes all StarUML Unregistered bullshit from an exported svg file.
File.WriteAllText(Environment.GetCommandLineArgs().Last(),File.ReadAllText(Environment.GetCommandLineArgs().Last()).Replace("UNREGISTERED", ""));

