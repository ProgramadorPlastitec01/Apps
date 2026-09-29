package Method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractValues {

    public String extraerValor(String html, String regex) {
        if (html == null) return "";
        Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(html);
        return m.find() ? m.group(1).trim() : "";
    }
}