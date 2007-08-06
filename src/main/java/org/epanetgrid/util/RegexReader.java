/**
 * 
 */
package org.epanetgrid.util;

import java.io.BufferedReader;
import java.io.FilterReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class takes a reader and a pattern and removes lines
 * that don't match the pattern. Line terminators are converted to a \n.
 *
 * @see http://www.exampledepot.com/egs/java.util.regex/LineFilter2.html?l=rel
 * 
 * @author Thiago Emmanuel Pereira da Cunha Silva, thiago.manel@gmail.com
 * since 04/08/2007
 */
public class RegexReader extends FilterReader {

    /*
     * This variable holds the current line.
     * If null and emitNewline is false, a newline must be fetched.
     */ 
    private String curLine;

    /* 
     * This is the index of the first unread character in curLine.
     * If at any time curLineIx == curLine.length, curLine is set to null.
     */
    private int curLineIx;

    /*
     * If true, the newline at the end of curLine has not been returned.
     * It would have been more convenient to append the newline
     * onto freshly fetched lines. However, that would incur another
     * allocation and copy.
    */ 
    private boolean emitNewline;

    // Matcher used to test every line
    private final Matcher matcher;

    public RegexReader(BufferedReader in, String patternStr) {
        super(in);
        Pattern pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher("");
    }

    /* (non-Javadoc)
     * @see java.io.Reader#read(char[], int, int)
     */
    public int read(char cbuf[], int off, int len) throws IOException {
        // This overridden method fills cbuf with characters read from in.
    	
        // Fetch new line if necessary
        if (curLine == null && !emitNewline) {
            getNextLine();
        }

        // Return characters from current line
        if (curLine != null) {
            int num = Math.min(len, Math.min(cbuf.length-off,
                                         curLine.length()-curLineIx));
            // Copy characters from curLine to cbuf
            for (int i=0; i<num; i++) {
                cbuf[off++] = curLine.charAt(curLineIx++);
            }

            // No more characters in curLine
            if (curLineIx == curLine.length()) {
                curLine = null;

                // Is there room for the newline?
                if (num < len && off < cbuf.length) {
                    cbuf[off++] = '\n';
                    emitNewline = false;
                    num++;
                }
            }

            // Return number of character read
            return num;
        } else if (emitNewline && len > 0) {
            // Emit just the newline
            cbuf[off] = '\n';
            emitNewline = false;
            return 1;
        } else if (len > 0) {
            return -1;// No more characters left in input reader
        } else {
            return 0;// Client did not ask for any characters
        }
    }

    /**
     * Get next matching line
     * @throws IOException
     */
    private void getNextLine() throws IOException {
        curLine = ((BufferedReader)in).readLine();
        while (curLine != null) {
            matcher.reset(curLine);
            if (matcher.find()) {
                emitNewline = true;
                curLineIx = 0;
                return;
            }
            curLine = ((BufferedReader)in).readLine();
        }
        return;
    }

    /* (non-Javadoc)
     * @see java.io.Reader#ready()
     */
    public boolean ready() throws IOException {
        return curLine != null || emitNewline || in.ready();
    }
    
    /* (non-Javadoc)
     * @see java.io.Reader#markSupported()
     */
    public boolean markSupported() {
        return false;
    }

}

