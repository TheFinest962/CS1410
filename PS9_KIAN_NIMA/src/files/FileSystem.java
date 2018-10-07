package files;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Collection of methods for examining the file system.
 */
public class FileSystem
{
    /**
     * Runs the methods below.
     */
    public static void main (String[] args) throws URISyntaxException
    {
        // FOR THIS TO WORK, YOU'LL NEED TO UNZIP demo.zip INTO YOUR
        // PS9_LASTNAME_FIRSTNAME FOLDER. WHEN YOU ARE ASKED WHAT
        // FOLDER TO UNZIP INTO, SPECIFY PS9_LASTNAME_FIRSTNAME.

        // TO SEE THE NEW demo FOLDER IN ECLIPSE, REFRESH THE PROJECT.

        // IF YOU END UP WITH demo/demo, YOU'VE UNZIPPED TO THE WRONG
        // FOLDER.
        File demo = new File(System.getProperty("user.dir") + "/demo");

        // Should be 22
        System.out.println("countFiles: " + count(demo));

        // Should be 6205
        System.out.println("largestFile: " + largestFileSize(demo));

        // Should be true
        System.out.println("atLeast : " + atLeast(demo, 6000));

        // Should be false
        System.out.println("atLeast : " + atLeast(demo, 7000));

        // Should be 7
        System.out.println("depth: " + depth(demo));

        // Should be 12
        System.out.println("countFolders: " + countFolders(demo));

        // Should be true
        System.out.println("searchByName : " + searchByName(demo, "document1.1.1.1.1.1.txt"));

        // Should be true
        System.out.println("searchByName : " + searchByName(demo, "folder4"));

        // Should be false
        System.out.println("searchByName : " + searchByName(demo, "document.txt"));
    }

    /**
     * If fd is a file, returns 1. Otherwise, returns the number of files/directories that are contained, directly or
     * indirectly, inside of directory fd.
     */
    public static int count (File fd)
    {
        if (fd.isFile())
        {
            return 1;
        }
        else
        {
            // Here is a recursive accumulation loop
            int total = 0;

            if (fd.listFiles() != null)
            {
                for (File f : fd.listFiles())
                {
                    total = total + count(f);
                }
            }

            return total + 1;
        }
    }

    /**
     * If fd is a file, returns its size. Otherwise, returns the size of the largest file/directory that is contained,
     * directly or indirectly, inside of directory fd.
     */
    public static long largestFileSize (File fd)
    {
        if (fd.isFile())
        {
            return fd.length();
        }
        else
        {
            // Here is a recursive optimization loop
            long largestSoFar = fd.length();

            if (fd.listFiles() != null)
            {
                for (File f : fd.listFiles())
                {
                    largestSoFar = Math.max(largestSoFar, largestFileSize(f));
                }
            }

            return largestSoFar;
        }
    }

    /**
     * If fd is a file, reports whether its size is at least b bytes. Otherwise, reports whether directory fd contains,
     * either directly or indirectly, a file/directory whose size is at least b bytes.
     */
    public static boolean atLeast (File fd, long bytes)
    {
        if (fd.isFile())
        {
            return fd.length() >= bytes;
        }
        else
        {
            if (fd.length() >= bytes)
            {
                return true;
            }

            // Here is a recursive searching loop
            if (fd.listFiles() != null)
            {
                for (File f : fd.listFiles())
                {
                    if (atLeast(f, bytes))
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * Returns the depth of the file system rooted at fd. If fd is a file, the depth is 1. If fd is a directory, the
     * depth is one more than the maximum depth of file system components contained by fd.
     */
    public static int depth (File fd)
    {
        if (fd.isFile())
        {
            return 1;
        }

        else
        {
            int depth = 1;

            if (fd.listFiles() != null)
            {
                for (File f : fd.listFiles())
                {
                    if (!f.isFile())
                    {
                        depth = Math.max(depth, depth(f));
                    }

                }
            }

            return depth + 1;

        }
    }

    /**
     * Returns the number of folders contained in the file system rooted at fd.
     */
    public static int countFolders (File fd)
    {
        if (!fd.isDirectory())
        {
            return 0;
        }
        else
        {
            int folders = 0;

            if (fd.listFiles() != null)
            {
                for (File f : fd.listFiles())
                {
                    if (f.isDirectory())
                    {
                        folders = folders + countFolders(f);
                    }

                }
            }

            return folders + 1;
        }
    }

    /**
     * Returns whether the file system rooted at fd contains a file or folder with the specified name.
     */
    public static boolean searchByName (File fd, String name)
    {
        if (fd.isFile())
        {
            return (fd.getName().compareTo(name)) == 0;
        }
        else
        {
            if (fd.getName().compareTo(name) == 0)
            {
                return true;
            }

            if (fd.listFiles() != null)
            {
                for (File f : fd.listFiles())
                {
                    if (searchByName(f, name))
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
