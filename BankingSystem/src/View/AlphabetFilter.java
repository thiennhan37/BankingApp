/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Hi
 */
class AlphabetFilter extends DocumentFilter {
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) 
            throws BadLocationException {
        if (string == null) return;

        // Chỉ cho phép chữ cái và khoảng trắng
        if (isAlphabet(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
            throws BadLocationException {
        if (text == null) return;

        // Chỉ cho phép chữ cái và khoảng trắng
        if (isAlphabet(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isAlphabet(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // Cho phép: chữ cái (hoa/thường), khoảng trắng, dấu cách
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
}
