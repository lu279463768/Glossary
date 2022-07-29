import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.map.Map;
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
 *
 * @author Vivian Lu
 *
 */
public class GlossaryTest {

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
     * boundary case to test.
     */
    @Test
    public void test_generateElements_1() {
        /*
         * set up variables and call method under test
         */
        String s = "a";
        Set<Character> set = new Set1L<>();

        GlossaryFacility.generateElements(s, set);

        /*
         * Assert that values of variables match expectation
         */
        Set<Character> res = set.newInstance();
        res.add('a');
        assertEquals(res, set);

    }

    /**
     * routine case to test.
     */
    @Test
    public void test_generateElements_2() {
        /*
         * set up variables and call method under test
         */
        String s = " \t, ";
        Set<Character> set = new Set1L<>();

        GlossaryFacility.generateElements(s, set);

        /*
         * Assert that values of variables match expectation
         */
        Set<Character> res = set.newInstance();
        res.add(' ');
        res.add('\t');
        res.add(',');

        assertEquals(res, set);
    }

    /**
     * challenge case to test so that make sure this Set contains no duplicates.
     */
    @Test
    public void test_generateElements_3() {
        /*
         * set up variables and call method under test
         */
        String s = "abccccccccd";
        Set<Character> set = new Set1L<>();

        GlossaryFacility.generateElements(s, set);

        /*
         * Assert that values of variables match expectation
         */
        Set<Character> res = set.newInstance();
        res.add('a');
        res.add('b');
        res.add('c');
        res.add('d');
        assertEquals(res, set);
    }

    /**
     * boundary case to test,position starts at zero,get a word.
     */
    @Test
    public void test_nextWordOrSeparator_1() {
        /*
         * set up variables and call method under test
         */

        String text = "a    cd";
        int position = 0;
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        String string = GlossaryFacility.nextWordOrSeparator(text, position,
                separators);

        /*
         * Assert that values of variables match expectation
         */
        String res = "a";
        assertEquals(res, string);
    }

    /**
     * boundary case to test,position starts at zero,get a separator.
     */
    @Test
    public void test_nextWordOrSeparator_2() {
        /*
         * set up variables and call method under test
         */

        String text = " a,nn    cd";
        int position = 0;

        String s = " \t, ";
        Set<Character> set = new Set1L<>();

        GlossaryFacility.generateElements(s, set);

        Set<Character> separators = new Set1L<>();

        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        String string = GlossaryFacility.nextWordOrSeparator(text, position,
                separators);

        /*
         * Assert that values of variables match expectation
         */
        String res = " ";
        assertEquals(res, string);
    }

    /**
     * routine case to test,position is greater than 0.
     */
    @Test
    public void test_nextWordOrSeparator_3() {
        /*
         * set up variables and call method under test
         */

        String text = "a,nn    cd";
        int position = 1;
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        String string = GlossaryFacility.nextWordOrSeparator(text, position,
                separators);

        /*
         * Assert that values of variables match expectation
         */
        String res = ",";
        assertEquals(res, string);
    }

    /**
     * challenge case to test,get separators in a row.
     */
    @Test
    public void test_nextWordOrSeparator_4() {
        /*
         * set up variables and call method under test
         */

        String text = "a,nn    ,cd";
        final int constantFour = 4;
        int position = constantFour;
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        String string = GlossaryFacility.nextWordOrSeparator(text, position,
                separators);

        /*
         * Assert that values of variables match expectation
         */
        String res = "    ,";
        assertEquals(res, string);
    }

    /**
     * boundary case to test,just one element in q.
     */
    @Test
    public void test_removeMin_1() {
        /*
         * set up variables and call method under test
         */

        Queue<String> q = new Queue1L<>();
        q.enqueue("v");
        Comparator<String> order = new StringLT();

        String min = GlossaryFacility.removeMin(q, order);

        /*
         * Assert that values of variables match expectation
         */
        String res = "v";
        assertEquals(res, min);
    }

    /**
     * routine case to test.
     */
    @Test
    public void test_removeMin_2() {
        /*
         * set up variables and call method under test
         */

        Queue<String> q = new Queue1L<>();
        q.enqueue("v");
        q.enqueue("apple");
        q.enqueue("bench");

        Comparator<String> order = new StringLT();

        String min = GlossaryFacility.removeMin(q, order);

        /*
         * Assert that values of variables match expectation
         */
        String res = "apple";
        assertEquals(res, min);
    }

    /**
     * challenge case to test,when some strings have the same prefix.
     */
    @Test
    public void test_removeMin_3() {
        /*
         * set up variables and call method under test
         */

        Queue<String> q = new Queue1L<>();
        q.enqueue("hello");
        q.enqueue("apa");
        q.enqueue("paper");
        q.enqueue("app");

        Comparator<String> order = new StringLT();

        String min = GlossaryFacility.removeMin(q, order);

        /*
         * Assert that values of variables match expectation
         */
        String res = "apa";
        assertEquals(res, min);

    }

