import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * easy-to-maintain glossary facility.
 *
 * @author Vivian Lu
 *
 */
public final class GlossaryFacility {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private GlossaryFacility() {

    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    public static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        for (char ch : str.toCharArray()) {
            if (!charSet.contains(ch)) {
                charSet.add(ch);
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        String res = "";
        boolean isWord = !separators.contains(text.charAt(position));
        int i = position;
        if (isWord) {
            while (i < text.length() && !separators.contains(text.charAt(i))) {
                res = res + text.charAt(i);
                i++;
            }
        } else {
            while (i < text.length() && separators.contains(text.charAt(i))) {
                res += text.charAt(i);
                i++;
            }
        }
        return res;
    }

    /**
     * Override the compare method in Comparator so that {@code String}s can be
     * sorted in non-decreasing lexicographic order.
     *
     * @author Vivian
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Removes and returns the minimum value from {@code q} according to the
     * ordering provided by the {@code compare} method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to compare entries
     * @return the minimum value from {@code q}
     * @updates q
     * @requires <pre>
     * q /= empty_string  and
     *  [the relation computed by order.compare is a total preorder]
     * </pre>
     * @ensures <pre>
     * (q * <removeMin>) is permutation of #q  and
     *  for all x: string of character
     *      where (x is in entries (q))
     *    ([relation computed by order.compare method](removeMin, x))
     * </pre>
     */
    public static String removeMin(Queue<String> q, Comparator<String> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";

        String min = q.dequeue();
        q.enqueue(min);
        //first find and update the minimum
        for (String s : q) {
            if (order.compare(min, s) > 0) {
                min = s;
            }
        }

        Queue<String> temp = new Queue1L<>();
        for (String element : q) {
            if (element != min) {
                temp.enqueue(element);
            }
        }
        q.transferFrom(temp);

        return min;
    }

    /**
     * Sort this queue into non-decreasing lexicographic order.
     *
     * @param q
     * @param order
     */

    public static void sortQueue(Queue<String> q, Comparator<String> order) {
        assert order != null : "Violation of: order is not null";

        Queue<String> temp = new Queue1L<>();

        while (q.length() > 0) {

            String min = removeMin(q, order);
            //first find and update the minimum

            temp.enqueue(min);
            //so that every time this temp add element from the smallest to biggest
        }
        q.transferFrom(temp);
    }

    /**
     * Generate the header of index page.
     *
     * @param out
     */
    private static void indexPageHeader(SimpleWriter out) {
        out.println("<html><head><title>  Glossary </title>");
        out.println("</head><body>");
        out.println("<h1> Glossary Page</h1>");
        out.println("<hr>");
        out.println("<h2>Index</h2>");
        out.println("<ul>");
    }

    /**
     * the body and tail of index page.
     *
     * @param out
     * @param keys
     */
    private static void indexPageBodyAndTail(SimpleWriter out,
            Queue<String> keys) {

        for (String key : keys) {
            out.println(
                    "<li><a href = \"" + key + ".html\">" + key + "</a></li>");
        }
        out.println("    </ul>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * a term page's header.
     *
     * @param out
     * @param term
     */

    private static void generateSingleTermPageHeader(SimpleWriter out,
            String term) {
        out.println("<html> <head> <title>" + term + "</title><body>");
        out.println("<h2><b><i><font color =\"red\">" + term
                + "</font></i></b></h2>");
    }

    /**
     * a term page's tail.
     *
     * @param out
     *
     */
    private static void generateSingleTermPageTail(SimpleWriter out) {
        out.println("<hr>");
        out.println("<p>Return to <a href = \" index.html \">index</a>.</p>");
        out.println("</body></html>");
    }

    /**
     * if there is any other term inside this description,there also should have
     * a link print out of this inside term's link.
     *
     * @param separators
     * @param description
     * @param map
     * @param out
     */
    public static void printDescription(SimpleWriter out, String description,
            Set<Character> separators, Map<String, String> map) {
        int position = 0;
        out.println("<blockquote>");
        while (position < description.length()) {
            String token = nextWordOrSeparator(description, position,
                    separators);
            if (!separators.contains(token.charAt(0)) && map.hasKey(token)) {
                out.print("<a href=\"" + token + ".html\">" + token + "</a>");
            } else {
                out.print(token);
            }

            position += token.length();
        }
        out.println("</blockquote>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        Map<String, String> map = new Map1L<>();

        out.print("Please input the file's name: ");
        String fileName = in.nextLine();
        SimpleReader fileReader = new SimpleReader1L(fileName);

        out.print("Please input the name of the folder "
                + "where all the output files will be saved: ");
        String folderName = in.nextLine();

        //then read and process the info in this file to a map:
        while (!fileReader.atEOS()) {
            String item = fileReader.nextLine();
            String description = "";
            String line = fileReader.nextLine();

            if (fileReader.atEOS() && !line.isEmpty()) {
                description += line;
            }
            while (!fileReader.atEOS() && !line.isEmpty()) {
                description += line;
                line = fileReader.nextLine();
            }
            map.add(item, description);
        }

        /*
         * take all keys in the map and put them in a queue and sorted the queue
         * in non-decreasing lexicographic order
         */
        Queue<String> keys = new Queue1L<>();
        for (Pair<String, String> pair : map) {
            String word = pair.key();
            keys.enqueue(word);
        }
        /*
         * sort this queue in alphabetically order
         */
        Comparator<String> cs = new StringLT();
        sortQueue(keys, cs);

        final String separatorStr = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);

        for (Pair<String, String> p : map) {
            String description = p.value();
            String term = p.key();
            SimpleWriter singleTerm = new SimpleWriter1L(
                    folderName + "/" + term + ".html");
            generateSingleTermPageHeader(singleTerm, term);
            printDescription(singleTerm, description, separatorSet, map);
            generateSingleTermPageTail(singleTerm);
        }

        /*
         * construct indexPage:
         */
        SimpleWriter indexPage = new SimpleWriter1L(folderName + "/index.html");
        indexPageHeader(indexPage);
        indexPageBodyAndTail(indexPage, keys);

        indexPage.close();
        fileReader.close();
        in.close();
        out.close();
    }
}
