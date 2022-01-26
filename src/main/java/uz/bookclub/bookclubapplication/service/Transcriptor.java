package uz.bookclub.bookclubapplication.service;

import com.ibm.icu.text.Transliterator;

/**
 * created by Muhammad
 * on 26.08.2020
 */


public class Transcriptor {

    String CYRILLIC_TO_LATIN = "Cyrillic-Latin";

    public String getRu(String word) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(word);
        }
}