    /**
     * boundary case to test.
     */
    @Test
    public void test_sortQueue_1() {
        /*
         * set up variables and call method under test
         */

        Comparator<String> order = new StringLT();
        Queue<String> q = new Queue1L<>();
        q.enqueue("ha");

        GlossaryFacility.sortQueue(q, order);

        /*
         * Assert that values of variables match expectation
         */
        Queue<String> res = new Queue1L<>();
        res.enqueue("ha");

        assertEquals(res, q);
    }

    /**
     * routine case to test.
     */
    @Test
    public void test_sortQueue_2() {
        /*
         * set up variables and call method under test
         */

        Comparator<String> order = new StringLT();
        Queue<String> q = new Queue1L<>();
        q.enqueue("ha");
        q.enqueue("d");
        q.enqueue("paper");
        q.enqueue("app");

        GlossaryFacility.sortQueue(q, order);

        /*
         * Assert that values of variables match expectation
         */
        Queue<String> res = new Queue1L<>();
        res.enqueue("app");
        res.enqueue("d");
        res.enqueue("ha");
        res.enqueue("paper");

        assertEquals(res, q);
    }

    /**
     * challenge case to test,when some strings have same prefix in q.
     */
    @Test
    public void test_sortQueue_3() {
        /*
         * set up variables and call method under test
         */
        Comparator<String> order = new StringLT();
        Queue<String> q = new Queue1L<>();
        q.enqueue("ha");
        q.enqueue("app");
        q.enqueue("paper");
        q.enqueue("apa");

        GlossaryFacility.sortQueue(q, order);

        /*
         * Assert that values of variables match expectation
         */
        Queue<String> res = new Queue1L<>();
        res.enqueue("apa");
        res.enqueue("app");
        res.enqueue("ha");
        res.enqueue("paper");

        assertEquals(res, q);
    }

    /**
     * boundary case to test :no link at all.
     */
    @Test
    public void test_printDescription_1() {
        /*
         * set up variables and call method under test
         */
        SimpleWriter text = new SimpleWriter1L("result.txt");
        String description = "book";
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        Map<String, String> map = new Map1L<>();
        map.add("bag", "school");

        GlossaryFacility.printDescription(text, description, separators, map);

        /*
         * Assert that values of variables match expectation
         */

        SimpleWriter res = new SimpleWriter1L("expect.txt");
        res.println("<blockquote>");
        res.print(description);
        res.println("</blockquote>");

        SimpleReader r1 = new SimpleReader1L("result.txt");
        SimpleReader r2 = new SimpleReader1L("expect.txt");

        String actual = "";
        String expected = "";
        while (!r1.atEOS() && !r2.atEOS()) {
            actual += r1.nextLine();
            expected += r2.nextLine();
        }

        assertEquals(expected, actual);
        r1.close();
        r2.close();
        res.close();
    }

    /**
     * routine case to test :one link.
     */
    @Test
    public void test_printDescription_2() {
        /*
         * set up variables and call method under test
         */
        SimpleWriter text = new SimpleWriter1L("result.txt");
        String description = "bag has book";
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        Map<String, String> map = new Map1L<>();
        map.add("bag", "school");

        GlossaryFacility.printDescription(text, description, separators, map);

        /*
         * Assert that values of variables match expectation
         */

        SimpleWriter res = new SimpleWriter1L("expect.txt");
        res.println("<blockquote>");
        res.print("<a href=\"bag.html\">bag</a> has book");
        res.println("</blockquote>");

        SimpleReader r1 = new SimpleReader1L("result.txt");
        SimpleReader r2 = new SimpleReader1L("expect.txt");

        String actual = "";
        String expected = "";
        while (!r1.atEOS() && !r2.atEOS()) {
            actual += r1.nextLine();
            expected += r2.nextLine();
        }

        assertEquals(expected, actual);
        r1.close();
        r2.close();
        res.close();
    }

    /**
     * routine case to test :multiple links.
     */
    @Test
    public void test_printDescription_3() {
        /*
         * set up variables and call method under test
         */
        SimpleWriter text = new SimpleWriter1L("result.txt");
        String description = "bag has book";
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');

        Map<String, String> map = new Map1L<>();
        map.add("bag", "school");
        map.add("book", "school");

        GlossaryFacility.printDescription(text, description, separators, map);

        /*
         * Assert that values of variables match expectation
         */

        SimpleWriter res = new SimpleWriter1L("expect.txt");
        res.println("<blockquote>");
        res.print(
                "<a href=\"bag.html\">bag</a> has <a href=\"book.html\">book</a>");
        res.println("</blockquote>");

        SimpleReader r1 = new SimpleReader1L("result.txt");
        SimpleReader r2 = new SimpleReader1L("expect.txt");

        String actual = "";
        String expected = "";
        while (!r1.atEOS() && !r2.atEOS()) {
            actual += r1.nextLine();
            expected += r2.nextLine();
        }

        assertEquals(expected, actual);
        r1.close();
        r2.close();
        res.close();
    }
}
