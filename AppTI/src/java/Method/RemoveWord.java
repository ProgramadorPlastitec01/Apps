package Method;

public class RemoveWord {
    
    public static String RemoveLastWord(String textMod, String word) {
        String text = textMod.trim();
        int lastSpace = text.lastIndexOf(" ");
        
        if (lastSpace != -1) {
            String lastWord = text.substring(lastSpace + 1);
            
            if (lastWord.equals(word)) {
                return text.substring(0, lastSpace);
            }
        }
        return text;
    }
    
}
