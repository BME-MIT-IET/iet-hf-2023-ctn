// See https://aka.ms/new-console-template for more information


using System.Text;

namespace Program
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            wd = Path.GetFullPath(args[0]);
            //var res = getJavaFileNames(wd);


            var res = getJavaFiles(wd).OrderBy(p => p.Item1);

            StringBuilder sb = new StringBuilder();

            foreach (var file in res)
            {
                sb.AppendLine($"{file.Item1},{file.Item2},{file.Item3},{file.Item4}");
            }
            File.WriteAllText("fileok.csv", sb.ToString());

           /* StringBuilder sw = new StringBuilder();

            sw.AppendLine("rmdir /s /q buildout");
            sw.AppendLine("mkdir buildout");

            sw.Append("javac -d buildout/ -encoding utf8 ");

            foreach(var file in res)
            {
                sw.Append(file + " ");
            }

            sw.AppendLine("\nSTART cmd.exe /k \"cd buildout && java main\"");

            File.WriteAllText("buildandrun.bat", sw.ToString());*/
        }

        static string wd;

        static IEnumerable<string> getJavaFileNames(string folderPath)
        {
            List<string> files = new List<string>();
            
            addFiles(files, new DirectoryInfo(folderPath));
            return files;
        }

        static void addFiles(List<string> list, DirectoryInfo dir)
        {
            foreach (var item in dir.GetDirectories())
            {
                addFiles(list, item);
            }

            foreach (var item in dir.GetFiles())
            {
                if(item.Extension == ".java" || item.Extension == ".bat" || item.Extension == ".tncfs" || item.Extension == ".cfg" || item.Extension == ".txt")
                list.Add(Path.GetRelativePath(wd, item.FullName));
            }
        }
        static IEnumerable<(string, string, string, string)> getJavaFiles(string folderPath)
        {
            List<(string, string, string, string)> files = new List<(string, string, string, string)>();
            
            addItem(files, new DirectoryInfo(folderPath));
            return files;
        }

        static void addItem(List<(string, string, string, string)> list, DirectoryInfo dir)
        {
            foreach (var item in dir.GetDirectories())
            {
                addItem(list, item);
            }

            foreach (var item in dir.GetFiles())
            {
                if(item.Extension == ".java")
                list.Add((Path.GetRelativePath(wd, item.FullName), item.Length.ToString(), item.CreationTime.ToString(),  ""));
            }
        }
    }
}

