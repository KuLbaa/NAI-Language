import java.util.ArrayList;
import java.util.List;

public class Result {
    List<Language> languages;

    public Result(List<Language> languages) {
        this.languages = languages;
    }

    String examin(String testText) {
        String result = "";
        List<String> tmp = new ArrayList<>();
        for (Language l : languages) {

                if (l.test(testText)) {
                    tmp.add(l.getName());
                }

        }
        for (int i = 0; i < tmp.size(); i++) {
            if (i == tmp.size()-1) {
                result += "Zakwalifikowado do: " + tmp.get(i);
            } else {
                result += "Zakwalifikowado do: " + tmp.get(i) + "\n";
            }
        }
        return result;
    }
}
